package com.chao.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.chao.domain.Book;
import com.chao.util.C3P0Util;

public class BookDaoImpl {
	
	/**
	 * �ҵ����е�ͼ��
	 * @return
	 * @throws SQLException
	 */
	public List<Book> findAllBooks() throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		List<Book> books = qr.query("select * from book", new BeanListHandler<Book>(Book.class));
		System.out.println(books);
		return books;
	}
	
	/**
	 * ���ͼ��
	 * @param book
	 * @throws SQLException
	 */
	public void addBook(Book book) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		qr.update("insert into book values(?,?,?,?,?,?)",book.getId(),book.getName(),book.getPrice(),book.getPnum(),book.getCategory(),book.getDescription());
	}
	
	/**
	 * ͨ��id�ҵ���ͼ��
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Book findBookById(String id) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		Book book = qr.query("select * from book where id=?", new BeanHandler<Book>(Book.class),id);
		return book;
	}
	
	/**
	 * ����ͼ��
	 * @param book
	 * @throws SQLException
	 */
	public void updateBook(Book book) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		qr.update("update book set name=?,price=?,pnum=?,category=?,description=? where id=?",book.getName(),book.getPrice(),book.getPnum(),book.getCategory(),book.getDescription(),book.getId());
	}
}
