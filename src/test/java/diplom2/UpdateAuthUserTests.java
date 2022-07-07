package diplom2;

import diplom2.dto.respose.dto.ResponseRegisterDTO;
import diplom2.dto.respose.dto.ResponseUpdateUserDTO;
import diplom2.entity.User;
import diplom2.rest.clients.UserRestClient;
import diplom2.service.UserService;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static diplom2.service.UserService.generateRandomEmail;
import static diplom2.service.UserService.generateRandomName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic("API Tests")
@DisplayName("Changing authorized user data tests")
public class UpdateAuthUserTests {

    private static User user;

    @Before
    @DisplayName("Creating a random user before the test")
    public void setUp() {
        user = UserService.generateRandomUser();
        ResponseRegisterDTO responseRegisterDTO = UserRestClient.registerUser(user).extract().as(ResponseRegisterDTO.class);
        user.setToken(responseRegisterDTO.getAccessToken());
    }

    @After
    @DisplayName("Deleting user data after the test")
    public void tearDown() {
        UserRestClient.deleteUser(user.getToken());
    }

    @Test
    @DisplayName("User mail change test")
    public void changeEmail() {
        user.setEmail(generateRandomEmail());

        ValidatableResponse validatableResponse = UserRestClient.setUserData(user);
        ResponseUpdateUserDTO responseUpdateUserDTO = validatableResponse.extract().as(ResponseUpdateUserDTO.class);

        assertEquals(200, validatableResponse.extract().statusCode());
        assertTrue(responseUpdateUserDTO.getSuccess());
        assertEquals(user.getEmail(), responseUpdateUserDTO.getUser().getEmail());
    }

    @Test
    @DisplayName("User Name Change test")
    public void changeName() {
        user.setName(generateRandomName());

        ValidatableResponse validatableResponse = UserRestClient.setUserData(user);
        ResponseUpdateUserDTO responseUpdateUserDTO = validatableResponse.extract().as(ResponseUpdateUserDTO.class);

        assertEquals(200, validatableResponse.extract().statusCode());
        assertTrue(responseUpdateUserDTO.getSuccess());
        assertEquals(user.getName(), responseUpdateUserDTO.getUser().getName());
    }
}
