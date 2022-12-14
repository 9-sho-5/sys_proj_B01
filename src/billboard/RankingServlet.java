package billboard;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ranking Servlet
 */
@WebServlet("/billboard/ranking")
public class RankingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		System.out.println("=== Get Data ===");

        // データベースの接続
        Database.setUp();

        // データベースからデータの取得
        String json = Database.getData();

        // JSONを文字列に変換
		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
        // JSONを返す
		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}