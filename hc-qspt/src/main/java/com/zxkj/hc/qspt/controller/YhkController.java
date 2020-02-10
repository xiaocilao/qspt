package com.zxkj.hc.qspt.controller;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.YinhangkaEntity;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/yhk")
public class YhkController extends SetuSessionController {

    @RequestMapping("list")
    List<YinhangkaEntity> list(){
        SetuSession session=(SetuSession) getSetuSession();
        YinhangkaEntity yinhangkaEntity=new YinhangkaEntity();
        yinhangkaEntity.setApplyUserId(session.getUserId());
        yinhangkaEntity.setXtZxbz("0");
        return yinhangkaEntity.queryListBySelf();
    }

    @RequestMapping("add")
    SetuResult add(YinhangkaEntity yinhangkaEntity) throws BusinessWarnException {
        if(StringUtils.isEmpty(yinhangkaEntity.getYhkh())){
            throw new BusinessWarnException("银行卡号不能为空");
        }
        if(StringUtils.isEmpty(yinhangkaEntity.getXm())){
            throw new BusinessWarnException("姓名不能为空");
        }
        if(StringUtils.isEmpty(yinhangkaEntity.getSjh())){
            throw new BusinessWarnException("手机号不能为空");
        }
        if(StringUtils.isEmpty(yinhangkaEntity.getYhlx())){
            throw new BusinessWarnException("银行类型不能为空");
        }
        SetuSession session=(SetuSession) getSetuSession();
        yinhangkaEntity.setApplyUserId(session.getUserId());
        yinhangkaEntity.insertSelf();
        return new SetuResult("添加成功");
    }

    @RequestMapping("delete")
    SetuResult delete(String id) throws BusinessWarnException {
        SetuSession session=(SetuSession) getSetuSession();
        YinhangkaEntity yinhangkaEntity=new YinhangkaEntity();
        yinhangkaEntity.setXtZxbz("1");
        yinhangkaEntity.addConditonItem(new ConditionItem("id","=",id));
        yinhangkaEntity.addConditonItem(new ConditionItem("apply_user_id","=",session.getUserId()));
        if(yinhangkaEntity.updateBySelfCondition()==0){
            throw new BusinessWarnException("操作失败");
        }
        return new SetuResult("删除成功");
    }

}
