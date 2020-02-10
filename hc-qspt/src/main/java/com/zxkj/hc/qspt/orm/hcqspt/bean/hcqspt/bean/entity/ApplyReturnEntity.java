package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.mapper.ApplyReturnMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto.ApplyReturnDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="apply_return")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class ApplyReturnEntity extends EntityBean<ApplyReturnEntity,ApplyReturnDto,ApplyReturnMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",length=11,nullable=false,columnDefinition="int")
	@SetuDescription(describe="")
	private String id;

	@Column(name="service",length=20,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String service;

	@Column(name="service_version",length=8,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String serviceVersion;

	@Column(name="input_charset",length=8,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String inputCharset;

	@Column(name="sign_type",length=8,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String signType;

	@Column(name="sign",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String sign;

	@Column(name="returnSerNo",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String returnserno;

	@Column(name="partner",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String partner;

	@Column(name="subpartner",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String subpartner;

	@Column(name="pay_info",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String payInfo;

	@Column(name="trans_channel",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String transChannel;

	@Column(name="out_trade_no",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String outTradeNo;

	@Column(name="transaction_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String transactionId;

	@Column(name="bank_type",length=16,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String bankType;

	@Column(name="bank_transno",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String bankTransno;

	@Column(name="total_fee",length=10,columnDefinition="decimal")
	@SetuDescription(describe="")
	private String totalFee;

	@Column(name="trade_state",length=11,columnDefinition="int")
	@SetuDescription(describe="")
	private String tradeState;

	@Column(name="notify_id",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String notifyId;

	@Column(name="fee_type",length=11,columnDefinition="int")
	@SetuDescription(describe="")
	private String feeType;

	@Column(name="time_end",length=17,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String timeEnd;

	@Column(name="transport_fee",length=10,columnDefinition="decimal")
	@SetuDescription(describe="")
	private String transportFee;

	@Column(name="product_fee",length=10,columnDefinition="decimal")
	@SetuDescription(describe="")
	private String productFee;

	@Column(name="discount",length=10,columnDefinition="decimal")
	@SetuDescription(describe="")
	private String discount;

	@Column(name="attach",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String attach;

	@Column(name="buyerId",length=30,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String buyerid;

	@Column(name="externalId",length=30,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String externalid;

	@Column(name="retcode",length=1,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String retcode;

	@Column(name="promotion_detail",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String promotionDetail;

	public ApplyReturnEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setService(String service){
		this.service=service;
	}

	public String getService(){
		return this.service;
	}

	public void setServiceVersion(String serviceVersion){
		this.serviceVersion=serviceVersion;
	}

	public String getServiceVersion(){
		return this.serviceVersion;
	}

	public void setInputCharset(String inputCharset){
		this.inputCharset=inputCharset;
	}

	public String getInputCharset(){
		return this.inputCharset;
	}

	public void setSignType(String signType){
		this.signType=signType;
	}

	public String getSignType(){
		return this.signType;
	}

	public void setSign(String sign){
		this.sign=sign;
	}

	public String getSign(){
		return this.sign;
	}

	public void setReturnserno(String returnserno){
		this.returnserno=returnserno;
	}

	public String getReturnserno(){
		return this.returnserno;
	}

	public void setPartner(String partner){
		this.partner=partner;
	}

	public String getPartner(){
		return this.partner;
	}

	public void setSubpartner(String subpartner){
		this.subpartner=subpartner;
	}

	public String getSubpartner(){
		return this.subpartner;
	}

	public void setPayInfo(String payInfo){
		this.payInfo=payInfo;
	}

	public String getPayInfo(){
		return this.payInfo;
	}

	public void setTransChannel(String transChannel){
		this.transChannel=transChannel;
	}

	public String getTransChannel(){
		return this.transChannel;
	}

	public void setOutTradeNo(String outTradeNo){
		this.outTradeNo=outTradeNo;
	}

	public String getOutTradeNo(){
		return this.outTradeNo;
	}

	public void setTransactionId(String transactionId){
		this.transactionId=transactionId;
	}

	public String getTransactionId(){
		return this.transactionId;
	}

	public void setBankType(String bankType){
		this.bankType=bankType;
	}

	public String getBankType(){
		return this.bankType;
	}

	public void setBankTransno(String bankTransno){
		this.bankTransno=bankTransno;
	}

	public String getBankTransno(){
		return this.bankTransno;
	}

	public void setTotalFee(String totalFee){
		this.totalFee=totalFee;
	}

	public String getTotalFee(){
		return this.totalFee;
	}

	public void setTradeState(String tradeState){
		this.tradeState=tradeState;
	}

	public String getTradeState(){
		return this.tradeState;
	}

	public void setNotifyId(String notifyId){
		this.notifyId=notifyId;
	}

	public String getNotifyId(){
		return this.notifyId;
	}

	public void setFeeType(String feeType){
		this.feeType=feeType;
	}

	public String getFeeType(){
		return this.feeType;
	}

	public void setTimeEnd(String timeEnd){
		this.timeEnd=timeEnd;
	}

	public String getTimeEnd(){
		return this.timeEnd;
	}

	public void setTransportFee(String transportFee){
		this.transportFee=transportFee;
	}

	public String getTransportFee(){
		return this.transportFee;
	}

	public void setProductFee(String productFee){
		this.productFee=productFee;
	}

	public String getProductFee(){
		return this.productFee;
	}

	public void setDiscount(String discount){
		this.discount=discount;
	}

	public String getDiscount(){
		return this.discount;
	}

	public void setAttach(String attach){
		this.attach=attach;
	}

	public String getAttach(){
		return this.attach;
	}

	public void setBuyerid(String buyerid){
		this.buyerid=buyerid;
	}

	public String getBuyerid(){
		return this.buyerid;
	}

	public void setExternalid(String externalid){
		this.externalid=externalid;
	}

	public String getExternalid(){
		return this.externalid;
	}

	public void setRetcode(String retcode){
		this.retcode=retcode;
	}

	public String getRetcode(){
		return this.retcode;
	}

	public void setPromotionDetail(String promotionDetail){
		this.promotionDetail=promotionDetail;
	}

	public String getPromotionDetail(){
		return this.promotionDetail;
	}

}
