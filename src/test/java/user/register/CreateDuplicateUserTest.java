package user.register;

import client.UserClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.FailedResponse;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;

@DisplayName("Create user")
public class CreateDuplicateUserTest {
    private final UserClient userClient = new UserClient();
    private CreateUserRequest userRequest;
    private Response createResponse;

    @Before
    public void beforeClass() {
        userRequest = new CreateUserRequest();
        createResponse = userClient.createUser(userRequest);
        createResponse.then().statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("Проверка создания пользователя, который уже существует")
    @Test
    public void checkSameUser() {
        Response response = userClient.createUser(userRequest);
        FailedResponse failedResponse = response.as(FailedResponse.class);

        Allure.step("Проверка ответа");
        Assert.assertEquals("Status code", SC_FORBIDDEN, response.statusCode());
        Assert.assertFalse(failedResponse.isSuccess());
        Assert.assertEquals("Error message",
                "User already exists", failedResponse.getMessage());
    }

    @After
    public void afterClass() {
        String token = createResponse.as(SuccessAuthResponse.class).getAccessToken();
        userClient.deleteUser(userRequest, token);
    }
}
