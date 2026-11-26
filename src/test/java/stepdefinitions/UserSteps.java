
package stepdefinitions;

import org.apache.logging.log4j.Logger;

import clients.UserApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import utils.LogsFactory;
import payloads.User;
import utils.UserFactory;
import validators.ResponseValidator;

public class UserSteps {

    private Logger logger = LogsFactory.getLogger(UserSteps.class);

    private UserApiClient userApiClient = new UserApiClient();

    private User user;
    private Response response;

    @Given("I have user details with an invalid email")
    public void createUserWithInvalidEmail() {

        user = UserFactory.createUserWithInvalidEmail();

        logger.info("Created user test data with invalid email: {}",
                user.getEmail());
    }

    @When("I send a POST request to create the user")
    public void createUser() {

        logger.info("Sending POST request to create user");

        response = userApiClient.createUser(user);

        logger.info("Create user response status code: {}",
                response.getStatusCode());

        logger.info("Create user response body: {}", response.asString());
    }

    @Then("the user creation response should be successful")
    public void verifyUserCreation() {

        ResponseValidator.validateStatusCode(response, 200);

        logger.info("User creation response validated successfully");
    }

    @When("I retrieve a user with username {string}")
    public void retrieveUser(String username) {

        logger.info("Sending GET request for username: {}", username);

        response = userApiClient.getUserByUsername(username);

        logger.info("Get user response status code: {}",
                response.getStatusCode());

        logger.info("Get user response body: {}", response.asString());
    }

    @Then("the user retrieval response should return 404")
    public void verifyUserNotFoundStatus() {

        ResponseValidator.validateStatusCode(response, 404);

        logger.info("Verified non-existing user returns 404");
    }

    @And("the error message should indicate user not found")
    public void verifyUserNotFoundMessage() {

        ResponseValidator.validateResponseContains(response, "User not found");

        logger.info("Verified error message: User not found");
    }

    @When("I login using username {string} and password {string}")
    public void loginWithInvalidCredentials(String username, String password) {

        logger.info("Sending login request for username: {}", username);

        response = userApiClient.login(username, password);

        logger.info("Login response status code: {}", response.getStatusCode());
        logger.info("Login response body: {}", response.asString());
    }

    @Then("the response should not contain a valid session token")
    public void verifyInvalidLogin() {

        ResponseValidator.validateNoValidSessionToken(response);

        logger.info("Validated that invalid login did not return a session token");
    }
}
