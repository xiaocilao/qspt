package com.zxkj.hc.qspt.orm.hcqspt.bean.enums;

public enum Spzt {
	  GQ("-3"),//过期
	  KKSB("-2"),//扣款失败
	  BTG("-1"),//不通过
	  XJ("0"),//新建
	  TJSH("1"),//提交审核
	  TG("2"),//通过
	  DJF("3"),//待缴费
	  KKQQ("4"),//扣款请求
	  KKCG("5"),//扣款成功
	  DYJ("6"),//待邮寄
	  YYJ("7"),//已邮寄
	  YQS("8"),//已签收
	  ZQ("9");//自取
	private String code;
	private Spzt(String code) {
		this.code = code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
}
