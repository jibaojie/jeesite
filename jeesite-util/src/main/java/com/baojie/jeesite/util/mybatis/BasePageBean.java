package com.baojie.jeesite.util.mybatis;

import java.io.Serializable;
import java.util.List;


/**
 * @author 冀保杰
 * * @date：2018-08-16
 * @desc：
 */
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
