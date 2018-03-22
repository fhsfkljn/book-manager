package com.itheima.product.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.product.domain.Order;
import com.itheima.product.domain.OrderItem;
import com.itheima.product.domain.Product;
import com.itheima.product.domain.User;
import com.itheima.product.service.OrderService;

public class OrderServlet extends BaseServlet {

	public void createOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1����װOrder����
		Order order = new Order();
		try {
			BeanUtils.populate(order, request.getParameterMap());
			order.setId(UUID.randomUUID().toString());
			order.setUser((User)request.getSession().getAttribute("user"));//��session�����е��û���Ϣ���浽order������
		} catch (Exception e) {
			e.printStackTrace();
		}
		//2����ȡsession�����еĹ��ﳵ����
		Map<Product, String> cart = (Map<Product, String>) request.getSession().getAttribute("cart");
		
		//3���������ﳵ�е����ݣ���ӵ�orderItem�����У�ͬʱ�Ѷ��orderItem��ӵ�list������
		List<OrderItem> list = new ArrayList<OrderItem>();
		for (Product p : cart.keySet()) {
			OrderItem oi = new OrderItem();
			oi.setOrder(order);//��Order������ӵ�OrderItem��
			oi.setP(p);   //�ѹ��ﳵ�е���Ʒ������ӵ�OrderItem��
			oi.setBuynum(Integer.parseInt(cart.get(p)));//���ﳵ�е���Ʒ����
			
			list.add(oi);//��ÿ����������ӵ�������
		}
		
		//4���Ѽ��Ϸ��뵽Order���� ��
		order.setOrderItems(list);
		
		//���� ҵ���߼�
		OrderService os = new OrderService();
		os.addOrder(order);
		
		//���û���
		request.setAttribute("orderid", order.getId());
		request.setAttribute("money", order.getMoney());
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}
	
	public void findOrderByUserId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		
		OrderService os = new OrderService();
		List<Order> orders = os.findOrdersByUserId(user.getId());
		
		request.setAttribute("orders", orders);
		request.setAttribute("count", orders.size());
		request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
	}
	
	public void findOrderItemsByOrderId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderid = request.getParameter("orderid");
		
		OrderService os = new OrderService();
		Order order = os.findOrdersByOrderId(orderid);
		
		request.setAttribute("order", order);
		request.getRequestDispatcher("/orderInfo.jsp").forward(request, response);
	}

	

}
