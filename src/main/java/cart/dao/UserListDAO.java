package cart.dao;

import java.util.List;

import cart.model.entity.User;

public interface UserListDAO {
	// 查詢所有使用者
	List<User> findAllUser();

	// 查詢單筆資料
	User getId(Integer id);

	User getcompleted(Boolean completed);

	// 新增資料
	void addUser(User user);

	// 修改email
	void updateEmail(String email);

	// 刪除指定user紀錄
	void deleteUser(Integer id);

}
