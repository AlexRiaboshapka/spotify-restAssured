package com.spotify.aouth2.api;

import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import static com.spotify.aouth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static java.nio.file.Files.readString;

public class TokenManager {
    static Path path = Path.of("src/test/resources/tokens/refresh_token");
    static String refresh_token;

    static {
        try {
            refresh_token = readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String tokenRefresh() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "refresh_token");
        formParams.put("refresh_token", refresh_token);
        formParams.put("client_id", "1790330e790846e491848a3302ec071a");
        formParams.put("client_secret", "230a41908d8e44cfb7cfd8a687e3244a");

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(formParams)
                .log().all()
                .when()
                .post("https://accounts.spotify.com/api/token")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();

        if (response.statusCode() != 200) {
            throw new RuntimeException("Token refresh failed");
        } else {
            return response.jsonPath().getString("access_token");
        }
    }
}
