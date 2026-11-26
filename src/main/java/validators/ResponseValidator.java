
package validators;

import java.util.List;

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

    // Validate login API response does not return a session token
    public static void validateNoValidSessionToken(Response response) {

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Unexpected login response status"
        );

        Assert.assertTrue(
                response.jsonPath().get("code") != null,
                "Response should contain API code field"
        );
    }

    // Validate created pet ID exists in the retrieved pets list
    public static void validatePetIdExists(Response response, long petId) {

        List<Long> petIds = response.jsonPath().getList("id", Long.class);

        Assert.assertTrue(
                petIds.contains(petId),
                "Created pet ID was not found in the retrieved pets list: " + petId
        );
    }
}
