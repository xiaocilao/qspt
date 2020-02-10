package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.FlowFileMapper;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowFileDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="flow_file")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class FlowFileEntity extends EntityBean<FlowFileEntity,FlowFileDto,FlowFileMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="apply_id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="申请id")
	private String applyId;

	@Column(name="flow_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="流程ID")
	private String flowId;

	@Column(name="info_id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="信息ID")
	private String infoId;

	@Column(name="file_bz",length=50,columnDefinition="varchar")
	@SetuDescription(describe="文件备注")
	private String fileBz;

	@Column(name="file_size",length=20,columnDefinition="varchar")
	@SetuDescription(describe="文件大小")
	private String fileSize;

	@Column(name="file_content",length=0,columnDefinition="blob")
	@SetuDescription(describe="文件内容")
	@Basic(fetch=FetchType.LAZY)
	private transient  byte[] fileContent;

	@Column(name="small_picture",length=0,columnDefinition="blob")
	@SetuDescription(describe="")
	@Basic(fetch=FetchType.LAZY)
	private transient  byte[] smallPicture;

	@Column(name="file_dxcc_url",length=255,columnDefinition="varchar")
	@SetuDescription(describe="对象存储URL")
	private String fileDxccUrl;

	@Column(name="shzt",length=1,columnDefinition="tinyint")
	@SetuDescription(describe="审核状态")
	private String shzt;

	@Column(name="shyj",length=255,columnDefinition="varchar")
	@SetuDescription(describe="审核意见")
	private String shyj;

	@Column(name="shr_id",length=32,columnDefinition="varchar")
	@SetuDescription(describe="审核人")
	private String shrId;

	@Column(name="shr_xm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="审核人姓名")
	private String shrXm;

	@Column(name="sort",length=4,columnDefinition="tinyint")
	@SetuDescription(describe="排序规则")
	private String sort;

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

	public FlowFileEntity(){
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

	public void setFlowId(String flowId){
		this.flowId=flowId;
	}

	public String getFlowId(){
		return this.flowId;
	}

	public void setInfoId(String infoId){
		this.infoId=infoId;
	}

	public String getInfoId(){
		return this.infoId;
	}

	public void setFileBz(String fileBz){
		this.fileBz=fileBz;
	}

	public String getFileBz(){
		return this.fileBz;
	}

	public void setFileSize(String fileSize){
		this.fileSize=fileSize;
	}

	public String getFileSize(){
		return this.fileSize;
	}

	public void setFileContent(byte[] fileContent){
		this.fileContent=fileContent;
	}

	public byte[] getFileContent(){
		return this.fileContent;
	}

	public void setSmallPicture(byte[] smallPicture){
		this.smallPicture=smallPicture;
	}

	public byte[] getSmallPicture(){
		return this.smallPicture;
	}

	public void setFileDxccUrl(String fileDxccUrl){
		this.fileDxccUrl=fileDxccUrl;
	}

	public String getFileDxccUrl(){
		return this.fileDxccUrl;
	}

	public void setShzt(String shzt){
		this.shzt=shzt;
	}

	public String getShzt(){
		return this.shzt;
	}

	public void setShyj(String shyj){
		this.shyj=shyj;
	}

	public String getShyj(){
		return this.shyj;
	}

	public void setShrId(String shrId){
		this.shrId=shrId;
	}

	public String getShrId(){
		return this.shrId;
	}

	public void setShrXm(String shrXm){
		this.shrXm=shrXm;
	}

	public String getShrXm(){
		return this.shrXm;
	}

	public void setSort(String sort){
		this.sort=sort;
	}

	public String getSort(){
		return this.sort;
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

}
