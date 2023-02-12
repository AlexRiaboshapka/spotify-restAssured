package com.spotify.aouth2.tests;

import com.spotify.aouth2.pojo.Playlist;
import com.spotify.aouth2.pojo.PlaylistError;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.spotify.aouth2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.aouth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class PlayListTests {
    private static String id;
    private SoftAssertions softly;

    @BeforeTest
    public void beforeMethod() {
        softly = new SoftAssertions();
    }

    @AfterTest
    public void afterClass() {
        softly.assertAll();
    }

    @Test
    public void createPlayList() {

        Playlist playlist = new Playlist()
                .setName("My New Playlist")
                .setDescription("New Playlist description")
                .setPublic(false);

        Playlist playlistResponse = given(getRequestSpecification())
                .body(playlist)
                .when()
                .post("users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists")
                .then()
                .spec(getResponseSpecification())
                .assertThat().statusCode(201)
                .extract()
                .response()
                .as(Playlist.class);

        id = playlistResponse.getId();

        softly.assertThat(playlistResponse.getPublic()).isEqualTo(playlist.getPublic());
        softly.assertThat(playlistResponse.getName()).isEqualTo(playlist.getName());
        softly.assertThat(playlistResponse.getDescription()).isEqualTo(playlist.getDescription());
    }

    @Test
    public void getPlayLists() {
        given()
                .spec(getRequestSpecification())
                .when()
                .get("/playlists/" + id)
                .then()
                .spec(getResponseSpecification())
                .assertThat().statusCode(200);
    }

    @Test
    public void updateThePlayList() {


        Playlist playlistUpdated = new Playlist()
                .setName("My Updated Playlist123")
                .setDescription("Updated Playlist123 description")
                .setPublic(false);

        given(getRequestSpecification())
                .body(playlistUpdated)
                .when()
                .put("/playlists/" + id)
                .then()
                .spec(getResponseSpecification())
                .assertThat().statusCode(200);
    }

    @Test(dependsOnMethods = {"updateThePlayList"})
    public void getPlayListsAfterUpdate() {

        Playlist playlistResponseAfterUpdate =
                given()
                        .spec(getRequestSpecification())
                        .when()
                        .get("/playlists/" + id)
                        .then()
                        .spec(getResponseSpecification())
                        .assertThat().statusCode(200)
                        .extract()
                        .response()
                        .as(Playlist.class);

        softly.assertThat(playlistResponseAfterUpdate.getPublic()).isEqualTo(false);
        softly.assertThat(playlistResponseAfterUpdate.getName()).isEqualTo("My Updated Playlist123");
        softly.assertThat(playlistResponseAfterUpdate.getDescription())
                .isEqualTo("Updated Playlist123 description");


    }

    @Test
    public void failedToCreatePlayListWithNoName() {

        Playlist playlistWithNoName = new Playlist()
                .setDescription("New Playlist123 description")
                .setPublic(false);


        PlaylistError playlistError =
                given(getRequestSpecification())
                        .body(playlistWithNoName)
                        .when()
                        .post("users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists")
                        .then()
                        .spec(getResponseSpecification())
                        .assertThat().statusCode(400)
                        .extract()
                        .response()
                        .as(PlaylistError.class);

        softly.assertThat(playlistError.getError().getStatus()).isEqualTo(400);
        softly.assertThat(playlistError.getError().getMessage())
                .isEqualTo("Missing required field: name");

    }

    @Test
    public void failedToCreatePlayListWithExpiredToken() {

        Playlist playlist = new Playlist()
                .setName("My New Playlist123")
                .setDescription("New Playlist123 description")
                .setPublic(false);

        PlaylistError playlistError =
                given()
                        .baseUri("https://api.spotify.com")
                        .header("Authorization", "Bearer " + "accessToken")
                        .header("Content-Type", "application/json")
                        .basePath("/v1/")
                        .log().all()
                        .body(playlist)
                        .when()
                        .post("users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists")
                        .then()
                        .spec(getResponseSpecification())
                        .assertThat().statusCode(401)
                        .extract()
                        .response()
                        .as(PlaylistError.class);

        softly.assertThat(playlistError.getError().getStatus()).isEqualTo(401);
        softly.assertThat(playlistError.getError().getMessage()).isEqualTo("Invalid access token");
    }
}
