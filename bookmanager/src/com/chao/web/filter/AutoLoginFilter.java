package com.chao.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chao.domain.User;
import com.chao.service.BookServiceImpl;

public class AutoLoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("��ʼ����ҳ��");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("�����˵�½ҳ��");
		// 1.ת��2������ServletRequest��ServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 2.����ҵ��
		// �õ�cookie���飬�������Ƿ���ֵ
		Cookie[] cookies = req.getCookies();
		String username = "";
		String password = "";
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			System.out.println(cookies[i].getName()+" " + cookies[i].getValue());
			if("user".equals(cookies[i].getName())){
				String value = cookies[i].getValue();
				String[] split = value.split("&");
				username = split[0];
				password = split[1];
			}
		}
		//��½
		BookServiceImpl service = new BookServiceImpl();
		User user = null;
		try {
			user = service.loginUser(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user!=null){
			req.getSession().setAttribute("user", user);
		}
		
		// 3.����
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
