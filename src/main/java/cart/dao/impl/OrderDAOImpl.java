package cart.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cart.dao.OrderDAO;
import cart.model.entity.Order;
import cart.model.entity.OrderItem;

public class OrderDAOImpl extends BaseDao implements OrderDAO {

	@Override
	public Integer addOrder(Integer userId) {
		String sql = "insert into `order` (user_id) values(?)";
		// 怕跟命令衝突的話order加上反``號比較好
		Integer orderId = null; // 存放新增後的 order_id 資料
		// 因為後續要取得新增後自動生成的 order_id 所以要加上 Statement.RETURN_GENERATED_KEYS 參數設定
		try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setInt(1, userId);
			pstmt.executeUpdate(); // 執行更新

			// 從這個 ResultSet 中取出主鍵值（取得 order_id）
			// 並存入變數 orderId。getInt(1) 表示取得第一個欄位（主鍵值）。
			ResultSet generateKeys = pstmt.getGeneratedKeys();
			if (generateKeys.next()) { // 有得到 key 資料
				orderId = generateKeys.getInt(1); // 取得新增後自動生成的 order_id
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderId;
	}

	@Override
	public void addOrderItem(Integer orderId, Integer productId, Integer quantity) {
		String sql = "insert into order_item(order_id, product_id, quantity) values(?, ?, ?)";
		// 要先新增order 才能新增orderitem
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			pstmt.setInt(2, productId);
			pstmt.setInt(3, quantity);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return; // 如果新增失敗直接return
		}

		// 扣抵庫存,去product的資料庫扣庫存,product的數量是qty!!
		sql = "update product set qty = qty - ? where product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, quantity);// 把第一個 ? 替換成數量，例如 2
			pstmt.setInt(2, productId);// 把第二個 ? 替換成產品 ID，例如 101
			// update product set qty = qty - 2 where product_id = 101
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 查詢某用戶的所有訂單
	@Override
	public List<Order> findAllOrdersByUserId(Integer userId) {
		List<Order> orders = new ArrayList<>();
		String sql = "select order_id, user_id, order_date from `order` where user_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				// 建立一個新的 Order 物件，並把資料表查出來的每個欄位值注入（Mapping）進物件中
				while (rs.next()) {
					// Mapping
					Order order = new Order();
					order.setOrderId(rs.getInt("order_id"));
					order.setUserId(rs.getInt("user_id"));
					order.setOrderDate(rs.getDate("order_date"));
					// 注入到 orders 集合中
					orders.add(order);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	// 查詢某筆訂單的所有明細項目
	@Override
	public List<OrderItem> findAllOrderItemsByOrderId(Integer orderId) {
		List<OrderItem> items = new ArrayList<>();
		String sql = "select item_id, order_id, product_id, quantity from order_item where order_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 使用 JDBC 的 PreparedStatement 防止 SQL injection，把 ? 替換成傳進來的 orderId。
			pstmt.setInt(1, orderId);

			try (ResultSet rs = pstmt.executeQuery()) {
				// 執行 SQL 查詢，rs.next() 表示有資料就一直往下讀。
				while (rs.next()) {
					// Mapping
					OrderItem item = new OrderItem();
					item.setItemId(rs.getInt("item_id"));
					item.setOrderId(rs.getInt("order_id"));
					item.setProductId(rs.getInt("product_id"));
					item.setQuantity(rs.getInt("quantity"));
					// 將這個項目加到 items 清單中。,注入到 items 集合中
					items.add(item);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// 錯誤處理：如果連線或查詢時出錯，就印出錯誤資訊。
		}

		return items;
	}

}