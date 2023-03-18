package org.example.model.user;

import lombok.Getter;
import org.example.util.FakerData;

@Getter
public class User {
    private String email;
    private String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
