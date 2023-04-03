package model.user.login;

public class LogInRequest {
    private String email;
    private String password;

    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
