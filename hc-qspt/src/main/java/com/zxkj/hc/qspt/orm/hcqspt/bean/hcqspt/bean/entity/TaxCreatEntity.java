package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.mapper.TaxCreatMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto.TaxCreatDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="tax_creat")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class TaxCreatEntity extends EntityBean<TaxCreatEntity,TaxCreatDto,TaxCreatMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",length=11,nullable=false,columnDefinition="int")
	@SetuDescription(describe="")
	private String id;

	@Column(name="prcscd",length=50,columnDefinition="varchar")
	@SetuDescription(describe="核心交易码")
	private String prcscd;

	@Column(name="brh_no",length=11,columnDefinition="int")
	@SetuDescription(describe="机构号")
	private String brhNo;

	@Column(name="opr_no",length=11,columnDefinition="int")
	@SetuDescription(describe="柜员号")
	private String oprNo;

	@Column(name="abst_cd",length=10,columnDefinition="varchar")
	@SetuDescription(describe="交易码(固定)")
	private String abstCd;

	@Column(name="mid_item",length=11,columnDefinition="varchar")
	@SetuDescription(describe="(固定)")
	private String midItem;

	@Column(name="mid_pay_type",length=1,columnDefinition="varchar")
	@SetuDescription(describe="(固定)")
	private String midPayType;

	@Column(name="jyje",length=50,columnDefinition="varchar")
	@SetuDescription(describe="金额")
	private String jyje;

	@Column(name="vou1_acc_no",length=50,columnDefinition="varchar")
	@SetuDescription(describe="账户")
	private String vou1AccNo;

	@Column(name="dwmc",length=100,columnDefinition="varchar")
	@SetuDescription(describe="账户名称")
	private String dwmc;

	@Column(name="zsjgdm",length=100,columnDefinition="varchar")
	@SetuDescription(describe="征收机构代码")
	private String zsjgdm;

	@Column(name="nsrbm",length=100,columnDefinition="varchar")
	@SetuDescription(describe="纳税人编码")
	private String nsrbm;

	@Column(name="wbsbdzxh",length=100,columnDefinition="varchar")
	@SetuDescription(describe="外部申报序号")
	private String wbsbdzxh;

	@Column(name="qydm",length=100,columnDefinition="varchar")
	@SetuDescription(describe="企业代码")
	private String qydm;

	@Column(name="jydm",length=30,columnDefinition="varchar")
	@SetuDescription(describe="交易代码(固定)")
	private String jydm;

	@Column(name="transcode",length=30,columnDefinition="varchar")
	@SetuDescription(describe="固定值")
	private String transcode;

	@Column(name="order_id",length=20,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String orderId;

	public TaxCreatEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setPrcscd(String prcscd){
		this.prcscd=prcscd;
	}

	public String getPrcscd(){
		return this.prcscd;
	}

	public void setBrhNo(String brhNo){
		this.brhNo=brhNo;
	}

	public String getBrhNo(){
		return this.brhNo;
	}

	public void setOprNo(String oprNo){
		this.oprNo=oprNo;
	}

	public String getOprNo(){
		return this.oprNo;
	}

	public void setAbstCd(String abstCd){
		this.abstCd=abstCd;
	}

	public String getAbstCd(){
		return this.abstCd;
	}

	public void setMidItem(String midItem){
		this.midItem=midItem;
	}

	public String getMidItem(){
		return this.midItem;
	}

	public void setMidPayType(String midPayType){
		this.midPayType=midPayType;
	}

	public String getMidPayType(){
		return this.midPayType;
	}

	public void setJyje(String jyje){
		this.jyje=jyje;
	}

	public String getJyje(){
		return this.jyje;
	}

	public void setVou1AccNo(String vou1AccNo){
		this.vou1AccNo=vou1AccNo;
	}

	public String getVou1AccNo(){
		return this.vou1AccNo;
	}

	public void setDwmc(String dwmc){
		this.dwmc=dwmc;
	}

	public String getDwmc(){
		return this.dwmc;
	}

	public void setZsjgdm(String zsjgdm){
		this.zsjgdm=zsjgdm;
	}

	public String getZsjgdm(){
		return this.zsjgdm;
	}

	public void setNsrbm(String nsrbm){
		this.nsrbm=nsrbm;
	}

	public String getNsrbm(){
		return this.nsrbm;
	}

	public void setWbsbdzxh(String wbsbdzxh){
		this.wbsbdzxh=wbsbdzxh;
	}

	public String getWbsbdzxh(){
		return this.wbsbdzxh;
	}

	public void setQydm(String qydm){
		this.qydm=qydm;
	}

	public String getQydm(){
		return this.qydm;
	}

	public void setJydm(String jydm){
		this.jydm=jydm;
	}

	public String getJydm(){
		return this.jydm;
	}

	public void setTranscode(String transcode){
		this.transcode=transcode;
	}

	public String getTranscode(){
		return this.transcode;
	}

	public void setOrderId(String orderId){
		this.orderId=orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

}
