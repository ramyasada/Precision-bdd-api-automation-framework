
package clients;

import builder.RequestBuilder;
import constants.ApiEndpoints;
import io.restassured.response.Response;

public class UserApiClient {

    private RequestBuilder requestBuilder = new RequestBuilder();

    public Response createUser(Object userPayload) {
        return requestBuilder.post(ApiEndpoints.USER, userPayload);
    }

    public Response getUserByUsername(String username) {
        return requestBuilder.get(
                ApiEndpoints.USER_BY_USERNAME,
                "username",
                username
        );
    }

    public Response login(String username, String password) {
        return requestBuilder.buildRequest()
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(ApiEndpoints.USER_LOGIN)
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
