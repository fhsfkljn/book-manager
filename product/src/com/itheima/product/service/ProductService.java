package com.itheima.product.service;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.List;

import com.itheima.product.dao.ProductDao;
import com.itheima.product.domain.Order;
import com.itheima.product.domain.PageBean;
import com.itheima.product.domain.Product;

public class ProductService {
	//创建一个Dao对象
	ProductDao productDao = new ProductDao();
	
	public List<Product> findAllBooks(){
		try {
			return productDao.findAllBooks();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//添加图书
	public void addBook(Product product){
		try {
			productDao.addBook(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Product findBookById(String id) {
		try {
			return productDao.findBookById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateBook(Product product) {
		try {
			productDao.updateBook(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteBook(String id) {
		try {
			productDao.delBook(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleAllBooks(String[] ids) {
		try {
			productDao.deleAllBooks(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> searchBooks(String id, String category, String name,
			String minprice, String maxprice) {
		try {
			return productDao.searchBooks(id,category,name,minprice,maxprice);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//分页查询
	public PageBean findBooksPage(int currentPage, int pageSize,String category) {
		
		try {
			int count  = productDao.count(category);//得到总记录数
			int totalPage = (int)Math.ceil(count*1.0/pageSize); //求出总页数
			List<Product> products= productDao.findBooks(currentPage,pageSize,category);
			
			//把5个变量封装到PageBean中，做为返回值
			PageBean pb = new PageBean();
			pb.setProducts(products);
			pb.setCount(count);
			pb.setCurrentPage(currentPage);
			pb.setPageSize(pageSize);
			pb.setTotalPage(totalPage);
			//在pageBean中添加属性，用于点击上一页或下一页时使用
			pb.setCategory(category);
			
			return pb;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<Object> searchBookByName(String name) {
		try {
			return productDao.searchBookByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	

	
	
	
}
