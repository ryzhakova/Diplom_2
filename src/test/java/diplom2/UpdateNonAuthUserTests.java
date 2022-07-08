package diplom2;

import diplom2.dto.respose.dto.ErrorResponseDTO;
import diplom2.dto.respose.dto.ResponseRegisterDTO;
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
import static org.junit.Assert.assertFalse;

@Epic("API Tests")
@DisplayName("Changing unauthorized user data tests")
public class UpdateNonAuthUserTests {

    private static User user;
    private static String token;

    @Before
    @DisplayName("Creating a random user before the test")
    public void setUp() {
        user = UserService.generateRandomUser();
        ResponseRegisterDTO responseRegisterDTO = UserRestClient.registerUser(user).extract().as(ResponseRegisterDTO.class);
        token = responseRegisterDTO.getAccessToken();
    }

    @After
    @DisplayName("Deleting user after tests")
    public void tearDown() {
        user.setToken(token);
        UserRestClient.deleteUser(user.getToken());
    }

    @Test
    @DisplayName("Changing user email test")
    public void changeEmail() {
        user.setEmail(generateRandomEmail());

        ValidatableResponse validatableResponse = UserRestClient.setUserData(user);
        ErrorResponseDTO errorResponseDTO = validatableResponse.extract().as(ErrorResponseDTO.class);

        assertEquals(401, validatableResponse.extract().statusCode());
        assertFalse(errorResponseDTO.getSuccess());
    }

    @Test
    @DisplayName("Test for changing the name of a non-existent user")
    public void changeName() {
        user.setName(generateRandomName());

        ValidatableResponse validatableResponse = UserRestClient.setUserData(user);
        ErrorResponseDTO errorResponseDTO = validatableResponse.extract().as(ErrorResponseDTO.class);

        assertEquals(401, validatableResponse.extract().statusCode());
        assertFalse(errorResponseDTO.getSuccess());
    }
}
