package order;

import client.OrderClient;
import client.UserClient;
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

public class CheckCreatedOrderTests {
    //Получение заказов конкретного пользователя:
    //авторизованный пользователь,
    //неавторизованный пользователь.
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
        token = validateToken(response.getAccessToken());
        ingredients = orderClient.getIngredients()
                .as(ListIngredientsResponse.class).getData();//todo проверять саксес, запихатьпроверку внутрь метода и выдавать список сразу
        orderClient.createOrder(
                List.of(ingredients.get(0).get_id(),
                        ingredients.get(1).get_id()));
    }

    @Test
    public void checkOrdersWithAuth() {
        var response = orderClient.getAllOrders(token);
        response.then().assertThat()
                .statusCode(SC_OK)
                .body("orders.ingredients", notNullValue());
    }

    @Test
    public void checkOrdersUnauth() {
        var response = orderClient.getAllOrders();
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"));
    }

    private String validateToken(String accessToken) {
        return accessToken.replaceFirst("Bearer ", "");
    }
}
