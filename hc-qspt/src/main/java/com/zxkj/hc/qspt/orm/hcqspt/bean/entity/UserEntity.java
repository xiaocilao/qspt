package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.UserMapper;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.UserDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="user")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class UserEntity extends EntityBean<UserEntity,UserDto,UserMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="user_name",length=45,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String userName;

	@Column(name="password",length=45,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String password;

	@Column(name="salt",length=45,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String salt;

	@Column(name="status",length=4,nullable=false,columnDefinition="tinyint")
	@SetuDescription(describe="")
	private String status;

	@Column(name="wx_openid",length=45,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String wxOpenid;

	@Column(name="wx_unionid",length=45,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String wxUnionid;

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

	public UserEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setUserName(String userName){
		this.userName=userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setSalt(String salt){
		this.salt=salt;
	}

	public String getSalt(){
		return this.salt;
	}

	public void setStatus(String status){
		this.status=status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setWxOpenid(String wxOpenid){
		this.wxOpenid=wxOpenid;
	}

	public String getWxOpenid(){
		return this.wxOpenid;
	}

	public void setWxUnionid(String wxUnionid){
		this.wxUnionid=wxUnionid;
	}

	public String getWxUnionid(){
		return this.wxUnionid;
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
