package model.user.register;

import lombok.Getter;
import org.example.util.FakerData;

@Getter
public class CreateUserRequest {
    private String email;
    private String password;
    private String name;

    public CreateUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public CreateUserRequest(){
        this.email = FakerData.getEmail();
        this.password = FakerData.getPassword();
        this.name = FakerData.getName();
    }
}
