package com.spotify.aouth2.tests;

import com.spotify.aouth2.api.application.api.PlayListApi;
import com.spotify.aouth2.pojo.Playlist;
import com.spotify.aouth2.pojo.PlaylistError;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PlayListTests {
    private static String id;
    private SoftAssertions softly;

    @BeforeTest
    public void beforeMethod() {
        softly = new SoftAssertions();
    }


    @Test
    public void createPlayList() {

        Playlist playlist = new Playlist()
                .setName("My New Playlist")
                .setDescription("New Playlist description")
                .setPublic(false);

        Response postResponse = PlayListApi.post(playlist);
        postResponse.then().assertThat().statusCode(201);
        Playlist playlistResponse = postResponse.as(Playlist.class);

        id = playlistResponse.getId();

        softly.assertThat(playlistResponse.getPublic()).isEqualTo(playlist.getPublic());
        softly.assertThat(playlistResponse.getName()).isEqualTo(playlist.getName());
        softly.assertThat(playlistResponse.getDescription()).isEqualTo(playlist.getDescription());
        softly.assertAll();
    }

    @Test
    public void getPlayLists() {
        Response getResponse =
                PlayListApi.get(id);
        getResponse.then().assertThat().statusCode(200);
    }

    @Test
    public void updateThePlayList() {

        Playlist playlistUpdated = new Playlist()
                .setName("My Updated Playlist123")
                .setDescription("Updated Playlist123 description")
                .setPublic(false);
        PlayListApi
                .put(id, playlistUpdated)
                .then().assertThat().statusCode(200);
    }

    @Test(dependsOnMethods = {"updateThePlayList"})
    public void getPlayListsAfterUpdate() {

        Response getResponseAfterUpdate = PlayListApi.get(id);
        getResponseAfterUpdate.then().assertThat().statusCode(200);
        Playlist playlistResponseAfterUpdate = getResponseAfterUpdate.as(Playlist.class);

        softly.assertThat(playlistResponseAfterUpdate.getPublic()).isEqualTo(false);
        softly.assertThat(playlistResponseAfterUpdate.getName()).isEqualTo("My Updated Playlist123");
        softly.assertThat(playlistResponseAfterUpdate.getDescription())
                .isEqualTo("Updated Playlist123 description");
        softly.assertAll();
    }

    @Test
    public void failedToCreatePlayListWithNoName() {

        Playlist playlistWithNoName = new Playlist()
                .setDescription("New Playlist123 description")
                .setPublic(false);

        Response responsePlaylistError = PlayListApi.post(playlistWithNoName);
        responsePlaylistError.then().assertThat().statusCode(400);
        PlaylistError playlistError = responsePlaylistError.as(PlaylistError.class);
        softly.assertThat(playlistError.getError().getStatus()).isEqualTo(400);
        softly.assertThat(playlistError.getError().getMessage())
                .isEqualTo("Missing required field: name");
        softly.assertAll();

    }

    @Test
    public void failedToCreatePlayListWithExpiredToken() {

        Playlist playlist = new Playlist()
                .setName("My New Playlist123")
                .setDescription("New Playlist123 description")
                .setPublic(false);
        String accessToke = "12345";
        Response responsePlaylistError = PlayListApi.post(accessToke, playlist);
        PlaylistError playlistError = responsePlaylistError.as(PlaylistError.class);
        softly.assertThat(playlistError.getError().getStatus()).isEqualTo(401);
        softly.assertThat(playlistError.getError().getMessage()).isEqualTo("Invalid access token");
        softly.assertAll();
    }
}
