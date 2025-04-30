package cart.controller;

import java.io.IOException;
import java.util.List;

import cart.model.dto.OrderDTO;
import cart.model.dto.UserDTO;
import cart.service.OrderService;
import cart.service.impl.OrderServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/product/order/history")
public class OrderHistoryServlet extends HttpServlet {

	private OrderService orderService = new OrderServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");
		int userId = userDTO.getId();

		// 查詢訂單歷史紀錄
		List<OrderDTO> orders = orderService.findAllOrdersByUserId(userId);

		req.setAttribute("orders", orders);
		req.getRequestDispatcher("/WEB-INF/view/cart/history.jsp").forward(req, resp);
	}
}
