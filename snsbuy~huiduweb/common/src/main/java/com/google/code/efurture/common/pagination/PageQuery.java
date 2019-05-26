package com.google.code.efurture.common.pagination;

public class PageQuery {

	/**
	 * 当前页数, 默认是第一页
	 * */
	private int currentPageNum = 1;

	/**
	 * 分页大小, 默认为15
	 * */
	private int pageSize = 15;

	/**
	 * 总条数， 记录总条数
	 * */
	private int totalCount;

	public int getStartIndex() {
		return (currentPageNum - 1) * pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageNum() {
		if (totalCount <= 0) {
			return 0;
		}
		return (((totalCount - 1) / pageSize) + 1);
	}

	public boolean hasDatas() {
		return totalCount > 0;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		if (currentPageNum <= 0) {
			currentPageNum = 1;
		}
		this.currentPageNum = currentPageNum;
	}

	public int getPageNum(){
		return this.getCurrentPageNum();
	}
	
	public void setPageNum(int pageNum){
		this.setCurrentPageNum(pageNum);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPageNum;
		result = prime * result + pageSize;
		result = prime * result + totalCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageQuery other = (PageQuery) obj;
		if (currentPageNum != other.currentPageNum)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (totalCount != other.totalCount)
			return false;
		return true;
	}
	
	
	
}
