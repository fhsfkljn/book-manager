package com.chao.service;

import java.sql.SQLException;
import java.util.List;

import com.chao.dao.BookDaoImpl;
import com.chao.domain.Book;
import com.chao.domain.PageBean;

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

	//删除一本图书
	public void deleteBook(String id) throws SQLException {
		dao.deleteBook(id);
	}

	//批量删除图书
	public void deleteAllBooks(String[] ids) throws SQLException {
		dao.deleteAllBooks(ids);
	}

	//多条件查询
	public List<Book> searchBooks(String id, String category, String name, String minprice, String maxprice) throws SQLException {
		return dao.searchBooks(id,category,name,minprice,maxprice);
	}

	//处理分页
	public PageBean findBookPage(int currentPage, int pageSize) throws SQLException {
		int count = dao.booksCount();   //获得书 的总数
		int totalPage = (int)Math.ceil(count*1.0/pageSize);   //获得总页数
		List<Book> books = dao.findBooks(currentPage,pageSize);    //获得分页图书
		
		PageBean page = new PageBean();
		page.setCount(count);
		page.setCurrentPage(currentPage);
		page.setBooks(books);
		page.setPageSize(pageSize);
		page.setTotalPage(totalPage);
		
		return page;
	}

}
