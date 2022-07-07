package diplom2.dto.respose.dto;

import diplom2.entity.Order;
import io.qameta.allure.Story;
import lombok.Data;

@Data
@Story("Response about creating an order")
public class ResponseOrderDTO {
    private Boolean success;
    private String name;
    private Order order;

}
