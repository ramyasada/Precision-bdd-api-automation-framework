
package stepdefinitions;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import clients.PetApiClient;
import clients.StoreApiClient;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utils.LogsFactory;
import validators.ResponseValidator;

public class InventorySteps {

    private Logger logger = LogsFactory.getLogger(InventorySteps.class);

    private StoreApiClient storeApiClient = new StoreApiClient();
    private PetApiClient petApiClient = new PetApiClient();

    private Response response;
    private int inventoryAvailableCount;
    private int availablePetsCount;

    @When("I retrieve the pet inventory")
    public void retrievePetInventory() {

        logger.info("Sending GET request to retrieve pet inventory");

        response = storeApiClient.getInventory();

        logger.info("Inventory API response status code: {}",
                response.getStatusCode());

        ResponseValidator.validateStatusCode(response, 200);

        logger.info("Pet inventory retrieved successfully");
    }

    @Then("the inventory should contain the available pet count")
    public void verifyInventoryAvailableCount() {

        inventoryAvailableCount =
                response.jsonPath().getInt("available");

        logger.info("Available pet count in inventory: {}",
                inventoryAvailableCount);

        Assert.assertTrue(inventoryAvailableCount >= 0,
                "Available pet count should not be negative");

        logger.info("Inventory available pet count verified successfully");
    }

    @When("I retrieve all pets with status {string}")
    public void retrievePetsByStatus(String status) {

        logger.info("Sending GET request to find pets with status: {}", status);

        response = petApiClient.findPetByStatus(status);

        logger.info("Find pets by status response code: {}",
                response.getStatusCode());

        ResponseValidator.validateStatusCode(response, 200);

        availablePetsCount = response.jsonPath().getList("$").size();

        logger.info("Number of pets returned with status {}: {}",
                status, availablePetsCount);
    }


			@Then("the available pets count should match the inventory count")
			public void verifyAvailablePetsCount() {
			
			    logger.info("Inventory available count: {}",
			            inventoryAvailableCount);
			
			    logger.info("Available pets API count: {}",
			            availablePetsCount);
			
			    if (inventoryAvailableCount != availablePetsCount) {
			        logger.error("Count mismatch! Inventory: {}, Available Pets: {}",
			                inventoryAvailableCount, availablePetsCount);
			    }
			
			    Assert.assertEquals(availablePetsCount, inventoryAvailableCount,
			            "Inventory available count does not match available pets list");
			
			    logger.info("Inventory count matches available pets count successfully");
}

}
