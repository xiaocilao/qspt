package com.zxkj.hc.qspt.service;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyUserEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UserEntity;

public interface LoginService {
	
	public ApplyUserEntity applyUserWxLogin(String userName,String wechatId,String wechatUnionId,boolean tag);
	
	public UserEntity userLogin(String userName,String password,String wxOpenid);
	
	public ApplyUserEntity bankLogin(String userName,String phoneNumber,String Name);
	
	public ApplyUserEntity banksign(String userName,String phoneNumber,String Name);
	
	//用前端传递过来的code获取微信openid
	public String[] getXcxOpenid(String code);

    public String getGzhOpenid(String code);
}
