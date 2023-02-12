package com.spotify.aouth2.api.application.api;

import com.spotify.aouth2.pojo.Playlist;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Path;

import static com.spotify.aouth2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.aouth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static java.nio.file.Files.readString;

public class PlayListApi {
    static Path path = Path.of("src/test/resources/tokens/access_token");
    static String accessToken;

    static {
        try {
            accessToken = readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Response post(Playlist playlist) {
        return given(getRequestSpecification())
                .header("Authorization", "Bearer " + accessToken)
                .body(playlist)
                .when()
                .post("users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response post(String token, Playlist playlist) {
        return given(getRequestSpecification())
                .header("Authorization", "Bearer " + token)
                .body(playlist)
                .when()
                .post("users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response get(String id) {
        return given()
                .spec(getRequestSpecification())
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/playlists/" + id)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response put(String id, Playlist playlist) {
        return given(getRequestSpecification())
                .header("Authorization", "Bearer " + accessToken)
                .body(playlist)
                .when()
                .put("playlists/" + id)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }
}
