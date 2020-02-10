package com.zxkj.hc.qspt.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.FbDataFgjEntity;
import com.zxkj.hc.qspt.util.ImgUtil;
import org.setu.framework.constant.SetuPersistConstant;
import org.setu.framework.exception.BusinessRuntimeException;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.CommonUtil;
import org.setu.framework.utils.FileHelper;
import org.setu.framework.utils.UuidHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.FlowType;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Tpshzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.ZlscVo;
import com.zxkj.hc.qspt.service.ApplyService;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import com.zxkj.hc.qspt.util.QzUitl;
@Service("applyService")
public class ApplyServiceImpl extends SetuSessionController implements ApplyService{
	private transient static final Logger logger = LoggerFactory.getLogger(ApplyServiceImpl.class);

	@Value("${fileUpload.smallPictureMaxSize}")
 	private int smallPictureMaxSize;

	/**
	 *
	 * @param qmType 如果执行到步骤5，则qmType表示三张不同签名的图片
	 * @param image 图片
	 * @param pressTexts
	 * @param applyId
	 * @param tag  不知道啥用
	 * @return
	 * @throws IOException
	 */
	private byte[] imageQm(String qmType,byte[] image,Object[][] pressTexts,String applyId,String tag) throws IOException {
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH)+1;
		if(m==13) {
			m=1;
		}
		int d = now.get(Calendar.DATE);
		byte[] tmpimg;
		if(qmType!=null) {
			BufferedImage bufferedImage=null;
			switch (qmType) {
			case QsptConsts.HYSMS:  //第一种签名照片
				if(tag!=null) {
					bufferedImage=ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.HYSMS+".png")); //读取第一张图片
					try {
						image=QzUitl.qzcl(bufferedImage, fileToByte(ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.HYSMS+".png"))), 1800, 2200, pressTexts,2);
					} catch (Exception e) {
						logger.error("",e);
					}
				}else {
					bufferedImage=ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.HYSMS+".png"));
					tmpimg=QzUitl.qzcl(bufferedImage, image, 1800, 2200, pressTexts,2);
					bufferedImage = ImageIO.read(new ByteArrayInputStream(tmpimg));
					image=QzUitl.qzcl(bufferedImage, image,1820, 2240,new Object[][] {new Object[] {Integer.toString(y),1520,2514},new Object[] {Integer.toString(m),1800,2514},new Object[] {Integer.toString(d),2000,2514}},300);
				}
				break;
			case QsptConsts.JTZFQKCXSQS:
				if(tag!=null) {  //E:\ms_hu\qishui_svn\qishui\hc-qspt\src\main\resources\res\jtzfqkcxsqs.png
         			bufferedImage=ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.JTZFQKCXSQS+".png"));
					try {
						image=QzUitl.qzcl(bufferedImage, fileToByte(ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.JTZFQKCXSQS+".png"))), 1800, 2300, pressTexts,2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("",e);
					}
				}else {
					bufferedImage=ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.JTZFQKCXSQS+".png"));
					tmpimg=QzUitl.qzcl(bufferedImage, image, 1800, 2200, pressTexts,2);
					bufferedImage = ImageIO.read(new ByteArrayInputStream(tmpimg));
					image=QzUitl.qzcl(bufferedImage, image,1820, 2310,new Object[][] {new Object[] {Integer.toString(y),1520,2550},new Object[] {Integer.toString(m),1800,2550},new Object[] {Integer.toString(d),2000,2550}},300);
				}
				break;
			case QsptConsts.JTZFQKSMS:
				if(tag!=null) {
					bufferedImage=ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.JTZFQKSMS+".png"));
					try {
						image=QzUitl.qzcl(bufferedImage, fileToByte(ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.JTZFQKSMS+".png"))), 1800, 1600, pressTexts,2);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("",e);
					}
				}else {
					bufferedImage=ImageIO.read(FileHelper.getClassPathInputStream("res/"+QsptConsts.JTZFQKSMS+".png"));
					tmpimg=QzUitl.qzcl(bufferedImage, image, 1800, 2200, pressTexts,2);
					bufferedImage = ImageIO.read(new ByteArrayInputStream(tmpimg));
					image=QzUitl.qzcl(bufferedImage, image,1820, 1630,new Object[][] {new Object[] {Integer.toString(y),1520,1910},new Object[] {Integer.toString(m),1800,1910},new Object[] {Integer.toString(d),2000,1910}},300);
				}
				break;
			default:
				break;
			}
		}
		return image;
	}
	
	/**
	 * 
	 */
	public String createFlowAndInfoWithoutFiles(String applyId,String userId,String flowType) {
		String flowId="";
		ApplyFlowEntity entity = new ApplyFlowEntity();
		entity.setApplyId(applyId);
		entity.setApplyUserId(userId);
		entity.setFlowType(flowType);
		entity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		entity.setXgsj(getTimeNow());
		if(entity.countBySelf()>0) {
			throw new BusinessRuntimeException("申请流程记录已存在，请刷新页面再试！");
		}
		BeanUtil.setXzField(entity);
		flowId = entity.insertSelf();
		FlowInfoEntity Infoentity = new FlowInfoEntity();
		Infoentity.setApplyId(applyId);
		Infoentity.setFlowId(flowId);
		Infoentity.setXgsj(getTimeNow());
		Infoentity.setXtZxbz("0");
		Infoentity.setSort(SetuPersistConstant.DEAL + "select IFNULL(max(sort),0)+1 from flow_info where flow_Id='"+flowId+"'");
		BeanUtil.setXzField(Infoentity);
		Infoentity.insertSelf();
		return flowId;
	}
	
	
	/**
	 * 图片转byte
	 * @param img
	 * @throws Exception
	 */
	public byte[] fileToByte(BufferedImage img) throws Exception {
		System.out.println("文件："+img);
		byte[] bytes=null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", baos);
			bytes = baos.toByteArray();
			System.err.println(bytes.length);
		} catch (Exception e) {
			logger.error("",e);
		} finally {
			baos.close();
		}
		return bytes;
	}
	
	
	/**
	 * 图片合成
	 */
	public Object[][] tphc(String applyId,String qmType,String tag) {
		Object[][] pressTexts = null;
		if(qmType!=null&&"jtzfqkcxsqs".equals(qmType)) {
			pressTexts= new Object[10][10];   //胡小天，数组越界报错
			FlowDataEntity gffpentity = new FlowDataEntity();
			FlowDataEntity hkbxxentity = new FlowDataEntity();
			FlowDataEntity people = new FlowDataEntity();
			int count = 0;
			int i=0;
			gffpentity.addConditonItem(new ConditionItem("apply_flow.flow_type", "=", "gffp"));
			gffpentity.setApplyId(applyId);
			List<FlowDataEntity> gffp=gffpentity.queryListBySelf(false, "LEFT JOIN apply_flow ON apply_flow.id =flow_data.flow_id", null, "info_id asc", null, null, new Column("flow_data.*"));
			for(i=0;i<gffp.size();i++) {
				if(gffp.get(i).getKeyName().equals("第几套房")) {
					System.out.println("第几套房:"+gffp.get(i).getKeyValue());
					pressTexts[0][0] =gffp.get(i).getKeyValue();
				}


			}
			pressTexts[0][1]=860;
			pressTexts[0][2]=906;
			hkbxxentity.addConditonItem(new ConditionItem("apply_flow.flow_type", "=", "hkbxx"));
			hkbxxentity.setApplyId(applyId);
			List<FlowDataEntity> list=hkbxxentity.queryListBySelf(false, "LEFT JOIN apply_flow ON apply_flow.id =flow_data.flow_id", null, "info_id desc", null, null, new Column("flow_data.*"));
			int nameheight=1300;
			int idheight=1300;
			String flowId = list.get(0).getFlowId();
			for(i=0;i<list.size();i++) {
				pressTexts[i+1][0]=list.get(i).getKeyValue();
				String keyname = list.get(i).getKeyName();

				if("真实姓名".equals(keyname)) {
					System.out.println("真实姓名:"+keyname);
					try {
						System.out.println(new String(keyname.getBytes("GBK"),"GBK"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if(list.get(i).getKeyValue().length()==2) {
						pressTexts[i+1][1]=770;
					}else if(list.get(i).getKeyValue().length()==3) {
						pressTexts[i+1][1]=736;
					}else {
						pressTexts[i+1][1]=700;
					}
					pressTexts[i+1][2]=nameheight;
					nameheight+=124;
				}else {
					pressTexts[i+1][1]=1300;
					pressTexts[i+1][2]=idheight;
					idheight+=124;
				}
			}
			people.setFlowId(flowId);
			count = people.countBySelf();
			System.out.println("家庭人数："+count/2+"i:"+(i));
			pressTexts[i+1][0] = Integer.toString(count/2);
			pressTexts[i+1][1]=432;
			pressTexts[i+1][2]=1730;
			
		}else if(qmType!=null && "jtzfqksms".equals(qmType)) {
			pressTexts= new Object[10][10];
			FlowDataEntity gffpentity = new FlowDataEntity();
			gffpentity.addConditonItem(new ConditionItem("apply_flow.flow_type", "=", "gffp"));
			gffpentity.setApplyId(applyId);
			pressTexts = new Object[][] {new Object[] {"",1120,976},new Object[] {"",310,1100},new Object[] {"",1220,1226}};
			List<FlowDataEntity> gffp=gffpentity.queryListBySelf(false, "LEFT JOIN apply_flow ON apply_flow.id =flow_data.flow_id", null, "info_id asc", null, null, new Column("flow_data.*"));
			for(int i=0;i<gffp.size();i++) {
				System.out.println(gffp.get(i).getKeyName()+":"+"购买房屋的详细地址");
				if(gffp.get(i).getKeyName().equals("购买房屋的详细地址")) {
					System.out.println("购买房屋的详细地址:"+gffp.get(i).getKeyValue());
					String tempString ="";
					tempString =gffp.get(i).getKeyValue();



					if(gffp.get(i).getKeyValue().length()>17) {
						pressTexts[0][0]=tempString.substring(0,15);
						pressTexts[1][0]=tempString.substring(15,tempString.length());
					}else {
						pressTexts[0][0]=tempString;
						pressTexts[1][0]="";
					}
				}
				if(gffp.get(i).getKeyName().equals("第几套房")) {
					String tempString =gffp.get(i).getKeyValue();
					pressTexts[2][0]=tempString.substring(1, tempString.length());
				}
			}
		}else if(qmType!=null&&"hysms".equals(qmType)) {
			pressTexts = new Object[][] {new Object[] {"默认",690,950},new Object[] {"511502199705145018",700,1070},new Object[] {"(曹博凯)",480,1200},new Object[] {"宜宾市翠屏区新码头街学圆小区13幢6楼1号",1050,1190},new Object[] {"新码头街13幢六楼一号",300,1320},new Object[] {"12345678",1650,1446},new Object[] {"我个人",410,1572},new Object[] {"21岁",900,1820},new Object[] {"于2018年12月13日结婚",580,1946}};
			FlowDataEntity gffpentity = new FlowDataEntity();
			FlowDataEntity hkbxxentity = new FlowDataEntity();
			FlowDataEntity hyzkentity = new FlowDataEntity();
			FlowDataEntity fwsyrentity = new FlowDataEntity();
			String hy = "已婚";
			String sj = "";
			hkbxxentity.addConditonItem(new ConditionItem("apply_flow.flow_type", "=", "sfyz"));//hkbxx
			gffpentity.addConditonItem(new ConditionItem("apply_flow.flow_type", "=", "gffp"));
			hyzkentity.addConditonItem(new ConditionItem("apply_flow.flow_type", "=", "hyzk"));
			fwsyrentity.addConditonItem(new ConditionItem("apply_flow.flow_type", "=", "sfyz"));
			hkbxxentity.setApplyId(applyId);
			gffpentity.setApplyId(applyId);
			hyzkentity.setApplyId(applyId);
			fwsyrentity.setApplyId(applyId);
			List<FlowDataEntity> hkbxx=hkbxxentity.queryListBySelf(false, "LEFT JOIN apply_flow ON apply_flow.id =flow_data.flow_id", null, "info_id asc", null, null, new Column("flow_data.*"));
			List<FlowDataEntity> gffp=gffpentity.queryListBySelf(false, "LEFT JOIN apply_flow ON apply_flow.id =flow_data.flow_id", null, "info_id asc", null, null, new Column("flow_data.*"));
			List<FlowDataEntity> hyzk=hyzkentity.queryListBySelf(false, "LEFT JOIN apply_flow ON apply_flow.id =flow_data.flow_id", null, "info_id asc", null, null, new Column("flow_data.*"));
			List<FlowDataEntity> fwsyr=fwsyrentity.queryListBySelf(false, "LEFT JOIN apply_flow ON apply_flow.id =flow_data.flow_id", null, "info_id asc", null, null, new Column("flow_data.*"));
			for(int i=0;i<hkbxx.size();i++) {
				if(hkbxx.get(i).getKeyName().equals("真实姓名")) {
					String tempString =hkbxx.get(i).getKeyValue();
					pressTexts[0][0] = tempString;
					pressTexts[2][0] = "("+hkbxx.get(i).getKeyValue()+")";
					System.out.println(pressTexts[0][0]+"==="+pressTexts[2][0]);
				}else if(hkbxx.get(i).getKeyName().equals("身份证号")) {
					pressTexts[1][0] = hkbxx.get(i).getKeyValue();
					int Y = Integer.parseInt(hkbxx.get(i).getKeyValue().substring(6,10));
					Calendar now = Calendar.getInstance();
					int y = now.get(Calendar.YEAR);
					pressTexts[7][0] = y-Y+"岁";
				}
			}
			if(fwsyr.size()>2) {
				pressTexts[6][0] = "";
				for(int i=0;i<fwsyr.size();i++) {
					if(fwsyr.get(i).getKeyName().endsWith("真实姓名")) {
						try {
							pressTexts[6][0] += fwsyr.get(i).getKeyValue() + ",";
						}catch (Exception e){
							System.out.println("读取名字出错");
						}
					}
				}
			}else {
				try {
					pressTexts[6][0] += new String("我个人".getBytes("GBK"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.info(e.getMessage());
				}
			}
			
			for(int i=0;i<gffp.size();i++) {

				if(gffp.get(i).getKeyName().equals("购买房屋的详细地址")) {
					if(gffp.get(i).getKeyValue().length()>16) {
						String tempString =gffp.get(i).getKeyValue();
						pressTexts[3][0] = tempString.substring(0,16);
						pressTexts[4][0] =tempString.substring(16,tempString.length()-1);

					}else {
						pressTexts[3][0] = gffp.get(i).getKeyValue();
						pressTexts[4][0] = "";
					}
				}else if("《房屋所有权证》证号".equals(gffp.get(i).getKeyName())) {
					try {
						pressTexts[5][0] = new String(gffp.get(i).getKeyValue().getBytes("GBK"),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						logger.info(e.getMessage());
					}
				}
			}
			for (int i = 0; i < hyzk.size(); i++) {
				if ("婚姻状况".equals( hyzk.get(i).getKeyName())) {
					hy = hyzk.get(i).getKeyValue();
				}else if("结婚时间".equals(hyzk.get(i).getKeyName())){ //胡小天 添加结婚日期判断
					String time = hyzk.get(i).getKeyValue();
					sj += time.substring(0,4)+"年";
					sj += time.substring(5,7)+"月";
					sj += time.substring(8,10)+"日";
				}
			}
			if("未婚".equals(hy)) {
				pressTexts[8][0] ="单身";
			}else {
				pressTexts[8][0] = "于"+sj+""+hy;
			}
		}
		return pressTexts;
	}
	
	
	/**
	 * 修改文件
	 */
	@Override
	public void updateFlowFile(String fileId, MultipartFile file,String qmType,Object[][] pressTexts,String applyId,String tag) throws IOException {
		//图片更新补充上传(fileId,file)
		System.out.println("更新文件！");
		FlowFileEntity entity=new FlowFileEntity();
		if(tag!=null) {
			entity.setShzt("3");
		}else {
			entity.setShzt("0");
		}
		if(file==null && tag!=null) {
			entity.setFileContent(imageQm(qmType,null,pressTexts,applyId,tag));
		}else if(file != null){
			byte[] fileData=file.getBytes();
			byte[] fileData2=imageQm(qmType,fileData,pressTexts,applyId,tag);
//            entity.setFileContent(fileData2);
			try {
                entity.setFileContent(ImgUtil.compressUnderSize(fileData2,201400));
				entity.setSmallPicture(ImgUtil.compressUnderSize(fileData2,smallPictureMaxSize));
			}catch (Exception e){
				logger.error("",e);
				entity.setSmallPicture(null);
			}
		}else{
			logger.info("进入啦啦啦啦啦啦啦");
		}
		entity.setId(fileId);
		entity.setFileBz(qmType);
		BeanUtil.setXgField(entity,getTimeNow());
		entity.updateBySelfId();
	}
	/**
	 * 新增文件
	 */
	@Override
	public void createFlowFile(String applyId, String flowId, String infoId,MultipartFile file,ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag) throws IOException {
		FlowFileEntity entity=new FlowFileEntity();
		byte[] img = null;
		if(file==null) {
			img = imageQm(qmType,null,pressTexts,applyId,tag);
			entity.setFileContent(img);
		}else {
			byte[] fileData=file.getBytes();
			byte[] fileData2=imageQm(qmType,fileData,pressTexts,applyId,tag);
//			entity.setFileContent(fileData2);
			entity.setFileContent(ImgUtil.compressUnderSize(fileData2,2014000));
			try {
				entity.setSmallPicture(ImgUtil.compressUnderSize(fileData2,smallPictureMaxSize));
			}catch (Exception e){
				logger.error("",e);
				entity.setSmallPicture(null);
			}
		}
		entity.setApplyId(applyId);
		entity.setFlowId(flowId);
		entity.setFileBz(qmType);
		if(file==null && img!=null) {
			entity.setFileSize(""+img.length);
		}else {
			if(file!=null)
			entity.setFileSize(String.valueOf(file.getSize()));
		}
		entity.setSort(SetuPersistConstant.DEAL + "select IFNULL(max(sort),0)+1 from flow_file where flow_Id='"+flowId+"'");
		entity.setInfoId(infoId);
		if(tag!=null) {
			entity.setShzt("3");
		}else {
			entity.setShzt(Tpshzt.DSH.getCode());
		}
		BeanUtil.setXzField(entity);
		zlscVo.setFlowFileId(entity.insertSelf());
	}
	/**
	  * 新增流程信息并上传图片
	 */
	@Override
	public void createFlowInfoAndFlowFile(String applyId,String flowId,MultipartFile  file,ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag)throws IOException  {
		FlowInfoEntity entity = new FlowInfoEntity();
		entity.setApplyId(applyId);
		entity.setFlowId(flowId);
		entity.setXgsj(getTimeNow());
		entity.setXtZxbz("0");
		entity.setSort(SetuPersistConstant.DEAL + "select IFNULL(max(sort),0)+1 from flow_info where flow_Id='"+flowId+"'");
		BeanUtil.setXzField(entity);
		zlscVo.setFlowInfoId(entity.insertSelf());
		createFlowFile(applyId,flowId, zlscVo.getFlowInfoId(), file, zlscVo,qmType,pressTexts,tag);
	}
	/**
	 * 新建流程并上传图片
	 */
	public void createFlowAndFlowFile(String applyId,String applyUserId,String flowType,MultipartFile  file,ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag)throws IOException  {
		ApplyFlowEntity entity = new ApplyFlowEntity();
		entity.setApplyId(applyId);
		entity.setApplyUserId(applyUserId);
		entity.setFlowType(flowType);
		entity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		entity.setXgsj(getTimeNow());
		if(entity.countBySelf()>0) {
			throw new BusinessRuntimeException("申请流程记录已存在，请刷新页面再试！");
		}
		BeanUtil.setXzField(entity);
		zlscVo.setFlowId(entity.insertSelf());
		createFlowInfoAndFlowFile(applyId, zlscVo.getFlowId(), file, zlscVo,qmType,pressTexts,tag);
	}
	/**
	 * 新建审批记录并上传图片
	 */
	@Override
	public void createApplyRecordAndFlowFile(String applyUserId, String applyUserName,String flowType, MultipartFile file, ZlscVo zlscVo,String qmType,Object[][] pressTexts,String tag)
			throws IOException {
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSqrId(applyUserId);
		entity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
		ApplyUserEntity applyUserEntity = new ApplyUserEntity();
		applyUserEntity.setId(applyUserId);
		applyUserEntity.queryBySelf();
		entity.setXtPhone(applyUserEntity.getPhoneNumber());
		if(entity.countBySelf()>0) {
			throw new BusinessRuntimeException("申请记录已存在，请刷新页面再试！");
		}
		entity.setSqrXm(applyUserName);
		BeanUtil.setXzField(entity);
		zlscVo.setApplyId(entity.insertSelf());
		createFlowAndFlowFile(zlscVo.getApplyId(), applyUserId, flowType, file, zlscVo,qmType,pressTexts,tag);
	}
	/**
	 * 保存flow数据并且更新flow状态
	 */
	@Override
	public void createFlowDataAndUpdateFlow(String applyId,String flowId,  String flowType, List<FlowDataEntity> flowDataListPack) {
		String infoId=null;
		if(CommonUtil.isNull(flowId)) {
			ApplyFlowEntity entity = new ApplyFlowEntity();
			entity.setApplyId(applyId);
			entity.setApplyUserId(((SetuSession)getSetuSession()).getUserId());
			entity.setFlowType(flowType);
			entity.setXgsj(getTimeNow());
			entity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
			if(entity.countBySelf()>0) {
				throw new BusinessRuntimeException("申请流程记录已存在，请刷新页面再试！");
			}
			BeanUtil.setXzField(entity);
			flowId=entity.insertSelf();
			FlowInfoEntity entity1 = new FlowInfoEntity();
			entity1.setXtZxbz("0");
			entity1.setApplyId(applyId);
			entity1.setFlowId(flowId);
			entity1.setXgsj(getTimeNow());
			entity1.setSort(SetuPersistConstant.DEAL + "select IFNULL(max(sort),0)+1 from flow_info where flow_Id='"+flowId+"'");
			BeanUtil.setXzField(entity1);
			infoId=entity1.insertSelf();
			System.out.println("插入成功！");
		}else {
			FlowInfoEntity info = new FlowInfoEntity();
			info.setXtZxbz("0");
			info.addConditonItem(new ConditionItem("flow_id", "=", flowId));
			info.addConditonItem(new ConditionItem("xt_zxbz","=",QsptConsts.XT_ZXBZ_WZX));
			info.updateBySelfCondition();
		}
		for(FlowDataEntity flowDataEntity:flowDataListPack){
			//清洗数据
			if(CommonUtil.isNull(flowDataEntity.getId())) {
				flowDataEntity.setId(UuidHelper.getUUID());
			}
			flowDataEntity.setApplyId(applyId);
			if(infoId!=null) {
				flowDataEntity.setInfoId(infoId);
				flowDataEntity.deleteBySelf();
			}
			flowDataEntity.setFlowId(flowId);
			BeanUtil.setXzField(flowDataEntity);
			flowDataEntity.mergeSelfById();
		}
		ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
		applyRecordEntity.setId(applyId);
		if(flowType.equals(FlowType.CQBC.getCode())){
			applyRecordEntity.setCqbc(QsptConsts.FLOW_YTG);
		}else if(flowType.equals(FlowType.GFFP.getCode())){
			applyRecordEntity.setGffp(QsptConsts.FLOW_YTG);
		}else if(flowType.equals(FlowType.GFHT.getCode())){
			applyRecordEntity.setGfht(QsptConsts.FLOW_YTG);
		}else if(flowType.equals(FlowType.HKBXX.getCode())){
			applyRecordEntity.setHkbxx(QsptConsts.FLOW_YTG);
		}else if(flowType.equals(FlowType.HYZK.getCode())){
			applyRecordEntity.setHyzk(QsptConsts.FLOW_YTG);
		}else if(flowType.equals(FlowType.SFYZ.getCode())){
			applyRecordEntity.setSfyz(QsptConsts.FLOW_YTG);
		}else if(flowType.equals(FlowType.SQS.getCode())){
			applyRecordEntity.setSqs(QsptConsts.FLOW_YTG);
		}
		BeanUtil.setXgField(applyRecordEntity,getTimeNow());
		applyRecordEntity.updateBySelfId();
	}

	/***
	 * 保存并更新房产数据
	 * @param userId
	 * @param bab_url
	 * @param wfzm_url
	 * @param sfk_id
	 * @param sfz_id
	 * @param hkb_id
	 * @param ghdyfcxy_id
	 * @param ht_url
	 * @return
	 */
	@Override
	public boolean InsertOrUpdateFGJData(String userId,String bab_url,String wfzm_url,String hyzkzm_id, String sfk_id,
										 String sfz_id, String hkb_id, String qzy_id, String ghdyfcxy_id,String ht_url)
			throws IOException {

		FbDataFgjEntity fgjData = new FbDataFgjEntity();
		fgjData.setUserId(userId);
		fgjData.setBabUrl(bab_url);
		fgjData.setGhdyfcxyId(ghdyfcxy_id);
		fgjData.setHkbId(hkb_id);
		fgjData.setQzyId(qzy_id);
		fgjData.setHtUrl(ht_url);
		fgjData.setHyzkzmId(hyzkzm_id);
		fgjData.setSfkId(sfk_id);
		fgjData.setSfzId(sfz_id);
		fgjData.setWfzmUrl(wfzm_url);
		List<FbDataFgjEntity> lst = fgjData.queryListBySelf();
		if (lst.size() == 0) {
			if (fgjData.createSelf() == 0) {
				throw new BusinessRuntimeException("添加数据失败！");
			}
		}else {
			if (fgjData.updateBySelfId() == 0 ) {
				throw new BusinessRuntimeException("更新数据失败！");
			}
		}

		return true;
	}

}
