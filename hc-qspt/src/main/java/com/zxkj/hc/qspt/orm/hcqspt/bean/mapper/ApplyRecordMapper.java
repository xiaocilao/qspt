package com.zxkj.hc.qspt.orm.hcqspt.bean.mapper;
import org.setu.framework.bean.EntityMapper;
import org.setu.framework.jdbc.annotation.SetuSQLUpdate;
/**
 * 
 * @author: hfl 
 */
import org.setu.framework.jdbc.annotation.TableMapper;
import org.setu.framework.jdbc.enums.SQLQueryFrom;
@TableMapper(dbConnResolveClass=org.setu.framework.jdbc.handler.BaseDbConnResolve.class)
public interface ApplyRecordMapper extends EntityMapper{
	/**
	   * 获取第一条审批数据并实现分派
	 * @return
	 */
	@SetuSQLUpdate(name="sqlUpdateSprForFirstApplyRecord",from= SQLQueryFrom.Session)
	String sqlUpdateSprForFirstApplyRecord();
}

