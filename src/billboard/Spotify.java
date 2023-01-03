package billboard;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.SecureRandom;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import io.github.cdimascio.dotenv.Dotenv;

public final class Spotify {

    // 環境変数の読み込み準備
    Dotenv dotenv = Dotenv.configure().load();

    // Spotifyシングルトンインスタンスの取得
    private static Spotify spotify = new Spotify();

    // 環境変数からの読み込み
    private final String clientId = dotenv.get("CLIENT_ID");
    private final String clientSecret = dotenv.get("CLIENT_SECRET");
    private final String redirectUri = dotenv.get("REDIRECT_URI");
    private final String authorizeUrl = dotenv.get("AUTHORIZE_URL");
    private final String apiEndpoint = dotenv.get("API_ENDPOINT");
    private final String[] scope = {
            "playlist-read-private",
            "playlist-modify-private",
            "playlist-read-collaborative",
            "playlist-modify-public",
            "user-read-email",
            "user-read-private",
            "user-modify-playback-state",
            "user-read-playback-state",
            "user-read-currently-playing",
            "user-read-recently-played",
            "user-read-playback-position",
            "user-top-read"
    };
    // ユーザーのAPI認証後に取得するcodeの格納変数
    private String code;
    // アクセストークンの格納変数
    private String accessToken;

    /**
     * コンストラクタ
     */
    private Spotify() {
        code = null;
        accessToken = null;
    }

    // シングルトンインスタンスのゲッター
    public static Spotify getInstance() {
        return spotify;
    }

    /**
     * ユーザー認証URLの発行メソッド
     */
    public String getAuthorizationUrl() throws UnsupportedEncodingException {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s", this.authorizeUrl,
                this.clientId, URLEncoder.encode(this.redirectUri, "utf-8"), String.join(" ", this.scope),
                new SecureRandom());
    }

    /**
     * アクセストークンの生成メソッド
     * 
     * @throws UnirestException
     * @throws UnsupportedEncodingException
     */
    public String crateAccessToken() throws UnirestException, UnsupportedEncodingException {
        
        String refresh_token = null;
        
        // HTTPクライアントの準備
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = null;
        if (dotenv.get("API_TEST_REFRESH_TOKEN").equals("")) {
            // HTTPリクエストの送信(初期アクセストークン取得)
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .setHeader("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(
                                    (this.clientId + ':' + this.clientSecret).getBytes(StandardCharsets.UTF_8)))
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(String.format("grant_type=%s&code=%s&redirect_uri=%s",
                            URLEncoder.encode("authorization_code", "UTF-8"), URLEncoder.encode(this.code, "UTF-8"),
                            URLEncoder.encode(this.redirectUri, "UTF-8"))))
                    .build();
        } else {
            // HTTPリクエストの送信(リフレッシュトークンでのアクセストークン取得)
            request = HttpRequest.newBuilder()
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .setHeader("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(
                                    (this.clientId + ':' + this.clientSecret).getBytes(StandardCharsets.UTF_8)))
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .POST(BodyPublishers.ofString(String.format("grant_type=%s&refresh_token=%s",
                            URLEncoder.encode("refresh_token", "UTF-8"),
                            URLEncoder.encode(dotenv.get("API_TEST_REFRESH_TOKEN"), "UTF-8"))))
                    .build();
        }
        try {
            // リクエストを送信
            var response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            // レスポンスボディからaccess_tokenの取得
            JSONObject json = new JSONObject(response.body());
            // アクセストークンの格納
            this.accessToken = json.getString("access_token");
            // リフレッシュトークン取得時のみ
            if (dotenv.get("API_TEST_REFRESH_TOKEN").equals("")) {
                refresh_token = json.getString("refresh_token");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // リフレッシュトークンを返す(.env記述用)
        return refresh_token;
    }

    /**
     * 楽曲検索メソッド
     * 
     * @param keyword
     * @return
     * @throws UnsupportedEncodingException
     */
    public String search(String keyword) throws UnsupportedEncodingException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint
                        + String.format("/search?q=%s&type=%s&limit=25", URLEncoder.encode(keyword, "utf-8"), URLEncoder.encode("track", "utf-8"))))
                .setHeader("Authorization", "Bearer " + this.accessToken)
                .setHeader("Content-Type", "application/json")
                .GET()
                .build();

        // レスポンスの格納変数
        StringBuilder builder = null;
        try {
            // リクエストを送信
            var response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            // レスポンスボディからaccess_tokenの取得
            JSONObject json = new JSONObject(response.body());
            // tracksの取得
            JSONObject tracks = json.getJSONObject("tracks");
            // tracksの中の楽曲データの取得
            JSONArray data = tracks.getJSONArray("items");

            // レスポンスの生成準備
            builder = new StringBuilder();
            // JSON形式でレスポンスデータを構成
            builder.append("{\"data\":");
            builder.append('[');
            for (int i = 0; i < data.length(); i++) {
                builder.append('{');
                builder.append("\"album_image_url\":\"").append(
                        data.getJSONObject(i).getJSONObject("album").getJSONArray("images").getJSONObject(0).get("url"))
                        .append("\",");
                builder.append("\"album_name\":\"").append(data.getJSONObject(i).getJSONObject("album").get("name"))
                        .append("\",");
                builder.append("\"artist_name\":\"")
                        .append(data.getJSONObject(i).getJSONArray("artists").getJSONObject(0).get("name"))
                        .append("\",");
                builder.append("\"track_name\":\"").append(data.getJSONObject(i).get("name")).append("\",");
                builder.append("\"track_id\":\"").append(data.getJSONObject(i).get("uri")).append("\"");
                builder.append("}");
                if (i != data.length() - 1) {
                    builder.append(",");
                }
            }
            builder.append(']');
            builder.append('}');

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        // JSON形式作ったレスポンスデータを返す
        return builder.toString();
    }

    /**
     * 楽曲追加メソッド
     * 
     * @param playlistId
     * @param uris
     * @return
     * @throws UnsupportedEncodingException
     */
    public String addTrack(JSONObject uris) throws UnsupportedEncodingException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint + String.format("/playlists/%s/tracks", dotenv.get("PLAYLIST_ID"))))
                .setHeader("Authorization", "Bearer " + this.accessToken)
                .setHeader("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(uris.toString()))
                .build();

        StringBuilder builder = null;
        try {
            // リクエストを送信
            var response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            // レスポンスボディからレスポンスの取得
            JSONObject json = new JSONObject(response.body());

            builder = new StringBuilder();
            builder.append('{');
            builder.append("\"snapshot\":").append(json).append("");
            builder.append('}');

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * codeのセッター
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
