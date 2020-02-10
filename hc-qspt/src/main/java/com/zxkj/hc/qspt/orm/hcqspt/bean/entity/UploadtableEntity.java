package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.UploadtableMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.UploadtableDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="uploadTable")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class UploadtableEntity extends EntityBean<UploadtableEntity,UploadtableDto,UploadtableMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="applyId",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String applyid;

	@Column(name="userId",length=45,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String userid;

	@Column(name="userName",length=50,columnDefinition="varchar")
	@SetuDescription(describe="购房人姓名")
	private String username;

	@Column(name="idcard",length=50,columnDefinition="varchar")
	@SetuDescription(describe="购房人身份证")
	private String idcard;

	@Column(name="houseAddress",length=50,columnDefinition="varchar")
	@SetuDescription(describe="购房地址")
	private String houseaddress;

	@Column(name="houseId",length=255,columnDefinition="varchar")
	@SetuDescription(describe="房产证号")
	private String houseid;

	@Column(name="zsxmmc",length=45,columnDefinition="varchar")
	@SetuDescription(describe="征收项目名称")
	private String zsxmmc;

	@Column(name="swjgmc",length=45,columnDefinition="varchar")
	@SetuDescription(describe="税务机关名称")
	private String swjgmc;

	@Column(name="nsrmc",length=45,columnDefinition="varchar")
	@SetuDescription(describe="纳税人名称")
	private String nsrmc;

	@Column(name="nsrbh",length=45,columnDefinition="varchar")
	@SetuDescription(describe="纳税人编号")
	private String nsrbh;

	@Column(name="pzxh",length=45,columnDefinition="varchar")
	@SetuDescription(describe="凭证序号")
	private String pzxh;

	@Column(name="jehjxx",length=45,columnDefinition="varchar")
	@SetuDescription(describe="金额合计（小写）")
	private String jehjxx;

	@Column(name="jehjdx",length=45,columnDefinition="varchar")
	@SetuDescription(describe="金额合计（大写）")
	private String jehjdx;

	@Temporal(TemporalType.DATE)
	@Column(name="skxjrq",length=0,columnDefinition="date")
	@SetuDescription(describe="税款限缴日期")
	private String skxjrq;

	@Column(name="isSuccess",length=1,columnDefinition="varchar")
	@SetuDescription(describe="是否支付成功 1：成功 0：失败")
	private String issuccess;

	@Column(name="jnzt",length=1,columnDefinition="varchar")
	@SetuDescription(describe="缴纳状态 0：未交费 1：已交费")
	private String jnzt;

	@Temporal(TemporalType.DATE)
	@Column(name="xt_lrsj",length=0,columnDefinition="datetime")
	@SetuDescription(describe="录入时间")
	private String xtLrsj;

	@Column(name="xt_lrrxm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="录入人姓名")
	private String xtLrrxm;

	@Column(name="xt_lrrid",length=32,columnDefinition="varchar")
	@SetuDescription(describe="录入人ID")
	private String xtLrrid;

	@Column(name="xt_lrip",length=15,columnDefinition="varchar")
	@SetuDescription(describe="录入IP")
	private String xtLrip;

	@Temporal(TemporalType.DATE)
	@Column(name="xt_zhxgsj",length=0,columnDefinition="datetime")
	@SetuDescription(describe="最后修改时间")
	private String xtZhxgsj;

	@Column(name="xt_zhxgrxm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="最后修改人姓名")
	private String xtZhxgrxm;

	@Column(name="xt_zhxgrid",length=32,columnDefinition="varchar")
	@SetuDescription(describe="最后修改人ID")
	private String xtZhxgrid;

	@Column(name="xt_zhxgip",length=15,columnDefinition="varchar")
	@SetuDescription(describe="最后修改IP")
	private String xtZhxgip;

	@Column(name="xt_zxbz",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="注销标志")
	private String xtZxbz;

	@Column(name="xt_zxyy",length=50,columnDefinition="varchar")
	@SetuDescription(describe="注销原因")
	private String xtZxyy;

	public UploadtableEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setApplyid(String applyid){
		this.applyid=applyid;
	}

	public String getApplyid(){
		return this.applyid;
	}

	public void setUserid(String userid){
		this.userid=userid;
	}

	public String getUserid(){
		return this.userid;
	}

	public void setUsername(String username){
		this.username=username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setIdcard(String idcard){
		this.idcard=idcard;
	}

	public String getIdcard(){
		return this.idcard;
	}

	public void setHouseaddress(String houseaddress){
		this.houseaddress=houseaddress;
	}

	public String getHouseaddress(){
		return this.houseaddress;
	}

	public void setHouseid(String houseid){
		this.houseid=houseid;
	}

	public String getHouseid(){
		return this.houseid;
	}

	public void setZsxmmc(String zsxmmc){
		this.zsxmmc=zsxmmc;
	}

	public String getZsxmmc(){
		return this.zsxmmc;
	}

	public void setSwjgmc(String swjgmc){
		this.swjgmc=swjgmc;
	}

	public String getSwjgmc(){
		return this.swjgmc;
	}

	public void setNsrmc(String nsrmc){
		this.nsrmc=nsrmc;
	}

	public String getNsrmc(){
		return this.nsrmc;
	}

	public void setNsrbh(String nsrbh){
		this.nsrbh=nsrbh;
	}

	public String getNsrbh(){
		return this.nsrbh;
	}

	public void setPzxh(String pzxh){
		this.pzxh=pzxh;
	}

	public String getPzxh(){
		return this.pzxh;
	}

	public void setJehjxx(String jehjxx){
		this.jehjxx=jehjxx;
	}

	public String getJehjxx(){
		return this.jehjxx;
	}

	public void setJehjdx(String jehjdx){
		this.jehjdx=jehjdx;
	}

	public String getJehjdx(){
		return this.jehjdx;
	}

	public void setSkxjrq(String skxjrq){
		this.skxjrq=skxjrq;
	}

	public String getSkxjrq(){
		return this.skxjrq;
	}

	public void setIssuccess(String issuccess){
		this.issuccess=issuccess;
	}

	public String getIssuccess(){
		return this.issuccess;
	}

	public void setJnzt(String jnzt){
		this.jnzt=jnzt;
	}

	public String getJnzt(){
		return this.jnzt;
	}

	public void setXtLrsj(String xtLrsj){
		this.xtLrsj=xtLrsj;
	}

	public String getXtLrsj(){
		return this.xtLrsj;
	}

	public void setXtLrrxm(String xtLrrxm){
		this.xtLrrxm=xtLrrxm;
	}

	public String getXtLrrxm(){
		return this.xtLrrxm;
	}

	public void setXtLrrid(String xtLrrid){
		this.xtLrrid=xtLrrid;
	}

	public String getXtLrrid(){
		return this.xtLrrid;
	}

	public void setXtLrip(String xtLrip){
		this.xtLrip=xtLrip;
	}

	public String getXtLrip(){
		return this.xtLrip;
	}

	public void setXtZhxgsj(String xtZhxgsj){
		this.xtZhxgsj=xtZhxgsj;
	}

	public String getXtZhxgsj(){
		return this.xtZhxgsj;
	}

	public void setXtZhxgrxm(String xtZhxgrxm){
		this.xtZhxgrxm=xtZhxgrxm;
	}

	public String getXtZhxgrxm(){
		return this.xtZhxgrxm;
	}

	public void setXtZhxgrid(String xtZhxgrid){
		this.xtZhxgrid=xtZhxgrid;
	}

	public String getXtZhxgrid(){
		return this.xtZhxgrid;
	}

	public void setXtZhxgip(String xtZhxgip){
		this.xtZhxgip=xtZhxgip;
	}

	public String getXtZhxgip(){
		return this.xtZhxgip;
	}

	public void setXtZxbz(String xtZxbz){
		this.xtZxbz=xtZxbz;
	}

	public String getXtZxbz(){
		return this.xtZxbz;
	}

	public void setXtZxyy(String xtZxyy){
		this.xtZxyy=xtZxyy;
	}

	public String getXtZxyy(){
		return this.xtZxyy;
	}

}
