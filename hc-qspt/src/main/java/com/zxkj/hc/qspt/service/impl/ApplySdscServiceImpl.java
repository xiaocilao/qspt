package com.zxkj.hc.qspt.service.impl;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.service.ApplySdscService;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.springframework.stereotype.Service;

@Service("applySdscService")
public class ApplySdscServiceImpl extends SetuSessionController implements ApplySdscService {
    @Override
    public int scsd(ShuidanEntity shuidan) {
        System.out.println("进入税单插入函数，同时更新缴费状态！");
        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
        applyRecordEntity.setSpzt(Spzt.DJF.getCode());
        applyRecordEntity.setQsjs("1");
        //不能用session，因为取不到数据，hxt
//        SetuSession session = (SetuSession) getSetuSession();
//        applyRecordEntity.setQsjsrId(session.getUserId());
//        applyRecordEntity.setQsjsrXm(session.getUserName());
        applyRecordEntity.setQsjssj(getTimeNow());
        BeanUtil.setXgField(applyRecordEntity, getTimeNow());
        applyRecordEntity.addConditonItem(new ConditionItem("id", "=", shuidan.getApplyId()));
//        applyRecordEntity.addConditonItem("and", new ConditionItem("spr_id", "=", session.getUserId()));
        applyRecordEntity.addConditonItem("and", new ConditionItem("spzt", "=", Spzt.TG.getCode()));
//        System.out.println("用户id："+session.getUserId());
//        System.out.println("用户名："+session.getUserName());
        System.out.println("税单id:"+shuidan.getApplyId());
        if (applyRecordEntity.updateBySelfCondition() == 1) {
            shuidan.insertSelf();
            return 1;
        }
        return 0;
    }
}
