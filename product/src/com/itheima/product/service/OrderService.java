package com.itheima.product.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.product.dao.OrderDao;
import com.itheima.product.dao.OrderItemDao;
import com.itheima.product.dao.ProductDao;
import com.itheima.product.domain.Order;
import com.itheima.product.exception.OrderException;
import com.itheima.product.util.ManagerThreadLocal;

public class OrderService {
	
	OrderDao orderDao = new OrderDao();
	OrderItemDao orderItemDao = new OrderItemDao();
	ProductDao productDao = new ProductDao();
	
	public void addOrder(Order order){
		try {
			ManagerThreadLocal.startTransacation();
			orderDao.addOrder(order);
			orderItemDao.addOrderItem(order);
			productDao.updateProductNum(order);
			
			ManagerThreadLocal.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ManagerThreadLocal.rollback();
		}
	}

	public List<Order> findOrdersByUserId(int id) {
		try {
			return orderDao.findOrders(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Order findOrdersByOrderId(String orderid) {
		try {
			return orderDao.findOrdersByOrderId(orderid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public void modifyOrderState(String orderid) throws OrderException {
			try {
				orderDao.modifyOrderState(orderid);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new OrderException("ÐÞ¸ÄÊ§°Ü");
			}
	}
}
