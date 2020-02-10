package com.zxkj.hc.qspt.setu.system;

import org.setu.framework.session.AbStractSetuSession;

public class SetuSession extends AbStractSetuSession {
	private static final String USERNAME="userName";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String remoteIp;
	@Override
	public String getShardingFlag() {
		return String.valueOf(getUserId().hashCode());	
	}
	
	@Override
	public String getUserId() {
		return (String) getAttribute(USERID);
	}
	
	public void setUserId(String userId) {
		setAttribute(USERID, userId);
	}
	
	public void setUserName(String userName) {
		setAttribute(USERNAME, userName);
	}
	
	public String getUserName() {
		return (String) getAttribute(USERNAME);
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
}
