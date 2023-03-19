package user.login;

import client.UserClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static util.FakerData.*;

@DisplayName("User log in")
@RunWith(Parameterized.class)
public class LoginIncorrectFieldsTests {
    private final UserClient userClient = new UserClient();
    private CreateUserRequest userRequest;
    private Response createResponse;

    private String difEmail;
    private String difPass;

    public LoginIncorrectFieldsTests(String email, String difPass) {
        this.difEmail = email;
        this.difPass = difPass;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"test", ""},
                {"", "test"},
                {"test", "test"}
        };
    }

    @Before
    public void beforeClass() {
        userRequest = new CreateUserRequest(getEmail(), getPassword(), getName());
        createResponse = userClient.createUser(userRequest);
    }

    @DisplayName("Авторизация пользователя с некорректно заполненными полями")
    @Test
    public void check() {
        var response = userClient.loginUser(userRequest.getEmail().concat(difEmail),
                userRequest.getPassword().concat(difPass));
        String expectedError = "email or password are incorrect";
        Allure.step("Проверка ответа");
        response.then().assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo(expectedError));
    }

    @After
    public void afterClass() {
        String token = createResponse.as(SuccessAuthResponse.class).getAccessToken();
        userClient.deleteUser(userRequest, token);
    }
}
