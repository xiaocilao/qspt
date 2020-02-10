package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;
import javax.persistence.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.mapper.NewsMapper;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.NewsDto;
import org.setu.framework.bean.EntityBean;
import org.setu.framework.jdbc.annotation.TableInfo;
import org.setu.framework.annotation.description.SetuDescription;
import com.zxkj.hc.qspt.setu.system.DbConnStructure;
/**
 * 
 * @author: hfl 
 */

@Entity
@Table(name="news")
@TableInfo(dbConn=DbConnStructure.DC$HCQSPT,dbName=DbConnStructure.hcqspt)
public class NewsEntity extends EntityBean<NewsEntity,NewsDto,NewsMapper>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="UUIDGenerator")
	@Column(name="id",length=32,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="")
	private String id;

	@Column(name="title",length=50,columnDefinition="varchar")
	@SetuDescription(describe="标题")
	private String title;

	@Column(name="name",length=50,columnDefinition="varchar")
	@SetuDescription(describe="发布人")
	private String name;

	@Column(name="content",length=0,columnDefinition="text")
	@SetuDescription(describe="内容")
	@Basic(fetch=FetchType.LAZY)
	private String content;

	@Column(name="edit_name",length=32,columnDefinition="varchar")
	@SetuDescription(describe="编辑人")
	private String editName;

	@Column(name="picture",length=0,columnDefinition="blob")
	@SetuDescription(describe="封面图片")
	@Basic(fetch=FetchType.LAZY)
	private transient  byte[] picture;

	@Temporal(TemporalType.DATE)
	@Column(name="fb_date",length=0,columnDefinition="datetime")
	@SetuDescription(describe="发布时间")
	private String fbDate;

	@Temporal(TemporalType.DATE)
	@Column(name="create_date",length=0,columnDefinition="datetime")
	@SetuDescription(describe="发布时间")
	private String createDate;

	@Temporal(TemporalType.DATE)
	@Column(name="update_date",length=0,columnDefinition="datetime")
	@SetuDescription(describe="更新时间")
	private String updateDate;

	@Column(name="state",length=1,columnDefinition="int")
	@SetuDescription(describe="新闻状态，0新建，编辑，1发布，")
	private String state;

	@Column(name="click_count",length=11,nullable=false,columnDefinition="int")
	@SetuDescription(describe="点击量")
	private String clickCount;

	@Column(name="tag_label",length=100,columnDefinition="varchar")
	@SetuDescription(describe="tag标签")
	private String tagLabel;

	@Column(name="columns",length=50,nullable=false,columnDefinition="varchar")
	@SetuDescription(describe="新闻栏目，1新闻")
	private String columns;

	@Column(name="short_title",length=50,columnDefinition="varchar")
	@SetuDescription(describe="新闻短标题")
	private String shortTitle;

	@Column(name="data_source",length=100,columnDefinition="varchar")
	@SetuDescription(describe="新闻数据来源，转载时需要")
	private String dataSource;

	@Column(name="write_name",length=50,columnDefinition="varchar")
	@SetuDescription(describe="新闻作者")
	private String writeName;

	@Column(name="stick_picture",length=0,columnDefinition="blob")
	@SetuDescription(describe="置顶图片")
	@Basic(fetch=FetchType.LAZY)
	private transient  byte[] stickPicture;

	@Column(name="stick",length=1,nullable=false,columnDefinition="int")
	@SetuDescription(describe="是否顶置1表示顶置")
	private String stick;

	@Column(name="check_state",length=1,nullable=false,columnDefinition="int")
	@SetuDescription(describe="")
	private String checkState;

	@Column(name="xt_lrrxm",length=20,columnDefinition="varchar")
	@SetuDescription(describe="录入人姓名")
	private String xtLrrxm;

	@Temporal(TemporalType.DATE)
	@Column(name="xt_lrsj",length=0,columnDefinition="datetime")
	@SetuDescription(describe="录入时间")
	private String xtLrsj;

	@Column(name="xt_lrrid",length=50,columnDefinition="varchar")
	@SetuDescription(describe="录入人id")
	private String xtLrrid;

	@Column(name="xt_lrip",length=50,columnDefinition="varchar")
	@SetuDescription(describe="录入ip")
	private String xtLrip;

	@Temporal(TemporalType.DATE)
	@Column(name="xt_zhxgsj",length=0,columnDefinition="datetime")
	@SetuDescription(describe="最后修改时间")
	private String xtZhxgsj;

	@Column(name="xt_zhxgrxm",length=50,columnDefinition="varchar")
	@SetuDescription(describe="最后修改人姓名")
	private String xtZhxgrxm;

	@Column(name="xt_zhxgrid",length=50,columnDefinition="varchar")
	@SetuDescription(describe="最后修改人id")
	private String xtZhxgrid;

	@Column(name="xt_zhxgip",length=50,columnDefinition="varchar")
	@SetuDescription(describe="最后修改ip")
	private String xtZhxgip;

	@Column(name="xt_zxbz",length=2,columnDefinition="varchar")
	@SetuDescription(describe="注销标志")
	private String xtZxbz;

	@Column(name="xt_zxyy",length=50,columnDefinition="varchar")
	@SetuDescription(describe="注销原因")
	private String xtZxyy;

	public NewsEntity(){
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return this.id;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return this.title;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

	public void setContent(String content){
		this.content=content;
	}

	public String getContent(){
		return this.content;
	}

	public void setEditName(String editName){
		this.editName=editName;
	}

	public String getEditName(){
		return this.editName;
	}

	public void setPicture(byte[] picture){
		this.picture=picture;
	}

	public byte[] getPicture(){
		return this.picture;
	}

	public void setFbDate(String fbDate){
		this.fbDate=fbDate;
	}

	public String getFbDate(){
		return this.fbDate;
	}

	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}

	public String getCreateDate(){
		return this.createDate;
	}

	public void setUpdateDate(String updateDate){
		this.updateDate=updateDate;
	}

	public String getUpdateDate(){
		return this.updateDate;
	}

	public void setState(String state){
		this.state=state;
	}

	public String getState(){
		return this.state;
	}

	public void setClickCount(String clickCount){
		this.clickCount=clickCount;
	}

	public String getClickCount(){
		return this.clickCount;
	}

	public void setTagLabel(String tagLabel){
		this.tagLabel=tagLabel;
	}

	public String getTagLabel(){
		return this.tagLabel;
	}

	public void setColumns(String columns){
		this.columns=columns;
	}

	public String getColumns(){
		return this.columns;
	}

	public void setShortTitle(String shortTitle){
		this.shortTitle=shortTitle;
	}

	public String getShortTitle(){
		return this.shortTitle;
	}

	public void setDataSource(String dataSource){
		this.dataSource=dataSource;
	}

	public String getDataSource(){
		return this.dataSource;
	}

	public void setWriteName(String writeName){
		this.writeName=writeName;
	}

	public String getWriteName(){
		return this.writeName;
	}

	public void setStickPicture(byte[] stickPicture){
		this.stickPicture=stickPicture;
	}

	public byte[] getStickPicture(){
		return this.stickPicture;
	}

	public void setStick(String stick){
		this.stick=stick;
	}

	public String getStick(){
		return this.stick;
	}

	public void setCheckState(String checkState){
		this.checkState=checkState;
	}

	public String getCheckState(){
		return this.checkState;
	}

	public void setXtLrrxm(String xtLrrxm){
		this.xtLrrxm=xtLrrxm;
	}

	public String getXtLrrxm(){
		return this.xtLrrxm;
	}

	public void setXtLrsj(String xtLrsj){
		this.xtLrsj=xtLrsj;
	}

	public String getXtLrsj(){
		return this.xtLrsj;
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
