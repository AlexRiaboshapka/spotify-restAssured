package com.spotify.aouth2.api.application.api;

import com.spotify.aouth2.api.RestAssuredCommon;
import com.spotify.aouth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.aouth2.api.TokenManager.getToken;

public class PlayListApi {

    public static Response post(Playlist playlist) {
        return RestAssuredCommon
                .post("/users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists", getToken(), playlist);
    }

    public static Response post(String token, Playlist playlist) {
        return RestAssuredCommon
                .post("/users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists", token, playlist);
    }

    public static Response get(String id) {
        return RestAssuredCommon.get("/playlists/" + id, getToken());
    }

    public static Response put(String id, Playlist playlist) {
        return RestAssuredCommon.put("/playlists/" + id, getToken(), playlist);
    }
}
