package spotify.aouth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {
    static String id;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass() throws IOException {
        Path path = Path.of("src/test/resources/tokens/access_token");
        String accessToken = Files.readString(path);
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .setBasePath("/v1/")
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectContentType("application/json")
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void createPlayList() throws IOException {
        Path path = Path.of("src/test/resources/payload/playlist.json");
        String payload = Files.readString(path);

        id = given(requestSpecification)
                .body(payload)
                .when()
                .post("users/31v5r5wfy4gnsl7c5eujg2fpf4oq/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(201)
                .body("name", equalTo("My New Playlist123"),
                        "description", equalTo("New Playlist123 description"))
                .extract().response().path("id");
    }

    @Test
    public void getPlayLists() {
        given()
                .spec(requestSpecification)
                .when()
                .get("/playlists/" + id)
                .then()
                .spec(responseSpecification);
    }
}
