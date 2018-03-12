package com.chao.service;

import java.sql.SQLException;
import java.util.List;

import com.chao.dao.BookDaoImpl;
import com.chao.domain.Book;

public class BookServiceImpl {
	
	private BookDaoImpl dao = new BookDaoImpl();
	
	//����ͼ��
	public List<Book> findAllBooks() throws SQLException{
		return dao.findAllBooks();
	}
	
	//���ͼ��
	public void addBook(Book book) throws SQLException{
		dao.addBook(book);
	}
	
	//ͨ��id�ҵ�ͼ��
	public Book findBookById(String id) throws SQLException {
		return dao.findBookById(id);
	}
	
	//����ͼ��
	public void updateBook(Book book) throws SQLException{
		dao.updateBook(book);
	}
}
