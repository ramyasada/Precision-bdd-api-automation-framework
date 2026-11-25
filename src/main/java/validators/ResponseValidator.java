package validators;

import org.testng.Assert;
import io.restassured.response.Response;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {

        Assert.assertEquals(
                response.getStatusCode(),
                expectedStatusCode,
                "Unexpected status code"
        );
    }

    public static void validateResponseContains(Response response, String expectedText) {

        Assert.assertTrue(
                response.asString().toLowerCase().contains(expectedText.toLowerCase()),
                "Expected response to contain: " + expectedText
        );
    }

    public static void validateResponseNotContains(Response response, String unexpectedText) {

        Assert.assertFalse(
                response.asString().toLowerCase().contains(unexpectedText.toLowerCase()),
                "Response should not contain: " + unexpectedText
        );
    }
    public static void validateLoginApiResponse(Response response) {

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Unexpected login status code"
        );

        Assert.assertTrue(
                response.jsonPath().get("code") != null,
                "Response should contain API code field"
        );
    }
}
