package cart.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/authcode")
public class AuthCodeServlet extends HttpServlet {

	/*
	 * 如果要用自訂認證碼可以使用這個方法,會隨機挑選亂數 0~9 a-z A-Z private String generateAuthCode() {
	 * String chars =
	 * "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 * StringBuffer authcode = new StringBuffer(); Random random = new Random();
	 * for(int i=0;i<4;i++) { int index = random.nextInt(chars.length()); // 隨機取位置
	 * authcode.append(chars.charAt(index)); // 取得該位置的資料 } return
	 * authcode.toString(); }
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Random random = new Random(); // 隨機挑選4個數字
		// %04d代表四位數,不足四位數補0
		String authcode = String.format("%04d", random.nextInt(10000)); // 0000~9999 的隨機數
		// String authcode = generateAuthCode();

		// 將 authcode 存入到HttpSession 屬性中
		HttpSession session = req.getSession();
		session.setAttribute("authcode", authcode); // 注意不是存session!!
		// getOutputStream 非文字的串流
		ImageIO.write(getAuthCodeImage(authcode), "JPEG", resp.getOutputStream());
	}

	// 利用 Java2D 產生動態圖像,BufferedImage把圖形先產生在記憶體中
	private BufferedImage getAuthCodeImage(String authcode) {
		// 建立圖像區域(80x30 TGB)
		BufferedImage img = new BufferedImage(80, 30, BufferedImage.TYPE_INT_RGB);
		// 建立畫布
		Graphics g = img.getGraphics();
		// 設定顏色
		g.setColor(Color.white);
		// 塗滿背景,rect是矩形
		g.fillRect(0, 0, 80, 30); // 全區域
		// 設定顏色
		g.setColor(Color.BLACK);
		// 設定字型
		g.setFont(new Font("Arial", Font.BOLD, 22)); // 字體, 風格:粗體, 大小
		// 繪文字
		g.drawString(authcode, 18, 22); // (18, 22) 表示繪文字左上角的起點,1234驗證碼第一個字開始的位置

		// 隨機畫紅色的干擾線
		g.setColor(Color.RED);
		Random random = new Random();
		for (int i = 0; i < 15; i++) {
			// 座標點
			int x1 = random.nextInt(80); // 0~79,因為畫布是80x30 xy成一條線
			int y1 = random.nextInt(30); // 0~29
			int x2 = random.nextInt(80); // 0~79
			int y2 = random.nextInt(30); // 0~29
			// 繪直線
			g.drawLine(x1, y1, x2, y2);
		}

		return img;
		// img：你畫圖的畫紙（BufferedImage）。g：你拿在手上的畫筆（Graphics）。

	}

}
