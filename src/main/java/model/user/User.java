package model.user;

import lombok.Getter;

@Getter
public class User {
    private String email;
    private String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
