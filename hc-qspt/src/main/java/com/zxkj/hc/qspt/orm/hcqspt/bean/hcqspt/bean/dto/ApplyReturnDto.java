package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.ApplyReturnEntity;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
/**
 * 
 * @author: hfl 
 */
public class ApplyReturnDto extends BaseDto<ApplyReturnEntity>{

	private static  final long serialVersionUID = 1L;

	public ApplyReturnDto(){
	}

	public static final String TABLE_NAME= "apply_return";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String SERVICE= "service";
	public static final Column SERVICE_C= new Column(SERVICE);

	public static final String SERVICE_VERSION_D = "service_version";
	public static final String SERVICE_VERSION_J = "serviceVersion";
	public static final Column SERVICE_VERSION_C= new Column(SERVICE_VERSION_D);

	public static final String INPUT_CHARSET_D = "input_charset";
	public static final String INPUT_CHARSET_J = "inputCharset";
	public static final Column INPUT_CHARSET_C= new Column(INPUT_CHARSET_D);

	public static final String SIGN_TYPE_D = "sign_type";
	public static final String SIGN_TYPE_J = "signType";
	public static final Column SIGN_TYPE_C= new Column(SIGN_TYPE_D);

	public static final String SIGN= "sign";
	public static final Column SIGN_C= new Column(SIGN);

	public static final String RETURNSERNO_D = "returnSerNo";
	public static final String RETURNSERNO_J = "returnserno";
	public static final Column RETURNSERNO_C= new Column(RETURNSERNO_D);

	public static final String PARTNER= "partner";
	public static final Column PARTNER_C= new Column(PARTNER);

	public static final String SUBPARTNER= "subpartner";
	public static final Column SUBPARTNER_C= new Column(SUBPARTNER);

	public static final String PAY_INFO_D = "pay_info";
	public static final String PAY_INFO_J = "payInfo";
	public static final Column PAY_INFO_C= new Column(PAY_INFO_D);

	public static final String TRANS_CHANNEL_D = "trans_channel";
	public static final String TRANS_CHANNEL_J = "transChannel";
	public static final Column TRANS_CHANNEL_C= new Column(TRANS_CHANNEL_D);

	public static final String OUT_TRADE_NO_D = "out_trade_no";
	public static final String OUT_TRADE_NO_J = "outTradeNo";
	public static final Column OUT_TRADE_NO_C= new Column(OUT_TRADE_NO_D);

	public static final String TRANSACTION_ID_D = "transaction_id";
	public static final String TRANSACTION_ID_J = "transactionId";
	public static final Column TRANSACTION_ID_C= new Column(TRANSACTION_ID_D);

	public static final String BANK_TYPE_D = "bank_type";
	public static final String BANK_TYPE_J = "bankType";
	public static final Column BANK_TYPE_C= new Column(BANK_TYPE_D);

	public static final String BANK_TRANSNO_D = "bank_transno";
	public static final String BANK_TRANSNO_J = "bankTransno";
	public static final Column BANK_TRANSNO_C= new Column(BANK_TRANSNO_D);

	public static final String TOTAL_FEE_D = "total_fee";
	public static final String TOTAL_FEE_J = "totalFee";
	public static final Column TOTAL_FEE_C= new Column(TOTAL_FEE_D);

	public static final String TRADE_STATE_D = "trade_state";
	public static final String TRADE_STATE_J = "tradeState";
	public static final Column TRADE_STATE_C= new Column(TRADE_STATE_D);

	public static final String NOTIFY_ID_D = "notify_id";
	public static final String NOTIFY_ID_J = "notifyId";
	public static final Column NOTIFY_ID_C= new Column(NOTIFY_ID_D);

	public static final String FEE_TYPE_D = "fee_type";
	public static final String FEE_TYPE_J = "feeType";
	public static final Column FEE_TYPE_C= new Column(FEE_TYPE_D);

	public static final String TIME_END_D = "time_end";
	public static final String TIME_END_J = "timeEnd";
	public static final Column TIME_END_C= new Column(TIME_END_D);

	public static final String TRANSPORT_FEE_D = "transport_fee";
	public static final String TRANSPORT_FEE_J = "transportFee";
	public static final Column TRANSPORT_FEE_C= new Column(TRANSPORT_FEE_D);

	public static final String PRODUCT_FEE_D = "product_fee";
	public static final String PRODUCT_FEE_J = "productFee";
	public static final Column PRODUCT_FEE_C= new Column(PRODUCT_FEE_D);

	public static final String DISCOUNT= "discount";
	public static final Column DISCOUNT_C= new Column(DISCOUNT);

	public static final String ATTACH= "attach";
	public static final Column ATTACH_C= new Column(ATTACH);

	public static final String BUYERID_D = "buyerId";
	public static final String BUYERID_J = "buyerid";
	public static final Column BUYERID_C= new Column(BUYERID_D);

	public static final String EXTERNALID_D = "externalId";
	public static final String EXTERNALID_J = "externalid";
	public static final Column EXTERNALID_C= new Column(EXTERNALID_D);

