package com.chao.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chao.domain.Book;

public class ChangeNumServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String id = req.getParameter("id");
		String num = req.getParameter("num");
		
		Book book = new Book();
		book.setId(id);
		
		HttpSession session = req.getSession();
		Map<Book, String> cart = (Map<Book, String>) session.getAttribute("cart");
		
		if("0".equals(num)){
			cart.remove(book);
		}
		
		//由于覆写了hashcode与equals方法，可以通过id来判断对象是否相等
		if(cart.containsKey(book)){
			cart.put(book, num);
		}
		
		resp.sendRedirect(req.getContextPath()+"/cart.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
