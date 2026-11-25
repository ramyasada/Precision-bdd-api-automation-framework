package clients;

import io.restassured.response.Response;
import constants.ApiEndpoints;
import builder.RequestBuilder;

public class StoreApiClient {

    private RequestBuilder requestBuilder = new RequestBuilder();

    public Response getInventory() {
        return requestBuilder.get(ApiEndpoints.INVENTORY);
    }
}
