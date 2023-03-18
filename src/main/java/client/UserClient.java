package client;

import io.restassured.response.Response;
import model.user.login.LogInRequest;
import model.user.register.CreateUserRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserClient extends RestApiClient {
    private final String REGISTER_PATH = "/api/auth/register";
    private final String LOGIN_PATH = "/api/auth/login";
    private final String USER_INFO_PATH = "/api/auth/user";//todo одинаково для удаления


    public Response createUser(CreateUserRequest createUserRequest) {
        Response response = given()
                .spec(getBaseSpec())
                .body(createUserRequest)
                .when()
                .post(REGISTER_PATH);
        return response;
    }

    public Response loginUser(String email, String password) {
        Response response = given()
                .spec(getBaseSpec())
                .body(new LogInRequest(email, password))
                .when()
                .post(LOGIN_PATH);
        return response;
    }

    public Response updateUserInfo(String email, String password, String name, String accessToken) {
        Response patch = given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .and()
                .body(new CreateUserRequest(email, password, name))
                .when()
                .patch(USER_INFO_PATH);
        return patch;
    }

    public Response updateUserInfoWithoutAuth(String email, String password, String name) {
        Response patch = given()
                .spec(getBaseSpec())
                .body(new CreateUserRequest(email, password, name))
                .when()
                .patch(USER_INFO_PATH);
        return patch;
    }


    public void deleteUser(CreateUserRequest createUserRequest, String token) {
        given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .and()
                .body(createUserRequest)
                .when()
                .delete(USER_INFO_PATH)
                .then().body("success", equalTo(true));
    }
}
