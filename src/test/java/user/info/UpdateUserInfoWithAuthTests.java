package user.info;

import client.UserClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.SuccessUpdateUser;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static util.FakerData.*;

@DisplayName("Update user info")
@RunWith(Parameterized.class)
public class UpdateUserInfoWithAuthTests {

    private final UserClient userClient = new UserClient();
    private CreateUserRequest userRequest;
    private Response createResponse;
    private String token;

    private String difEmail;
    private String difPass;
    private String difName;

    public UpdateUserInfoWithAuthTests(String difEmail, String difPass, String difName) {
        this.difEmail = difEmail;
        this.difPass = difPass;
        this.difName = difName;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"test", "test", "test"},
                {"test", "", ""},
                {"", "test", ""},
                {"", "", "test"},
        };
    }

    @Before
    public void beforeClass() {
        userRequest = new CreateUserRequest(getEmail(), getPassword(), getName());
        createResponse = userClient.createUser(userRequest);
        token = createResponse.as(SuccessAuthResponse.class).getAccessToken();
    }

    @DisplayName("Обновление инфорации о пользователе с авторизацией")
    @Test
    public void check() {
        String expectedEmail = userRequest.getEmail().concat(difEmail);
        String expectedPassword = userRequest.getPassword().concat(difPass);
        String expectedName = userRequest.getName().concat(difName);
        var response = userClient.updateUserInfo(expectedEmail,
                expectedPassword,
                expectedName,
                token).as(SuccessUpdateUser.class);
        Allure.step("Проверка ответа");
        Assert.assertTrue("Invalid success status", response.isSuccess());
        Assert.assertEquals("Invalid email", expectedEmail, response.getUser().getEmail());
        Assert.assertEquals("Invalid name", expectedName, response.getUser().getName());
    }

    @After
    public void afterClass() {
        userClient.deleteUser(userRequest, token);
    }
}
