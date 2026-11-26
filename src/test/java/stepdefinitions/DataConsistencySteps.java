
package stepdefinitions;

import org.apache.logging.log4j.Logger;

import clients.PetApiClient;
import clients.StoreApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import payloads.Pet;
import utils.PetsFactory;
import utils.LogsFactory;
import validators.ResponseValidator;

public class DataConsistencySteps {

    private static final Logger logger =
            LogsFactory.getLogger(DataConsistencySteps.class);

    private PetApiClient petApiClient = new PetApiClient();
    private StoreApiClient storeApiClient = new StoreApiClient();

    private Pet pet;
    private Response response;
    private long createdPetId;

    // Create pet payload
    @Given("I have a pet with category {string} and status {string}")
    public void createPetPayload(String categoryName, String initialStatus) {

        logger.info("Creating pet payload with category: {} and status: {}",
                categoryName, initialStatus);

        pet = PetsFactory.createPetWithCategory(categoryName, initialStatus);

        logger.info("Pet payload created successfully. Pet name: {}",
                pet.getName());
    }

    // POST request
    @When("I create the pet using POST request")
    public void createPet() {

        logger.info("Sending POST request to create a new pet");

        response = petApiClient.createPet(pet);

        logger.info("POST response status code: {}",
                response.getStatusCode());
    }

    // Validate pet creation
    @Then("the consistency pet should be created successfully")
    public void verifyConsistencyPetCreated() {

        logger.info("Validating consistency pet creation response");

        ResponseValidator.validateStatusCode(response, 200);

        logger.info("Consistency pet created successfully");
    }

    // Extract created pet ID
    @Then("I extract the created pet ID")
    public void extractPetId() {

        createdPetId = response.jsonPath().getLong("id");

        pet.setId(createdPetId);

        logger.info("Extracted created pet ID: {}", createdPetId);
    }

    // Update pet status
    @When("I update the created consistency pet status to {string} using PUT request")
    public void updatePetStatus(String updatedStatus) {

        logger.info("Updating pet ID {} status to {}",
                createdPetId, updatedStatus);

        pet.setStatus(updatedStatus);

        response = petApiClient.updatePet(pet);

        logger.info("PUT response status code: {}",
                response.getStatusCode());
    }

    // Validate updated pet
    @Then("the created consistency pet status should be updated successfully")
    public void verifyPetUpdated() {

        logger.info("Validating updated pet status");

        ResponseValidator.validateStatusCode(response, 200);

        ResponseValidator.validateResponseContains(response, pet.getStatus());

        logger.info("Pet status updated successfully to: {}",
                pet.getStatus());
    }

    // Retrieve store inventory
    @When("I retrieve the store inventory")
    public void retrieveInventory() {

        logger.info("Sending GET request to retrieve store inventory");

        response = storeApiClient.getInventory();

        logger.info("Inventory response status code: {}",
                response.getStatusCode());
    }

    // Validate inventory response
    @Then("the inventory response should be successful")
    public void verifyInventoryResponse() {

        logger.info("Validating store inventory response");

        ResponseValidator.validateStatusCode(response, 200);

        logger.info("Store inventory response validated successfully");
    }

    // Retrieve pets by status
    @When("I retrieve pets with status {string}")
    public void retrievePetsByStatus(String updatedStatus) {

        logger.info("Retrieving pets with status: {}", updatedStatus);

        response = petApiClient.findPetByStatus(updatedStatus);

        logger.info("Find pets response status code: {}",
                response.getStatusCode());
    }

    // Verify created pet ID exists in retrieved list
    @Then("the created pet ID should exist in the retrieved pets list")
    public void verifyCreatedPetExists() {

        logger.info("Checking whether pet ID {} exists in the retrieved list",
                createdPetId);

        ResponseValidator.validateStatusCode(response, 200);

        ResponseValidator.validatePetIdExists(response, createdPetId);

        logger.info("Created pet ID {} found successfully", createdPetId);
    }
}
