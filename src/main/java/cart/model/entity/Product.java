package cart.model.entity;

import lombok.Data;

@Data
public class Product {
	private Integer productId;
	private String productName;
	private Integer price;
	private Integer qty;
	private String imageBase64;
}

//java是駝峰式命名,SQL是product_id,用底線