package com.chao.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao.domain.Book;
import com.chao.service.BookServiceImpl;

public class BookInfoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		//获取数据
		String id = req.getParameter("id");
		Book book = null;
		
		BookServiceImpl service = new BookServiceImpl();
		try {
			book = service.findBookById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(book);
		req.setAttribute("b", book);
		req.getRequestDispatcher("/product_info.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
