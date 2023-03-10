package com.spotify.aouth2.api;

import com.spotify.aouth2.utils.ConfigLoader;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;

import static com.spotify.aouth2.api.RestAssuredCommon.postAccount;
import static java.nio.file.Files.readString;

public class TokenManager {
    private static final Path path = Path.of("src/test/resources/tokens/refresh_token");
    private static final String refresh_token;
    private static String access_token;
    private static Instant expires_at;

    static {
        try {
            refresh_token = readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static String getToken() {
        try {
            if (access_token == null || Instant.now().isAfter(expires_at)) {
                System.out.println("New token is generated");
                Response response = tokenRefresh();
                access_token = response.path("access_token");
                int expiresInSeconds = response.path("expires_in");
                expires_at = Instant.now().plusSeconds(expiresInSeconds - 60);
            } else {
                System.out.println("Token is still valid");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to renew token");
        }
        return access_token;
    }

    private static Response tokenRefresh() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", refresh_token);
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());

        Response response = postAccount(formParams);

        if (response.statusCode() != 200) {
            throw new RuntimeException("Token refresh failed");
        } else {
            return response;
        }
    }
}
