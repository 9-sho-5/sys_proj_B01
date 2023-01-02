package billboard;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/billboard/spotify")
public class ConnectSpotifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Spotify spotify = Spotify.getInstance();
		String authorizationUrl = spotify.getAuthorizationUrl();

		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"AuthorizationUrl\":\"").append(authorizationUrl).append("\"");
		builder.append('}');
		String json = builder.toString();

		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
    
}
