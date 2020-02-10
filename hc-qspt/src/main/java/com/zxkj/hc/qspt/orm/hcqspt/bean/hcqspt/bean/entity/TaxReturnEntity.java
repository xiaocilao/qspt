package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.mapper.TaxReturnMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto.TaxReturnDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="tax_return")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class TaxReturnEntity extends EntityBean<TaxReturnEntity,TaxReturnDto,TaxReturnMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",length=11,nullable=false,columnDefinition="int")
	@SetuDescription(describe="")
	private String id;

	@Column(name="proc_result",length=10,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String procResult;

	@Column(name="err_tex",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String errTex;

	@Column(name="trans_date",length=21,columnDefinition="varchar")
	@SetuDescription(describe="日期")
	private String transDate;

	@Column(name="trans_time",length=21,columnDefinition="varchar")
	@SetuDescription(describe="时间")
	private String transTime;

	@Column(name="mid_serial_no",length=50,columnDefinition="varchar")
	@SetuDescription(describe="中间业务流水")
	private String midSerialNo;

	@Column(name="ctl_serial_no",length=50,columnDefinition="varchar")
	@SetuDescription(describe="核心流水")
	private String ctlSerialNo;

	@Column(name="wjbz",length=10,columnDefinition="varchar")
	@SetuDescription(describe="文件标志")
	private String wjbz;

	@Column(name="wjmc",length=255,columnDefinition="varchar")
	@SetuDescription(describe="文件名称")
	private String wjmc;

	@Column(name="jyje",length=50,columnDefinition="varchar")
	@SetuDescription(describe="交易金额")
	private String jyje;

	@Column(name="bz",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String bz;

	@Column(name="order_id",length=20,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String orderId;

	@Column(name="resource",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String resource;

	public TaxReturnEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setProcResult(String procResult){
		this.procResult=procResult;
	}

	public String getProcResult(){
		return this.procResult;
	}

	public void setErrTex(String errTex){
		this.errTex=errTex;
	}

	public String getErrTex(){
		return this.errTex;
	}

	public void setTransDate(String transDate){
		this.transDate=transDate;
	}

	public String getTransDate(){
		return this.transDate;
	}

	public void setTransTime(String transTime){
		this.transTime=transTime;
	}

	public String getTransTime(){
		return this.transTime;
	}

	public void setMidSerialNo(String midSerialNo){
		this.midSerialNo=midSerialNo;
	}

	public String getMidSerialNo(){
		return this.midSerialNo;
	}

	public void setCtlSerialNo(String ctlSerialNo){
		this.ctlSerialNo=ctlSerialNo;
	}

	public String getCtlSerialNo(){
		return this.ctlSerialNo;
	}

	public void setWjbz(String wjbz){
		this.wjbz=wjbz;
	}

	public String getWjbz(){
		return this.wjbz;
	}

	public void setWjmc(String wjmc){
		this.wjmc=wjmc;
	}

	public String getWjmc(){
		return this.wjmc;
	}

	public void setJyje(String jyje){
		this.jyje=jyje;
	}

	public String getJyje(){
		return this.jyje;
	}

	public void setBz(String bz){
		this.bz=bz;
	}

	public String getBz(){
		return this.bz;
	}

	public void setOrderId(String orderId){
		this.orderId=orderId;
	}

	public String getOrderId(){
		return this.orderId;
	}

	public void setResource(String resource){
		this.resource=resource;
	}

	public String getResource(){
		return this.resource;
	}

}