	public static final String RETCODE= "retcode";
	public static final Column RETCODE_C= new Column(RETCODE);

	public static final String PROMOTION_DETAIL_D = "promotion_detail";
	public static final String PROMOTION_DETAIL_J = "promotionDetail";
	public static final Column PROMOTION_DETAIL_C= new Column(PROMOTION_DETAIL_D);



	public Integer getId(){
		return Integer.parseInt(po.getId());
	}

	public void setId(int id){
		 po.setId(String.valueOf(id));
	}

	public String getService(){
		return po.getService();
	}

	public void setService(String service){
		 po.setService(service);
	}

	public String getServiceVersion(){
		return po.getServiceVersion();
	}

	public void setServiceVersion(String serviceVersion){
		 po.setServiceVersion(serviceVersion);
	}

	public String getInputCharset(){
		return po.getInputCharset();
	}

	public void setInputCharset(String inputCharset){
		 po.setInputCharset(inputCharset);
	}

	public String getSignType(){
		return po.getSignType();
	}

	public void setSignType(String signType){
		 po.setSignType(signType);
	}

	public String getSign(){
		return po.getSign();
	}

	public void setSign(String sign){
		 po.setSign(sign);
	}

	public String getReturnserno(){
		return po.getReturnserno();
	}

	public void setReturnserno(String returnserno){
		 po.setReturnserno(returnserno);
	}

	public String getPartner(){
		return po.getPartner();
	}

	public void setPartner(String partner){
		 po.setPartner(partner);
	}

	public String getSubpartner(){
		return po.getSubpartner();
	}

	public void setSubpartner(String subpartner){
		 po.setSubpartner(subpartner);
	}

	public String getPayInfo(){
		return po.getPayInfo();
	}

	public void setPayInfo(String payInfo){
		 po.setPayInfo(payInfo);
	}

	public String getTransChannel(){
		return po.getTransChannel();
	}

	public void setTransChannel(String transChannel){
		 po.setTransChannel(transChannel);
	}

	public String getOutTradeNo(){
		return po.getOutTradeNo();
	}

	public void setOutTradeNo(String outTradeNo){
		 po.setOutTradeNo(outTradeNo);
	}

	public String getTransactionId(){
		return po.getTransactionId();
	}

	public void setTransactionId(String transactionId){
		 po.setTransactionId(transactionId);
	}

	public String getBankType(){
		return po.getBankType();
	}

	public void setBankType(String bankType){
		 po.setBankType(bankType);
	}

	public String getBankTransno(){
		return po.getBankTransno();
	}

	public void setBankTransno(String bankTransno){
		 po.setBankTransno(bankTransno);
	}

	public Float getTotalFee(){
		return Float.parseFloat(po.getTotalFee());
	}

	public void setTotalFee(float totalFee){
		 po.setTotalFee(String.valueOf(totalFee));
	}

	public Integer getTradeState(){
		return Integer.parseInt(po.getTradeState());
	}

	public void setTradeState(int tradeState){
		 po.setTradeState(String.valueOf(tradeState));
	}

	public String getNotifyId(){
		return po.getNotifyId();
	}

	public void setNotifyId(String notifyId){
		 po.setNotifyId(notifyId);
	}

	public Integer getFeeType(){
		return Integer.parseInt(po.getFeeType());
	}

	public void setFeeType(int feeType){
		 po.setFeeType(String.valueOf(feeType));
	}

	public String getTimeEnd(){
		return po.getTimeEnd();
	}

	public void setTimeEnd(String timeEnd){
		 po.setTimeEnd(timeEnd);
	}

	public Float getTransportFee(){
		return Float.parseFloat(po.getTransportFee());
	}

	public void setTransportFee(float transportFee){
		 po.setTransportFee(String.valueOf(transportFee));
	}

	public Float getProductFee(){
		return Float.parseFloat(po.getProductFee());
	}

	public void setProductFee(float productFee){
		 po.setProductFee(String.valueOf(productFee));
	}

	public Float getDiscount(){
		return Float.parseFloat(po.getDiscount());
	}

	public void setDiscount(float discount){
		 po.setDiscount(String.valueOf(discount));
	}

	public String getAttach(){
		return po.getAttach();
	}

	public void setAttach(String attach){
		 po.setAttach(attach);
	}

	public String getBuyerid(){
		return po.getBuyerid();
	}

	public void setBuyerid(String buyerid){
		 po.setBuyerid(buyerid);
	}

	public String getExternalid(){
		return po.getExternalid();
	}

	public void setExternalid(String externalid){
		 po.setExternalid(externalid);
	}

	public String getRetcode(){
		return po.getRetcode();
	}

	public void setRetcode(String retcode){
		 po.setRetcode(retcode);
	}

	public String getPromotionDetail(){
		return po.getPromotionDetail();
	}

	public void setPromotionDetail(String promotionDetail){
		 po.setPromotionDetail(promotionDetail);
	}

}
