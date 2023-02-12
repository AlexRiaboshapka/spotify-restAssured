package com.spotify.aouth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .addHeader("Content-Type", "application/json")
                .setBasePath("/v1/")
                .log(LogDetail.ALL).build();
    }

    public static RequestSpecification getAccountRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://accounts.spotify.com/api/token")
                .setContentType(ContentType.URLENC)
                .log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL).build();
    }
}
