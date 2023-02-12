package com.spotify.aouth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.Files.readString;

public class SpecBuilder {
    static Path path = Path.of("src/test/resources/tokens/access_token");
    static String accessToken;

    static {
        try {
            accessToken = readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .setBasePath("/v1/")
                .log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL).build();
    }
}
