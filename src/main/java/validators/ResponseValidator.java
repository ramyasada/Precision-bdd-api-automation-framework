package validators;
import org.testng.Assert;
import io.restassured.response.Response;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(),expectedStatusCode,"Unexpected status code");
    }
}
