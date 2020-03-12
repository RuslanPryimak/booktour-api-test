package com.epam.apitests;

import model.TourPackage;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.*;

public class PositiveTests {

    @ParameterizedTest
    @EnumSource(TourPackage.class)
    public void getAllTourPackages(TourPackage tourPackage) {
        given()
                .when()
                .get("/packages")
                .then().log().ifError()
                .body("_embedded.packages.name", hasItems(tourPackage.getLabel())).statusCode(200);
    }

    @Test
    public void getAllTours() {
        given()
                .when()
                .get("/tours")
                .then().log().ifError()
                .body("page.totalElements", equalTo(30)).statusCode(200);
    }

    @Test
    public void lookUpForTourByCode() {
        given()
                .when()
                .get("/tours/search/findByTourPackageCode?code=BC")
                .then().log().ifError()
                .statusCode(200);
    }

    @Test
    public void addRatingToTheTour() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 4);
        jsonAsMap.put("comment", "Not bad!");
        jsonAsMap.put("customerId", 40);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .post("/tours/3/ratings")
                .then().log().ifError()
                .statusCode(201);
    }

    @Test
    public void addSeveralRatingsToTheTour() {
        given().pathParam("customers", "41,42,43")
                .when()
                .post("/tours/3/ratings/3?customers={customers}")
                .then().log().ifError()
                .statusCode(201);
    }

    @Test
    public void lookUpForRatings() {
        given()
                .when()
                .get("tours/2/ratings")
                .then().log().ifError()
                .rootPath("_embedded.ratingDtoes")
                .body("score", hasItems(5))
                .body("comment", hasItems("I really thought it could have been better"))
                .body("customerId", hasItems(100))
                .statusCode(200);
    }

    @Test
    public void lookUpForAverageScore() {
        given().config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE)))
                .when()
                .get("/tours/1/ratings/average")
                .then().log().ifError()
                .body("average", is(5.0)).statusCode(200);
    }

    @Test
    public void updateRatingWithGivenId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 5);
        jsonAsMap.put("comment", "It was very good!");
        jsonAsMap.put("customerId", 4);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .put("/tours/1/ratings")
                .then().log().ifError()
                .statusCode(200);
    }
}
