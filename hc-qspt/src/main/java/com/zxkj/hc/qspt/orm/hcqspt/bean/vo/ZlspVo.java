package com.zxkj.hc.qspt.orm.hcqspt.bean.vo;

import java.util.List;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyFlowEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;

/**
 * 资料审批视图
 * @author hfl
 * 2018年11月17日
 */
public class ZlspVo extends ApplyRecordEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6499479449900164263L;
	List<ApplyFlowEntity> applyFlows;
	public List<ApplyFlowEntity> getApplyFlows() {
		return applyFlows;
	}
	public void setApplyFlows(List<ApplyFlowEntity> applyFlows) {
		this.applyFlows = applyFlows;
	}
	
}
