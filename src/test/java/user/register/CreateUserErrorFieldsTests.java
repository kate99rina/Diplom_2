package user.register;

import client.UserClient;
import model.FailedResponse;
import model.user.register.CreateUserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static util.FakerData.*;

@RunWith(Parameterized.class)
//@DisplayName("Create user")
public class CreateUserErrorFieldsTests {
    private final UserClient userClient = new UserClient();
    private final String msgError = "Email, password and name are required fields";
    private CreateUserRequest userRequest;

    private final String email;
    private final String pass;
    private final String name;

    public CreateUserErrorFieldsTests(String email, String pass, String name) {
        this.email = email;
        this.pass = pass;
        this.name = name;
    }

    /**
     * Создание пользователя:
     * создать пользователя и не заполнить одно из обязательных полей.
     */

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {getEmail(), getPassword(), null},
                {getEmail(), null, getName()},
                {null, getPassword(), getName()}
        };
    }

    @Test
    public void check() {
        userRequest = new CreateUserRequest(email, pass, name);
        FailedResponse failedResponse = userClient.createUser(userRequest)
                .as(FailedResponse.class);
        Assert.assertEquals("Response error", msgError, failedResponse.getMessage());//todo роверить статус?
    }
}
