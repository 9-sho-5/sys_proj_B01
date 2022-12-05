package billboard;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

@WebServlet("/billboard/callback")
public class CallbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // cookieの格納変数
        Cookie cookie = null;
        // Spotifyの格納変数
        Spotify spotify = Spotify.getInstance();;
        
        // callbackからのレスポンス(code)を取得
        String code = (request.getParameter("code") == null) ? "" : (String) request.getParameter("code");
        
        if (code != null){
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
            spotify.crateAccessToken();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        
        // レスポンスにcookieの追加
        response.addCookie(cookie);
        request.setCharacterEncoding("UTF-8");
		response.sendRedirect("index.html");
		
	}
    
}
