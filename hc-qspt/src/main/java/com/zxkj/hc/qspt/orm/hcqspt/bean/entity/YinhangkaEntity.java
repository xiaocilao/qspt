package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.YinhangkaMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.YinhangkaDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="yinhangka")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class YinhangkaEntity extends EntityBean<YinhangkaEntity,YinhangkaDto,YinhangkaMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="apply_user_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String applyUserId;

	@Column(name="yhkh",length=45,columnDefinition="varchar")
	@SetuDescription(describe="银行卡号")
	private String yhkh;

	@Column(name="xm",length=45,columnDefinition="varchar")
	@SetuDescription(describe="姓名")
	private String xm;

	@Column(name="sjh",length=45,columnDefinition="varchar")
	@SetuDescription(describe="手机号")
	private String sjh;

	@Column(name="yhlx",length=45,columnDefinition="varchar")
	@SetuDescription(describe="银行类型")
	private String yhlx;

	@Column(name="xt_zxbz",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="")
	private String xtZxbz;

	public YinhangkaEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setApplyUserId(String applyUserId){
		this.applyUserId=applyUserId;
	}

	public String getApplyUserId(){
		return this.applyUserId;
	}

	public void setYhkh(String yhkh){
		this.yhkh=yhkh;
	}

	public String getYhkh(){
		return this.yhkh;
	}

	public void setXm(String xm){
		this.xm=xm;
	}

	public String getXm(){
		return this.xm;
	}

	public void setSjh(String sjh){
		this.sjh=sjh;
	}

	public String getSjh(){
		return this.sjh;
	}

	public void setYhlx(String yhlx){
		this.yhlx=yhlx;
	}

	public String getYhlx(){
		return this.yhlx;
	}

	public void setXtZxbz(String xtZxbz){
		this.xtZxbz=xtZxbz;
	}

	public String getXtZxbz(){
		return this.xtZxbz;
	}

}
