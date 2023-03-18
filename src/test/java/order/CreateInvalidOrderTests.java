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

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static util.FakerData.*;
import static org.hamcrest.Matchers.equalTo;

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
        token = validateToken(response.getAccessToken());
        ingredients = orderClient.getIngredients()
                .as(ListIngredientsResponse.class).getData();//todo проверять саксес, запихатьпроверку внутрь метода и выдавать список сразу

    }

    //без ингредиентов
    @Test
    public void checkNullIngredients() {
        var createOrderResponse = orderClient.createOrder(token, null);
        createOrderResponse.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    //с неверным хешем ингредиентов
    @Test
    public void checkInvalidIngredients() {
        var createOrderResponse = orderClient.createOrder(token,
                List.of("test", ingredients.get(1).get_id()));
        createOrderResponse.then().assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }


    private String validateToken(String accessToken) {
        return accessToken.replaceFirst("Bearer ", "");
    }
}
