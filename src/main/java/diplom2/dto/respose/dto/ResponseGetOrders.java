package diplom2.dto.respose.dto;

import diplom2.entity.Order;
import io.qameta.allure.Story;
import lombok.Data;

import java.util.List;

@Data
@Story("Response to order receipt")
public class ResponseGetOrders {
    private Boolean success;
    private List<Order> orders;
    private Long total;
    private Long totalToday;
}
