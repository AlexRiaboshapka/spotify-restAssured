package com.spotify.aouth2.api.application.api;

import com.spotify.aouth2.api.RestAssuredCommon;
import com.spotify.aouth2.pojo.Playlist;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Path;

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
        return RestAssuredCommon
                .post("/users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists", accessToken, playlist);
    }

    public static Response post(String token, Playlist playlist) {
        return RestAssuredCommon
                .post("/users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists", token, playlist);
    }

    public static Response get(String id) {
        return RestAssuredCommon.get("/playlists/" + id, accessToken);
    }

    public static Response put(String id, Playlist playlist) {
        return RestAssuredCommon.put("/playlists/" + id, accessToken, playlist);
    }
}
