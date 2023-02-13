package com.spotify.aouth2.tests;

import com.spotify.aouth2.api.StatusCode;
import com.spotify.aouth2.api.application.api.PlayListApi;
import com.spotify.aouth2.pojo.Playlist;
import com.spotify.aouth2.pojo.PlaylistError;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static com.spotify.aouth2.utils.FakerUtils.genDescription;
import static com.spotify.aouth2.utils.FakerUtils.genName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlayListTests extends BaseTest {
     private static String id;
    private SoftAssertions softly;

    @Step
    private static void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("ID1234")
    @Issue("PR-123")
    @Description("Alex creates new playlist")
    @Test(description = "Create a play list")
    @Story("Alex creates his playlist")
    public void createPlayList() {
        Playlist playlist = playlistBuilder(genName(), genDescription(), false);
        Response postResponse = PlayListApi.post(playlist);
        assertStatusCode(postResponse.statusCode(), StatusCode.CODE_201);
        Playlist playlistResponse = postResponse.as(Playlist.class);
        id = playlistResponse.getId();
        assertPlaylist(playlist, playlistResponse);
    }

    @Test
    public void getPlayLists() {
        Response getResponse =
                PlayListApi.get(id);
        assertStatusCode(getResponse.statusCode(), StatusCode.CODE_200);
    }

    @Test
    public void updateThePlayList() {
        Playlist playlistUpdated = playlistBuilder("My Updated Playlist123",
                "Updated Playlist123 description", false);
        Response response = PlayListApi
                .put(id, playlistUpdated)
                .then().extract().response();
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
    }

    @Test(dependsOnMethods = {"updateThePlayList"})
    public void getPlayListsAfterUpdate() {
        Playlist expectedPlayList =
                playlistBuilder("My Updated Playlist123", "Updated Playlist123 description", false);

        Response getResponseAfterUpdate = PlayListApi.get(id);
        assertStatusCode(getResponseAfterUpdate.statusCode(), StatusCode.CODE_200);
        Playlist playlistResponseAfterUpdate = getResponseAfterUpdate.as(Playlist.class);
        assertPlaylist(expectedPlayList, playlistResponseAfterUpdate);
    }

    @Test
    @Story("Alex creates his playlist")
    public void failedToCreatePlayListWithNoName() {
        Playlist playlistWithNoName =
                playlistBuilder("", genDescription(), false);

        Response responsePlaylistError = PlayListApi.post(playlistWithNoName);
        assertStatusCode(responsePlaylistError.statusCode(), StatusCode.CODE_400);
        assertErrorResponse(responsePlaylistError.as(PlaylistError.class),
                StatusCode.CODE_400);
    }

    @Test
    @Story("Alex creates his playlist")
    public void failedToCreatePlayListWithExpiredToken() {
        Playlist playlist = playlistBuilder(genName(), genDescription(), false);
        String accessToke = "12345";
        Response responsePlaylistError = PlayListApi.post(accessToke, playlist);
        assertStatusCode(responsePlaylistError.statusCode(), StatusCode.CODE_401);
        assertErrorResponse(responsePlaylistError.as(PlaylistError.class),
                StatusCode.CODE_401);
    }

    @Step
    private void assertPlaylist(Playlist playlist, Playlist playlistResponse) {
        softly = new SoftAssertions();
        softly.assertThat(playlistResponse.get_public()).isEqualTo(playlist.get_public());
        softly.assertThat(playlistResponse.getName()).isEqualTo(playlist.getName());
        softly.assertThat(playlistResponse.getDescription()).isEqualTo(playlist.getDescription());
        softly.assertAll();
    }

    @Step
    public Playlist playlistBuilder(String name, String description, Boolean _public) {
        return Playlist.builder().name(name).description(description)._public(_public).build();
    }

    @Step
    private void assertErrorResponse(PlaylistError playlistError, StatusCode statusCode) {
        softly = new SoftAssertions();
        softly.assertThat(playlistError.getError().getStatus()).isEqualTo(statusCode.code);
        softly.assertThat(playlistError.getError().getMessage()).isEqualTo(statusCode.msg);
        softly.assertAll();
    }
}
