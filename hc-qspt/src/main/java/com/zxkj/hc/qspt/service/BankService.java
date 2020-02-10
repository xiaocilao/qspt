package com.zxkj.hc.qspt.service;

import java.sql.Date;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.setu.system.SetuResult;

public interface BankService {
	int setBank(ShuidanEntity shuidan,String isSuccess);
	//通过调用时间和时间差来查询记录
	SetuResult getBank(String time) throws Exception;
}
