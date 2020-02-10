package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.mapper.YoujixinxiMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto.YoujixinxiDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="youjixinxi")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class YoujixinxiEntity extends EntityBean<YoujixinxiEntity,YoujixinxiDto,YoujixinxiMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="apply_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String applyId;

	@Column(name="shuidan_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String shuidanId;

	@Column(name="sjr",length=45,columnDefinition="varchar")
	@SetuDescription(describe="收件人")
	private String sjr;

	@Column(name="sjhm",length=45,columnDefinition="varchar")
	@SetuDescription(describe="手机号码")
	private String sjhm;

	@Column(name="lqdz",length=0,columnDefinition="text")
	@SetuDescription(describe="领取地址")
	@Basic(fetch=FetchType.LAZY)
	private String lqdz;

	@Column(name="user_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="用户id")
	private String userId;

	@Column(name="postal_code",length=10,columnDefinition="varchar")
	@SetuDescription(describe="邮编")
	private String postalCode;

	public YoujixinxiEntity(){
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

	public void setShuidanId(String shuidanId){
		this.shuidanId=shuidanId;
	}

	public String getShuidanId(){
		return this.shuidanId;
	}

	public void setSjr(String sjr){
		this.sjr=sjr;
	}

	public String getSjr(){
		return this.sjr;
	}

	public void setSjhm(String sjhm){
		this.sjhm=sjhm;
	}

	public String getSjhm(){
		return this.sjhm;
	}

	public void setLqdz(String lqdz){
		this.lqdz=lqdz;
	}

	public String getLqdz(){
		return this.lqdz;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setPostalCode(String postalCode){
		this.postalCode=postalCode;
	}

	public String getPostalCode(){
		return this.postalCode;
	}

}
