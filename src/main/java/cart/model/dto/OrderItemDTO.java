package cart.model.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
	private Integer itemId;
	private Integer orderId;
	private Integer productId;
	private Integer quantity;
}
//代表訂單中的某一筆「商品項目」，例如：買了哪一個商品、數量是多少。
//orderId 對應 OrderDTO 的主鍵 orderId