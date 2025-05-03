package cart.controller;

import java.io.IOException;
import java.util.List;

import cart.model.dto.OrderDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 刪除購物車項目
@WebServlet("/product/cart/item/delete")
public class CartItemDeleteServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/* 從表單中取得名為 index 的參數（這是來自 form 裡面的 hidden input），並轉換為 int。 */
		int index = Integer.parseInt(req.getParameter("index"));
		HttpSession session = req.getSession();
		if (session.getAttribute("cart") != null) {
			List<OrderDTO> cart = (List<OrderDTO>) session.getAttribute("cart");
			cart.remove(index);
			// 回存給 session (可以不用加)
			/*
			 * 回存購物車資料到 Session 中。實務上這行可以不加，因為 cart 是參照型別（List 是物件），你直接修改內容後，Session
			 * 裡的資料也會變。
			 */
			session.setAttribute("cart", cart);
		}

		resp.sendRedirect("/JavaWebCart/product/cart");
		/* 重導回購物車頁面，讓使用者看到刪除後的結果（避免重新整理會重送表單）。 */
	}

}