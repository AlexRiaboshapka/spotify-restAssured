package com.spotify.aouth2.api;

public enum StatusCode {
    CODE_200(200,""),
    CODE_201(201,""),
    CODE_400(400,"Missing required field: name"),
    CODE_401(401,"Invalid access token");
    public int code;
    public String msg;
    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
