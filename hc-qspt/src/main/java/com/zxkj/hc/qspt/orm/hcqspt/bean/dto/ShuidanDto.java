package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import java.util.*;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.utils.DateTimeHelper;
/**
 * 
 * @author: hfl 
 */
public class ShuidanDto extends BaseDto<ShuidanEntity>{

	private static  final long serialVersionUID = 1L;

	public ShuidanDto(){
	}

	public static final String TABLE_NAME= "shuidan";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String APPLY_ID_D = "apply_id";
	public static final String APPLY_ID_J = "applyId";
	public static final Column APPLY_ID_C= new Column(APPLY_ID_D);

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

	public static final String ZFZT= "zfzt";
	public static final Column ZFZT_C= new Column(ZFZT);

	public static final String SDTP= "sdtp";
	public static final Column SDTP_C= new Column(SDTP);

	public static final String KK= "kk";
	public static final Column KK_C= new Column(KK);

	public static final String YHS= "yhs";
	public static final Column YHS_C= new Column(YHS);



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

	public Integer getZfzt(){
		return Integer.parseInt(po.getZfzt());
	}

	public void setZfzt(int zfzt){
		 po.setZfzt(String.valueOf(zfzt));
	}

	public Long getSdtp(){
		return Long.parseLong(po.getSdtp());
	}

	public void setSdtp(byte[] sdtp){
		 po.setSdtp(String.valueOf(sdtp));
	}

	public String getKk(){
		return po.getKk();
	}

	public void setKk(String kk){
		po.setKk(kk);
	}

	public String getYhs(){
		return po.getYhs();
	}

	public void setYhs(String yhs){
		po.setYhs(yhs);
	}
}
