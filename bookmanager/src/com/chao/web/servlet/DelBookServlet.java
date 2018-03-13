package com.chao.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao.service.BookServiceImpl;

public class DelBookServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		//取得表单数据
		String id = req.getParameter("id");
		
		BookServiceImpl service = new BookServiceImpl();
		try {
			service.deleteBook(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		req.getRequestDispatcher("/servlet/bookListServlet").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
