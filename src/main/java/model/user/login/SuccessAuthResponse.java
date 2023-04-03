package model.user.login;

import lombok.Getter;
import model.user.User;

public class SuccessAuthResponse {
    @Getter
    private boolean success;
    @Getter
    private User user;

    private String accessToken;

    @Getter
    private String refreshToken;

    public String getAccessToken() {
        return accessToken.replaceFirst("Bearer ", "");
    }

    public SuccessAuthResponse(boolean success, String accessToken, String refreshToken, User user) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }
}
