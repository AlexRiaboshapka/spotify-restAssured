package com.spotify.aouth2.utils;

import com.github.javafaker.Faker;

public class FakerUtils {
    public static String genName(){
        Faker faker = new Faker();
        return "Playlist: " + faker.regexify("[A-Za-z0-9,_ -]{20}");
    }

    public static String genDescription(){
        Faker faker = new Faker();
        return "Description: " + faker.regexify("[A-Za-z0-9,_ -!@#$%^&*()]{30}");
    }
}
