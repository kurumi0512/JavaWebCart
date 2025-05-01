package cart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cart.model.dto.ProductDTO;
import cart.service.ProductService;
import cart.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 將商品加入到購物車中暫存
// 將所訂購的商品暫時存放在 session 中
@WebServlet("/product/order/add/cart")
public class OrderAddCartServlet extends HttpServlet {

	private ProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		List<ProductDTO> cart = null; // 購物車
		// 確認 session 中有無購物車資料
		if (session.getAttribute("cart") == null) {
			cart = new ArrayList<>(); // 如果購物車不存在，就建立一個新的空車
		} else {
			cart = (List<ProductDTO>) session.getAttribute("cart"); // 目前在使用中的購物車
		}
		// -------------------------------------------------------------------------
		// 得到要購買的商品 id,從網址中取得商品編號（如：?productId=3）。
		Integer productId = Integer.parseInt(req.getParameter("productId"));
		// 根據 productId 取得 ProductDTO。如果有找到對應商品，就把它加到購物車裡
		Optional<ProductDTO> optProductDTO = productService.findAllProducts().stream()
				.filter(dto -> dto.getProductId().equals(productId)).findFirst();

		if (optProductDTO.isPresent()) {
			// 於購物車中加入一筆商品
			cart.add(optProductDTO.get());
			// 將 cart 資料回存到 session
			session.setAttribute("cart", cart);
		}

		// 重新導向到 /product/order 頁面。回到訂單主頁
		resp.sendRedirect("/JavaWebCart/product/order");

		System.out.println(session.getAttribute("cart"));
	}

}