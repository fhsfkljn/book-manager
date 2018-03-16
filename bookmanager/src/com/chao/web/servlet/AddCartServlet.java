package com.chao.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chao.domain.Book;
import com.chao.service.BookServiceImpl;

public class AddCartServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String  id = req.getParameter("id");
		
		BookServiceImpl service = new BookServiceImpl();
		try {
			Book book = service.findBookById(id);
			//System.out.println(id);
			//System.out.println(book);
			
			//从session中把购物车取出来
			HttpSession session = req.getSession();
			Map<Book, String> cart = (Map<Book, String>) session.getAttribute("cart");
			int num = 1;
			//第一次访问购物车为空
			if(cart == null){
				cart = new HashMap<Book, String>();
			}
			if(cart.containsKey(book)){
				num = Integer.parseInt(cart.get(book)) + 1;
			}
			
			cart.put(book, num+"");
			session.setAttribute("cart", cart);
			
			//System.out.println(cart);
			
			out.println("<a href='"+req.getContextPath()+"/servlet/pageServlet'>继续购物</a>");
			out.println("<a href='"+req.getContextPath()+"/cart.jsp'>查看购物车</a>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
