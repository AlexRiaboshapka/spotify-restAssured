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
        Playlist playlist = playlist("My New Playlist", "New Playlist description", false);
        Response postResponse = PlayListApi.post(playlist);
        postResponse.then().assertThat().statusCode(201);
        Playlist playlistResponse = postResponse.as(Playlist.class);
        id = playlistResponse.getId();
        assertPlaylist(playlist, playlistResponse);
    }

    @Test
    public void getPlayLists() {
        Response getResponse =
                PlayListApi.get(id);
        getResponse.then().assertThat().statusCode(200);
    }

    @Test
    public void updateThePlayList() {
        Playlist playlistUpdated = playlist("My Updated Playlist123",
                "Updated Playlist123 description", false);
        PlayListApi
                .put(id, playlistUpdated)
                .then().assertThat().statusCode(200);
    }

    @Test(dependsOnMethods = {"updateThePlayList"})
    public void getPlayListsAfterUpdate() {
        Playlist expectedPlayList =
                playlist("My Updated Playlist123", "Updated Playlist123 description", false);

        Response getResponseAfterUpdate = PlayListApi.get(id);
        getResponseAfterUpdate.then().assertThat().statusCode(200);
        Playlist playlistResponseAfterUpdate = getResponseAfterUpdate.as(Playlist.class);
        assertPlaylist(expectedPlayList, playlistResponseAfterUpdate);
    }

    @Test
    public void failedToCreatePlayListWithNoName() {
        Playlist playlistWithNoName = new Playlist()
                .setDescription("New Playlist123 description")
                .setPublic(false);

        Response responsePlaylistError = PlayListApi.post(playlistWithNoName);
        responsePlaylistError.then().assertThat().statusCode(400);
        assertErrorResponse(responsePlaylistError.as(PlaylistError.class),
                400, "Missing required field: name");
    }

    @Test
    public void failedToCreatePlayListWithExpiredToken() {
        Playlist playlist = playlist("My New Playlist123", "New Playlist123 description", false);
        String accessToke = "12345";
        Response responsePlaylistError = PlayListApi.post(accessToke, playlist);
        assertErrorResponse(responsePlaylistError.as(PlaylistError.class),
                401, "Invalid access token");
    }

    private void assertPlaylist(Playlist playlist, Playlist playlistResponse) {
        softly.assertThat(playlistResponse.getPublic()).isEqualTo(playlist.getPublic());
        softly.assertThat(playlistResponse.getName()).isEqualTo(playlist.getName());
        softly.assertThat(playlistResponse.getDescription()).isEqualTo(playlist.getDescription());
        softly.assertAll();
    }

    public Playlist playlist(String name, String description, Boolean _public) {
        return new Playlist()
                .setName(name)
                .setDescription(description)
                .setPublic(_public);
    }

    private void assertErrorResponse(PlaylistError playlistError, int expected, String expected1) {
        softly.assertThat(playlistError.getError().getStatus()).isEqualTo(expected);
        softly.assertThat(playlistError.getError().getMessage()).isEqualTo(expected1);
        softly.assertAll();
    }
}
