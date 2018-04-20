package jp.co.alpha.handson.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CounterServlet extends HttpServlet {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;

	private int counter = 1;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int cnt = counter;
		cnt++;

		res.setContentType("text/html; charset=SJIS");
		try {
			PrintWriter writer = res.getWriter();
			writer.println("<HTML><BODY>");
			writer.print("<H1>キミは" + counter + "人目の訪問者だ</H1>");
			writer.println("</BODY></HTML>");
			writer.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
		// ちょっと時間かかる。
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(5) * 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// カウンター更新
		counter = cnt;
	}
}
