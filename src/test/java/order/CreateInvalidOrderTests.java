package order;

import client.OrderClient;
import client.UserClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import model.order.Ingredient;
import model.order.ListIngredientsResponse;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static util.FakerData.*;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Create order")
public class CreateInvalidOrderTests {
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

    @DisplayName("Создание заказа без ингредиентов")
    @Test
    public void checkNullIngredients() {
        var createOrderResponse = orderClient.createOrder(token, null);
        Allure.step("Проверка ответа");
        createOrderResponse.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    public void checkInvalidIngredients() {
        var createOrderResponse = orderClient.createOrder(token,
                List.of("test", ingredients.get(1).get_id()));
        Allure.step("Проверка ответа");
        createOrderResponse.then().assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
