package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UploadtableEntity;
import java.util.*;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.utils.DateTimeHelper;
/**
 * 
 * @author: hfl 
 */
public class UploadtableDto extends BaseDto<UploadtableEntity>{

	private static  final long serialVersionUID = 1L;

	public UploadtableDto(){
	}

	public static final String TABLE_NAME= "uploadTable";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String APPLYID_D = "applyId";
	public static final String APPLYID_J = "applyid";
	public static final Column APPLYID_C= new Column(APPLYID_D);

	public static final String USERID_D = "userId";
	public static final String USERID_J = "userid";
	public static final Column USERID_C= new Column(USERID_D);

	public static final String USERNAME_D = "userName";
	public static final String USERNAME_J = "username";
	public static final Column USERNAME_C= new Column(USERNAME_D);

	public static final String IDCARD= "idcard";
	public static final Column IDCARD_C= new Column(IDCARD);

	public static final String HOUSEADDRESS_D = "houseAddress";
	public static final String HOUSEADDRESS_J = "houseaddress";
	public static final Column HOUSEADDRESS_C= new Column(HOUSEADDRESS_D);

	public static final String HOUSEID_D = "houseId";
	public static final String HOUSEID_J = "houseid";
	public static final Column HOUSEID_C= new Column(HOUSEID_D);

	public static final String ZSXMMC= "zsxmmc";
	public static final Column ZSXMMC_C= new Column(ZSXMMC);

	public static final String SWJGMC= "swjgmc";
	public static final Column SWJGMC_C= new Column(SWJGMC);

	public static final String NSRMC= "nsrmc";
	public static final Column NSRMC_C= new Column(NSRMC);

	public static final String NSRBH= "nsrbh";
	public static final Column NSRBH_C= new Column(NSRBH);

	public static final String PZXH= "pzxh";
	public static final Column PZXH_C= new Column(PZXH);

	public static final String JEHJXX= "jehjxx";
	public static final Column JEHJXX_C= new Column(JEHJXX);

	public static final String JEHJDX= "jehjdx";
	public static final Column JEHJDX_C= new Column(JEHJDX);

	public static final String SKXJRQ= "skxjrq";
	public static final Column SKXJRQ_C= new Column(SKXJRQ);

	public static final String ISSUCCESS_D = "isSuccess";
	public static final String ISSUCCESS_J = "issuccess";
	public static final Column ISSUCCESS_C= new Column(ISSUCCESS_D);

	public static final String JNZT= "jnzt";
	public static final Column JNZT_C= new Column(JNZT);

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

	public String getApplyid(){
		return po.getApplyid();
	}

	public void setApplyid(String applyid){
		 po.setApplyid(applyid);
	}

	public String getUserid(){
		return po.getUserid();
	}

	public void setUserid(String userid){
		 po.setUserid(userid);
	}

	public String getUsername(){
		return po.getUsername();
	}

	public void setUsername(String username){
		 po.setUsername(username);
	}

	public String getIdcard(){
		return po.getIdcard();
	}

	public void setIdcard(String idcard){
		 po.setIdcard(idcard);
	}

	public String getHouseaddress(){
		return po.getHouseaddress();
	}

	public void setHouseaddress(String houseaddress){
		 po.setHouseaddress(houseaddress);
	}

	public String getHouseid(){
		return po.getHouseid();
	}

	public void setHouseid(String houseid){
		 po.setHouseid(houseid);
	}

	public String getZsxmmc(){
		return po.getZsxmmc();
	}

	public void setZsxmmc(String zsxmmc){
		 po.setZsxmmc(zsxmmc);
	}

	public String getSwjgmc(){
		return po.getSwjgmc();
	}

	public void setSwjgmc(String swjgmc){
		 po.setSwjgmc(swjgmc);
	}

	public String getNsrmc(){
		return po.getNsrmc();
	}

	public void setNsrmc(String nsrmc){
		 po.setNsrmc(nsrmc);
	}

	public String getNsrbh(){
		return po.getNsrbh();
	}

	public void setNsrbh(String nsrbh){
		 po.setNsrbh(nsrbh);
	}

	public String getPzxh(){
		return po.getPzxh();
	}

	public void setPzxh(String pzxh){
		 po.setPzxh(pzxh);
	}

	public String getJehjxx(){
		return po.getJehjxx();
	}

	public void setJehjxx(String jehjxx){
		 po.setJehjxx(jehjxx);
	}

	public String getJehjdx(){
		return po.getJehjdx();
	}

	public void setJehjdx(String jehjdx){
		 po.setJehjdx(jehjdx);
	}

	public Date getSkxjrq(){
		return  DateTimeHelper.parseToDate(po.getSkxjrq());
	}

	public void setSkxjrq(Date skxjrq){
		 po.setSkxjrq(DateTimeHelper.formatDateTimetoString(skxjrq));
	}

	public String getIssuccess(){
		return po.getIssuccess();
	}

	public void setIssuccess(String issuccess){
		 po.setIssuccess(issuccess);
	}

	public String getJnzt(){
		return po.getJnzt();
	}

	public void setJnzt(String jnzt){
		 po.setJnzt(jnzt);
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
