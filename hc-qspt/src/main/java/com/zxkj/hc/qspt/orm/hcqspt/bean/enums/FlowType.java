package com.zxkj.hc.qspt.orm.hcqspt.bean.enums;

public enum FlowType {
	//身份认证
	SFYZ("sfyz"),
	//婚姻状况
	HYZK("hyzk"),
	//户口簿信息
	HKBXX("hkbxx"),
	//购房发票
	GFFP("gffp"),
	//购房合同
	GFHT("gfht"),
	//授权书
	SQS("sqs"),
	//拆迁补偿
	CQBC("cqbc");
	
	private String code;

	private FlowType(String code) {
		this.code = code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
}
