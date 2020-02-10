package com.zxkj.hc.qspt.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowDataDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowFileDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowInfoDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Tpshzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.FlowVo;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.LcxxlbVo;
import com.zxkj.hc.qspt.service.ApplySpService;
import org.setu.framework.exception.BusinessInfoException;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.jdbc.core.Condition;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.ZlspVo;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;

import javax.annotation.Resource;

@RestController()
@RequestMapping("/api/apply")
public class ApplySpController extends SetuSessionController{
	@Resource
	ApplySpService applySpService;

	/**
	 * 获取自己或者新增的数据用于资料审核列表
	 * @return
	 */
	@RequestMapping("/zlshlb")
	@ResponseBody
	public SetuResult zlshlb(String orderby, int pageOffset, int pageRowSize,String keywords,String filter){
		SetuSession session=(SetuSession) getSetuSession();
		ApplyRecordEntity entity=new ApplyRecordEntity();

		Condition condition=new Condition();
		entity.addConditon(condition);

		condition=new Condition();
		if(StringUtils.isEmpty(filter)){
			condition.add(new ConditionItem("spzt","=",Spzt.TJSH.getCode()));
			condition.add("or",new ConditionItem("spzt","=",Spzt.TG.getCode()));
			condition.add("or",new ConditionItem("spzt","=",Spzt.BTG.getCode()));
		}else{
			condition.add(new ConditionItem("spzt","=",filter));
		}
		entity.addConditon("and",condition);

		entity.addConditonItem("and",new ConditionItem("xt_zxbz","=","0"));

		if(!StringUtils.isEmpty(keywords)){
			entity.addConditonItem("and",new ConditionItem("sqr_xm","like","%"+keywords+"%"));
		}

        SetuResult result=new SetuResult();
        result.setTotal(entity.countBySelf());
        result.setRows(entity.queryListBySelf(orderby,pageOffset,pageRowSize));
        return result;
	}
	@RequestMapping("/zlspRecord")
	@ResponseBody
	public ZlspVo zlspRecord(Integer flag,String userId,String index) throws BusinessInfoException{
		if(flag==null) {
			flag=0;
		}else if(flag>1) {
			throw new BusinessInfoException("没有待审核的记录");
		}
		SetuSession session=(SetuSession) getSetuSession();
		//分配审批人
		ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
		Condition condition=new Condition();
		condition.add(new ConditionItem("num","=",index));
		applyRecordEntity.setSprXm(session.getUserName());
		applyRecordEntity.update(condition);
		ZlspVo zlspVo=new ZlspVo();
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSprId(session.getUserId());
		entity.setSpzt(Spzt.TJSH.getCode());
		entity.setNum(index);
		entity.setXtZxbz("0");
		List<ZlspVo> list=entity.queryVoListBySelf(ZlspVo.class, false, null, null, "xt_zhxgsj asc", 0, 1);
		if(list.size()==0) {
			// 重新分配未分配的记录
			if(entity.bind().sqlUpdateSprForFirstApplyRecord().equals("0")) {
				throw new BusinessInfoException("没有待审核的记录");
			}
			return zlspRecord(flag++,"","");
		}else {
			zlspVo=list.get(0);
			ApplyFlowEntity flowEntity=new ApplyFlowEntity();
			flowEntity.setApplyId(zlspVo.getId());
			flowEntity.setXtZxbz("0");
			zlspVo.setApplyFlows(flowEntity.queryListBySelf());
		}
		return zlspVo;
	}

