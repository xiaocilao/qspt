package com.zxkj.hc.qspt.orm.hcqspt.bean.vo;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyFlowEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class FlowVo extends ApplyFlowEntity {

    List<LcxxlbVo> infos=new ArrayList<>();

    public List<LcxxlbVo> getInfos() {
        return infos;
    }

    public void setInfos(List<LcxxlbVo> infos) {
        this.infos = infos;
    }
}
