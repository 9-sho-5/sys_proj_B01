package billboard;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

@WebServlet("/billboard/add_track")
public class AddTrackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // paramsの取得
        String trackId = request.getParameter("trackId");
        String track_name = request.getParameter("track_name");
        String album_name = request.getParameter("album_name");
        String artist_name = request.getParameter("artist_name");
        String album_image_url = request.getParameter("album_image_url");

        // リクエストの作成
        JSONObject data = new JSONObject();
        JSONArray urisArray = new JSONArray();
        urisArray.put(trackId);
        data.put("uris", urisArray);

        // データベースの接続
        Database.setUp();
        // データベースへのデータ追加
        Database.insertData(trackId, track_name, artist_name, album_name, album_image_url);

        // レスポンスの取得
        Spotify spotify = Spotify.getInstance();
        try {
            spotify.crateAccessToken();
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String json = spotify.addTrack(data);

        // レスポンスの送信
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.append(json);
        writer.flush();

    }
}
