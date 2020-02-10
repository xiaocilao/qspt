package com.zxkj.hc.qspt.setu.system;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SetuResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3658656979748841873L;
	private String msg;
	private int code=200;
	private Object rows;
	private String time;
	private Integer total=null;
	public SetuResult() {
		time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	public SetuResult(Object obj) {
		this();
		setRows(obj);
	}
	public SetuResult(int code,String msg) {
		this();
		if(code==0) {
			code=500;
		}
		setCode(code);
		setMsg(msg);
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		if(rows==null) {
			return;
		}
		if(!rows.getClass().isArray()&&!(rows instanceof List)) {
			List<Object> list=new ArrayList<>();
			list.add(rows);
			rows=list;
		}
		this.rows = rows;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
}
