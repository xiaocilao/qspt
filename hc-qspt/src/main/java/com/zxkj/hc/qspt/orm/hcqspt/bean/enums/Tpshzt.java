package com.zxkj.hc.qspt.orm.hcqspt.bean.enums;

public enum Tpshzt {

	BTG("-1"),//不通过
	DSH("0"),//待审核
	TG("1");//通过

	private String code;

	private Tpshzt(String code) {
		this.code = code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
}
