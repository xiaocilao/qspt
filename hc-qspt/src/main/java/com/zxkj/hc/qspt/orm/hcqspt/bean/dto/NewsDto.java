package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.NewsEntity;
import java.util.*;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.utils.DateTimeHelper;
/**
 * 
 * @author: hfl 
 */
public class NewsDto extends BaseDto<NewsEntity>{

	private static  final long serialVersionUID = 1L;

	public NewsDto(){
	}

	public static final String TABLE_NAME= "news";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String TITLE= "title";
	public static final Column TITLE_C= new Column(TITLE);

	public static final String NAME= "name";
	public static final Column NAME_C= new Column(NAME);

	public static final String CONTENT= "content";
	public static final Column CONTENT_C= new Column(CONTENT);

	public static final String EDIT_NAME_D = "edit_name";
	public static final String EDIT_NAME_J = "editName";
	public static final Column EDIT_NAME_C= new Column(EDIT_NAME_D);

	public static final String PICTURE= "picture";
	public static final Column PICTURE_C= new Column(PICTURE);

	public static final String FB_DATE_D = "fb_date";
	public static final String FB_DATE_J = "fbDate";
	public static final Column FB_DATE_C= new Column(FB_DATE_D);

	public static final String CREATE_DATE_D = "create_date";
	public static final String CREATE_DATE_J = "createDate";
	public static final Column CREATE_DATE_C= new Column(CREATE_DATE_D);

	public static final String UPDATE_DATE_D = "update_date";
	public static final String UPDATE_DATE_J = "updateDate";
	public static final Column UPDATE_DATE_C= new Column(UPDATE_DATE_D);

	public static final String STATE= "state";
	public static final Column STATE_C= new Column(STATE);

	public static final String CLICK_COUNT_D = "click_count";
	public static final String CLICK_COUNT_J = "clickCount";
	public static final Column CLICK_COUNT_C= new Column(CLICK_COUNT_D);

	public static final String TAG_LABEL_D = "tag_label";
	public static final String TAG_LABEL_J = "tagLabel";
	public static final Column TAG_LABEL_C= new Column(TAG_LABEL_D);

	public static final String COLUMNS= "columns";
	public static final Column COLUMNS_C= new Column(COLUMNS);

	public static final String SHORT_TITLE_D = "short_title";
	public static final String SHORT_TITLE_J = "shortTitle";
	public static final Column SHORT_TITLE_C= new Column(SHORT_TITLE_D);

	public static final String DATA_SOURCE_D = "data_source";
	public static final String DATA_SOURCE_J = "dataSource";
	public static final Column DATA_SOURCE_C= new Column(DATA_SOURCE_D);

	public static final String WRITE_NAME_D = "write_name";
	public static final String WRITE_NAME_J = "writeName";
	public static final Column WRITE_NAME_C= new Column(WRITE_NAME_D);

	public static final String STICK_PICTURE_D = "stick_picture";
	public static final String STICK_PICTURE_J = "stickPicture";
	public static final Column STICK_PICTURE_C= new Column(STICK_PICTURE_D);

	public static final String STICK= "stick";
	public static final Column STICK_C= new Column(STICK);

	public static final String CHECK_STATE_D = "check_state";
	public static final String CHECK_STATE_J = "checkState";
	public static final Column CHECK_STATE_C= new Column(CHECK_STATE_D);

	public static final String XT_LRRXM_D = "xt_lrrxm";
	public static final String XT_LRRXM_J = "xtLrrxm";
	public static final Column XT_LRRXM_C= new Column(XT_LRRXM_D);

	public static final String XT_LRSJ_D = "xt_lrsj";
	public static final String XT_LRSJ_J = "xtLrsj";
	public static final Column XT_LRSJ_C= new Column(XT_LRSJ_D);

	public static final String XT_LRRID_D = "xt_lrrid";
	public static final String XT_LRRID_J = "xtLrrid";
	public static final Column XT_LRRID_C= new Column(XT_LRRID_D);

	public static final String XT_LRIP_D = "xt_lrip";
	public static final String XT_LRIP_J = "xtLrip";
	public static final Column XT_LRIP_C= new Column(XT_LRIP_D);

	public static final String XT_ZHXGSJ_D = "xt_zhxgsj";
	public static final String XT_ZHXGSJ_J = "xtZhxgsj";
	public static final Column XT_ZHXGSJ_C= new Column(XT_ZHXGSJ_D);

	public static final String XT_ZHXGRXM_D = "xt_zhxgrxm";
	public static final String XT_ZHXGRXM_J = "xtZhxgrxm";
	public static final Column XT_ZHXGRXM_C= new Column(XT_ZHXGRXM_D);

	public static final String XT_ZHXGRID_D = "xt_zhxgrid";
	public static final String XT_ZHXGRID_J = "xtZhxgrid";
	public static final Column XT_ZHXGRID_C= new Column(XT_ZHXGRID_D);

	public static final String XT_ZHXGIP_D = "xt_zhxgip";
	public static final String XT_ZHXGIP_J = "xtZhxgip";
	public static final Column XT_ZHXGIP_C= new Column(XT_ZHXGIP_D);

	public static final String XT_ZXBZ_D = "xt_zxbz";
	public static final String XT_ZXBZ_J = "xtZxbz";
	public static final Column XT_ZXBZ_C= new Column(XT_ZXBZ_D);

