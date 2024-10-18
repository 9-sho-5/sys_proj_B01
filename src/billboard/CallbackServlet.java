package billboard;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

@WebServlet("/billboard/callback")
public final class CallbackServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // cookieの格納変数
    Cookie cookie = null;
    // refresh_tokenの格納変数
    String refresh_token = null;
    // Spotifyの格納変数
    Spotify spotify = Spotify.getInstance();
    ;

    // callbackからのレスポンス(code)を取得
    String code = (request.getParameter("code") == null) ? "" : (String) request.getParameter("code");

    if (code != null) {
      // codeのセット
      spotify.setCode(code);
      // cookieにログイン状態の設定
      cookie = new Cookie("loginStatus", "loggedIn");
    } else {
      // cookieにログイン状態の設定
      cookie = new Cookie("loginStatus", "notLoggedIn");
    }

    try {
      // アクセストークンの生成
      refresh_token = spotify.crateAccessToken();
    } catch (UnirestException e) {
      e.printStackTrace();
    }

    // レスポンスにcookieの追加
    response.addCookie(cookie);
    request.setCharacterEncoding("UTF-8");

    StringBuilder builder = new StringBuilder();
    builder.append('{');
    builder.append("\"refresh_token\":\"").append(refresh_token).append("\"");
    builder.append('}');
    String json = builder.toString();

    Writer writer = response.getWriter();
    writer.append(json);
    writer.flush();
  }

}
