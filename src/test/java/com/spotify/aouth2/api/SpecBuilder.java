package com.spotify.aouth2.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.aouth2.api.ApiRoute.*;

public class SpecBuilder {

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(System.getProperty("BASE_URL") == null ? BASE_URL: System.getProperty("BASE_URL"))
                .addHeader("Content-Type", "application/json")
                .setBasePath(BASE_PATH)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL).build();
    }

    public static RequestSpecification getAccountRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(System.getProperty("BASE_URL_ACCOUNT") == null ? BASE_URL_ACCOUNT: System.getProperty("BASE_URL_ACCOUNT"))
                .setBasePath(API + TOKEN)
                .setContentType(ContentType.URLENC)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL).build();
    }
}
