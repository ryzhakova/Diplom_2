package diplom2.dto.request.dto;

import diplom2.entity.Ingredient;
import io.qameta.allure.Story;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Story("Create Order DTO")
public class CreateOrderDTO {

    private List<IngredientDTO> ingredients;

    public CreateOrderDTO(List<Ingredient> ingredientList) {
        ingredients = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            IngredientDTO ingredientDTO = new IngredientDTO(ingredient);
            ingredients.add(ingredientDTO);
        }
    }

    public CreateOrderDTO(Ingredient... ingredientList) {
        ingredients = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            IngredientDTO ingredientDTO = new IngredientDTO(ingredient);
            ingredients.add(ingredientDTO);
        }
    }

    @Data
    private class IngredientDTO {
        String _id;

        public IngredientDTO(Ingredient ingredient) {
            this._id = ingredient.getId();
        }
    }
}
