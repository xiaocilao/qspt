package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.ApplyRecordMapper;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.ApplyRecordDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="apply_record")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class ApplyRecordEntity extends EntityBean<ApplyRecordEntity,ApplyRecordDto,ApplyRecordMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="num",length=11,nullable=false,columnDefinition="int")
	@SetuDescription(describe="")
	private String num;

	@Column(name="sqr_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="申请人ID")
	private String sqrId;

	@Column(name="sqr_xm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="申请人姓名")
	private String sqrXm;

	@Temporal(TemporalType.DATE)
	@Column(name="sqsj",length=0,columnDefinition="datetime")
	@SetuDescription(describe="申请时间")
	private String sqsj;

	@Column(name="spr_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="审批人")
	private String sprId;

	@Column(name="spr_xm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="审批人姓名")
	private String sprXm;

	@Temporal(TemporalType.DATE)
	@Column(name="spsj",length=0,columnDefinition="datetime")
	@SetuDescription(describe="审批时间")
	private String spsj;

	@Column(name="spzt",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="审批状态")
	private String spzt;

	@Column(name="sfyz",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="身份认证")
	private String sfyz;

	@Column(name="hyzk",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="婚姻状况")
	private String hyzk;

	@Column(name="hkbxx",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="户口簿信息")
	private String hkbxx;

	@Column(name="gffp",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="购房发票")
	private String gffp;

	@Column(name="gfht",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="购房合同")
	private String gfht;

	@Column(name="sqs",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="授权书")
	private String sqs;

	@Column(name="cqbc",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="拆迁补偿")
	private String cqbc;

	@Column(name="zlsh",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="资料审核")
	private String zlsh;

	@Column(name="qsjs",length=1,columnDefinition="varchar")
	@SetuDescription(describe="契税计算")
	private String qsjs;

	@Column(name="qsjsr_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="契税计算人")
	private String qsjsrId;

	@Column(name="qsjsr_xm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="契税计算人姓名")
	private String qsjsrXm;

	@Temporal(TemporalType.DATE)
	@Column(name="qsjssj",length=0,columnDefinition="date")
	@SetuDescription(describe="契税计算时间")
	private String qsjssj;

	@Column(name="sqkk",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="申请扣款")
	private String sqkk;

	@Column(name="sqkk_yhzh",length=50,columnDefinition="varchar")
	@SetuDescription(describe="申请银行扣款账户")
	private String sqkkYhzh;

	@Column(name="sqkk_khh",length=100,columnDefinition="varchar")
	@SetuDescription(describe="申请扣款开户行")
	private String sqkkKhh;

	@Column(name="sqkk_zhm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="申请扣款账户名")
	private String sqkkZhm;

	@Temporal(TemporalType.DATE)
	@Column(name="sqkksj",length=0,columnDefinition="date")
	@SetuDescription(describe="申请扣款时间")
	private String sqkksj;

	@Column(name="kkcg",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="扣款成功")
	private String kkcg;

	@Temporal(TemporalType.DATE)
	@Column(name="kkcgsj",length=0,columnDefinition="date")
	@SetuDescription(describe="扣款完成时间")
	private String kkcgsj;

	@Column(name="yjpz",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="邮寄凭证")
	private String yjpz;

	@Column(name="yjr_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="邮寄人Id")
	private String yjrId;

	@Column(name="yjr_xm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="邮寄人姓名")
	private String yjrXm;

	@Temporal(TemporalType.DATE)
	@Column(name="xt_lrsj",length=0,columnDefinition="date")
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
	@Column(name="xt_zhxgsj",length=0,columnDefinition="date")
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


	@Column(name="xt_phone",length=20,columnDefinition="varchar")
	@SetuDescription(describe="电话号码")
	private String xtPhone;

	@Column(name="xt_sqbz",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="房管局授权")
	private String xtSqbz;

	public String getXtPhone() {
		return xtPhone;
	}

	public void setXtPhone(String xtPhone) {
		this.xtPhone = xtPhone;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getXtSqbz() {
		return xtSqbz;
	}

	public void setXtSqbz(String xtSqbz) {
		this.xtSqbz = xtSqbz;
	}

	public ApplyRecordEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setNum(String num){
		this.num=num;
	}

	public String getNum(){
		return this.num;
	}

	public void setSqrId(String sqrId){
		this.sqrId=sqrId;
	}

	public String getSqrId(){
		return this.sqrId;
	}

	public void setSqrXm(String sqrXm){
		this.sqrXm=sqrXm;
	}

	public String getSqrXm(){
		return this.sqrXm;
	}

	public void setSqsj(String sqsj){
		this.sqsj=sqsj;
	}

	public String getSqsj(){
		return this.sqsj;
	}

	public void setSprId(String sprId){
		this.sprId=sprId;
	}

	public String getSprId(){
		return this.sprId;
	}

	public void setSprXm(String sprXm){
		this.sprXm=sprXm;
	}

	public String getSprXm(){
		return this.sprXm;
	}

	public void setSpsj(String spsj){
		this.spsj=spsj;
	}

	public String getSpsj(){
		return this.spsj;
	}

	public void setSpzt(String spzt){
		this.spzt=spzt;
	}

	public String getSpzt(){
		return this.spzt;
	}

	public void setSfyz(String sfyz){
		this.sfyz=sfyz;
	}

	public String getSfyz(){
		return this.sfyz;
	}

	public void setHyzk(String hyzk){
		this.hyzk=hyzk;
	}

	public String getHyzk(){
		return this.hyzk;
	}

	public void setHkbxx(String hkbxx){
		this.hkbxx=hkbxx;
	}

	public String getHkbxx(){
		return this.hkbxx;
	}

	public void setGffp(String gffp){
		this.gffp=gffp;
	}

	public String getGffp(){
		return this.gffp;
	}

	public void setGfht(String gfht){
		this.gfht=gfht;
	}

	public String getGfht(){
		return this.gfht;
	}

	public void setSqs(String sqs){
		this.sqs=sqs;
	}

	public String getSqs(){
		return this.sqs;
	}

	public void setCqbc(String cqbc){
		this.cqbc=cqbc;
	}

	public String getCqbc(){
		return this.cqbc;
	}

	public void setZlsh(String zlsh){
		this.zlsh=zlsh;
	}

	public String getZlsh(){
		return this.zlsh;
	}

	public void setQsjs(String qsjs){
		this.qsjs=qsjs;
	}

	public String getQsjs(){
		return this.qsjs;
	}

	public void setQsjsrId(String qsjsrId){
		this.qsjsrId=qsjsrId;
	}

	public String getQsjsrId(){
		return this.qsjsrId;
	}

	public void setQsjsrXm(String qsjsrXm){
		this.qsjsrXm=qsjsrXm;
	}

	public String getQsjsrXm(){
		return this.qsjsrXm;
	}

	public void setQsjssj(String qsjssj){
		this.qsjssj=qsjssj;
	}

	public String getQsjssj(){
		return this.qsjssj;
	}

	public void setSqkk(String sqkk){
		this.sqkk=sqkk;
	}

	public String getSqkk(){
		return this.sqkk;
	}

	public void setSqkkYhzh(String sqkkYhzh){
		this.sqkkYhzh=sqkkYhzh;
	}

	public String getSqkkYhzh(){
		return this.sqkkYhzh;
	}

	public void setSqkkKhh(String sqkkKhh){
		this.sqkkKhh=sqkkKhh;
	}

	public String getSqkkKhh(){
		return this.sqkkKhh;
	}

	public void setSqkkZhm(String sqkkZhm){
		this.sqkkZhm=sqkkZhm;
	}

	public String getSqkkZhm(){
		return this.sqkkZhm;
	}

	public void setSqkksj(String sqkksj){
		this.sqkksj=sqkksj;
	}

	public String getSqkksj(){
		return this.sqkksj;
	}

	public void setKkcg(String kkcg){
		this.kkcg=kkcg;
	}

	public String getKkcg(){
		return this.kkcg;
	}

	public void setKkcgsj(String kkcgsj){
		this.kkcgsj=kkcgsj;
	}

	public String getKkcgsj(){
		return this.kkcgsj;
	}

	public void setYjpz(String yjpz){
		this.yjpz=yjpz;
	}

	public String getYjpz(){
		return this.yjpz;
	}

	public void setYjrId(String yjrId){
		this.yjrId=yjrId;
	}

	public String getYjrId(){
		return this.yjrId;
	}

	public void setYjrXm(String yjrXm){
		this.yjrXm=yjrXm;
	}

	public String getYjrXm(){
		return this.yjrXm;
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
