package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.TaxCreatEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class TaxCreatDto extends BaseDto<TaxCreatEntity>{

	private static  final long serialVersionUID = 1L;

	public TaxCreatDto(){
	}

	public static final String TABLE_NAME= "tax_creat";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String PRCSCD= "prcscd";
	public static final Column PRCSCD_C= new Column(PRCSCD);

	public static final String BRH_NO_D = "brh_no";
	public static final String BRH_NO_J = "brhNo";
	public static final Column BRH_NO_C= new Column(BRH_NO_D);

	public static final String OPR_NO_D = "opr_no";
	public static final String OPR_NO_J = "oprNo";
	public static final Column OPR_NO_C= new Column(OPR_NO_D);

	public static final String ABST_CD_D = "abst_cd";
	public static final String ABST_CD_J = "abstCd";
	public static final Column ABST_CD_C= new Column(ABST_CD_D);

	public static final String MID_ITEM_D = "mid_item";
	public static final String MID_ITEM_J = "midItem";
	public static final Column MID_ITEM_C= new Column(MID_ITEM_D);

	public static final String MID_PAY_TYPE_D = "mid_pay_type";
	public static final String MID_PAY_TYPE_J = "midPayType";
	public static final Column MID_PAY_TYPE_C= new Column(MID_PAY_TYPE_D);

	public static final String JYJE= "jyje";
	public static final Column JYJE_C= new Column(JYJE);

	public static final String VOU1_ACC_NO_D = "vou1_acc_no";
	public static final String VOU1_ACC_NO_J = "vou1AccNo";
	public static final Column VOU1_ACC_NO_C= new Column(VOU1_ACC_NO_D);

	public static final String DWMC= "dwmc";
	public static final Column DWMC_C= new Column(DWMC);

	public static final String ZSJGDM= "zsjgdm";
	public static final Column ZSJGDM_C= new Column(ZSJGDM);

	public static final String NSRBM= "nsrbm";
	public static final Column NSRBM_C= new Column(NSRBM);

	public static final String WBSBDZXH= "wbsbdzxh";
	public static final Column WBSBDZXH_C= new Column(WBSBDZXH);

	public static final String QYDM= "qydm";
	public static final Column QYDM_C= new Column(QYDM);

	public static final String JYDM= "jydm";
	public static final Column JYDM_C= new Column(JYDM);

	public static final String TRANSCODE= "transcode";
	public static final Column TRANSCODE_C= new Column(TRANSCODE);

	public static final String ORDER_ID_D = "order_id";
	public static final String ORDER_ID_J = "orderId";
	public static final Column ORDER_ID_C= new Column(ORDER_ID_D);



	public Integer getId(){
		return Integer.parseInt(po.getId());
	}

	public void setId(int id){
		 po.setId(String.valueOf(id));
	}

	public String getPrcscd(){
		return po.getPrcscd();
	}

	public void setPrcscd(String prcscd){
		 po.setPrcscd(prcscd);
	}

	public Integer getBrhNo(){
		return Integer.parseInt(po.getBrhNo());
	}

	public void setBrhNo(int brhNo){
		 po.setBrhNo(String.valueOf(brhNo));
	}

	public Integer getOprNo(){
		return Integer.parseInt(po.getOprNo());
	}

	public void setOprNo(int oprNo){
		 po.setOprNo(String.valueOf(oprNo));
	}

	public String getAbstCd(){
		return po.getAbstCd();
	}

	public void setAbstCd(String abstCd){
		 po.setAbstCd(abstCd);
	}

	public String getMidItem(){
		return po.getMidItem();
	}

	public void setMidItem(String midItem){
		 po.setMidItem(midItem);
	}

	public String getMidPayType(){
		return po.getMidPayType();
	}

	public void setMidPayType(String midPayType){
		 po.setMidPayType(midPayType);
	}

	public String getJyje(){
		return po.getJyje();
	}

	public void setJyje(String jyje){
		 po.setJyje(jyje);
	}

	public String getVou1AccNo(){
		return po.getVou1AccNo();
	}

	public void setVou1AccNo(String vou1AccNo){
		 po.setVou1AccNo(vou1AccNo);
	}

	public String getDwmc(){
		return po.getDwmc();
	}

	public void setDwmc(String dwmc){
		 po.setDwmc(dwmc);
	}

	public String getZsjgdm(){
		return po.getZsjgdm();
	}

	public void setZsjgdm(String zsjgdm){
		 po.setZsjgdm(zsjgdm);
	}

	public String getNsrbm(){
		return po.getNsrbm();
	}

	public void setNsrbm(String nsrbm){
		 po.setNsrbm(nsrbm);
	}

	public String getWbsbdzxh(){
		return po.getWbsbdzxh();
	}

	public void setWbsbdzxh(String wbsbdzxh){
		 po.setWbsbdzxh(wbsbdzxh);
	}

	public String getQydm(){
		return po.getQydm();
	}

	public void setQydm(String qydm){
		 po.setQydm(qydm);
	}

	public String getJydm(){
		return po.getJydm();
	}

	public void setJydm(String jydm){
		 po.setJydm(jydm);
	}

	public String getTranscode(){
		return po.getTranscode();
	}

	public void setTranscode(String transcode){
		 po.setTranscode(transcode);
	}

	public String getOrderId(){
		return po.getOrderId();
	}

	public void setOrderId(String orderId){
		 po.setOrderId(orderId);
	}

}
