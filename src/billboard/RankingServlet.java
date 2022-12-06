package billboard;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    // MySQLの接続設定
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		System.out.println("=== Get Data ===");

        // レスポンスの格納変数
        StringBuilder builder = null;

        // データベースとの接続
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // 作成したデータベースの使用
            String sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
                System.out.println("use Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // データの取得
            sql = String.format("select * from Ranking;");
            try {

                // データベースからデータの全件取得
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("get data completely!!");
                // レスポンスの生成準備
                builder = new StringBuilder();
                // JSON形式でレスポンスデータを構成
                builder.append("{\"data\":");
                builder.append('[');
                while(rs.next()){
                    builder.append('{');
                    builder.append("\"track_name\":\"").append(rs.getString("track_name")).append("\",");
                    builder.append("\"artist_name\":\"").append(rs.getString("artist_name")).append("\",");
                    builder.append("\"album_name\":\"").append(rs.getString("album_name")).append("\",");
                    builder.append("\"album_image_url\":\"").append(rs.getString("album_image_url")).append("\"");
                    builder.append("}");
                    builder.append(",");
                }
                builder.delete(builder.length() - 1, builder.length());
                builder.append(']');
                builder.append('}');
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // JSONを文字列に変換
		String json = builder.toString();
		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
        // JSONを返す
		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}