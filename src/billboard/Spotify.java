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

public class Spotify {
     
    // 環境変数の読み込み準備
    Dotenv dotenv = Dotenv.configure().load();

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
		"user-top-read",
	};
    // ユーザーのAPI認証後に取得するcodeの格納変数
    private String code = null;
    // アクセストークンの格納変数
	private String accessToken = null;
    
    /**
     * ユーザー認証URLの発行メソッド
     */
    public String getAuthorizationUrl() throws UnsupportedEncodingException {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s", authorizeUrl, clientId, URLEncoder.encode(redirectUri, "utf-8"), String.join(" ", scope), new SecureRandom());
    }
}

