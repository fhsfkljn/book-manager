package com.chao.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao.service.BookServiceImpl;

public class SearchBookAJAXServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		String name = req.getParameter("name");
		//System.out.println(name);
		BookServiceImpl service = new BookServiceImpl();
		try {
			List<Object> list = service.searchBookByName(name);
			//System.out.println(list);
			String str="";
			for(int i=0;i<list.size();i++){
				if(i>0){
					str += ",";
				}
				str += list.get(i);
				//System.out.println(list);
			}
			resp.getWriter().print(str);
			//System.out.println(str);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
