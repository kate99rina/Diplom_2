package org.example.model.user;

import lombok.Getter;

@Getter
public class SuccessUpdateUser {
    private boolean success;
    private User user;

    public SuccessUpdateUser(boolean success, User user) {
        this.success = success;
        this.user = user;
    }
}
