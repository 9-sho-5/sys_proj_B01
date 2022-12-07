package billboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/billboard/add_track")
public class AddTrackServlet {
    
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response) 
    throws ServletException,IOException{
        //paramsの取得
        String trackId = request.getParameter("trackId");
        String track_name = request.getParameter("track_name");
        String album_name = request.getParameter("album_name");
        String artist_name = request.getParameter("artist_name");
        String album_image_uri = request.getParameter("album_image_uri");

        // レスポンスの準備
        StringBuilder builder = new StringBuilder();
        Spotify spotify = Spotify.getInstance();

        //リクエストの作成
        String uris = "{\"uris\":[\"spotify:track:"+trackId+"\"]}";
        JSONObject object = new JSONObject(uris);

        //レスポンスの取得
        builder.append(spotify.addTrack(object));

        //jsonの格納
        String json = builder.toString();

        //レスポンスの送信
        response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.append(json);
		writer.flush();

        
        
    }
}
