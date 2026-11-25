package stepdefinitions;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import clients.PetApiClient;
import payloads.Pet;
import utils.PetsFactory;
import utils.LogsFactory;
import validators.ResponseValidator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;

public class PetSteps {

    private Logger logger = LogsFactory.getLogger(PetSteps.class);

    private Pet pet;
    private PetApiClient petApiClient = new PetApiClient();
    private Response response;

    @Given("I have a new pet with a unique name and available status")
    public void createPetTestData() {
        pet = PetsFactory.createPet();

        logger.info("Created test data for pet: {}", pet.getName());
    }

    @When("I send a POST request to create the pet")
    public void createPet() {
        logger.info("Sending POST request to create a new pet");

        response = petApiClient.createPet(pet);

        logger.info("POST response status code: {}", response.getStatusCode());
    }

    @Then("the pet should be created successfully")
    public void verifyPetCreated() {
        ResponseValidator.validateStatusCode(response, 200);

        logger.info("Pet created successfully");
    }

    @And("I extract the pet ID from the response")
    public void extractPetId() {
        long petId = response.jsonPath().getLong("id");

        Assert.assertTrue(petId > 0, "Pet ID should be valid");

        pet.setId(petId);

        logger.info("Extracted pet ID: {}", petId);
    }

    @When("I send a GET request using the extracted pet ID")
    public void getPetById() {
        logger.info("Sending GET request for pet ID: {}", pet.getId());

        response = petApiClient.getPetById(pet.getId());

        logger.info("GET response status code: {}", response.getStatusCode());
    }

    @Then("the pet details should match the initial request")
    public void verifyPetDetails() {
        ResponseValidator.validateStatusCode(response, 200);

        Assert.assertEquals(
                response.jsonPath().getString("name"),
                pet.getName(),
                "Pet name does not match");

        Assert.assertEquals(
                response.jsonPath().getString("status"),
                "available",
                "Pet status does not match");

        logger.info("Pet details verified successfully");
    }

    @When("I update the pet status to {string} using PUT request")
    public void updatePetStatus(String status) {
        logger.info("Updating pet ID {} status to {}", pet.getId(), status);

        pet.setStatus(status);
        response = petApiClient.updatePet(pet);

        logger.info("PUT response status code: {}", response.getStatusCode());
    }

    @Then("the pet status should be updated successfully")
    public void verifyPetUpdated() {
        ResponseValidator.validateStatusCode(response, 200);

        Assert.assertEquals(
                response.jsonPath().getString("status"),
                "sold",
                "Pet status was not updated");

        logger.info("Pet status updated successfully to sold");
    }

    @When("I send a DELETE request using the extracted pet ID")
    public void deletePet() {
        logger.info("Sending DELETE request for pet ID: {}", pet.getId());

        response = petApiClient.deletePet(pet.getId());

        logger.info("DELETE response status code: {}", response.getStatusCode());
    }

    @Then("the pet should be deleted successfully with status code 200")
    public void verifyPetDeleted() {
        ResponseValidator.validateStatusCode(response, 200);

        logger.info("Pet deleted successfully");
    }

    @When("I send a GET request using the deleted pet ID")
    public void getDeletedPet() {
        logger.info("Verifying deleted pet ID: {}", pet.getId());

        response = petApiClient.getPetById(pet.getId());

        logger.info("GET response status code after deletion: {}", response.getStatusCode());
    }

    @Then("the response status code should be 404")
    public void verifyPetNotFound() {
        ResponseValidator.validateStatusCode(response, 404);

        logger.info("Verified that deleted pet returns 404");
    }
}
