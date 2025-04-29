package cart.service;

import cart.model.dto.UserDTO;

public interface UserLoginService {

	// 驗證是否登入成功
	// username, password ,authCode = 登入三劍客 (使用者所自行輸入的資訊,帳號、密碼以及要輸入的驗證碼)
	// sessionAuthCode = 目前存在session的驗證碼 (會員登入中 驗證碼的1234之類的)
	UserDTO login(String username, String password, String authCode, String sessionAuthCode);
}
