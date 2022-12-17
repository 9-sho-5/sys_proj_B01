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

            // テーブルの作成
            String sql = "CREATE TABLE Ranking (\n"
                    + "id integer primary key autoincrement,"
                    + "track_id text,"
                    + "track_name text,"
                    + "artist_name text,"
                    + "album_name text,"
                    + "album_image_url text,"
                    + "access integer);";
            try {
                stmt.executeUpdate(sql);
                System.out.println("Table created successfully...");
            } catch (SQLException e) {
                System.out.println("Table already exists...");
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
    public static Boolean insertData(String track_id, String track_name, String artist_name, String album_name,
            String album_image_url) {

        System.out.println("=== insert Data ===");

        //データベースにtrackを保存できたかの判定フラグ
        Boolean track_inserted_flg = false;

        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            // track_idがデータベースになければ、データベースに保存する
            if(!exists(track_id)){
                System.out.println("New Data!!");
                // データの挿入
                String sql = String.format(
                        "insert into Ranking(track_id, track_name, artist_name, album_name, album_image_url, access) values ('%s', '%s', '%s', '%s', '%s', 1);",
                        track_id, track_name, artist_name, album_name, album_image_url);
                try {
                    stmt.executeUpdate(sql);
                    System.out.println("inserted data completely!!");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                track_inserted_flg = true;
            // track_idがデータベースにすでに存在する場合、accsessを+1する
            } else {
                System.out.println("This track is already exists...");
                // access数の更新
                String sql = String.format("update Ranking set access = access + 1 where track_id = '%s';", track_id);
                try {
                    stmt.executeUpdate(sql);
                    System.out.println("update data completely!!");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return track_inserted_flg;
    }

    /**
     * データベースからデータ取得
     * @return
     */
    public static String getData() {

        // レスポンスの格納変数
        StringBuilder builder = null;

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

            // データの取得
            String sql = String.format("select * from Ranking order by access desc limit 20;");
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
                    builder.append("\"track_id\":\"").append(rs.getString("track_id")).append("\",");
                    builder.append("\"track_name\":\"").append(rs.getString("track_name")).append("\",");
                    builder.append("\"artist_name\":\"").append(rs.getString("artist_name")).append("\",");
                    builder.append("\"album_name\":\"").append(rs.getString("album_name")).append("\",");
                    builder.append("\"album_image_url\":\"").append(rs.getString("album_image_url")).append("\",");
                    builder.append("\"access\":\"").append(rs.getInt("access")).append("\"");
                    builder.append("}");
                    builder.append(",");
                }
                char[] checkArray = new char[1];
                builder.getChars(builder.length() - 1, builder.length(), checkArray, 0);
                if(checkArray[0] == ','){
                    builder.delete(builder.length() - 1, builder.length());
                }
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

    /**
     * 指定のトラックがデータベースの存在するかどうかを判定
     * @param track_id
     * @return
     */
    public static Boolean exists(String track_id) {

        System.out.println("=== firstOrCreate ===");

        // 引数のtrack_idがデータベースに存在するかの判定フラグ
        Boolean flg = false;

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

            // 引数のtrack_idの数を取得するSQL文
            String sql = String.format("select count(track_id) from Ranking where track_id = '%s';", track_id);
            try {

                // 引数のtrack_idの数を取得
                ResultSet rs = stmt.executeQuery(sql);

                while(rs.next()){
                    // データベースにすでに存在していれば、flgをtrueにする
                    if(Integer.parseInt(rs.getString("count(track_id)")) != 0){
                        flg = true;
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flg;
    }
}
