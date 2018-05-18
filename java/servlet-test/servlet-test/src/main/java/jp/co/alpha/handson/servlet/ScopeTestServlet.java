package jp.co.alpha.handson.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScopeTestServlet extends HttpServlet {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().substring(req.getContextPath().length() + "/scope".length());
		String view = null;
		switch (path) {
		case "":
		case "/":
			view = "/view/scope/show.jsp";
			break;
		case "/input":
			view = "/view/scope/input.jsp";
			break;
		default:
			view = "/index.jsp";
			break;
		}

		req.getRequestDispatcher(view).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String requestValue = req.getParameter("requestValue");
		String sessionValue = req.getParameter("sessionValue");
		String cookieValue = req.getParameter("cookieValue");
		
		// Request属性設定
		req.setAttribute("MyRequestAttribute", requestValue);

		// Session属性設定
		req.getSession().setAttribute("MySessionAttribute", sessionValue);

		// Cookie設定
		Cookie cookie = new Cookie("MyCookieValue", cookieValue);
		cookie.setMaxAge(3600);
		res.addCookie(cookie);
		
		req.getRequestDispatcher("/view/scope/show.jsp").forward(req, res);
	}
}
