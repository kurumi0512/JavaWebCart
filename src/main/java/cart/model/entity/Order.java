package cart.model.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Order {
	private Integer orderId;
	private Integer userId;
	private Date orderDate;
}

//Order 是單純的資料庫物件（表格對應）
//而 OrderDTO 是為了前端顯示與資料傳輸，封裝更多資訊（包含訂單明細）用的。