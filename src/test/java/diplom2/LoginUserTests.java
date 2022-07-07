package diplom2;

import diplom2.dto.respose.dto.ErrorResponseDTO;
import diplom2.dto.respose.dto.ResponseAuthorizationDTO;
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

import static org.junit.Assert.*;

@Epic("API Tests")
@DisplayName("User authorization tests")
public class LoginUserTests {

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
    @DisplayName("Test for authorization of an existing user")
    public void loginWithExistingUser() {
        ValidatableResponse validatableResponse = UserRestClient.authorizationUser(user);
        ResponseAuthorizationDTO responseAuthorizationDTO = validatableResponse.extract().as(ResponseAuthorizationDTO.class);

        assertEquals(200, validatableResponse.extract().statusCode());
        assertTrue(responseAuthorizationDTO.getSuccess());
        assertEquals(user.getEmail(), responseAuthorizationDTO.getUser().getEmail());
        assertEquals(user.getName(), responseAuthorizationDTO.getUser().getName());
    }

    @Test
    @DisplayName("Test for authorization of a non-existent user")
    public void loginWithUnExistingUser() {
        String email = user.getEmail();
        user.setEmail(null);

        ValidatableResponse validatableResponse = UserRestClient.authorizationUser(user);
        ErrorResponseDTO errorResponseDTO = validatableResponse.extract().as(ErrorResponseDTO.class);

        user.setEmail(email);

        assertEquals(401, validatableResponse.extract().statusCode());
        assertEquals("email or password are incorrect", errorResponseDTO.getMessage());
        assertFalse(errorResponseDTO.getSuccess());
    }
}
