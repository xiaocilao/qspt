package com.zxkj.hc.qspt.orm.hcqspt.bean.vo;

import org.setu.framework.bean.BaseVo;
/**
 * 资料上传返回视图
 * @author 甘晓龙
 * 2018年11月17日
 */
public class ZlscVo extends BaseVo{

	private static final long serialVersionUID = 1125457700181134225L;
	String applyId;
	String flowId;
	String flowInfoId;
	String flowFileId;
	
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getFlowInfoId() {
		return flowInfoId;
	}
	public void setFlowInfoId(String flowInfoId) {
		this.flowInfoId = flowInfoId;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getFlowFileId() {
		return flowFileId;
	}
	public void setFlowFileId(String flowFileId) {
		this.flowFileId = flowFileId;
	}
	

}
