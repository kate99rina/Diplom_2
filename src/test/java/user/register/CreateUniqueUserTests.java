package user.register;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import client.UserClient;
import model.user.login.SuccessAuthResponse;
import model.user.register.CreateUserRequest;
import org.junit.After;
import org.junit.Test;

public class CreateUniqueUserTests {
    private final UserClient userClient = new UserClient();
    private CreateUserRequest userRequest;
    private Response createResponse;

    @Test
    public void checkUniqueUser() {
        userRequest = new CreateUserRequest();
        createResponse = userClient.createUser(userRequest);
        createResponse.then().statusCode(HttpStatus.SC_OK);
    }

    @After
    public void afterClass() {
        String token = createResponse.as(SuccessAuthResponse.class).getAccessToken();
        userClient.deleteUser(userRequest, validateToken(token));
    }

    private String validateToken(String accessToken){
        return accessToken.replaceFirst("Bearer ", "");
    }
}
