package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.FbDataFgjEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class FbDataFgjDto extends BaseDto<FbDataFgjEntity>{

	private static  final long serialVersionUID = 1L;

	public FbDataFgjDto(){
	}

	public static final String TABLE_NAME= "fb_data_fgj";

	public static final String USER_ID_D = "user_id";
	public static final String USER_ID_J = "userId";
	public static final Column USER_ID_C= new Column(USER_ID_D);

	public static final String BAB_URL_D = "bab_url";
	public static final String BAB_URL_J = "babUrl";
	public static final Column BAB_URL_C= new Column(BAB_URL_D);

	public static final String WFZM_URL_D = "wfzm_url";
	public static final String WFZM_URL_J = "wfzmUrl";
	public static final Column WFZM_URL_C= new Column(WFZM_URL_D);

	public static final String SFK_ID_D = "sfk_id";
	public static final String SFK_ID_J = "sfkId";
	public static final Column SFK_ID_C= new Column(SFK_ID_D);

	public static final String SFZ_ID_D = "sfz_id";
	public static final String SFZ_ID_J = "sfzId";
	public static final Column SFZ_ID_C= new Column(SFZ_ID_D);

	public static final String HKB_ID_D = "hkb_id";
	public static final String HKB_ID_J = "hkbId";
	public static final Column HKB_ID_C= new Column(HKB_ID_D);

	public static final String HYZKZM_ID_D = "hyzkzm_id";
	public static final String HYZKZM_ID_J = "hyzkzmId";
	public static final Column HYZKZM_ID_C= new Column(HYZKZM_ID_D);

	public static final String GHDYFCXY_ID_D = "ghdyfcxy_id";
	public static final String GHDYFCXY_ID_J = "ghdyfcxyId";
	public static final Column GHDYFCXY_ID_C= new Column(GHDYFCXY_ID_D);

	public static final String HT_URL_D = "ht_url";
	public static final String HT_URL_J = "htUrl";
	public static final Column HT_URL_C= new Column(HT_URL_D);

	public static final String QZY_ID_D = "qzy_id";
	public static final String QZY_ID_J = "qzyId";
	public static final Column QZY_ID_C= new Column(QZY_ID_D);



	public String getUserId(){
		return po.getUserId();
	}

	public void setUserId(String userId){
		 po.setUserId(userId);
	}

	public String getBabUrl(){
		return po.getBabUrl();
	}

	public void setBabUrl(String babUrl){
		 po.setBabUrl(babUrl);
	}

	public String getWfzmUrl(){
		return po.getWfzmUrl();
	}

	public void setWfzmUrl(String wfzmUrl){
		 po.setWfzmUrl(wfzmUrl);
	}

	public String getSfkId(){
		return po.getSfkId();
	}

	public void setSfkId(String sfkId){
		 po.setSfkId(sfkId);
	}

	public String getSfzId(){
		return po.getSfzId();
	}

	public void setSfzId(String sfzId){
		 po.setSfzId(sfzId);
	}

	public String getHkbId(){
		return po.getHkbId();
	}

	public void setHkbId(String hkbId){
		 po.setHkbId(hkbId);
	}

	public String getHyzkzmId(){
		return po.getHyzkzmId();
	}

	public void setHyzkzmId(String hyzkzmId){
		 po.setHyzkzmId(hyzkzmId);
	}

	public String getGhdyfcxyId(){
		return po.getGhdyfcxyId();
	}

	public void setGhdyfcxyId(String ghdyfcxyId){
		 po.setGhdyfcxyId(ghdyfcxyId);
	}

	public String getHtUrl(){
		return po.getHtUrl();
	}

	public void setHtUrl(String htUrl){
		 po.setHtUrl(htUrl);
	}

	public String getQzyId(){
		return po.getQzyId();
	}

	public void setQzyId(String qzyId){
		 po.setQzyId(qzyId);
	}

}
