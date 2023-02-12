package com.spotify.aouth2.api.application.api;

import com.spotify.aouth2.api.RestAssuredCommon;
import com.spotify.aouth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.aouth2.api.ApiRoute.PLAYLIST;
import static com.spotify.aouth2.api.ApiRoute.USERS;
import static com.spotify.aouth2.api.TokenManager.getToken;

public class PlayListApi {

    public static Response post(Playlist playlist) {
        return RestAssuredCommon
                .post(USERS + "/31v5r5wfy4gnsl7c5eujg2fpf4oq" + PLAYLIST, getToken(), playlist);
    }

    public static Response post(String token, Playlist playlist) {
        return RestAssuredCommon
                .post(USERS + "/31v5r5wfy4gnsl7c5eujg2fpf4oq" + PLAYLIST, token, playlist);
    }

    public static Response get(String id) {
        return RestAssuredCommon.get(PLAYLIST + "/" + id, getToken());
    }

    public static Response put(String id, Playlist playlist) {
        return RestAssuredCommon.put(PLAYLIST + "/" + id, getToken(), playlist);
    }
}
