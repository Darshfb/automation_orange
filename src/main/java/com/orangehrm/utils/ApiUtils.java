package com.orangehrm.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class ApiUtils {
  private static final Logger log = LoggerUtils.getLogger(ApiUtils.class);

  public static void setBaseURI() {
    RestAssured.baseURI = ConfigReader.getProperty("api.base.url", "http://localhost/api");
  }

  public static Response get(String endpoint, Map<String, String> headers) {
    log.info("Sending GET request to: {}", endpoint);
    return RestAssured.given().headers(headers).when().get(endpoint).then().extract().response();
  }

  public static Response post(String endpoint, Object payload, Map<String, String> headers) {
    log.info("Sending POST request to: {}", endpoint);
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .headers(headers)
        .body(payload)
        .when()
        .post(endpoint)
        .then()
        .extract()
        .response();
  }

  public static Response put(String endpoint, Object payload, Map<String, String> headers) {
    log.info("Sending PUT request to: {}", endpoint);
    return RestAssured.given()
        .contentType(ContentType.JSON)
        .headers(headers)
        .body(payload)
        .when()
        .put(endpoint)
        .then()
        .extract()
        .response();
  }

  public static Response delete(String endpoint, Map<String, String> headers) {
    log.info("Sending DELETE request to: {}", endpoint);
    return RestAssured.given().headers(headers).when().delete(endpoint).then().extract().response();
  }
}
