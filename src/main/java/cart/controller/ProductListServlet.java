package cart.controller;

import java.io.IOException;
import java.util.List;

import cart.model.dto.ProductDTO;
import cart.service.ProductService;
import cart.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product/list")
public class ProductListServlet extends HttpServlet {
	// 雖然 ProductService 看起來是介面型態//但它其實「裝的是
	// ProductServiceImpl()實體」這樣可以隨時更換實作而不改動其餘程式碼。
	private ProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 取得所有商品
		List<ProductDTO> productDTOs = productService.findAllProducts();
		// 把資料（List<ProductDTO>）放到 request 中，給 JSP 使用。
		req.setAttribute("productDTOs", productDTOs);
		req.getRequestDispatcher("/WEB-INF/view/cart/product_list.jsp").forward(req, resp);

	}

}

/*
 * 使用者（瀏覽器）→ 請求 /product/list ↓ ProductListServlet 接收請求 ↓ 呼叫
 * ProductServiceImpl.findAllProducts() ↓ → 內部呼叫
 * ProductDAOImpl.findAllProducts() 從資料庫查詢 ↓ → 將資料轉成 ProductDTO List ↓ 放到
 * req.setAttribute(...) ↓ 轉發給 JSP 顯示
 */