package com.spotify.aouth2.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.aouth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestAssuredCommon {

    public static Response post(String path, String token, Object payload) {
        return given(getRequestSpecification())
                .auth().oauth2(token)
                .body(payload)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response get(String path, String accessToken) {
        return given()
                .spec(getRequestSpecification())
                .auth().oauth2(accessToken)
                .when()
                .get(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }


    public static Response put(String path, String accessToken, Object payload) {
        return given(getRequestSpecification())
                .auth().oauth2(accessToken)
                .body(payload)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response postAccount(HashMap<String, String> formParams) {
        return given(getAccountRequestSpecification())
                .formParams(formParams)
                .when()
                .post()
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }
}
