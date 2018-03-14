package com.chao.service;

import java.sql.SQLException;
import java.util.List;

import com.chao.dao.BookDaoImpl;
import com.chao.domain.Book;
import com.chao.domain.PageBean;

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

	//ɾ��һ��ͼ��
	public void deleteBook(String id) throws SQLException {
		dao.deleteBook(id);
	}

	//����ɾ��ͼ��
	public void deleteAllBooks(String[] ids) throws SQLException {
		dao.deleteAllBooks(ids);
	}

	//��������ѯ
	public List<Book> searchBooks(String id, String category, String name, String minprice, String maxprice) throws SQLException {
		return dao.searchBooks(id,category,name,minprice,maxprice);
	}

	//�����ҳ
	public PageBean findBookPage(int currentPage, int pageSize) throws SQLException {
		int count = dao.booksCount();   //����� ������
		int totalPage = (int)Math.ceil(count*1.0/pageSize);   //�����ҳ��
		List<Book> books = dao.findBooks(currentPage,pageSize);    //��÷�ҳͼ��
		
		PageBean page = new PageBean();
		page.setCount(count);
		page.setCurrentPage(currentPage);
		page.setBooks(books);
		page.setPageSize(pageSize);
		page.setTotalPage(totalPage);
		
		return page;
	}

}
