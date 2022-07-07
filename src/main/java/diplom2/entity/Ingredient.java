package diplom2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Story;
import lombok.Data;

import java.io.Serializable;

@Data
@Story("Response about changing user data")
public class Ingredient implements Serializable {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String type;
    private Long proteins;
    private Long fat;
    private Long carbohydrates;
    private Long calories;
    private Long price;
    private String image;
    private String image_mobile;
    private String image_large;
    @JsonProperty("__v")
    private Long v;
}
