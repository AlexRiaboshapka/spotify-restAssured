package com.spotify.aouth2.api.application.api;

import com.spotify.aouth2.api.RestAssuredCommon;
import com.spotify.aouth2.pojo.Playlist;
import com.spotify.aouth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.aouth2.api.ApiRoute.PLAYLIST;
import static com.spotify.aouth2.api.ApiRoute.USERS;
import static com.spotify.aouth2.api.TokenManager.getToken;

public class PlayListApi {
    @Step
    public static Response post(Playlist playlist) {
        return RestAssuredCommon
                .post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLIST, getToken(), playlist);
    }
    @Step
    public static Response post(String token, Playlist playlist) {
        return RestAssuredCommon
                .post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLIST, token, playlist);
    }
    @Step
    public static Response get(String id) {
        return RestAssuredCommon.get(PLAYLIST + "/" + id, getToken());
    }
    @Step
    public static Response put(String id, Playlist playlist) {
        return RestAssuredCommon.put(PLAYLIST + "/" + id, getToken(), playlist);
    }
}
