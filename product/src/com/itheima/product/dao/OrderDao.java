package com.itheima.product.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.product.domain.Order;
import com.itheima.product.domain.OrderItem;
import com.itheima.product.domain.Product;
import com.itheima.product.util.C3P0Util;
import com.itheima.product.util.ManagerThreadLocal;

public class OrderDao {
	// 添加定单
	public void addOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.update(ManagerThreadLocal.getConnection(),
				"INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)", order.getId(),
				order.getMoney(), order.getReceiverAddress(), order
						.getReceiverName(), order.getReceiverPhone(), order
						.getPaystate(), order.getOrdertime(), order.getUser()
						.getId());
	}

	// 根据用户id查询所有定单
	public List<Order> findOrders(int id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from orders where user_id=?",
				new BeanListHandler<Order>(Order.class), id);
	}

	// 查询指定用户的定单信息
	public Order findOrdersByOrderId(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		// 得到一个定单
		Order order = qr.query("select * from orders where id=?",
				new BeanHandler<Order>(Order.class), orderid);
		// 得到当前定单中的所有定单项
		// List<OrderItem> orderItems =
		// qr.query("select * from orderItem where order_id=? ", new
		// BeanListHandler<OrderItem>(OrderItem.class),orderid);
		// 得到所有定单项中的商品信息
		// List<Product> products =
		// qr.query("select * from products where id in(select product_id from orderitem where order_id=?)",
		// new BeanListHandler<Product>(Product.class),orderid);

		List<OrderItem> orderItems = qr
				.query("SELECT * FROM orderitem o,products p WHERE p.id=o.product_id AND order_id=?",
						new ResultSetHandler<List<OrderItem>>() {

							public List<OrderItem> handle(ResultSet rs)
									throws SQLException {
								List<OrderItem> orderItems = new ArrayList<OrderItem>();
								while (rs.next()) {

									// 封装OrderItem对象
									OrderItem oi = new OrderItem();
									oi.setBuynum(rs.getInt("buynum"));
									// 封装Product对象
									Product p = new Product();
									p.setName(rs.getString("name"));
									p.setPrice(rs.getDouble("price"));
									// 把每个p对象封装到OrderItem对象中
									oi.setP(p);
									// 把每个OrderItem对象封装到集合中
									orderItems.add(oi);
								}
								return orderItems;
							}

						}, orderid);

		// 把所有的定单项orderItems添加到主单对象Order中
		order.setOrderItems(orderItems);

		return order;
	}

	// 修改定单支付状态
	public void modifyOrderState(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update orders set paystate=1 where id=?", orderid);
	}
}
