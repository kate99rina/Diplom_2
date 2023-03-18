package client;

import io.restassured.response.Response;
import model.order.CreateOrderRequest;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestApiClient {
    private final String ORDERS_PATH = "/api/orders";
    private final String INGREDIENTS_PATH = "/api/ingredients";

    public Response createOrder(String accessToken, List<String> ingredients) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .and()
                .body(new CreateOrderRequest(ingredients))
                .when()
                .post(ORDERS_PATH);
    }

    public Response createOrder(List<String> ingredients) {
        return given()
                .spec(getBaseSpec())
                .body(new CreateOrderRequest(ingredients))
                .when()
                .post(ORDERS_PATH);
    }

    public Response getIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH);
    }

    public Response getAllOrders(String token){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(ORDERS_PATH);
    }

    public Response getAllOrders(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }
}
