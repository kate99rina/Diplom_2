package model.user;

import lombok.Getter;

@Getter
public class UpdateInfoRequest {
    private String email;
    private String password;
    private String name;

    public UpdateInfoRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
