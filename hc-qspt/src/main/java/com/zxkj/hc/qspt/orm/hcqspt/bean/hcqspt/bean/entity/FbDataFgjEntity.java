package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.mapper.FbDataFgjMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto.FbDataFgjDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="fb_data_fgj")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class FbDataFgjEntity extends EntityBean<FbDataFgjEntity,FbDataFgjDto,FbDataFgjMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="user_id",length=255,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String userId;

	@Column(name="bab_url",length=255,columnDefinition="varchar")
	@SetuDescription(describe="合同备案表图像地址")
	private String babUrl;

	@Column(name="wfzm_url",length=255,columnDefinition="varchar")
	@SetuDescription(describe="家庭成员无房证明图像地址")
	private String wfzmUrl;

	@Column(name="sfk_id",length=255,columnDefinition="varchar")
	@SetuDescription(describe="首付款收据图像ID")
	private String sfkId;

	@Column(name="sfz_id",length=255,columnDefinition="varchar")
	@SetuDescription(describe="身份证图像ID")
	private String sfzId;

	@Column(name="hkb_id",length=255,columnDefinition="varchar")
	@SetuDescription(describe="户口簿图像ID")
	private String hkbId;

	@Column(name="hyzkzm_id",length=255,columnDefinition="varchar")
	@SetuDescription(describe="婚姻状况证明ID")
	private String hyzkzmId;

	@Column(name="ghdyfcxy_id",length=255,columnDefinition="varchar")
	@SetuDescription(describe="共有房产协议或独有房产声明书ID")
	private String ghdyfcxyId;

	@Column(name="ht_url",length=255,columnDefinition="varchar")
	@SetuDescription(describe="合同全文图像地址")
	private String htUrl;

	@Column(name="qzy_id",length=255,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String qzyId;

	public FbDataFgjEntity(){
	}

	public void setUserId(String userId){
		this.userId=userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setBabUrl(String babUrl){
		this.babUrl=babUrl;
	}

	public String getBabUrl(){
		return this.babUrl;
	}

	public void setWfzmUrl(String wfzmUrl){
		this.wfzmUrl=wfzmUrl;
	}

	public String getWfzmUrl(){
		return this.wfzmUrl;
	}

	public void setSfkId(String sfkId){
		this.sfkId=sfkId;
	}

	public String getSfkId(){
		return this.sfkId;
	}

	public void setSfzId(String sfzId){
		this.sfzId=sfzId;
	}

	public String getSfzId(){
		return this.sfzId;
	}

	public void setHkbId(String hkbId){
		this.hkbId=hkbId;
	}

	public String getHkbId(){
		return this.hkbId;
	}

	public void setHyzkzmId(String hyzkzmId){
		this.hyzkzmId=hyzkzmId;
	}

	public String getHyzkzmId(){
		return this.hyzkzmId;
	}

	public void setGhdyfcxyId(String ghdyfcxyId){
		this.ghdyfcxyId=ghdyfcxyId;
	}

	public String getGhdyfcxyId(){
		return this.ghdyfcxyId;
	}

	public void setHtUrl(String htUrl){
		this.htUrl=htUrl;
	}

	public String getHtUrl(){
		return this.htUrl;
	}

	public void setQzyId(String qzyId){
		this.qzyId=qzyId;
	}

	public String getQzyId(){
		return this.qzyId;
	}

}
