
package builder;

import utils.ConfigReader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestBuilder {

    public RequestSpecification buildRequest() {
        return RestAssured.given()
                .baseUri(ConfigReader.getProperty("base.url"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    // GET request
    public Response get(String endpoint) {
        return buildRequest()
                .when()
                .get(endpoint)
                .then()
                .log().ifError()
                .extract().response();
    }

    // GET request with path parameter
    public Response get(String endpoint, String parameterName, Object value) {
        return buildRequest()
                .pathParam(parameterName, value)
                .when()
                .get(endpoint)
                .then()
                .log().ifError()
                .extract().response();
    }

    // POST request
    public Response post(String endpoint, Object payload) {
        return buildRequest()
                .body(payload)
                .when()
                .post(endpoint)
                .then()
                .log().ifError()
                .extract().response();
    }

    // PUT request
    public Response put(String endpoint, Object payload) {
        return buildRequest()
                .body(payload)
                .when()
                .put(endpoint)
                .then()
                .log().ifError()
                .extract().response();
    }

    // DELETE request
    public Response delete(String endpoint, String parameterName, Object value) {
        return buildRequest()
                .pathParam(parameterName, value)
                .when()
                .delete(endpoint)
                .then()
                .log().ifError()
                .extract().response();
    }
}
