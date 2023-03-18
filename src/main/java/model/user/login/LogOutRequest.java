package model.user.login;

public class LogOutRequest {
    /**
     * refreshToken
     */
    private String token;

    public LogOutRequest(String token) {
        this.token = token;
    }
}
