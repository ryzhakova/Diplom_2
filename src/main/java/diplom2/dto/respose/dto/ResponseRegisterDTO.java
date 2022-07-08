package diplom2.dto.respose.dto;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import lombok.Data;

@Data
@Story("Response about user registration")
public class ResponseRegisterDTO {

    private String accessToken;
    private String refreshToken;
    private ResponseCreateUserDTO.ResponseUser user;
    private Boolean success;

    @Data
    @DisplayName("User data return")
    public static class ResponseUser {
        private String email;
        private String name;
    }
}
