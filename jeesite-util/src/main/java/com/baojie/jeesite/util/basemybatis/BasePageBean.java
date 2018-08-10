package com.baojie.jeesite.util.basemybatis;

import java.io.Serializable;
import java.util.List;

public class BasePageBean<T> implements Serializable{

	private static final long serialVersionUID = 8470697978259453214L;

	private Long total;
	private List<T> data;

	public BasePageBean(){}

	public BasePageBean(Long total, List<T> data) {
		this.total = total;
		this.data = data;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
