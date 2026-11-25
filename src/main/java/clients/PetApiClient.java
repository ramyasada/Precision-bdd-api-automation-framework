package clients;
import builder.RequestBuilder;
import constants.ApiEndpoints;
import io.restassured.response.Response;

public class PetApiClient {

    private RequestBuilder requestBuilder = new RequestBuilder();

    // Create a new pet
    public Response createPet(Object petPayload) {
        return requestBuilder.post(
                ApiEndpoints.PET, petPayload);
    }

    // Get pet by ID
    public Response getPetById(long petId) {
        return requestBuilder.get(ApiEndpoints.PET_BY_ID, "petId", petId);
    }

    // Update an existing pet
    public Response updatePet(Object petPayload) {
        return requestBuilder.put(
                ApiEndpoints.PET, petPayload);
    }

    // Delete pet by ID
    public Response deletePet(long petId) {
        return requestBuilder.delete(ApiEndpoints.PET_BY_ID, "petId", petId);
    }

    // Find pets by status
    public Response findPetByStatus(String status) {
        return requestBuilder.buildRequest()
                .queryParam("status", status)
                .when()
                .get(ApiEndpoints.FIND_PET_BY_STATUS)
                .then()
                .log().ifError()
                .extract().response();
    }
}
