package com.zxkj.hc.qspt.controller;

import com.alibaba.fastjson.JSON;
import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Tpshzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.ZlscVo;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.util.ImgUtil;
import org.apache.http.entity.ContentType;
import org.setu.framework.constant.SetuPersistConstant;
import org.setu.framework.http.filter.DefaultSetuFilter;
import org.setu.framework.session.controller.SetuSessionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * made by hxt
 */

public class ImageDataSyncController extends SetuSessionController{
    private transient static final Logger logger = LoggerFactory.getLogger(ImageDataSyncController.class);

    private static final String SUCCESS="SUCCESS";

    @Value("${fileUpload.smallPictureMaxSize}")
    private int smallPictureMaxSize;
    /**
     * 同步房管局信息
     * @return 胡小天
     */
    public String sync(String applyId,String flowType,String filePath,String userId,boolean getPictrueById) throws Exception {

        /**
         * 取图片数据
         */
        System.out.println("======房管局图片同步模块======");
        System.out.println("applyId="+applyId);
        System.out.println("flowType="+flowType);
        System.out.println("filePath="+filePath);
        System.out.println("getPictrueById="+getPictrueById);
        System.out.println("user_id="+userId);
        File file=null;
        //图片获取方式
        if(!getPictrueById) {
            System.out.println("以URL方式获取图片！");
            File file1=null;
            file1 = getPicByUrl(filePath);
            String status = insertImage(file1,applyId,userId,flowType,1);
            System.out.println("同步房管局图片状态为:"+status);
        }else{
            System.out.println("以ID的方式获取图片！");
        if(flowType.equalsIgnoreCase("sfyz")){
            Object result = JSON.parseObject(filePath);
            Map<String, List> map = (Map<String, List>) result;
            map.forEach((k, v) -> {
                System.out.println(k);
                List list = v;
                for (int i = 0; i < list.size(); i++) {
                    String o = list.get(i).toString();
                    System.out.println("多URL的地址为："+o);
                    try {
                        File file1=null;
                        file1 = getPicById(o);
                        String status = insertImage(file1,applyId,userId,flowType,i+1);
                        System.out.println("同步房管局图片状态为:"+status);
                    } catch (Exception e) {
                        logger.error("",e);
                    }
                }
            });
        }else{
            file = getPicById(filePath);
            String status = insertImage(file,applyId,userId,flowType,1);
            System.out.println("同步房管局图片状态为:"+status);
        }

        }
        return SUCCESS;
    }

