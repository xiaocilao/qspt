package com.zxkj.hc.qspt.service.impl;

import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyFlowEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowFileEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.FlowType;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Tpshzt;
import com.zxkj.hc.qspt.service.ApplySpService;
import com.zxkj.hc.qspt.service.WxgzhService;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@Service("applySpService")
public class ApplySpServiceImpl extends SetuSessionController implements ApplySpService {
    @Autowired
    private WxgzhService wxgzhService;

    @Override
    public SetuResult tjzl(String applyId) throws BusinessWarnException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        SetuResult result=new SetuResult();

        ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
        applyRecordEntity.setId(applyId);
        SetuSession session= (SetuSession) getSetuSession();
        applyRecordEntity.setSprId(session.getUserId());
        applyRecordEntity.setSpzt(Spzt.TJSH.getCode());
        applyRecordEntity.queryBySelf();

        ApplyFlowEntity applyFlowEntity=new ApplyFlowEntity();
        applyFlowEntity.setApplyId(applyId);
        applyFlowEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
        List<ApplyFlowEntity> applyFlows=applyFlowEntity.queryListBySelf();
        int tag=1;
        for(ApplyFlowEntity applyFlow:applyFlows){
            FlowFileEntity flowFileEntity=new FlowFileEntity();
            flowFileEntity.setFlowId(applyFlow.getId());
            flowFileEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
            List<FlowFileEntity> flowFiles=flowFileEntity.loadListBySelf();
            int tag2=1;
            for(FlowFileEntity flowFile:flowFiles){
                if(Tpshzt.BTG.getCode().equals(flowFile.getShzt())){
                    tag2=-1;
                }else if(!Tpshzt.TG.getCode().equals(flowFile.getShzt())){
                    tag2=0;
                    break;
                }
            }

            if(tag2==1){
                String flowType=applyFlow.getFlowType();
                String setterName="set"+Character.toUpperCase(flowType.charAt(0))+flowType.substring(1);
                ApplyRecordEntity.class.getMethod(setterName, String.class).invoke(applyRecordEntity,"1");
            }else if(tag2==-1){
                String flowType=applyFlow.getFlowType();
                String setterName="set"+Character.toUpperCase(flowType.charAt(0))+flowType.substring(1);
                ApplyRecordEntity.class.getMethod(setterName, String.class).invoke(applyRecordEntity,"0");
                tag=-1;
            }else{
                tag=0;
                break;
            }
        }

        if(tag==1 && !("1".equals(applyRecordEntity.getSfyz()) && "1".equals(applyRecordEntity.getHyzk())
                && "1".equals(applyRecordEntity.getHkbxx()) && "1".equals(applyRecordEntity.getGffp())
                && "1".equals(applyRecordEntity.getGfht()) && "1".equals(applyRecordEntity.getSqs())
                && "1".equals(applyRecordEntity.getCqbc()))){
            tag=-1;
        }

        if(tag==1){
            applyRecordEntity.setSpzt(Spzt.TG.getCode());
            applyRecordEntity.setZlsh("1");
            BeanUtil.setXgField(applyRecordEntity, getTimeNow());
            if(applyRecordEntity.updateBySelfId()==0){
                throw new BusinessWarnException("操作失败");
            }
            result.setMsg("提交成功：审核通过");
            result.setCode(200);
            //推送通知
            HashMap<String, WxgzhService.TempleteValue> data=new HashMap<>();
            data.put("first",new WxgzhService.TempleteValue("","#000000"));
            data.put("keyword1",new WxgzhService.TempleteValue("审核结果通知","#000000"));
            data.put("keyword2",new WxgzhService.TempleteValue(getTimeNow(),"#000000"));
            data.put("keyword3",new WxgzhService.TempleteValue("审核通过","#00ff00"));
            data.put("keyword4",new WxgzhService.TempleteValue("进入小程序查看详情","#000000"));
            data.put("remark",new WxgzhService.TempleteValue("","#000000"));
            wxgzhService.sendTempleteMessage(session.getUserId(),"7NU985dIpitIGm_o-xZHMv_pSd7-JBtBmjiJQd6g1xE",data);
        }else if(tag==-1){
            applyRecordEntity.setSpzt(Spzt.BTG.getCode());
            applyRecordEntity.setSqs("0");
            applyRecordEntity.setZlsh("0");
            BeanUtil.setXgField(applyRecordEntity, getTimeNow());
            if(applyRecordEntity.updateBySelfId()==0){
                throw new BusinessWarnException("操作失败");
            }
            applyFlowEntity=new ApplyFlowEntity();
            applyFlowEntity.setApplyId(applyId);
            applyFlowEntity.setFlowType(FlowType.SQS.getCode());
            List<ApplyFlowEntity> flows=applyFlowEntity.queryListBySelf();
            if(flows.size()>0){
                ApplyFlowEntity flow=flows.get(0);
                FlowFileEntity flowFileEntity=new FlowFileEntity();
                flowFileEntity.setShzt(Tpshzt.BTG.getCode());
                flowFileEntity.addConditonItem(new ConditionItem("flow_id","=",flow.getId()));
                flowFileEntity.updateBySelfCondition();
            }
            result.setMsg("提交成功：审核不通过");
            result.setCode(201);
            //推送通知
            HashMap<String, WxgzhService.TempleteValue> data=new HashMap<>();
            data.put("first",new WxgzhService.TempleteValue("","#000000"));
            data.put("keyword1",new WxgzhService.TempleteValue("审核结果通知","#000000"));
            data.put("keyword2",new WxgzhService.TempleteValue(getTimeNow(),"#000000"));
            data.put("keyword3",new WxgzhService.TempleteValue("审核不通过","#ff0000"));
            data.put("keyword4",new WxgzhService.TempleteValue("进入小程序查看详情","#000000"));
            data.put("remark",new WxgzhService.TempleteValue("","#000000"));
            wxgzhService.sendTempleteMessage(session.getUserId(),"7NU985dIpitIGm_o-xZHMv_pSd7-JBtBmjiJQd6g1xE",data);
        }else {
            result.setMsg("提交失败：审核未完成");
            result.setCode(202);
        }

        return result;
    }
}
