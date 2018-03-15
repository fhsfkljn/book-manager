package com.chao.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.chao.domain.Book;
import com.chao.util.C3P0Util;

public class BookDaoImpl {
	
	/**
	 * 找到所有的图书
	 * @return
	 * @throws SQLException
	 */
	public List<Book> findAllBooks() throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		List<Book> books = qr.query("select * from book", new BeanListHandler<Book>(Book.class));
		//System.out.println(books);
		return books;
	}
	
	/**
	 * 添加图书
	 * @param book
	 * @throws SQLException
	 */
	public void addBook(Book book) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		qr.update("insert into book values(?,?,?,?,?,?)",book.getId(),book.getName(),book.getPrice(),book.getPnum(),book.getCategory(),book.getDescription());
	}
	
	/**
	 * 通过id找到该图书
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
	 * 更新图书
	 * @param book
	 * @throws SQLException
	 */
	public void updateBook(Book book) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		qr.update("update book set name=?,price=?,pnum=?,category=?,description=? where id=?",book.getName(),book.getPrice(),book.getPnum(),book.getCategory(),book.getDescription(),book.getId());
	}
	
	/**
	 * 删除一本图书
	 * @param id
	 * @throws SQLException
	 */
	public void deleteBook(String id) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		qr.update("delete from book where id=?", id);
	}
	
	/**
	 * 批量删除图书
	 * @param ids
	 * @throws SQLException 
	 */
	public void deleteAllBooks(String[] ids) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		Object[][] obj = new Object[ids.length][];
		for(int i=0;i<obj.length;i++){
			obj[i] = new Object[]{ids[i]};
		}
		qr.batch("delete from book where id=?", obj);
	}

	/**
	 * 多条件查询
	 * @param id
	 * @param category
	 * @param name
	 * @param minprice
	 * @param maxprice
	 * @throws SQLException 
	 */
	public List<Book> searchBooks(String id, String category, String name, String minprice, String maxprice) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		String sql = "select * from book where 1=1";
		List<String> list = new ArrayList<String>();
		
		System.out.println(id);
		System.out.println(category);
		System.out.println(name);
		System.out.println(minprice);
		System.out.println(maxprice);
		
		if(!"".equals(id.trim())){
			sql += " and id like ?";
			list.add("%"+id+"%");
		}
		if(!"".equals(name.trim())){
			sql += " and name like ?";
			list.add("%"+name+"%");
		}
		if(!"".equals(category.trim())){
			sql += " and category=?";
			list.add(category);
		}
		if(!"".equals(minprice.trim())){
			sql += " and price>?";
			list.add(minprice);
		}
		if(!"".equals(maxprice.trim())){
			sql += " and price<?";
			list.add(maxprice);
		}
		
		System.out.println(sql);
		
		return qr.query(sql, new BeanListHandler<Book>(Book.class),list.toArray());
	}

	/**
	 * 取得书 的总数
	 * @return
	 * @throws SQLException 
	 */
	public int booksCount() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		long l = (long)qr.query("select count(*) from book", new ScalarHandler(1));
		return (int)l;
	}

	/**
	 * 获得当前分页的图书
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws SQLException 
	 */
	public List<Book> findBooks(int currentPage, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		return qr.query("select * from book limit ?,?", new BeanListHandler<Book>(Book.class),(currentPage-1)*pageSize,pageSize);
	}

	/**
	 * 通过邮箱名获取邮箱
	 * @param email
	 * @throws SQLException 
	 */
	public String getEmailByName(String email) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		return (String) qr.query("select email from users where email=?", new ScalarHandler(),email);
	}

	/**
	 * 根据图书名字找到图书
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public List<Object> searchBookByName(String name) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		List<Object> query = qr.query("select name from book where name like ?", new ColumnListHandler(),"%"+name+"%");
		//System.out.println(query);
		return query;
	}
}
