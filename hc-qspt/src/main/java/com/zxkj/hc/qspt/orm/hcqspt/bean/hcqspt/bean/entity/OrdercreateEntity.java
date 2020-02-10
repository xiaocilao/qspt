package com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.mapper.OrdercreateMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.dto.OrdercreateDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="orderCreate")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class OrdercreateEntity extends EntityBean<OrdercreateEntity,OrdercreateDto,OrdercreateMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="apply_id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="申请记录ID")
	private String applyId;

	@Column(name="apply_user_id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="申请人ID")
	private String applyUserId;

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

	@Temporal(TemporalType.DATE)
	@Column(name="xgsj",length=0,columnDefinition="datetime")
	@SetuDescription(describe="修改时间")
	private String xgsj;

	@Column(name="status",length=4,columnDefinition="tinyint")
	@SetuDescription(describe="订单状态0成功，1失败，4处理中")
	private String status;

	@Column(name="key_id",length=50,columnDefinition="varchar")
	@SetuDescription(describe="银行返回的订单号")
	private String keyId;

	@Column(name="product_id",length=20,columnDefinition="varchar")
	@SetuDescription(describe="商品id")
	private String productId;

	public OrdercreateEntity(){
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

	public void setApplyUserId(String applyUserId){
		this.applyUserId=applyUserId;
	}

	public String getApplyUserId(){
		return this.applyUserId;
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

	public void setXgsj(String xgsj){
		this.xgsj=xgsj;
	}

	public String getXgsj(){
		return this.xgsj;
	}

	public void setStatus(String status){
		this.status=status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setKeyId(String keyId){
		this.keyId=keyId;
	}

	public String getKeyId(){
		return this.keyId;
	}

	public void setProductId(String productId){
		this.productId=productId;
	}

	public String getProductId(){
		return this.productId;
	}

}
