package diplom2;

import diplom2.dto.respose.dto.ErrorResponseDTO;
import diplom2.dto.respose.dto.ResponseGetOrders;
import diplom2.dto.respose.dto.ResponseRegisterDTO;
import diplom2.entity.User;
import diplom2.rest.clients.OrderRestClient;
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
@DisplayName("Get user orders test")
public class GetOrderUserTest {

    private static User user;
    private static ResponseRegisterDTO responseRegisterDTO;

    @Before
    @DisplayName("Creating a random user before the test")
    public void setUp() {
        user = UserService.generateRandomUser();
        responseRegisterDTO = UserRestClient.registerUser(user).extract().as(ResponseRegisterDTO.class);
    }

    @After
    @DisplayName("Deleting user data after the test")
    public void tearDown() {
        UserRestClient.deleteUser(user.getToken());
    }

    @Test
    @DisplayName("Test for receiving orders from an authorized user")
    public void getOrderAuthUser() {
        user.setToken(responseRegisterDTO.getAccessToken());

        ValidatableResponse validatableResponse = OrderRestClient.getAllOrder(user);
        ResponseGetOrders responseGetOrders = validatableResponse.extract().as(ResponseGetOrders.class);

        assertEquals(200, validatableResponse.extract().statusCode());
        assertNotNull(responseGetOrders.getOrders());
        assertTrue(responseGetOrders.getSuccess());
    }

    @Test
    @DisplayName("Test for receiving orders from an unauthorized user")
    public void getOrderNonAuthUser() {

        ValidatableResponse validatableResponse = OrderRestClient.getAllOrder(user);
        ErrorResponseDTO errorResponseDTO = validatableResponse.extract().as(ErrorResponseDTO.class);

        user.setToken(responseRegisterDTO.getAccessToken());

        assertEquals(401, validatableResponse.extract().statusCode());
        assertEquals("You should be authorised", errorResponseDTO.getMessage());
        assertFalse(errorResponseDTO.getSuccess());
    }
}
