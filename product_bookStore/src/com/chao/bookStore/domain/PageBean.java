package com.chao.bookStore.domain;

import java.util.List;

public class PageBean {
	private int currentPage;// ��ǰҳ��
	private int totalCount;// ������
	private int totalPage;// ��ҳ��
	private int currentCount;// ÿҳ����
	private List<Product> ps;// ÿҳ��ʾ������
	private String category; // ����
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public List<Product> getPs() {
		return ps;
	}
	public void setPs(List<Product> ps) {
		this.ps = ps;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
