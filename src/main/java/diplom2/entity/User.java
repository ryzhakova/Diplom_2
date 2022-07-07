package diplom2.entity;

import io.qameta.allure.Story;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Story("User Description")
public class User implements Serializable {
    private String email;
    private String password;
    private String name;
    private String token;
}
