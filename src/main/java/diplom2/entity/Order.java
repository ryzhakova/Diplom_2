package diplom2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Story;
import lombok.Data;

import java.util.List;

@Data
@Story("Order Description")
public class Order {
    private List<Ingredient> ingredients;
    @JsonProperty("_id")
    private String id;
    private Order.Owner owner;
    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private Integer number;
    private Long price;

    @Data
    public static class Owner {
        private String name;
        private String email;
        private String createdAt;
        private String updatedAt;
    }
}
