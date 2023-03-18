package user.login;

import client.UserClient;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static util.FakerData.*;

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
        token = validateToken(response.getAccessToken());
    }

    /**
     * Логин пользователя:
     * логин под существующим пользователем,
     * логин с неверным логином и паролем.
     */
    @Test
    public void check() {
        var response = userClient.loginUser(userRequest.getEmail(),
                userRequest.getPassword());
        response.then().assertThat().statusCode(SC_OK);
    }
    @After
    public void afterClass() {
        userClient.deleteUser(userRequest, validateToken(token));
    }

    private String validateToken(String accessToken) {
        return accessToken.replaceFirst("Bearer ", "");
    }
}
