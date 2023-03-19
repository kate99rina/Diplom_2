package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.CreateOrderRequest;
import model.order.Ingredient;
import model.order.ListIngredientsResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestApiClient {
    private final String ORDERS_PATH = "/api/orders";
    private final String INGREDIENTS_PATH = "/api/ingredients";

    @Step("Создание заказа с авторизацией по токену")
    public Response createOrder(String accessToken, List<String> ingredients) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .and()
                .body(new CreateOrderRequest(ingredients))
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Создание заказа без авторизации")
    public Response createOrder(List<String> ingredients) {
        return given()
                .spec(getBaseSpec())
                .body(new CreateOrderRequest(ingredients))
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Получение всех ингредиентов")
    public List<Ingredient> getIngredients() {
        ListIngredientsResponse ingredientsResponse = given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .as(ListIngredientsResponse.class);
        if (ingredientsResponse == null) {
            throw new NullPointerException("Nothing ingredients in response");
        }
        return ingredientsResponse.getData();
    }

    @Step("Получение всех заказов конкретного пользователя по токену")
    public Response getAllOrders(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(ORDERS_PATH);
    }

    @Step("Получение всех заказов без авторизации")
    public Response getAllOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }
}
