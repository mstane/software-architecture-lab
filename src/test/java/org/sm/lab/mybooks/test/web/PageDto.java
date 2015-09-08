package org.sm.lab.mybooks.test.web;

import java.io.Serializable;
import java.util.List;

public class PageDto<T> implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private int pageNumber;
    private int pageSize;
    private List<T> content;
    private int totalPages;

    public PageDto() {

    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

    


}
