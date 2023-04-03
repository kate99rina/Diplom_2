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

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static util.FakerData.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Create order")
public class CheckCreatedOrderTests {
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
        orderClient.createOrder(
                List.of(ingredients.get(0).get_id(),
                        ingredients.get(1).get_id()));
    }

    @DisplayName("Получение заказов авторизованного пользователя")
    @Test
    public void checkOrdersWithAuth() {
        var response = orderClient.getAllOrders(token);
        Allure.step("Проверка ответа");
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("orders.ingredients", notNullValue());
    }

    @DisplayName("Получение заказов не авторизованного пользователя")
    @Test
    public void checkOrdersUnauth() {
        var response = orderClient.getAllOrders();
        Allure.step("Проверка ответа");
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }
}
