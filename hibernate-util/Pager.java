package com.joiniot.lock.util;

import java.util.List;

public class Pager<T> {
	private Integer pageNO;
	private Integer pageSize;
	private Integer totalSize;
	private Integer totalPages;
	private Integer start;
	private List<T> items;
	
	public Pager(){
	};
	public Pager(Integer totalSize,Integer pageSize){
		this.totalSize = totalSize;
		this.pageSize = pageSize;
		
		Integer tempInteger = totalSize/pageSize;
		if (totalSize%pageSize != 0) {
			tempInteger ++;
		}
		this.totalPages = tempInteger;
	}
	public Pager(Integer pageNO,Integer totalSize,Integer pageSize){
		this(totalSize,pageSize);
		if (pageNO < 1) {
			pageNO = 1;
		}else if(pageNO > totalPages){
			pageNO = totalPages;
		}
		
		this.pageNO = pageNO;
		
		if (pageNO == 0) {
			this.start = 0;
		}else {
			this.start = (pageNO -1) * pageSize;
		}
	}
	public Integer getPageNO() {
		return pageNO;
	}
	public void setPageNO(Integer pageNO) {
		this.pageNO = pageNO;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	
}
