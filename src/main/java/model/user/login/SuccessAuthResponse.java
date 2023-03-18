package model.user.login;

import lombok.Getter;
import model.user.User;

@Getter
public class SuccessAuthResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public SuccessAuthResponse(boolean success, String accessToken, String refreshToken, User user) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }
}
