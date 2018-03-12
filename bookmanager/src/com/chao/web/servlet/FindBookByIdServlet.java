package com.chao.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao.domain.Book;
import com.chao.service.BookServiceImpl;

public class FindBookByIdServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		//��ȡ������
		String id = req.getParameter("id");
		
		//����ҵ���߼�
		BookServiceImpl service = new BookServiceImpl();
		Book book = null;
		try {
			book = service.findBookById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//�ַ�ת��
		req.setAttribute("book", book);
		req.getRequestDispatcher("/admin/products/edit.jsp").forward(req, resp);
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
