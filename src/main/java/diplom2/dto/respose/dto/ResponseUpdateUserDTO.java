package diplom2.dto.respose.dto;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import lombok.Data;

@Data
@Story("Response about changing user data")
public class ResponseUpdateUserDTO {
    private ResponseCreateUserDTO.ResponseUser user;
    private Boolean success;

    @Data
    @DisplayName("Return of changed user data")
    public static class ResponseUser {
        private String email;
        private String name;
    }
}
