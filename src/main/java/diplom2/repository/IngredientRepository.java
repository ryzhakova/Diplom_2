package diplom2.repository;

import diplom2.dto.respose.dto.ResponseGetIngredients;
import diplom2.entity.Ingredient;
import diplom2.rest.clients.IngredientRestClient;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;

import java.util.List;

@Story("Storage of ingredients")
public class IngredientRepository {

    public final static IngredientRepository ingredientRepository = new IngredientRepository();

    @DisplayName("Getting ingredients")
    public List<Ingredient> getIngredients() {
        return IngredientRestClient.getAllIngredients().extract().as(ResponseGetIngredients.class).getData();
    }
}
