package cart.service.impl;

import java.util.List;

import cart.dao.ProductDAO;
import cart.dao.impl.ProductDAOImpl;
import cart.model.dto.ProductDTO;
import cart.model.entity.Product;
import cart.service.ProductService;

public class ProductServiceImpl implements ProductService {
	// 用介面當作變數型別，但使用的是該介面的實作類別來建立物件」，這就是 Java 中的**多型（Polymorphism）**概念
	// 雖然 productDAO 看起來是介面型態，但它其實「裝的是 ProductDAOImpl 實體」。這樣可以隨時更換實作而不改動其餘程式碼。
	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	// ✅ 接收來自 Servlet 層的表單輸入（都是字串），然後轉換成 Product 物件交給 DAO 層儲存。
	public void add(String productName, String price, String qty, String productImageBase64) {
		Product product = new Product();
		product.setProductName(productName);
		product.setPrice(Integer.parseInt(price)); // 將字串轉為整數
		product.setQty(Integer.parseInt(qty));
		product.setImageBase64(productImageBase64);

		productDAO.add(product); // 呼叫 DAO 層方法，寫入資料庫
	}

	@Override
	public List<ProductDTO> findAllProducts() {
		return productDAO.findAllProducts() // 呼叫productDAO.findAllProducts()的方法->List<Product>
				.stream() // ... Product
				// .map(product -> mapToProductDTO(product)) // ... ProductDTO
				.map(this::mapToProductDTO) // ... ProductDTO
				.toList(); // List<ProductDTO>
	}

	// ProductDTO 轉 Product 物件的方法
	private Product mapToProduct(ProductDTO productDTO) {
		Product product = new Product();
		// 將 productDTO 的屬性內容逐一設定到 product 屬性中
		product.setProductId(productDTO.getProductId());
		product.setProductName(productDTO.getProductName());
		product.setPrice(productDTO.getPrice());
		product.setQty(productDTO.getQty());
		product.setImageBase64(productDTO.getImageBase64());
		return product;
	}

	// Product 轉 ProductDTO 物件的方法
	private ProductDTO mapToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(product.getProductId());
		productDTO.setProductName(product.getProductName());
		productDTO.setPrice(product.getPrice());
		productDTO.setQty(product.getQty());
		productDTO.setImageBase64(product.getImageBase64());
		// 重要 !! 自訂欄位：總價（單價 x 數量）
		Integer total = product.getPrice() * product.getQty();
		productDTO.setTotal(total);

		return productDTO;
	}

	// ✅ 呼叫 DAO 的刪除方法，負責刪掉對應 productId 的商品資料。
	@Override
	public void delete(Integer productId) {
		productDAO.delete(productId);
	}

}