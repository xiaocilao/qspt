package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowFileEntity;
import java.util.*;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.utils.DateTimeHelper;
/**
 * 
 * @author: hfl 
 */
public class FlowFileDto extends BaseDto<FlowFileEntity>{

	private static  final long serialVersionUID = 1L;

	public FlowFileDto(){
	}

	public static final String TABLE_NAME= "flow_file";

	public static final String ID= "id";
	public static final Column ID_C= new Column(ID);

	public static final String APPLY_ID_D = "apply_id";
	public static final String APPLY_ID_J = "applyId";
	public static final Column APPLY_ID_C= new Column(APPLY_ID_D);

	public static final String FLOW_ID_D = "flow_id";
	public static final String FLOW_ID_J = "flowId";
	public static final Column FLOW_ID_C= new Column(FLOW_ID_D);

	public static final String INFO_ID_D = "info_id";
	public static final String INFO_ID_J = "infoId";
	public static final Column INFO_ID_C= new Column(INFO_ID_D);

	public static final String FILE_BZ_D = "file_bz";
	public static final String FILE_BZ_J = "fileBz";
	public static final Column FILE_BZ_C= new Column(FILE_BZ_D);

	public static final String FILE_SIZE_D = "file_size";
	public static final String FILE_SIZE_J = "fileSize";
	public static final Column FILE_SIZE_C= new Column(FILE_SIZE_D);

	public static final String FILE_CONTENT_D = "file_content";
	public static final String FILE_CONTENT_J = "fileContent";
	public static final Column FILE_CONTENT_C= new Column(FILE_CONTENT_D);

	public static final String SMALL_PICTURE_D = "small_picture";
	public static final String SMALL_PICTURE_J = "smallPicture";
	public static final Column SMALL_PICTURE_C= new Column(SMALL_PICTURE_D);

	public static final String FILE_DXCC_URL_D = "file_dxcc_url";
	public static final String FILE_DXCC_URL_J = "fileDxccUrl";
	public static final Column FILE_DXCC_URL_C= new Column(FILE_DXCC_URL_D);

	public static final String SHZT= "shzt";
	public static final Column SHZT_C= new Column(SHZT);

	public static final String SHYJ= "shyj";
	public static final Column SHYJ_C= new Column(SHYJ);

	public static final String SHR_ID_D = "shr_id";
	public static final String SHR_ID_J = "shrId";
	public static final Column SHR_ID_C= new Column(SHR_ID_D);

	public static final String SHR_XM_D = "shr_xm";
	public static final String SHR_XM_J = "shrXm";
	public static final Column SHR_XM_C= new Column(SHR_XM_D);

	public static final String SORT= "sort";
	public static final Column SORT_C= new Column(SORT);

	public static final String XT_LRSJ_D = "xt_lrsj";
	public static final String XT_LRSJ_J = "xtLrsj";
	public static final Column XT_LRSJ_C= new Column(XT_LRSJ_D);

	public static final String XT_LRRXM_D = "xt_lrrxm";
	public static final String XT_LRRXM_J = "xtLrrxm";
	public static final Column XT_LRRXM_C= new Column(XT_LRRXM_D);

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

	public static final String XGSJ= "xgsj";
	public static final Column XGSJ_C= new Column(XGSJ);



	public String getId(){
		return po.getId();
	}

	public void setId(String id){
		 po.setId(id);
	}

	public String getApplyId(){
		return po.getApplyId();
	}

	public void setApplyId(String applyId){
		 po.setApplyId(applyId);
	}

	public String getFlowId(){
		return po.getFlowId();
	}

	public void setFlowId(String flowId){
		 po.setFlowId(flowId);
	}

	public String getInfoId(){
		return po.getInfoId();
	}

	public void setInfoId(String infoId){
		 po.setInfoId(infoId);
	}

	public String getFileBz(){
		return po.getFileBz();
	}

	public void setFileBz(String fileBz){
		 po.setFileBz(fileBz);
	}

	public String getFileSize(){
		return po.getFileSize();
	}

	public void setFileSize(String fileSize){
		 po.setFileSize(fileSize);
	}

	public byte[] getFileContent(){
		return po.getFileContent();
	}

	public void setFileContent(byte[] fileContent){
		 po.setFileContent(fileContent);
	}

	public byte[] getSmallPicture(){
		return po.getSmallPicture();
	}

	public void setSmallPicture(byte[] smallPicture){
		 po.setSmallPicture(smallPicture);
	}

	public String getFileDxccUrl(){
		return po.getFileDxccUrl();
	}

	public void setFileDxccUrl(String fileDxccUrl){
		 po.setFileDxccUrl(fileDxccUrl);
	}

	public Integer getShzt(){
		return Integer.parseInt(po.getShzt());
	}

	public void setShzt(int shzt){
		 po.setShzt(String.valueOf(shzt));
	}

	public String getShyj(){
		return po.getShyj();
	}

	public void setShyj(String shyj){
		 po.setShyj(shyj);
	}

	public String getShrId(){
		return po.getShrId();
	}

	public void setShrId(String shrId){
		 po.setShrId(shrId);
	}

	public String getShrXm(){
		return po.getShrXm();
	}

	public void setShrXm(String shrXm){
		 po.setShrXm(shrXm);
	}

	public Integer getSort(){
		return Integer.parseInt(po.getSort());
	}

	public void setSort(int sort){
		 po.setSort(String.valueOf(sort));
	}

	public Date getXtLrsj(){
		return  DateTimeHelper.parseToDate(po.getXtLrsj());
	}

	public void setXtLrsj(Date xtLrsj){
		 po.setXtLrsj(DateTimeHelper.formatDateTimetoString(xtLrsj));
	}

	public String getXtLrrxm(){
		return po.getXtLrrxm();
	}

	public void setXtLrrxm(String xtLrrxm){
		 po.setXtLrrxm(xtLrrxm);
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

	public void setXtZxbz(int xtZxbz){
		 po.setXtZxbz(String.valueOf(xtZxbz));
	}

	public String getXtZxyy(){
		return po.getXtZxyy();
	}

	public void setXtZxyy(String xtZxyy){
		 po.setXtZxyy(xtZxyy);
	}

	public Date getXgsj(){
		return  DateTimeHelper.parseToDate(po.getXgsj());
	}

	public void setXgsj(Date xgsj){
		 po.setXgsj(DateTimeHelper.formatDateTimetoString(xgsj));
	}

}
