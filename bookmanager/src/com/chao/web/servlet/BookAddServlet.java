package com.chao.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.chao.domain.Book;
import com.chao.service.BookServiceImpl;
import com.chao.util.UUIDUtil;

public class BookAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		Book book = new Book();
		//获取表单数据
		try {
			BeanUtils.populate(book, req.getParameterMap());
			book.setId(UUIDUtil.getUUID());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//实现业务逻辑
		BookServiceImpl service = new BookServiceImpl();
		try {
			service.addBook(book);
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