	public static final String XT_ZXYY_D = "xt_zxyy";
	public static final String XT_ZXYY_J = "xtZxyy";
	public static final Column XT_ZXYY_C= new Column(XT_ZXYY_D);



	public Integer getId(){
		return Integer.parseInt(po.getId());
	}

	public void setId(String id){
		 po.setId(String.valueOf(id));
	}

	public String getTitle(){
		return po.getTitle();
	}

	public void setTitle(String title){
		 po.setTitle(title);
	}

	public String getName(){
		return po.getName();
	}

	public void setName(String name){
		 po.setName(name);
	}

	public String getContent(){
		return po.getContent();
	}

	public void setContent(String content){
		 po.setContent(content);
	}

	public String getEditName(){
		return po.getEditName();
	}

	public void setEditName(String editName){
		 po.setEditName(editName);
	}

	public byte[] getPicture(){
		return po.getPicture();
	}

	public void setPicture(byte[] picture){
		 po.setPicture(picture);
	}

	public Date getFbDate(){
		return  DateTimeHelper.parseToDate(po.getFbDate());
	}

	public void setFbDate(Date fbDate){
		 po.setFbDate(DateTimeHelper.formatDateTimetoString(fbDate));
	}

	public Date getCreateDate(){
		return  DateTimeHelper.parseToDate(po.getCreateDate());
	}

	public void setCreateDate(Date createDate){
		 po.setCreateDate(DateTimeHelper.formatDateTimetoString(createDate));
	}

	public Date getUpdateDate(){
		return  DateTimeHelper.parseToDate(po.getUpdateDate());
	}

	public void setUpdateDate(Date updateDate){
		 po.setUpdateDate(DateTimeHelper.formatDateTimetoString(updateDate));
	}

	public Integer getState(){
		return Integer.parseInt(po.getState());
	}

	public void setState(int state){
		 po.setState(String.valueOf(state));
	}

	public Integer getClickCount(){
		return Integer.parseInt(po.getClickCount());
	}

	public void setClickCount(int clickCount){
		 po.setClickCount(String.valueOf(clickCount));
	}

	public String getTagLabel(){
		return po.getTagLabel();
	}

	public void setTagLabel(String tagLabel){
		 po.setTagLabel(tagLabel);
	}

	public String getColumns(){
		return po.getColumns();
	}

	public void setColumns(String columns){
		 po.setColumns(columns);
	}

	public String getShortTitle(){
		return po.getShortTitle();
	}

	public void setShortTitle(String shortTitle){
		 po.setShortTitle(shortTitle);
	}

	public String getDataSource(){
		return po.getDataSource();
	}

	public void setDataSource(String dataSource){
		 po.setDataSource(dataSource);
	}

	public String getWriteName(){
		return po.getWriteName();
	}

	public void setWriteName(String writeName){
		 po.setWriteName(writeName);
	}

	public byte[] getStickPicture(){
		return po.getStickPicture();
	}

	public void setStickPicture(byte[] stickPicture){
		 po.setStickPicture(stickPicture);
	}

	public Integer getStick(){
		return Integer.parseInt(po.getStick());
	}

	public void setStick(int stick){
		 po.setStick(String.valueOf(stick));
	}

	public Integer getCheckState(){
		return Integer.parseInt(po.getCheckState());
	}

	public void setCheckState(int checkState){
		 po.setCheckState(String.valueOf(checkState));
	}

	public String getXtLrrxm(){
		return po.getXtLrrxm();
	}

	public void setXtLrrxm(String xtLrrxm){
		 po.setXtLrrxm(xtLrrxm);
	}

	public Date getXtLrsj(){
		return  DateTimeHelper.parseToDate(po.getXtLrsj());
	}

	public void setXtLrsj(Date xtLrsj){
		 po.setXtLrsj(DateTimeHelper.formatDateTimetoString(xtLrsj));
	}

	public String getXtLrrid(){
		return po.getXtLrrid();
	}

	public void setXtLrrid(String xtLrrid){
		 po.setXtLrrid(xtLrrid);
	}

	public String getXtLrip(){
		return po.getXtLrip();
	}

	public void setXtLrip(String xtLrip){
		 po.setXtLrip(xtLrip);
	}

	public Date getXtZhxgsj(){
		return  DateTimeHelper.parseToDate(po.getXtZhxgsj());
	}

	public void setXtZhxgsj(Date xtZhxgsj){
		 po.setXtZhxgsj(DateTimeHelper.formatDateTimetoString(xtZhxgsj));
	}

	public String getXtZhxgrxm(){
		return po.getXtZhxgrxm();
	}

	public void setXtZhxgrxm(String xtZhxgrxm){
		 po.setXtZhxgrxm(xtZhxgrxm);
	}

	public String getXtZhxgrid(){
		return po.getXtZhxgrid();
	}

	public void setXtZhxgrid(String xtZhxgrid){
		 po.setXtZhxgrid(xtZhxgrid);
	}

	public String getXtZhxgip(){
		return po.getXtZhxgip();
	}

	public void setXtZhxgip(String xtZhxgip){
		 po.setXtZhxgip(xtZhxgip);
	}

	public Integer getXtZxbz(){
		return Integer.parseInt(po.getXtZxbz());
	}

	public void setXtZxbz(String xtZxbz){
		 po.setXtZxbz(String.valueOf(xtZxbz));
	}

	public String getXtZxyy(){
		return po.getXtZxyy();
	}

	public void setXtZxyy(String xtZxyy){
		 po.setXtZxyy(xtZxyy);
	}

}
