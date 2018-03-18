package com.chao.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao.domain.User;
import com.chao.service.BookServiceImpl;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		BookServiceImpl service = new BookServiceImpl();
		try {
			User user = service.loginUser(username,password);
			
			if(user!=null){
				//判断是否勾选自动登陆
				String autoLogin = req.getParameter("autologin");
				System.out.println(autoLogin);
				//得到浏览器传来的cookie,并保存用户名和密码
				Cookie cookie = new Cookie("user",user.getUsername() + "&" + user.getPassword());
				cookie.setPath("/");
				//如果勾选了自动登陆
				if(autoLogin!=null){
					cookie.setMaxAge(60*60*24);
				}else {
					cookie.setMaxAge(0);
				}
				resp.addCookie(cookie);
				req.getSession().setAttribute("user", user);
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}else {
				req.setAttribute("msg", "用户名或密码错误！");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
