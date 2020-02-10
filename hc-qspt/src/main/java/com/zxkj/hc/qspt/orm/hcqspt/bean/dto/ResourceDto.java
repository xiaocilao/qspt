package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ResourceEntity;
import java.util.*;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.utils.DateTimeHelper;
/**
 * 
 * @author: hfl 
 */
public class ResourceDto extends BaseDto<ResourceEntity>{

	private static  final long serialVersionUID = 1L;

	public ResourceDto(){
	}

	public static final String TABLE_NAME= "resource";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String NAME= "name";
	public static final Column NAME_C= new Column(NAME);

	public static final String TYPE= "type";
	public static final Column TYPE_C= new Column(TYPE);

	public static final String RES_URL_D = "res_url";
	public static final String RES_URL_J = "resUrl";
	public static final Column RES_URL_C= new Column(RES_URL_D);

	public static final String STATUS= "status";
	public static final Column STATUS_C= new Column(STATUS);

	public static final String XT_LRSJ_D = "xt_lrsj";
	public static final String XT_LRSJ_J = "xtLrsj";
	public static final Column XT_LRSJ_C= new Column(XT_LRSJ_D);

	public static final String XT_LRRXM_D = "xt_lrrxm";
	public static final String XT_LRRXM_J = "xtLrrxm";
	public static final Column XT_LRRXM_C= new Column(XT_LRRXM_D);

	public static final String XT_LRRID_D = "xt_lrrid";
	public static final String XT_LRRID_J = "xtLrrid";
	public static final Column XT_LRRID_C= new Column(XT_LRRID_D);

	public static final String XT_LRIP_D = "xt_lrip";
	public static final String XT_LRIP_J = "xtLrip";
	public static final Column XT_LRIP_C= new Column(XT_LRIP_D);

	public static final String XT_ZHXGSJ_D = "xt_zhxgsj";
	public static final String XT_ZHXGSJ_J = "xtZhxgsj";
	public static final Column XT_ZHXGSJ_C= new Column(XT_ZHXGSJ_D);

	public static final String XT_ZHXGRXM_D = "xt_zhxgrxm";
	public static final String XT_ZHXGRXM_J = "xtZhxgrxm";
	public static final Column XT_ZHXGRXM_C= new Column(XT_ZHXGRXM_D);

	public static final String XT_ZHXGRID_D = "xt_zhxgrid";
	public static final String XT_ZHXGRID_J = "xtZhxgrid";
	public static final Column XT_ZHXGRID_C= new Column(XT_ZHXGRID_D);

	public static final String XT_ZHXGIP_D = "xt_zhxgip";
	public static final String XT_ZHXGIP_J = "xtZhxgip";
	public static final Column XT_ZHXGIP_C= new Column(XT_ZHXGIP_D);

	public static final String XT_ZXBZ_D = "xt_zxbz";
	public static final String XT_ZXBZ_J = "xtZxbz";
	public static final Column XT_ZXBZ_C= new Column(XT_ZXBZ_D);

	public static final String XT_ZXYY_D = "xt_zxyy";
	public static final String XT_ZXYY_J = "xtZxyy";
	public static final Column XT_ZXYY_C= new Column(XT_ZXYY_D);



	public String getId(){
		return po.getId();
	}

	public void setId(String id){
		 po.setId(id);
	}

	public String getName(){
		return po.getName();
	}

	public void setName(String name){
		 po.setName(name);
	}

	public Integer getType(){
		return Integer.parseInt(po.getType());
	}

	public void setType(int type){
		 po.setType(String.valueOf(type));
	}

	public String getResUrl(){
		return po.getResUrl();
	}

	public void setResUrl(String resUrl){
		 po.setResUrl(resUrl);
	}

	public Integer getStatus(){
		return Integer.parseInt(po.getStatus());
	}

	public void setStatus(int status){
		 po.setStatus(String.valueOf(status));
	}

	public Date getXtLrsj(){
		return  DateTimeHelper.parseToDate(po.getXtLrsj());
	}

	public void setXtLrsj(Date xtLrsj){
		 po.setXtLrsj(DateTimeHelper.formatDateTimetoString(xtLrsj));
	}

	public String getXtLrrxm(){
		return po.getXtLrrxm();
	}

	public void setXtLrrxm(String xtLrrxm){
		 po.setXtLrrxm(xtLrrxm);
	}

	public String getXtLrrid(){
		return po.getXtLrrid();
	}

	public void setXtLrrid(String xtLrrid){
		 po.setXtLrrid(xtLrrid);
	}

	public String getXtLrip(){
		return po.getXtLrip();
	}

	public void setXtLrip(String xtLrip){
		 po.setXtLrip(xtLrip);
	}

	public Date getXtZhxgsj(){
		return  DateTimeHelper.parseToDate(po.getXtZhxgsj());
	}

	public void setXtZhxgsj(Date xtZhxgsj){
		 po.setXtZhxgsj(DateTimeHelper.formatDateTimetoString(xtZhxgsj));
	}

	public String getXtZhxgrxm(){
		return po.getXtZhxgrxm();
	}

	public void setXtZhxgrxm(String xtZhxgrxm){
		 po.setXtZhxgrxm(xtZhxgrxm);
	}

	public String getXtZhxgrid(){
		return po.getXtZhxgrid();
	}

	public void setXtZhxgrid(String xtZhxgrid){
		 po.setXtZhxgrid(xtZhxgrid);
	}

	public String getXtZhxgip(){
		return po.getXtZhxgip();
	}

	public void setXtZhxgip(String xtZhxgip){
		 po.setXtZhxgip(xtZhxgip);
	}

	public Integer getXtZxbz(){
		return Integer.parseInt(po.getXtZxbz());
	}

	public void setXtZxbz(int xtZxbz){
		 po.setXtZxbz(String.valueOf(xtZxbz));
	}

	public String getXtZxyy(){
		return po.getXtZxyy();
	}

	public void setXtZxyy(String xtZxyy){
		 po.setXtZxyy(xtZxyy);
	}

}
