package com.chao.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.chao.domain.Book;
import com.chao.service.BookServiceImpl;

public class BookUpdateServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;chatset=UTF-8");
		
		//获取表单数据
		Book book = new Book();
		
		try {
			BeanUtils.populate(book, req.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//处理业务逻辑
		BookServiceImpl service = new BookServiceImpl();
		try {
			service.updateBook(book);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//分发转向
		req.getRequestDispatcher("bookListServlet").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
