package diplom2;

import diplom2.dto.respose.dto.ErrorResponseDTO;
import diplom2.dto.respose.dto.ResponseCreateUserDTO;
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
@DisplayName("User Creation Tests")
public class CreateUserTests {

    private static User user;

    @Before
    @DisplayName("Creating a random user before the test")
    public void setUp() {
        user = UserService.generateRandomUser();
    }

    @After
    @DisplayName("Deleting user data after the test")
    public void tearDown() {
        UserRestClient.deleteUser(user.getToken());
    }

    @Test
    @DisplayName("Test for creating a new user order")
    public void createUniqueUser() {
        ValidatableResponse validatableResponse = UserRestClient.createUser(user);
        ResponseCreateUserDTO responseCreateUserDTO = validatableResponse.extract().as(ResponseCreateUserDTO.class);
        user.setToken(responseCreateUserDTO.getAccessToken());

        assertEquals(200, validatableResponse.extract().statusCode());
        assertTrue(responseCreateUserDTO.getSuccess());
        assertEquals(user.getEmail(), responseCreateUserDTO.getUser().getEmail());
        assertEquals(user.getName(), responseCreateUserDTO.getUser().getName());
    }

    @Test
    @DisplayName("Test for creating a user order with already existing data")
    public void createNonUniqueUser() {
        ValidatableResponse correctResponse = UserRestClient.createUser(user);
        user.setToken(correctResponse.extract().as(ResponseCreateUserDTO.class).getAccessToken());

        ValidatableResponse validatableResponse = UserRestClient.createUser(user);
        ErrorResponseDTO responseDTO = validatableResponse.extract().as(ErrorResponseDTO.class);

        assertEquals(403, validatableResponse.extract().statusCode());
        assertEquals("User already exists", responseDTO.getMessage());
        assertFalse(responseDTO.getSuccess());
    }

    @Test
    @DisplayName("Test for creating a new user order without a required field")
    public void createUserWithoutField() {
        String name = user.getName();
        user.setName(null);

        ValidatableResponse validatableResponse = UserRestClient.createUser(user);
        ErrorResponseDTO responseDTO = validatableResponse.extract().as(ErrorResponseDTO.class);

        user.setName(name);

        assertEquals(403, validatableResponse.extract().statusCode());
        assertEquals("Email, password and name are required fields", responseDTO.getMessage());
        assertFalse(responseDTO.getSuccess());
    }
}
