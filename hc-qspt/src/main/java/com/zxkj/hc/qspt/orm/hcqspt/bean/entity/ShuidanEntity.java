package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.ShuidanMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.ShuidanDto;
import org.apache.commons.codec.binary.Base64;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="shuidan")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class ShuidanEntity extends EntityBean<ShuidanEntity,ShuidanDto,ShuidanMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="apply_id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String applyId;

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

	@Column(name="zfzt",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="支付状态")
	private String zfzt;


	@Column(name="kk",length=45,columnDefinition="varchar")
	@SetuDescription(describe="扣款")
	private String kk;

	@Column(name="yhs",length=45,columnDefinition="varchar")
	@SetuDescription(describe="印花税")
	private String yhs;


	public String getKk() {
		return kk;
	}

	public void setKk(String kk) {
		this.kk = kk;
	}

	public String getYhs() {
		return yhs;
	}

	public void setYhs(String yhs) {
		this.yhs = yhs;
	}

	@Column(name="sdtp",length=0,columnDefinition="blob")
	@SetuDescription(describe="")
	@Basic(fetch=FetchType.LAZY)
	private transient  String sdtp;

	public ShuidanEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setApplyId(String applyId){
		this.applyId=applyId;
	}

	public String getApplyId(){
		return this.applyId;
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

	public void setZfzt(String zfzt){
		this.zfzt=zfzt;
	}

	public String getZfzt(){
		return this.zfzt;
	}

	public void setSdtp(String string){
		this.sdtp=string;
	}

	public String getSdtp(){
		return this.sdtp;
	}

}
