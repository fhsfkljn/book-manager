package com.chao.bookStore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.chao.bookStore.domain.User;
import com.chao.bookStore.exception.UserException;
import com.chao.bookStore.service.UserService;

public class RegisterServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//������֤��
		String ckcode = req.getParameter("ckcode");
		String checkcode_session = (String) req.getSession().getAttribute("checkcode_session");
		if(!checkcode_session.equals(ckcode)){
			//�����֤�벻һ��
			req.setAttribute("ckcode_msg", "��֤���������������!");
			req.getRequestDispatcher("/register.jsp").forward(req, resp);
			return;
		}
		//��ȡ������
		User user = new User();
		try {
			BeanUtils.populate(user, req.getParameterMap());
			user.setActiveCode(UUID.randomUUID().toString());
			//����ҵ���߼�
			UserService service = new UserService();
			service.regist(user);
			//�ַ�ת��
			req.setAttribute("user", user);
			req.getRequestDispatcher("/registersuccess.jsp").forward(req, resp);
			
		} catch (UserException e) {
			//e.printStackTrace();
			req.setAttribute("user_msg", e.getMessage());
			req.getRequestDispatcher("/register.jsp").forward(req, resp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
