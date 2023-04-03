package order;

import client.OrderClient;
import client.UserClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import model.order.Ingredient;
import model.order.SuccessCreateOrderResponse;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static util.FakerData.*;

@DisplayName("Create order")
public class CreateOrderAuthTests {
    private final UserClient userClient = new UserClient();
    private CreateUserRequest userRequest;
    private String token;
    private OrderClient orderClient = new OrderClient();
    private List<Ingredient> ingredients;

    @Before
    public void beforeClass() {
        userRequest = new CreateUserRequest(getEmail(), getPassword(), getName());
        var response = userClient
                .createUser(userRequest)
                .as(SuccessAuthResponse.class);
        token = response.getAccessToken();
        ingredients = orderClient.getIngredients();
    }

    @DisplayName("Создание заказа с ингредиентами с авторизацией")
    @Test
    public void checkIngredientsWithAuth() {
        var createOrderResponse = orderClient.createOrder(token,
                List.of(ingredients.get(0).get_id(),
                        ingredients.get(1).get_id()));
        createOrderResponse.then().assertThat().statusCode(SC_OK);
        var successResponse = createOrderResponse.as(SuccessCreateOrderResponse.class);
        Allure.step("Проверка ответа");
        Assert.assertNotNull("Incorrect name of order", successResponse.getName());
        Assert.assertNotNull("Incorrect number of order", successResponse.getOrder().getNumber());
    }

    @DisplayName("Создание заказа с ингредиентами без авторизации")
    @Test
    public void checkIngredientsUnauth() {
        var createOrderResponse = orderClient.createOrder(
                List.of(ingredients.get(0).get_id(),
                        ingredients.get(1).get_id()));
        createOrderResponse.then().assertThat().statusCode(SC_OK);
        var successResponse = createOrderResponse.as(SuccessCreateOrderResponse.class);
        Allure.step("Проверка ответа");
        Assert.assertNotNull("Incorrect name of order", successResponse.getName());
        Assert.assertNotNull("Incorrect number of order", successResponse.getOrder().getNumber());
    }
}
