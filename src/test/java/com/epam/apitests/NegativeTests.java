package com.epam.apitests;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class NegativeTests {

    @Test
    public void lookUpForTourByIncorrectCode() {
        given()
                .when()
                .get("/tours/search/findByTourPackageCode?code=QQ")
                .then().log().ifValidationFails()
                .statusCode(404);
    }

    @Test
    public void addRatingToTheTourWithNonexistentTourId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 4);
        jsonAsMap.put("comment", "Comment");
        jsonAsMap.put("customerId", 40);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .post("/tours/123/ratings")
                .then().log().ifValidationFails()
                .statusCode(404);
    }

    @Test
    public void addRatingToTheTourWithIncorrectScore() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 6);
        jsonAsMap.put("comment", "Comment");
        jsonAsMap.put("customerId", 50);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .post("/tours/3/ratings")
                .then().log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void addRatingToTheTourWithNullScore() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", null);
        jsonAsMap.put("comment", "Comment");
        jsonAsMap.put("customerId", 51);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .post("/tours/3/ratings")
                .then().log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void addRatingToTheTourWithExistingCustomerId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 5);
        jsonAsMap.put("comment", "Some comment");
        jsonAsMap.put("customerId", 4);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .post("/tours/1/ratings")
                .then().log().ifValidationFails()
                .statusCode(500);
    }

    @Test
    public void addRatingToTheTourWithNullCustomerId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 5);
        jsonAsMap.put("comment", "Some comment");
        jsonAsMap.put("customerId", null);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .post("/tours/1/ratings")
                .then().log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void addRatingToTheTourWithLongComment() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 5);
        jsonAsMap.put("comment", "This comment has 256 characters! This comment has 256 characters! This comment has 256 characters! This comment has 256 characters! This comment has 256 characters! This comment has 256 characters! This comment has 256 characters! This is the last sentence");
        jsonAsMap.put("customerId", 52);

        given().contentType(JSON).body(jsonAsMap)
                .when()
                .post("/tours/3/ratings")
                .then().log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void addSeveralRatingsWithIncorrectScore() {
        given()
                .pathParam("customers", "53,54,55")
                .when()
                .post("/tours/3/ratings/6?customers={customers}")
                .then().log().ifValidationFails()
                .statusCode(400);
    }

    @Test
    public void addSeveralRatingsWithExistingCustomerId() {
        given()
                .pathParam("customers", "2,3,4")
                .when()
                .post("/tours/1/ratings/5?customers={customers}")
                .then().log().ifValidationFails()
                .statusCode(500);
    }

    @Test
    public void addSeveralRatingsWithNullCustomerId() {
        given()
                .pathParam("customers", "")
                .when()
                .post("/tours/3/ratings/5?customers={customers}")
                .then().log().ifValidationFails()
                .statusCode(500);
    }

    @Test
    public void lookUpForRatingsWithNonexistentCustomerId() {
        given()
                .when()
                .get("tours/123/ratings")
                .then().log().ifValidationFails()
                .statusCode(404);
    }


    @Test
    public void updateRatingWithNonexistentCustomerId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("score", 5);
        jsonAsMap.put("comment", "Good!");
        jsonAsMap.put("customerId", 100);

        given()
                .contentType(JSON)
                .body(jsonAsMap)
                .when()
                .put("/tours/3/ratings")
                .then().log().ifValidationFails()
                .statusCode(404);
    }
}
