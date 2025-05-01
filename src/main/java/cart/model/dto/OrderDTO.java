package cart.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
	private Integer orderId;
	private Integer userId;
	private Date orderDate;
	// 集合該筆訂單所有的訂單項目
	private List<OrderItemDTO> items = new ArrayList<>();
}

//訂單主檔,代表一筆訂單的整體資訊
//一筆訂單（OrderDTO）中可以包含多筆訂單項目（OrderItemDTO）