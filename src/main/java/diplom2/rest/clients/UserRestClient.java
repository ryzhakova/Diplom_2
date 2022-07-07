package diplom2.rest.clients;

import diplom2.entity.User;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

@Story("Creating and authorizing a user")
public class UserRestClient extends BasicRestClient{
    public static final String REGISTER_PATH = "/api/auth/register";
    public static final String LOGIN_PATH = "/api/auth/login";
    public static final String USER_DATA_PATH = "/api/auth/user";


    @Step("Creating a user")
    public static ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then();
    }

    @Step("User Authorization")
    public static ValidatableResponse authorizationUser(User user) {
        return given()
                .spec(getBaseSpec())
                .and()
                .header(new Header("Authorization", user.getToken()))
                .body(user)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Changing user data")
    public static ValidatableResponse setUserData(User user) {
        return given()
                .spec(getBaseSpec())
                .header(new Header("Authorization", user.getToken()))
                .body(user)
                .patch(USER_DATA_PATH)
                .then();
    }

    @Step("User Registration")
    public static ValidatableResponse registerUser(User user) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then();
    }

    @Step("Deleting a user")
    public static ValidatableResponse deleteUser(String token) {
        return given()
                .spec(getBaseSpec())
                .and()
                .header(new Header("Authorization", token))
                .when()
                .delete(USER_DATA_PATH)
                .then();
    }
}
