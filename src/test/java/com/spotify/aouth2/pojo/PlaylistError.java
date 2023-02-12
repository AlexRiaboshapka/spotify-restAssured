package com.spotify.aouth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)


public class PlaylistError {

    @JsonProperty("error")
    private PlaylistInnerError error;

    @JsonProperty("error")
    public PlaylistInnerError getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(PlaylistInnerError error) {
        this.error = error;
    }

}
