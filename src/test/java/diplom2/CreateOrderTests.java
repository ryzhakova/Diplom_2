package diplom2;

import diplom2.dto.request.dto.CreateOrderDTO;
import diplom2.dto.respose.dto.ErrorResponseDTO;
import diplom2.dto.respose.dto.ResponseOrderDTO;
import diplom2.dto.respose.dto.ResponseRegisterDTO;
import diplom2.entity.Ingredient;
import diplom2.entity.User;
import diplom2.rest.clients.OrderRestClient;
import diplom2.rest.clients.UserRestClient;
import diplom2.service.UserService;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static diplom2.repository.IngredientRepository.ingredientRepository;
import static org.junit.Assert.*;

@Epic("API Tests")
@DisplayName("Create Order Tests")
public class CreateOrderTests {

    private static User user;
    private static ResponseRegisterDTO responseRegisterDTO;

    @Before
    @DisplayName("Create test data")
    public void setUp() {
        user = UserService.generateRandomUser();
        responseRegisterDTO = UserRestClient.registerUser(user).extract().as(ResponseRegisterDTO.class);
    }

    @After
    @DisplayName("Delete test data")
    public void tearDown() {
        UserRestClient.deleteUser(user.getToken());
    }

    @Test
    @DisplayName("Test for creating an order with an authorized user")
    public void createOrderWithAuthorization() {
        user.setToken(responseRegisterDTO.getAccessToken());
        List<Ingredient> listIngredients = ingredientRepository.getIngredients();
        CreateOrderDTO orderDTO = new CreateOrderDTO(listIngredients.get(0), listIngredients.get(1));

        ValidatableResponse validatableResponse = OrderRestClient.createOrder(orderDTO, user);
        ResponseOrderDTO responseOrderDTO = validatableResponse.extract().as(ResponseOrderDTO.class);

        assertEquals(200, validatableResponse.extract().statusCode());
        assertNotNull(responseOrderDTO.getOrder());
        assertNotNull(responseOrderDTO.getName());
        assertTrue(responseOrderDTO.getSuccess());
    }

    @Test
    @DisplayName("Test for creating an order with an unauthorized user")
    public void createOrderWithoutAuthorization() {
        List<Ingredient> listIngredients = ingredientRepository.getIngredients();
        CreateOrderDTO orderDTO = new CreateOrderDTO(listIngredients.get(0), listIngredients.get(1));

        ValidatableResponse validatableResponse = OrderRestClient.createOrder(orderDTO, user);
        ResponseOrderDTO responseOrderDTO = validatableResponse.extract().as(ResponseOrderDTO.class);

        user.setToken(responseRegisterDTO.getAccessToken());

        assertEquals(200, validatableResponse.extract().statusCode());
        assertNotNull(responseOrderDTO.getOrder());
        assertNotNull(responseOrderDTO.getName());
        assertTrue(responseOrderDTO.getSuccess());
        assertNull(responseOrderDTO.getOrder().getOwner());
    }

    @Test
    @DisplayName("Test for creating an order with ingredients")
    public void createOrderWithIngredients() {
        user.setToken(responseRegisterDTO.getAccessToken());
        List<Ingredient> listIngredients = ingredientRepository.getIngredients();
        CreateOrderDTO orderDTO = new CreateOrderDTO(listIngredients.get(0), listIngredients.get(1));

        ValidatableResponse validatableResponse = OrderRestClient.createOrder(orderDTO, user);
        ResponseOrderDTO responseOrderDTO = validatableResponse.extract().as(ResponseOrderDTO.class);

        assertEquals(200, validatableResponse.extract().statusCode());
        assertNotNull(responseOrderDTO.getOrder());
        assertNotNull(responseOrderDTO.getOrder().getIngredients());
        assertNotNull(responseOrderDTO.getName());
        assertTrue(responseOrderDTO.getSuccess());
    }

    @Test
    @DisplayName("Test for creating an order with no ingredients")
    public void createOrderWithoutIngredients() {
        user.setToken(responseRegisterDTO.getAccessToken());
        CreateOrderDTO orderDTO = new CreateOrderDTO();

        ValidatableResponse validatableResponse = OrderRestClient.createOrder(orderDTO, user);
        ErrorResponseDTO responseOrderDTO = validatableResponse.extract().as(ErrorResponseDTO.class);

        assertEquals(400, validatableResponse.extract().statusCode());
        assertFalse(responseOrderDTO.getSuccess());
        assertEquals("Ingredient ids must be provided", responseOrderDTO.getMessage());
    }

    @Test
    @DisplayName("Test for creating an order with an incorrect hash")
    public void createOrderWithoutIncorrectIngredientsHash() {
        user.setToken(responseRegisterDTO.getAccessToken());
        List<Ingredient> listIngredients = ingredientRepository.getIngredients();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(RandomStringUtils.randomAlphanumeric(16));

        CreateOrderDTO orderDTO = new CreateOrderDTO(listIngredients.get(0), ingredient);
        ValidatableResponse validatableResponse = OrderRestClient.createOrder(orderDTO, user);

        assertEquals(500, validatableResponse.extract().statusCode());
    }
}
