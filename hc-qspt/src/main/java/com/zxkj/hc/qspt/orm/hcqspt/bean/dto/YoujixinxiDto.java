package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.YoujixinxiEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class YoujixinxiDto extends BaseDto<YoujixinxiEntity>{

	private static  final long serialVersionUID = 1L;

	public YoujixinxiDto(){
	}

	public static final String TABLE_NAME= "youjixinxi";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String APPLY_ID_D = "apply_id";
	public static final String APPLY_ID_J = "applyId";
	public static final Column APPLY_ID_C= new Column(APPLY_ID_D);

	public static final String SHUIDAN_ID_D = "shuidan_id";
	public static final String SHUIDAN_ID_J = "shuidanId";
	public static final Column SHUIDAN_ID_C= new Column(SHUIDAN_ID_D);

	public static final String SJR= "sjr";
	public static final Column SJR_C= new Column(SJR);

	public static final String SJHM= "sjhm";
	public static final Column SJHM_C= new Column(SJHM);

	public static final String LQDZ= "lqdz";
	public static final Column LQDZ_C= new Column(LQDZ);

	public static final String STATUS= "status";
	public static final Column STATUS_C= new Column(STATUS);

	public static final String USER_ID_D = "user_id";
	public static final String USER_ID_J = "userId";
	public static final Column USER_ID_C= new Column(USER_ID_D);

	public static final String POST_NUMBER_D = "post_number";
	public static final String POST_NUMBER_J = "postNumber";
	public static final Column POST_NUMBER_C= new Column(POST_NUMBER_D);

	public static final String POST_NAME_D = "post_name";
	public static final String POST_NAME_J = "postName";
	public static final Column POST_NAME_C= new Column(POST_NAME_D);

	public static final String POSTAL_CODE_D = "postal_code";
	public static final String POSTAL_CODE_J = "postal_code";
	public static final Column POSTAL_CODE_C= new Column(POSTAL_CODE_D);

	public String getStatus(){
		return po.getStatus();
	}

	public void setStatus(String status){
		po.setStatus(status);
	}

	public String getPostNumber(){
		return po.getPostNumber();
	}

	public void setPostNumber(String postNumber){
		po.setPostNumber(postNumber);
	}

	public String getPostName(){
		return po.getPostName();
	}

	public void setPostName(String postName){
		po.setPostName(postName);
	}


	public String getId(){
		return po.getId();
	}

	public void setId(String id){
		 po.setId(id);
	}

	public String getApplyId(){
		return po.getApplyId();
	}

	public void setApplyId(String applyId){
		 po.setApplyId(applyId);
	}

	public String getShuidanId(){
		return po.getShuidanId();
	}

	public void setShuidanId(String shuidanId){
		 po.setShuidanId(shuidanId);
	}
	public String getUserId(){
		return po.getUserId();
	}

	public void setUserId(String userId){
		po.setUserId(userId);
	}

	public String getPostalCode(){
		return po.getPostalCode();
	}

	public void setPostalCode(String postalCode){
		po.setUserId(postalCode);
	}

	public String getSjr(){
		return po.getSjr();
	}


	public void setSjr(String sjr){
		 po.setSjr(sjr);
	}

	public String getSjhm(){
		return po.getSjhm();
	}

	public void setSjhm(String sjhm){
		 po.setSjhm(sjhm);
	}

	public String getLqdz(){
		return po.getLqdz();
	}

	public void setLqdz(String lqdz){
		 po.setLqdz(lqdz);
	}

}
