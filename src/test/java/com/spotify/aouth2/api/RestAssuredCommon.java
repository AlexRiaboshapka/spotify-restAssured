package com.spotify.aouth2.api;

import com.spotify.aouth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.aouth2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.aouth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class RestAssuredCommon {

    public static Response post(String path, String token, Object payload) {
        return given(getRequestSpecification())
                .header("Authorization", "Bearer " + token)
                .body(payload)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }
    public static Response get(String path, String accessToken ) {
        return given()
                .spec(getRequestSpecification())
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }


    public static Response put(String path, String accessToken, Object payload) {
        return given(getRequestSpecification())
                .header("Authorization", "Bearer " + accessToken)
                .body(payload)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }
}