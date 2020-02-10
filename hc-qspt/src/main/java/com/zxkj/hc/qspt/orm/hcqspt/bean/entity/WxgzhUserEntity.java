package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.WxgzhUserMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.WxgzhUserDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="wxgzh_user")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class WxgzhUserEntity extends EntityBean<WxgzhUserEntity,WxgzhUserDto,WxgzhUserMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="unionid",length=45,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String unionid;

	@Column(name="openid",length=45,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String openid;

	@Column(name="access_token",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String accessToken;

	@Temporal(TemporalType.DATE)
	@Column(name="token_invalidate_time",length=0,columnDefinition="datetime")
	@SetuDescription(describe="")
	private String tokenInvalidateTime;

	@Column(name="nickname",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String nickname;

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

	public WxgzhUserEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setUnionid(String unionid){
		this.unionid=unionid;
	}

	public String getUnionid(){
		return this.unionid;
	}

	public void setOpenid(String openid){
		this.openid=openid;
	}

	public String getOpenid(){
		return this.openid;
	}

	public void setAccessToken(String accessToken){
		this.accessToken=accessToken;
	}

	public String getAccessToken(){
		return this.accessToken;
	}

	public void setTokenInvalidateTime(String tokenInvalidateTime){
		this.tokenInvalidateTime=tokenInvalidateTime;
	}

	public String getTokenInvalidateTime(){
		return this.tokenInvalidateTime;
	}

	public void setNickname(String nickname){
		this.nickname=nickname;
	}

	public String getNickname(){
		return this.nickname;
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
