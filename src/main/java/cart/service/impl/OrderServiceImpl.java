package cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import cart.dao.OrderDAO;
import cart.dao.impl.OrderDAOImpl;
import cart.model.dto.OrderDTO;
import cart.model.dto.OrderItemDTO;
import cart.model.dto.ProductDTO;
import cart.model.entity.Order;
import cart.model.entity.OrderItem;
import cart.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private OrderDAO orderDAO = new OrderDAOImpl();

	@Override
	public void addOrder(Integer userId, List<ProductDTO> cart) {
		Integer quantity = 1; // 固定數量(Homework:如何數量可以調整)
		// 呼叫 orderDAO 的方法，在資料庫新增一筆訂單主檔（只含 userId），並回傳資料庫自動產生的 order_id。
		// 新增訂單主檔後可以得到 orderId
		Integer orderId = orderDAO.addOrder(userId);
		// 逐一新增訂單明細紀錄
		// 從購物車中一個一個拿出商品（每一筆是 ProductDTO 物件）
		// cart 是一個 List<ProductDTO>，也就是購物車的清單
		for (ProductDTO productDTO : cart) {
			orderDAO.addOrderItem(orderId, productDTO.getProductId(), quantity);
		}
	}

	// 查詢某個使用者所有的訂單，並把「主檔 + 明細」都組合成 OrderDTO 回傳。
	@Override
	public List<OrderDTO> findAllOrdersByUserId(Integer userId) {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		// 呼叫 DAO 查出使用者的訂單「主檔」。取得該使用者的訂單主檔資訊
		List<Order> orders = orderDAO.findAllOrdersByUserId(userId);
		// 將資料庫中的 Order 物件轉成 OrderDTO（這是傳輸用的 DTO 物件）。
		for (Order order : orders) {
			// OrderDTO Mapping
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setUserId(order.getUserId());
			orderDTO.setOrderDate(order.getOrderDate());
			// 明細
			for (OrderItem item : orderDAO.findAllOrderItemsByOrderId(order.getOrderId())) {
				// OrderItem 轉 OrderItemDTO
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setItemId(item.getItemId());
				itemDTO.setOrderId(item.getOrderId());
				itemDTO.setProductId(item.getProductId());
				itemDTO.setQuantity(item.getQuantity());
				// 注入
				orderDTO.getItems().add(itemDTO);
				// 最後，將轉換好的 itemDTO 物件加到 orderDTO 物件的 items 清單中（這個清單是 OrderDTO 的一部分）。
				// 這個步驟是把所有的 OrderItemDTO 加進 OrderDTO 的 items 屬性中，組成完整的訂單資料。
			}
		}
		return orderDTOs;
	}

}
