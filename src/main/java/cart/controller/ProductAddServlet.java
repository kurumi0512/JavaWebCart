package cart.controller;

import java.io.IOException;
import java.util.Base64;

import cart.service.ProductService;
import cart.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/product/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) // 設定圖片上傳大小 10M,最大限制檔案10M
public class ProductAddServlet extends HttpServlet {
	// 建立一個服務層（Service Layer）物件，用來處理商品的商業邏輯（新增到資料庫）
	private ProductService productService = new ProductServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收一般表單資料
		String productName = req.getParameter("productName");
		String price = req.getParameter("price");
		String qty = req.getParameter("qty");
		// 接收上傳檔案資料
		// 將 Part 物件 -> 轉 byte[] -> 轉字串 -> 存入到資料表
		// 使用 getPart() 抓取上傳的圖片檔案欄位，這個 Part 是 Servlet 處理 multipart/form-data 上傳的一部分
		Part productImage = req.getPart("productImage");
		// 將 productImage 轉 byte[]
		byte[] bytes = productImage.getInputStream().readAllBytes();
		// 將 byte[] 轉 base64 字串方便儲存到資料庫中，並可以在網頁 <img> 標籤中直接顯示。
		String productImageBase64 = Base64.getEncoder().encodeToString(bytes);

		// 這裡會呼叫 add() 方法，把表單內容和圖片資料存到資料庫中
		productService.add(productName, price, qty, productImageBase64);

		// 重導到 result.jsp,這段是html,商品照片如果要顯示出來要寫img src這段
		String message = String.format(
				"商品新增成功<p/>商品名稱：%s<p/>商品價格：%s<p/>商品庫存：%s<p/>商品照片：<img src='data:image/png;base64,%s'><p/>", productName,
				price, qty, productImageBase64);
		req.setAttribute("resultTitle", "商品新增");
		req.setAttribute("resultMessage", message);
		req.getRequestDispatcher("/WEB-INF/view/cart/result.jsp").forward(req, resp);

	}

}
