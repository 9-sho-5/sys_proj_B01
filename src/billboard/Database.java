package billboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    // MySQLの接続設定
    private final static String DB_URL = "jdbc:sqlite:sys_proj_B01.sqlite3";

    /**
     * データベースの初期設定
     * データベースの作成、テーブルの作成を行う
     */
    public static void setUp() {

        // ライブラリのパス設定
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("set lib path");
        } catch (Exception e) {
            e.getMessage();
        }

        // データベースとの接続
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            // データベースの作成
            String sql = "sqlite3 sys_proj_B01.sqlite3;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // テーブルの作成
            sql = "CREATE TABLE Ranking (\n"
                    + "id integer primary key autoincrement,"
                    + "track_id text,"
                    + "track_name text,"
                    + "artist_name text,"
                    + "album_name text,"
                    + "album_image_url text);";
            try {
                stmt.executeUpdate(sql);
                System.out.println("Table created successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not connected Database...");
        }
    }

    /**
     * デーブルの削除
     */
    public static void drop() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            // データベースの削除
            String sql = "drop table Ranking;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("drop table Ranking successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("could not connected Database...");
        }
    }

    /**
     * データの挿入
     * 
     * @param track_name
     * @param artist_name
     * @param album_name
     * @param album_image_url
     */
    public static void insertData(String track_id, String track_name, String artist_name, String album_name,
            String album_image_url) {

        System.out.println("=== insert Data ===");

        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            // データの挿入
            String sql = String.format(
                    "insert into Ranking(track_id, track_name, artist_name, album_name, album_image_url) values ('%s', '%s', '%s', '%s', '%s');",
                    track_id, track_name, artist_name, album_name, album_image_url);
            try {
                stmt.executeUpdate(sql);
                System.out.println("inserted data completely!!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * データベースからデータ取得
     * @return
     */
    public static String getData() {

        // レスポンスの格納変数
        StringBuilder builder = null;

        // データベースとの接続
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            // データの取得
            String sql = String.format("select * from Ranking;");
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

        return builder.toString();
    }
}
