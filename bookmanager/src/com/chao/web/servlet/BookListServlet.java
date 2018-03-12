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

public class BookListServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//…Ë÷√‰Ø¿¿∆˜±‡¬Î
		resp.setContentType("text/html;charset=UTF-8");
		
		BookServiceImpl service = new BookServiceImpl();
		List<Book> list = null;
		try {
			list = service.findAllBooks();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(list!=null){
			System.out.println(list);
			req.setAttribute("books", list);
			req.getRequestDispatcher("/admin/products/list.jsp").forward(req, resp);;
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
