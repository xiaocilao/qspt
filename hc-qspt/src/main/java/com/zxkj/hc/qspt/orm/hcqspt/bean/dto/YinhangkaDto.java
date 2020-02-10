package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.YinhangkaEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class YinhangkaDto extends BaseDto<YinhangkaEntity>{

	private static  final long serialVersionUID = 1L;

	public YinhangkaDto(){
	}

	public static final String TABLE_NAME= "yinhangka";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String APPLY_USER_ID_D = "apply_user_id";
	public static final String APPLY_USER_ID_J = "applyUserId";
	public static final Column APPLY_USER_ID_C= new Column(APPLY_USER_ID_D);

	public static final String YHKH= "yhkh";
	public static final Column YHKH_C= new Column(YHKH);

	public static final String XM= "xm";
	public static final Column XM_C= new Column(XM);

	public static final String SJH= "sjh";
	public static final Column SJH_C= new Column(SJH);

	public static final String YHLX= "yhlx";
	public static final Column YHLX_C= new Column(YHLX);

	public static final String XT_ZXBZ_D = "xt_zxbz";
	public static final String XT_ZXBZ_J = "xtZxbz";
	public static final Column XT_ZXBZ_C= new Column(XT_ZXBZ_D);



	public String getId(){
		return po.getId();
	}

	public void setId(String id){
		 po.setId(id);
	}

	public String getApplyUserId(){
		return po.getApplyUserId();
	}

	public void setApplyUserId(String applyUserId){
		 po.setApplyUserId(applyUserId);
	}

	public String getYhkh(){
		return po.getYhkh();
	}

	public void setYhkh(String yhkh){
		 po.setYhkh(yhkh);
	}

	public String getXm(){
		return po.getXm();
	}

	public void setXm(String xm){
		 po.setXm(xm);
	}

	public String getSjh(){
		return po.getSjh();
	}

	public void setSjh(String sjh){
		 po.setSjh(sjh);
	}

	public String getYhlx(){
		return po.getYhlx();
	}

	public void setYhlx(String yhlx){
		 po.setYhlx(yhlx);
	}

	public Integer getXtZxbz(){
		return Integer.parseInt(po.getXtZxbz());
	}

	public void setXtZxbz(int xtZxbz){
		 po.setXtZxbz(String.valueOf(xtZxbz));
	}

}
