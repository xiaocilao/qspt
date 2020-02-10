package com.zxkj.hc.qspt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.setu.framework.exception.BusinessRuntimeException;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.jdbc.core.Condition;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.ApplyRecordDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowDataDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowFileDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowInfoDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyFlowEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowDataEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowFileEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowInfoEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.LcxxlbVo;

@RestController()
@RequestMapping("/api/apply")
public class ApplyController extends SetuSessionController{

	private static String SUCCESS="SUCCESS";
	/**
	 * 查询申请人审批记录详情
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/spjl")
    @ResponseBody
    public ApplyRecordEntity spjl(){
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSqrId(getSetuSession().getUserId());
		entity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		//获取最新的一条记录
		List<ApplyRecordEntity> list=entity.queryListBySelf(ApplyRecordDto.XT_ZHXGSJ_D+" desc", 0, 1);
		if(list.size()>0) {
			return list.get(0);
		}
		throw new BusinessRuntimeException("没有审批记录");
	}
	
	/**
	 * 流程 信息列表
	 * @param applyId
	 * @param flowType
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/lcxxlb")
    @ResponseBody
    public List<LcxxlbVo> lcxxlb(String applyId,String flowType) throws BusinessWarnException{
		if(applyId==null) {
			throw new BusinessWarnException("申请记录ID不能为空");
		}
		if(flowType==null) {
			System.err.println("流程为空");
			return null;
		}
		if(flowType.equalsIgnoreCase("sqs")){
			String[] listString ={"hysms","jtzfqkcxsqs","jtzfqksms"};
			try{
				FlowFileEntity flowFileEntity1 = new FlowFileEntity();
				flowFileEntity1.addConditonItem(new ConditionItem("apply_id","=",applyId));
				flowFileEntity1.addConditonItem(new ConditionItem("file_bz","IN",listString));
				flowFileEntity1.deleteBySelfCondition();
			}catch (Exception e){
				System.out.println("删除授权页面失败");
			}
		}
		System.out.println("applyId:"+applyId);
		System.out.println("flowType:"+flowType);
		ApplyFlowEntity entity=new ApplyFlowEntity();
		entity.setApplyId(applyId);
		entity.setFlowType(flowType);
		entity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		entity.queryBySelf();
		String flowId=entity.getId();
		//查询出所有的流程信息
		FlowInfoEntity flowInfoEntity=new FlowInfoEntity();
		flowInfoEntity.setApplyId(applyId);
		flowInfoEntity.setFlowId(flowId);
		flowInfoEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		List<LcxxlbVo> list=flowInfoEntity.queryVoListBySelf(LcxxlbVo.class, false, null, null, FlowInfoDto.SORT, null, null);
		if(list.size()>0) {
			//查询出所有的图片信息 hxt
			FlowInfoEntity flowInfoEntity1=new FlowInfoEntity();
			flowInfoEntity1.setApplyId(applyId);
			flowInfoEntity1.setFlowId(flowId);
			flowInfoEntity1.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
			Map<String,List<FlowInfoEntity>> flowInfoMap=flowInfoEntity1.queryMapPackForListBySelf(FlowInfoDto.ID);

//ceshi
			//查询出所有的流程数据    都是
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
	        	vo.setFlowInfos(flowInfoMap.get(vo.getId()));
	        }
		}
		return list;
    }
	/**
	 * 查询附件详细信息
	 * @param fileId
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/fjxx.png")
    @ResponseBody
    public byte[] fjxx(String fileId) throws BusinessWarnException{
		if(fileId==null) {
			throw new BusinessWarnException("附件ID不能为空");
		}
		FlowFileEntity entity=new FlowFileEntity();
		entity.setId(fileId);
		return entity.queryBlobBySelf(FlowFileDto.FILE_CONTENT_C);
	}

	/**
	 * 获取缩略图
	 * @param fileId
	 * @return
	 * @throws BusinessWarnException
	 */
	@RequestMapping("/smallPicture.png")
	@ResponseBody
	public byte[] smallPicture(String fileId) throws BusinessWarnException{
		if(fileId==null) {
			throw new BusinessWarnException("附件ID不能为空");
		}
		FlowFileEntity entity=new FlowFileEntity();
		entity.setId(fileId);
		return entity.queryBlobBySelf(FlowFileDto.SMALL_PICTURE_C);
	}



	/**
	 * 删除图片
	 * @param
	 * @return 胡小天
	 * @throws
	 */
	@RequestMapping("/delImage")
	@ResponseBody
	public String delImage(String url){
		if(url==null) {
			return "图片删除失败，请联系管理人员！";
		}
		String list[] = url.split("=");
		FlowFileEntity flowFileEntity = new FlowFileEntity();
		flowFileEntity.setId(list[list.length-1]);
		int status = flowFileEntity.deleteBySelfId();
		System.out.println("删除图片的状态为:"+status);
		if(status == 0){
			return "图片删除失败，请联系管理人员！";
		}
		return SUCCESS;
	}
}
