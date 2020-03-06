package com.epam.apitests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class PositiveTests {

    @ParameterizedTest
    @ValueSource(strings = {"Backpack Cal", "California Calm", "California Hot springs", "Cycle California", "From Desert to Sea", "Kids California", "Nature Watch", "Snowboard Cali", "Taste of California"})
    public void getAllTourPackages(String packageName) {
        given()
        .when()
                .get("/packages")
        .then()
                .body("_embedded.packages.name", hasItems(packageName))
                .statusCode(200);
    }

    @Test
    public void getAllTours() {
        given()
        .when()
                .get("/tours")
        .then()
                .body("page.totalElements", equalTo(30))
                .statusCode(200);
    }

    @Test
    public void lookUpForTourByCode() {
        given()
        .when()
                .get("/tours/search/findByTourPackageCode?code=BC")
        .then()
                .statusCode(200);
    }

//    @Test
//    public void addRatingToTheTour() {
//        Map<String, Object> jsonAsMap = new HashMap<>();
//        jsonAsMap.put("score", 4);
//        jsonAsMap.put("comment", "Not bad!");
//        jsonAsMap.put("customerId", 40);
//
//        given()
//                .contentType(JSON)
//                .body(jsonAsMap)
//        .when()
//                .post("/tours/3/ratings")
//        .then()
//                .statusCode(201);
//    }

//    @Test
//    public void addSeveralRatingsToTheTour() {
//        given()
//                .pathParam("customers", "41,42,43")
//        .when()
//                .post("/tours/3/ratings/3?customers={customers}")
//        .then()
//                .statusCode(201);
//    }

    @Test
    public void lookUpForRatings() {
        given()
        .when()
                .get("tours/2/ratings")
        .then()
                .root("_embedded.ratingDtoes")
                .body("score", hasItems(5))
                .body("comment", hasItems("I really thought it could have been better"))
                .body("customerId", hasItems(100))
                .statusCode(200);
    }

    @Test
    public void lookUpForAverageScore() {
        given()
        .when()
                .get("/tours/2/ratings/average")
        .then()
//                .body("average", equalTo(5.0))
                .statusCode(200);
    }
}