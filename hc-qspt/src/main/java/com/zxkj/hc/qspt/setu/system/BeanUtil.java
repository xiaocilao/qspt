package com.zxkj.hc.qspt.setu.system;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.session.thread.SessionThreadLocalHandler;

import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	private transient static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	public static  SetuSession getSession() {
		return (SetuSession)SessionThreadLocalHandler.localSession.get();
	}
	/**
	  * 新增
	 * @param entity
	 */
	
	public static void setXzField(EntityBean<?, ?, ?> entity) {
		String time=getTimeNow();
		SetuSession session=getSession();
		setField(entity, "xtLrip",session.getRemoteIp());
		setField(entity, "xtLrrid",session.getUserId());
		setField(entity, "xtLrrxm",session.getUserName());
		setField(entity, "xtLrsj",time);
		setXgField(entity,time);

	}
	/**
	  * 注销
	 * @param entity
	 * @param xtZxyy
	 */
	public static void setZxField(EntityBean<?, ?, ?> entity,String xtZxyy) {
		setField(entity, "xtZxbz",QsptConsts.XT_ZXBZ_YZX);
		setField(entity, "xtZxyy",xtZxyy);
		setXgField(entity,getTimeNow());
		
	}
	/**
	 * 修改
	 * @param entity
	 * @param time
	 */
	public static void setXgField(EntityBean<?, ?, ?> entity,String time) {
		SetuSession session=getSession();
		setField(entity, "xtZhxgip",session.getRemoteIp());
		setField(entity, "xtZhxgrid",session.getUserId());
		setField(entity, "xtZhxgrxm",session.getUserName());
		setField(entity, "xtZhxgsj",time);


	}
	/**
	 * 修改
	 * @param entity
	 */
	public static void setXgField(EntityBean<?, ?, ?> entity) {
		setXgField(entity, getTimeNow());
	}
	private static void setField(Object obj,String fieldName,Object fieldValue) {
		try {
			Field field=obj.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("",e);
		}
	}
	
	protected static String getTimeNow() {
	      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      return dateFormat.format(new Date());
	}
}
