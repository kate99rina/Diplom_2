package user.login;

import client.UserClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static util.FakerData.*;

@DisplayName("User log in")
public class LoginUserTests {

    private final UserClient userClient = new UserClient();
    private CreateUserRequest userRequest;
    private String token;

    //логин под существующим пользователем
    @Before
    public void beforeClass() {
        userRequest = new CreateUserRequest(getEmail(), getPassword(), getName());
        var response = userClient
                .createUser(userRequest)
                .as(SuccessAuthResponse.class);
        token = response.getAccessToken();
    }

    @DisplayName("Авторизация под существующим пользователем")
    @Test
    public void check() {
        var response = userClient.loginUser(userRequest.getEmail(),
                userRequest.getPassword());
        Allure.step("Проверка ответа");
        response.then().assertThat().statusCode(SC_OK);
    }
    @After
    public void afterClass() {
        userClient.deleteUser(userRequest, token);
    }

}
