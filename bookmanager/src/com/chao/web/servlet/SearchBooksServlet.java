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

public class SearchBooksServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		//获取表单数据
		String id = req.getParameter("id");
		String category = req.getParameter("category");
		String name = req.getParameter("name");
		String minprice = req.getParameter("minprice");
		String maxprice = req.getParameter("maxprice");
		
		//调用业务逻辑
		List<Book> books=null;
		BookServiceImpl service = new BookServiceImpl();
		try {
			books = service.searchBooks(id,category,name,minprice,maxprice);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(books);
		req.setAttribute("books", books);
		req.getRequestDispatcher("/admin/products/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