    /*
    提取出公共部分代码
     */
    public String insertImage(File file,String applyId,String userId,String flowType,int i)  throws Exception{
        System.out.println("file="+file);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("",e);
        }
        MultipartFile multipartFile = new MockMultipartFile("copy"+file.getName(),file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),fileInputStream);
        System.out.println("得到的图片名字为：" + multipartFile.getName()); // 输出copyte
        String status = syncImage(applyId,userId,flowType,"",multipartFile);
        System.out.println(status);
        return status;
    }


    // 读取的方法
    public byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        // 关闭流
        outstream.close();
        inStream.close();
        return outstream.toByteArray();
    }
    /**
     * create by 胡小天
     * @param applyId
     * @param applyUserId
     * @param flowType 流程
     * @param qmType  区分签名到哪一步了
     * @param file    图片文件
     * @return  成功返回SUCCSS，不返回SUCCESS则认为同步失败
     * @throws IOException
     */

    public String syncImage(String applyId,String applyUserId,String flowType,String qmType,MultipartFile file) throws IOException {
        //流程是否已存在标志，0表示未创建
        int flowStatus = 0;
        //info存在标志
        int infoStatus = 0;
        ZlscVo zlscVo=new ZlscVo();
        //创建新的流程
        ApplyFlowEntity applyFlow = new ApplyFlowEntity();
        applyFlow.setApplyId(applyId);
        applyFlow.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
        applyFlow.setApplyUserId(applyUserId);
        applyFlow.setFlowType(flowType);
        //判断流程是否已存在
        if(applyFlow.countBySelf()>0) {
            flowStatus = 1;
            System.out.println("流程"+flowType+"已存在");
            applyFlow.queryBySelf();
        }else{
            applyFlow.setXgsj(getTimeNow());
            BeanUtil.setXzField(applyFlow);
            zlscVo.setFlowId(applyFlow.insertSelf());
        }
        //创建新的info
        FlowInfoEntity flowInfoEntity = new FlowInfoEntity();
        flowInfoEntity.setApplyId(applyId);
        if(flowStatus == 0){
            flowInfoEntity.setFlowId(zlscVo.getFlowId());
        }else{
            flowInfoEntity.setFlowId(applyFlow.getId());
        }
        FlowFileEntity flowfile=new FlowFileEntity();
        flowfile.setFlowId(flowInfoEntity.getFlowId());
        int info_count = flowfile.countBySelf();
        System.out.println("info_count="+info_count);
        if(flowInfoEntity.countBySelf()>0 && info_count!=2){
            infoStatus = 1;
            System.out.println("info "+flowType+"已存在");
            //如果存在多个组，则取最新的infoid
            if(info_count>2){
                flowInfoEntity.setSort("2");
            }
            flowInfoEntity.queryBySelf();
        }else{
            System.out.println("file_info的count数:"+flowInfoEntity.countBySelf());
            flowInfoEntity.setXgsj(getTimeNow());
            flowInfoEntity.setXtZxbz(QsptConsts.XT_ZXBZ_WZX);
            flowInfoEntity.setSort(SetuPersistConstant.DEAL + "select IFNULL(max(sort),0)+1 from flow_info where flow_Id='"+flowInfoEntity.getFlowId()+"'");
            BeanUtil.setXzField(flowInfoEntity);
            zlscVo.setFlowInfoId(flowInfoEntity.insertSelf());
        }


        FlowFileEntity flowFileEntity=new FlowFileEntity();
        byte[] fileData=file.getBytes();

//        flowFileEntity.setFileContent(fileData);
        try {
            flowFileEntity.setFileContent(ImgUtil.compressUnderSize(fileData,201400));
            System.out.println("图片的大小："+smallPictureMaxSize);
            flowFileEntity.setSmallPicture(ImgUtil.compressUnderSize(fileData,201400));
        }catch (Exception e){
            logger.error("",e);
            flowFileEntity.setSmallPicture(null);
        }
        flowFileEntity.setApplyId(applyId);
        if(flowStatus == 0){
            flowFileEntity.setFlowId(zlscVo.getFlowId());
        }else{
            flowFileEntity.setFlowId(applyFlow.getId());
        }
        if(infoStatus == 0){
            flowFileEntity.setInfoId(zlscVo.getFlowInfoId());
            flowFileEntity.setSort(SetuPersistConstant.DEAL + "select IFNULL(max(sort),0)+1 from flow_file where flow_Id='"+zlscVo.getFlowId()+"'");

        }else{
            flowFileEntity.setInfoId( flowInfoEntity.getId());
            flowFileEntity.setSort(SetuPersistConstant.DEAL + "select IFNULL(max(sort),0)+1 from flow_file where flow_Id='"+flowInfoEntity.getFlowId()+"'");
        }

        flowFileEntity.setFileBz(qmType);
        flowFileEntity.setFileSize(String.valueOf(file.getSize()));
        flowFileEntity.setShzt(Tpshzt.DSH.getCode());
        BeanUtil.setXzField(flowFileEntity);
        flowFileEntity.insertSelf();
        return SUCCESS;
    }
    public  File getPicByUrl(String filePath) throws Exception {
        File file = null;
        FileOutputStream outStream = null;
        URL url = new URL(filePath);//获取到路径
        // http协议连接对象
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6 * 1000);
        System.out.println(conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            byte[] data = readStream(inputStream);
            file = new File("smart.jpg");// 给图片起名子
            try {
                outStream =  new FileOutputStream(file);//写出对象
                outStream.write(data);// 写入
            }catch (Exception e){
                e.getMessage();
            }finally {
                if(outStream!=null)
                outStream.close();  // 关闭流
            }
        }
        return file;
    }

    public  File getPicById(String filePath){
        File file = new File("smart.jpg");
        FileOutputStream write=null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            ApplyZlscController t = new ApplyZlscController();
            write = new FileOutputStream(file);
            byte[] decodeBytes = decoder.decodeBuffer(t.GetBase64ImageInfo(filePath));
            write.write(decodeBytes);

        }catch (IOException e) {
            logger.error("",e);
        } catch (Exception e) {
            logger.error("",e);
        }finally {
            try {
                if (write!=null)
                write.close();
            } catch (IOException e) {
                logger.error("",e);
            }
        }
        return file;
    }
}
