package diplom2.rest.clients;

import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

@Story("Adding ingredients")
public class IngredientRestClient extends BasicRestClient {
    public static final String INGREDIENTS_PATH = "/api/ingredients/";

    @Step("Getting all the ingredients")
    public static ValidatableResponse getAllIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }
}
