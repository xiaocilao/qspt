package com.zxkj.hc.qspt.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowDataEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.ZlscVo;

public interface ApplyService {
	/**
	 * 修改文件
	 */
	public void updateFlowFile(String fileId, MultipartFile file,String qmType,Object[][] pressTexts,String applyId,String tag) throws IOException;
	
	/**
	 * 图片合成
	 */
	public Object[][] tphc(String applyId,String qmType,String tag);
	
	/**
	 * 新增文件
	 * @return 
	 */
	public void createFlowFile(String applyId, String flowId, String infoId,MultipartFile file,ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag)throws IOException ;
	/**
	 * 新增流程信息并上传图片
	 */
	public void createFlowInfoAndFlowFile(String applyId,String flowId,MultipartFile  file,ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag)throws IOException ;
	/**
	 * 新建流程并上传图片
	 */
	public void createFlowAndFlowFile(String applyId,String applyUserId,String flowType,MultipartFile  file,ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag)throws IOException ;
	/**
	 * 新建审批记录并上传图片
	 */
	public void createApplyRecordAndFlowFile(String applyUserId, String applyUserName,String flowType,MultipartFile  file,ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag)throws IOException ;
	/**
	 * 保存flow数据并且更新flow状态
	 */
	public void createFlowDataAndUpdateFlow(String applyId,String flowId,String flowType,List<FlowDataEntity> flowDataListPack);
	/**
	 * 只有文字没有图片时创建flow和info
	 */
	public String createFlowAndInfoWithoutFiles(String applyId,String userId,String flowType);

	/***
	 * 提交、更新房管局数据
	 */
	public boolean InsertOrUpdateFGJData(String userId,String bab_url, String wfzm_url,String hyzkzm_id, String sfk_id,
										 String sfz_id, String hkb_id, String qzy_id, String ghdyfcxy_id,String ht_url)
			throws IOException;
}