	/**
	 *图片通过
	 * @param id
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/tptg")
	@ResponseBody
	public SetuResult tptg(String id) throws BusinessWarnException {
		if(id==null) {
			throw new BusinessWarnException("图片ID不能为空");
		}

		FlowFileEntity flowFileEntity=new FlowFileEntity();
		flowFileEntity.setId(id);
		flowFileEntity.queryBySelf();

		ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
		applyRecordEntity.setId(flowFileEntity.getApplyId());
		SetuSession session=(SetuSession) getSetuSession();
		applyRecordEntity.setSprId(session.getUserId());
		applyRecordEntity.setSpzt(Spzt.TJSH.getCode());
		if(applyRecordEntity.countBySelf()==0){
			throw new BusinessWarnException("没有操作此记录的权限");
		}

		flowFileEntity.setShzt(Tpshzt.TG.getCode());
		BeanUtil.setXgField(flowFileEntity, getTimeNow());
		if(flowFileEntity.updateBySelfId()==0){
			throw new BusinessWarnException("操作失败");
		}
		return new SetuResult("图片通过");
	}

	/**
	 * 申请详细
	 * @param applyId
	 * @return
	 * @throws BusinessInfoException
	 */
	@RequestMapping("/sqxx")
	@ResponseBody
	public List<FlowVo> zlspxxd(String applyId) throws BusinessWarnException {
		if(applyId==null) {
			throw new BusinessWarnException("申请记录ID不能为空");
		}

		ApplyFlowEntity applyFlowEntity=new ApplyFlowEntity();
		applyFlowEntity.setApplyId(applyId);
		applyFlowEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		List<FlowVo> entities=applyFlowEntity.queryVoListBySelf(FlowVo.class, false, null, null, null, null, null);
		for(FlowVo entity:entities){
			String flowId=entity.getId();
			//查询出所有的流程信息
			FlowInfoEntity flowInfoEntity=new FlowInfoEntity();
			flowInfoEntity.setApplyId(applyId);
			flowInfoEntity.setFlowId(flowId);
			flowInfoEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
			List<LcxxlbVo> list=flowInfoEntity.queryVoListBySelf(LcxxlbVo.class, false, null, null, FlowInfoDto.SORT, null, null);
			if(list.size()>0) {
				//查询出所有的流程数据
				FlowDataEntity flowDataEntity=new FlowDataEntity();
				flowDataEntity.setFlowId(flowId);
				flowDataEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
				Map<String,List<FlowDataEntity>> flowDatasMap=flowDataEntity.queryMapPackForListBySelf(FlowDataDto.INFO_ID_D);
				//查询出所有的图片信息
				FlowFileEntity flowFileEntity=new FlowFileEntity();
				flowFileEntity.setFlowId(flowId);
				flowFileEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
				Map<String,List<FlowFileEntity>> flowFileMap=flowFileEntity.loadMapPackForListBySelf(FlowFileDto.INFO_ID_D,null,FlowFileDto.SORT);
				//分别拼装数据
				for(LcxxlbVo vo:list) {
					vo.setFlowDatas(flowDatasMap.get(vo.getId()));
					vo.setFlowFiles(flowFileMap.get(vo.getId()));
				}
			}
			entity.setInfos(list);
		}

		return entities;
	}


	/**
	 *图片不通过
	 * @param id
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/tpbtg")
	@ResponseBody
	public SetuResult tpbtg(String id,String reason) throws BusinessWarnException {
		if(id==null) {
			throw new BusinessWarnException("图片ID不能为空");
		}

		FlowFileEntity flowFileEntity=new FlowFileEntity();
		flowFileEntity.setId(id);
		flowFileEntity.queryBySelf();

		ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
		applyRecordEntity.setId(flowFileEntity.getApplyId());
		SetuSession session=(SetuSession) getSetuSession();
		applyRecordEntity.setSprId(session.getUserId());
		applyRecordEntity.setSpzt(Spzt.TJSH.getCode());
		if(applyRecordEntity.countBySelf()==0){
			throw new BusinessWarnException("没有操作此记录的权限");
		}
        applyRecordEntity.setXtZxyy("审核未通过用户,等待再次审核");
        applyRecordEntity.updateBySelfId();
		flowFileEntity.setShzt(Tpshzt.BTG.getCode());
		flowFileEntity.setShyj(reason);
		BeanUtil.setXgField(flowFileEntity, getTimeNow());
		if(flowFileEntity.updateBySelfId()==0){
			throw new BusinessWarnException("操作失败");
		}
		return new SetuResult("图片不通过");
	}

	/**
	 *提交资料
	 * @param applyId
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/tjzl")
	@ResponseBody
	public SetuResult tjzl(String applyId) throws BusinessWarnException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		if(applyId==null) {
			throw new BusinessWarnException("申请ID不能为空");
		}
		return applySpService.tjzl(applyId);
	}

}
