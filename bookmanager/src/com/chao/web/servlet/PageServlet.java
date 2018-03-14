package com.chao.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao.domain.PageBean;
import com.chao.service.BookServiceImpl;

public class PageServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		int pageSize = 4;  //һҳ�ж��ټ���Ʒ
		int currentPage = 1;  //��ǰҳ
		
		String newCurrentPage = req.getParameter("currentPage");  //��ȡ�µĵ�ǰҳ��
		
		if(newCurrentPage!=null){
			currentPage = Integer.parseInt(newCurrentPage);
		}
		
		BookServiceImpl service = new BookServiceImpl();
		PageBean page = null;
		
		try {
			page = service.findBookPage(currentPage,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		req.setAttribute("page", page);
		req.getRequestDispatcher("/product_list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
