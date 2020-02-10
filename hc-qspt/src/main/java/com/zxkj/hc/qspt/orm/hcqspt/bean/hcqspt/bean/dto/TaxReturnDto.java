package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.TaxReturnEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class TaxReturnDto extends BaseDto<TaxReturnEntity>{

	private static  final long serialVersionUID = 1L;

	public TaxReturnDto(){
	}

	public static final String TABLE_NAME= "tax_return";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String PROC_RESULT_D = "proc_result";
	public static final String PROC_RESULT_J = "procResult";
	public static final Column PROC_RESULT_C= new Column(PROC_RESULT_D);

	public static final String ERR_TEX_D = "err_tex";
	public static final String ERR_TEX_J = "errTex";
	public static final Column ERR_TEX_C= new Column(ERR_TEX_D);

	public static final String TRANS_DATE_D = "trans_date";
	public static final String TRANS_DATE_J = "transDate";
	public static final Column TRANS_DATE_C= new Column(TRANS_DATE_D);

	public static final String TRANS_TIME_D = "trans_time";
	public static final String TRANS_TIME_J = "transTime";
	public static final Column TRANS_TIME_C= new Column(TRANS_TIME_D);

	public static final String MID_SERIAL_NO_D = "mid_serial_no";
	public static final String MID_SERIAL_NO_J = "midSerialNo";
	public static final Column MID_SERIAL_NO_C= new Column(MID_SERIAL_NO_D);

	public static final String CTL_SERIAL_NO_D = "ctl_serial_no";
	public static final String CTL_SERIAL_NO_J = "ctlSerialNo";
	public static final Column CTL_SERIAL_NO_C= new Column(CTL_SERIAL_NO_D);

	public static final String WJBZ= "wjbz";
	public static final Column WJBZ_C= new Column(WJBZ);

	public static final String WJMC= "wjmc";
	public static final Column WJMC_C= new Column(WJMC);

	public static final String JYJE= "jyje";
	public static final Column JYJE_C= new Column(JYJE);

	public static final String BZ= "bz";
	public static final Column BZ_C= new Column(BZ);

	public static final String ORDER_ID_D = "order_id";
	public static final String ORDER_ID_J = "orderId";
	public static final Column ORDER_ID_C= new Column(ORDER_ID_D);

	public static final String RESOURCE= "resource";
	public static final Column RESOURCE_C= new Column(RESOURCE);



	public Integer getId(){
		return Integer.parseInt(po.getId());
	}

	public void setId(int id){
		 po.setId(String.valueOf(id));
	}

	public String getProcResult(){
		return po.getProcResult();
	}

	public void setProcResult(String procResult){
		 po.setProcResult(procResult);
	}

	public String getErrTex(){
		return po.getErrTex();
	}

	public void setErrTex(String errTex){
		 po.setErrTex(errTex);
	}

	public String getTransDate(){
		return po.getTransDate();
	}

	public void setTransDate(String transDate){
		 po.setTransDate(transDate);
	}

	public String getTransTime(){
		return po.getTransTime();
	}

	public void setTransTime(String transTime){
		 po.setTransTime(transTime);
	}

	public String getMidSerialNo(){
		return po.getMidSerialNo();
	}

	public void setMidSerialNo(String midSerialNo){
		 po.setMidSerialNo(midSerialNo);
	}

	public String getCtlSerialNo(){
		return po.getCtlSerialNo();
	}

	public void setCtlSerialNo(String ctlSerialNo){
		 po.setCtlSerialNo(ctlSerialNo);
	}

	public String getWjbz(){
		return po.getWjbz();
	}

	public void setWjbz(String wjbz){
		 po.setWjbz(wjbz);
	}

	public String getWjmc(){
		return po.getWjmc();
	}

	public void setWjmc(String wjmc){
		 po.setWjmc(wjmc);
	}

	public String getJyje(){
		return po.getJyje();
	}

	public void setJyje(String jyje){
		 po.setJyje(jyje);
	}

	public String getBz(){
		return po.getBz();
	}

	public void setBz(String bz){
		 po.setBz(bz);
	}

	public String getOrderId(){
		return po.getOrderId();
	}

	public void setOrderId(String orderId){
		 po.setOrderId(orderId);
	}

	public String getResource(){
		return po.getResource();
	}

	public void setResource(String resource){
		 po.setResource(resource);
	}

}
