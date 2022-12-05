package billboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    // MySQLの接続設定
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "";

    /**
     * データベースの初期設定
     * データベースの作成、テーブルの作成を行う
     */
    public static void setUp() {
        // データベースとの接続
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            // データベースの作成
            String sql = "CREATE DATABASE sys_proj_B01;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // 作成したデータベースの使用
            sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
                System.out.println("use Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // テーブルの作成
            sql = "CREATE TABLE Ranking (\n"
                    + "id INT(11) AUTO_INCREMENT NOT NULL, "
                    + "track_name VARCHAR(255) NOT NULL ,"
                    + "artist_name VARCHAR(255) NOT NULL ,"
                    + "album_name VARCHAR(255) NOT NULL ,"
                    + "album_image_url VARCHAR(255) NOT NULL ,"
                    + "PRIMARY KEY (id))";
            try {
                stmt.executeUpdate(sql);
                System.out.println("Table created successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * データベースの削除
     */
    public static void drop() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            System.out.println("connected Database successfully...");

            // データベースの削除
            String sql = "drop DATABASE sys_proj_B01;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * データベースの存在確認
     * @return
     */
    public static boolean check() {
        boolean flg = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // データベースの作成
            String sql = "CREATE DATABASE sys_proj_B01;";
            try {
                stmt.executeUpdate(sql);
                System.out.println("created Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // 作成したデータベースの使用
            sql = "USE sys_proj_B01; ";
            try {
                stmt.executeUpdate(sql);
                System.out.println("use Database successfully...");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // データベースの存在確認
            sql = "select database()";
            try {
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    String name = rs.getString("database()");
                    System.out.println(name + " is selected");
                }
                flg = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flg;
    }

    /**
     * データの挿入
     * @param track_name
     * @param artist_name
     * @param album_name
     * @param album_image_url
     */
    public static void insertData(String track_name, String artist_name, String album_name, String album_image_url) {

        System.out.println("=== insert Data ===");

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

            // データの挿入
            sql = String.format("insert into Ranking values (0, '%s', '%s', '%s', '%s');", track_name, artist_name, album_name, album_image_url);
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
}
