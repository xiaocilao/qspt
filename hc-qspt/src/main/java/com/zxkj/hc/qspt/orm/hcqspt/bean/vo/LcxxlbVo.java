package com.zxkj.hc.qspt.orm.hcqspt.bean.vo;

import java.util.ArrayList;
import java.util.List;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowDataEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowFileEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowInfoEntity;
/**
 * 查询流程信息列表视图
 * @author hfl
 * 2018年11月17日
 */
public class LcxxlbVo extends FlowInfoEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<FlowDataEntity> flowDatas=new ArrayList<>();
	List<FlowFileEntity> flowFiles=new ArrayList<>();
	List<FlowInfoEntity> flowInfos=new ArrayList<>();
	public List<FlowDataEntity> getFlowDatas() {
		return flowDatas;
	}
	public void setFlowDatas(List<FlowDataEntity> flowDatas) {
		this.flowDatas = flowDatas;
	}
	public List<FlowFileEntity> getFlowFiles() {
		return flowFiles;
	}
	public void setFlowFiles(List<FlowFileEntity> flowFiles) {
		this.flowFiles = flowFiles;
	}
	public List<FlowInfoEntity> getFlowInfos() {
		return flowInfos;
	}
	public void setFlowInfos(List<FlowInfoEntity> flowInfos) {
		this.flowInfos = flowInfos;
	}




}
