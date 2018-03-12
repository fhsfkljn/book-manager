package com.chao.service;

import java.sql.SQLException;
import java.util.List;

import com.chao.dao.BookDaoImpl;
import com.chao.domain.Book;

public class BookServiceImpl {
	
	private BookDaoImpl dao = new BookDaoImpl();
	
	//查找图书
	public List<Book> findAllBooks() throws SQLException{
		return dao.findAllBooks();
	}
	
	//添加图书
	public void addBook(Book book) throws SQLException{
		dao.addBook(book);
	}
	
	//通过id找到图书
	public Book findBookById(String id) throws SQLException {
		return dao.findBookById(id);
	}
	
	//更新图书
	public void updateBook(Book book) throws SQLException{
		dao.updateBook(book);
	}
}
