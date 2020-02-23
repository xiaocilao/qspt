package com.zxkj.hc.qspt.controller;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.FlowFileDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.*;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.vo.ZlspVo;
import com.zxkj.hc.qspt.service.LoginService;
import com.zxkj.hc.qspt.service.RoleService;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.setu.framework.exception.BusinessInfoException;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.jdbc.core.Condition;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.encrypt.Md5Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/apply")
public class PCLoginController  extends SetuSessionController {
    @Resource
    LoginService loginService;
    @Resource
    RoleService roleService;
    @RequestMapping("/login")
    @ResponseBody
    public Map login(String username,String password) {
        SetuSession session = (SetuSession) getSetuSession();
        Map map = new HashMap();

        UserEntity userEntity = loginService.userLogin(username, password,null);
        if (userEntity == null) {
            map.put("code","101"); //101用户找不到
            return map;
        }
        session.setUserId(userEntity.getId());
        session.setUserName(userEntity.getUserName());
        List<RoleEntity> roleEntityList=roleService.listRoleByUserId(userEntity.getId());
        String[] userRole=new String[roleEntityList.size()];
        for(int i=0;i<userRole.length;i++){
            userRole[i]=roleEntityList.get(i).getId();
        }
        session.setUserRole(userRole);
        map.put("code","200");
        map.put("ClientKey",session.initClientKey());
        return map;
    }
    @RequestMapping("/shenhe")
    @ResponseBody
    public Map shenhe() {
        Map map = new HashMap();
        SetuSession session=(SetuSession) getSetuSession();
        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
        Condition condition=new Condition();
        condition.add(new ConditionItem("xt_zxbz","=", "0"));
        condition.add(new ConditionItem("spzt","=", Spzt.TJSH.getCode()));
        condition.add("or",new ConditionItem("spzt","=",Spzt.TG.getCode()));
        condition.add("or",new ConditionItem("spzt","=",Spzt.BTG.getCode()));
        applyRecordEntity.addConditon("and",condition);
        List<ApplyRecordEntity> recordData = applyRecordEntity.queryListBySelf();
        if(recordData.size()==0){
            map.put("code","201");
            return map;
        }
        map.put("code","200");
        map.put("list",recordData);
        return map;
    }
   /*
   得到邮寄信息
    */
    @RequestMapping("/yjxx")
    @ResponseBody
    public Map yjxx(String userId) {
        Map map = new HashMap();
        YoujixinxiEntity youjixinxiEntity = new YoujixinxiEntity();
        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
        applyRecordEntity.setSqrId(userId);
        applyRecordEntity.setXtZxbz("0");
        applyRecordEntity.queryBySelf();
        youjixinxiEntity.setUserId(applyRecordEntity.getId());
        youjixinxiEntity.queryBySelf();
        map.put("code","200");
        map.put("data",youjixinxiEntity);
        return map;
    }

    @RequestMapping("/yjsc")
    @ResponseBody
    public Map yjsc(String userId,String name,String postNumber) {
        Map map = new HashMap();
        YoujixinxiEntity youjixinxiEntity = new YoujixinxiEntity();
        Condition condition=new Condition();
        ApplyRecordEntity applyRecordEntity1 = new ApplyRecordEntity();
        applyRecordEntity1.setSqrId(userId);
        applyRecordEntity1.setXtZxbz("0");
        applyRecordEntity1.queryBySelf();
        condition.add(new ConditionItem("user_id","=", applyRecordEntity1.getId()));
        youjixinxiEntity.setStatus("1");
        youjixinxiEntity.setPostNumber(postNumber);
        youjixinxiEntity.setPostName(name);
        int status =  youjixinxiEntity.update(condition);
        if(status>0){
            ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
            Condition condition1=new Condition();
            condition1.add(new ConditionItem("sqr_id","=", userId));
            condition1.add("and",new ConditionItem("xt_zxbz","=", "0"));
            applyRecordEntity.setSpzt("7");
            int status1 = applyRecordEntity.update(condition1);
            if(status1>0){
                map.put("code","200");
                return map;
            }
        }
        map.put("code","201");
        return map;
    }

    @RequestMapping("/report")
    @ResponseBody
    public Map report(int pageOffset,int pageRowSize,String filter,String keywords) {
        Map map = new HashMap();
        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
//        applyRecordEntity.addConditonItem(new ConditionItem("xt_zxbz", "=", "0"));
        applyRecordEntity.addConditonItem("and", new ConditionItem("spzt", ">", "0"));
        if(!filter.equalsIgnoreCase("")){
            applyRecordEntity.addConditonItem("and", new ConditionItem("spzt", "=", filter));
        }
        if(!keywords.equalsIgnoreCase("")){
            applyRecordEntity.addConditonItem("and", new ConditionItem("xt_zhxgrxm", "like", "%"+keywords+"%"));

        }
        List<ApplyRecordEntity> list = applyRecordEntity.queryListBySelf("num"+" asc", pageOffset, pageRowSize);
        if(list.size()>0){
            map.put("code","200");
            map.put("data",list);
            return map;
        }
        map.put("code","201");
        return map;
    }


    @RequestMapping("/reload")
    @ResponseBody
    public Map reload() {
        Map map = new HashMap();
        ApplyRecordEntity applyRecordEntity =new ApplyRecordEntity();
        applyRecordEntity.addConditonItem(new ConditionItem("xt_zxbz","=","0"));
        applyRecordEntity.setXtZxbz("1");
        applyRecordEntity.updateBySelfCondition();
        map.put("code","200");
        return map;
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public Map addUser() {
        Map map = new HashMap();
        String salt = "f7d16bef35c740c4884ffc4b9a89ab91";
        String name = "admin";
        String str1 = Md5Util.encode(name,salt);
        map.put("code","200");
        return map;
    }
            
    @RequestMapping("/dowload")
    public void  dowload(HttpServletResponse response,String id) throws Exception{
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        Map<String, Object> indata = new LinkedHashMap<String, Object>();
        
        //下载时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        indata.put("日期", simpleDateFormat.format(date));
        data.put("契税", indata);// 标题 
        
        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
        applyRecordEntity.setId(id);
        try{
            applyRecordEntity.queryBySelf();
        }catch (Exception e){
            System.out.println("查询用户出错！");
            return;
        }
        //身份证
        indata = new LinkedHashMap<String, Object>();
        List<FlowDataEntity> listFlowData  = queryFlow(id,"sfyz");
        extractData(listFlowData, indata);
        if(listFlowData != null && !listFlowData.isEmpty()){
        	String flowid = null;
            for(FlowDataEntity fde : listFlowData){
            	indata.put(fde.getKeyName(), fde.getKeyValue());
            	if(flowid == null)
            		flowid = fde.getFlowId();
            }
            setImag(flowid, indata);
        }
        data.put("身份验证", indata);
        //婚姻状况
        indata = new LinkedHashMap<String, Object>();
        extractData(queryFlow(id,"hyzk"), indata);
        data.put("婚姻状态", indata);        

        //户口簿信息
        indata = new LinkedHashMap<String, Object>();
        extractData(queryFlow(id,"hkbxx"), indata);
        data.put("户口簿信息", indata);

        //购房发票
        indata = new LinkedHashMap<String, Object>();
        extractData(queryFlow(id,"gffp"), indata);
        data.put("购房发票", indata);
        
        //购房合同
        indata = new LinkedHashMap<String, Object>();
        extractData(queryFlow(id,"gfht"), indata);
        data.put("购房合同", indata);
        
        //授权书
        indata = new LinkedHashMap<String, Object>();
        extractData(queryFlow(id,"sqs"), indata);
        data.put("授权书", indata);
        
        //拆迁补偿
        indata = new LinkedHashMap<String, Object>();
        extractData(queryFlow(id,"cqbc"), indata);
        data.put("拆迁补偿", indata);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        this.download(response, data, simpleDateFormat.format(date)+".pdf");
        System.out.println("下载结束。。。。。");
    }
    
    private void extractData(List<FlowDataEntity> listFlowData, Map<String, Object> indata){
    	if(listFlowData != null && !listFlowData.isEmpty()){
        	String flowid = null;
            for(FlowDataEntity fde : listFlowData){
            	indata.put(fde.getKeyName(), fde.getKeyValue());
            	if(flowid == null)
            		flowid = fde.getFlowId();
            }
            try{
            	setImag(flowid, indata);
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
    }
    
    public void setImag(String id,Map map) throws Exception{
        FlowFileEntity hyzkFileEntity = new FlowFileEntity();
        hyzkFileEntity.setFlowId(id);
        List<FlowFileEntity> hyzkList = hyzkFileEntity.queryListBySelf();
        List<Map<String, Object>> imgs = new LinkedList<Map<String, Object>>();
        for (FlowFileEntity forHyzk :hyzkList) {
            FlowFileEntity hyzkFile = new FlowFileEntity();
            hyzkFile.setId(forHyzk.getId());
            Image img = Image.getInstance(hyzkFile.queryBlobBySelf(FlowFileDto.FILE_CONTENT_C));
            imgs.add(new HashMap<String, Object>(){
                {
                    this.put("path", img);
                    this.put("w", 300);
                    this.put("h", 500);
                }
            });
        }
        map.put("img", imgs);
    }
    public String insertData(List<FlowDataEntity> hkbList,Map doc) throws Exception{
        String flowId = "";
        for (FlowDataEntity flowDataEntity1:hkbList
        ) {
            if(flowDataEntity1.getKeyName().equalsIgnoreCase("真实姓名")){
                doc.put("姓名",flowDataEntity1.getKeyValue());
                flowId = flowDataEntity1.getFlowId();
            }else{
                doc.put("身份证",flowDataEntity1.getKeyValue());
            }

        }
        return flowId;
    }
    public List<FlowDataEntity> queryFlow(String id,String flowType){
        ApplyFlowEntity hyEntity = new ApplyFlowEntity();
        hyEntity.setApplyId(id);
        hyEntity.setFlowType(flowType);
        try{
            hyEntity.queryBySelf();
        }catch (Exception e){
            return null;
        }
        FlowDataEntity flowDataEntity = new FlowDataEntity();
        flowDataEntity.setFlowId(hyEntity.getId());
        List<FlowDataEntity> gffpList =flowDataEntity.queryListBySelf(false,"LEFT JOIN flow_info ON flow_data.id = flow_info.flow_id" +
                " and flow_data.info_id = flow_info.id",null,"flow_info.sort asc",null,null, new Column("flow_data.*"));

        return gffpList;
    }
    
    //文件下载
    @RequestMapping("/dowload_old")
    @ResponseBody
    public void  dowloads(HttpServletResponse response,String id) throws Exception{
        File file = null;
        String filePar = null;
        filePar = "D:qishuidowload/";// 文件夹路径
        File myPath = new File( filePar );
        file = new File(filePar+"/"+"qishui.pdf");
        if (!myPath.exists()){
            myPath.mkdir();
        }
        Rectangle tRectangle = new Rectangle(PageSize.A4); // 页面大小 
        tRectangle.setBackgroundColor(BaseColor.WHITE); // 页面背景色 
        tRectangle.setBorder(1220);// 边框 
        tRectangle.setBorderColor(BaseColor.BLUE);// 边框颜色 
        tRectangle.setBorderWidth(244.2f);// 边框宽度 
        Document doc = new Document(tRectangle.rotate());// 定义文档  
        doc.setMargins(10, 20, 30, 40);// 页边空白 
        //doc = new Document(tRectangle.rotate());// 横向打印 
        //PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(file));// 书写器 
        PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);//版本(默认1.4) 
        //设置PDF文档属性 
        doc.addTitle("契税");// 标题 
        Paragraph tParagraph = new Paragraph("契税", getChineseFont());
        tParagraph.setAlignment(Element.ALIGN_JUSTIFIED);// 对齐方式 
        //下载时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Paragraph xzsj = new Paragraph(simpleDateFormat.format(date), getChineseFontContent());
        xzsj.setAlignment(Element.ALIGN_RIGHT);// 对齐方式



        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
        applyRecordEntity.setId(id);
        try{
            applyRecordEntity.queryBySelf();
        }catch (Exception e){
            System.out.println("查询用户出错！");
            return;
        }
        Paragraph czdx = new Paragraph(applyRecordEntity.getSprXm(), getChineseFontContent());
        czdx.setAlignment(Element.ALIGN_RIGHT);// 对齐方式 
        doc.open();// 打开文档 

        List<FlowDataEntity> listFlowData  = queryFlow(id,"sfyz");
        Paragraph sfyz = new Paragraph("身份验证", getChineseFont());
        doc.add(sfyz);
        String flowId = insertData(listFlowData,doc);
        setImag(flowId,doc);
        //婚姻状况
        Boolean canGo = true;
        String hyzkString = "";
        List<FlowDataEntity> listHyzk  = queryFlow(id,"hyzk");
            if(listHyzk==null){
                canGo = false;
            }
        if(canGo){
            Paragraph hyzk = new Paragraph("婚姻状态", getChineseFont());
            hyzk.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
            doc.add(hyzk);
            for (FlowDataEntity forHyzk:listHyzk
            ) {
                if(forHyzk.getKeyName().equalsIgnoreCase("结婚时间")){
                    StringBuffer yh = new StringBuffer();
                    yh.append("婚姻状况：已婚");
                    Paragraph hyPage = new Paragraph(yh.toString(), getChineseFontContent());
                    doc.add(hyPage);
                    StringBuffer jhsh = new StringBuffer();
                    jhsh.append("结婚时间："+forHyzk.getKeyValue());
                    sfyz.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
                    Paragraph sfz = new Paragraph(jhsh.toString(), getChineseFontContent());
                    doc.add(sfz);
                    hyzkString = forHyzk.getFlowId();
                }

            }
            //婚姻状况图片
            if(!hyzkString.equals("")){
                setImag(hyzkString,doc);
            }
        }else{
            StringBuffer yh = new StringBuffer();
            yh.append("婚姻状况：未婚");
            Paragraph hyPage = new Paragraph(yh.toString(), getChineseFontContent());
            doc.add(hyPage);
        }

        //户口簿信息
        List<FlowDataEntity> hkbList  = queryFlow(id,"hkbxx");
        Paragraph hkbxx = new Paragraph("户口簿信息", getChineseFont());
        doc.add(hkbxx);
        String flowId1 = insertData(hkbList,doc);
        setImag(flowId1,doc);

        //购房发票
        String flowId2 = "";
        List<FlowDataEntity> gffpList  = queryFlow(id,"gffp");
        Paragraph gffp = new Paragraph("购房发票", getChineseFont());
        doc.add(gffp);
        for (FlowDataEntity flowDataEntity:gffpList
        ) {

            gffp.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
            StringBuffer xxdistring = new StringBuffer();
            if(flowDataEntity.getKeyName().equalsIgnoreCase("购买房屋的详细地址")){
                xxdistring.append("购买房屋的详细地址："+flowDataEntity.getKeyValue());
                Paragraph xxdiP = new Paragraph(xxdistring.toString(), getChineseFontContent());
                doc.add(xxdiP);
                flowId2 = flowDataEntity.getFlowId();
            }
            if(flowDataEntity.getKeyName().equalsIgnoreCase("第几套房")){
                xxdistring.append("第几套房："+flowDataEntity.getKeyValue());
                Paragraph jtfP = new Paragraph(xxdistring.toString(), getChineseFontContent());
                doc.add(jtfP);
            }
            if(flowDataEntity.getKeyName().equalsIgnoreCase("获取方式")){
                xxdistring.append("获取方式："+flowDataEntity.getKeyValue());
                Paragraph hqfsP = new Paragraph(xxdistring.toString(), getChineseFontContent());
                doc.add(hqfsP);
            }
            if(flowDataEntity.getKeyName().equalsIgnoreCase("《房屋所有权证》证号")){
                xxdistring.append("《房屋所有权证》证号："+flowDataEntity.getKeyValue());
                Paragraph zhP = new Paragraph(xxdistring.toString(), getChineseFontContent());
                doc.add(zhP);
            }
        }
        if(!flowId2.equalsIgnoreCase(""))
            setImag(flowId2,doc);
        //购房合同
        String flowId3 = "";
        Paragraph gfht = new Paragraph("购房合同", getChineseFont());
        gfht.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
        doc.add(gfht);
        List<FlowDataEntity> gfhtList  = queryFlow(id,"gfht");
        Paragraph gfhtp = new Paragraph("购房发票", getChineseFont());
        doc.add(gfhtp);
        for (FlowDataEntity flowDataEntity:gfhtList
        ) {
            if(flowDataEntity.getKeyName().equalsIgnoreCase("证件类型")){
                StringBuffer zjlxstring = new StringBuffer();
                zjlxstring.append("证件类型："+flowDataEntity.getKeyValue());
                Paragraph zjlxP = new Paragraph(zjlxstring.toString(), getChineseFontContent());
                doc.add(zjlxP);
            }
            if(flowDataEntity.getKeyName().equalsIgnoreCase("房屋价格")){
                StringBuffer zjlxstring = new StringBuffer();
                zjlxstring.append("房屋价格："+flowDataEntity.getKeyValue());
                Paragraph zjlxP = new Paragraph(zjlxstring.toString(), getChineseFontContent());
                doc.add(zjlxP);
                flowId3 = flowDataEntity.getFlowId();
            }
            if(flowDataEntity.getKeyName().equalsIgnoreCase("房屋类型")){
                StringBuffer zjlxstring = new StringBuffer();
                zjlxstring.append("房屋类型："+flowDataEntity.getKeyValue());
                Paragraph zjlxP = new Paragraph(zjlxstring.toString(), getChineseFontContent());
                doc.add(zjlxP);
            }
            if(flowDataEntity.getKeyName().equalsIgnoreCase("房屋面积")){
                StringBuffer zjlxstring = new StringBuffer();
                zjlxstring.append("房屋面积："+flowDataEntity.getKeyValue());
                Paragraph zjlxP = new Paragraph(zjlxstring.toString(), getChineseFontContent());
                doc.add(zjlxP);
            }
        }
        if(!flowId3.equalsIgnoreCase(""))
            setImag(flowId3,doc);
        //授权书
        Paragraph sqs = new Paragraph("授权书", getChineseFont());
        sqs.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
        //拆迁补偿
        Paragraph qqbc = new Paragraph("拆迁补偿", getChineseFont());
        qqbc.setAlignment(Element.ALIGN_LEFT);// 对齐方式 
        tParagraph.setAlignment(Element.ALIGN_CENTER);// 居中
        tParagraph.setIndentationLeft(12);// 左缩进 
        tParagraph.setIndentationRight(12);// 右缩进 
        tParagraph.setFirstLineIndent(24);// 首行缩进 
        tParagraph.setLeading(20f);// 行间距 
        tParagraph.setSpacingBefore(5f);// 设置上空白 
        tParagraph.setSpacingAfter(10f);// 设置段落下空白
        Image img = Image.getInstance("E:\\timg.jpg");
//        Image img = Image.getInstance(flowFileEntity.getFileContent());
        img.setAlignment(Image.MIDDLE);  //设置图片居中
        img.setBorder(Image.BOX);
        img.setBorderWidth(10);
        img.setBorderColor(BaseColor.WHITE);
        img.scaleToFit(900, 350);// 设置图片大小 

//        ///  doc.add(tRectangle);
//        doc.add(tParagraph); //添加段落
//        doc.add(xzsj);
//        doc.add(czdx);
//
//        //婚姻状况
//        doc.add(hyzk);
//        doc.add(hyzkP);
//        doc.add(jhshsP);
//        //户口簿信息
//        doc.add(hkbxx);
//        doc.add(hkbxmP);
//        doc.add(hkbsfzP);
//        //购房发票
//        doc.add(gffp);
//        doc.add(xxdiP);
//        doc.add(djtfP);
//        doc.add(hqfsP);
//        doc.add(fcsyzzhP);
//        //购房合同
//        doc.add(gfht);
//        doc.add(zjlxP);
//        doc.add(fwjgP);
//        doc.add(fwlxP);
//        doc.add(fwmjP);
//        //授权书
//        doc.add(sqs);
//        //拆迁补偿
//        doc.add(qqbc);
        doc.close();  //记得关闭document
       //输出文件流

//        OutputStream os = null;
//        InputStream is= null;
//        try {
//            // 取得输出流
//            os = response.getOutputStream();
//            // 清空输出流
//            response.reset();
////            response.setHeader("content-type", "application/octet-stream");
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + "1.pdf");
//            //读取流
//            File fileTest = new File("D:qishuidowload/qishui.pdf");
//            byte[] buffer = new byte[1024];
//            BufferedInputStream bis = null;
//            is = new FileInputStream(file);
//            bis = new BufferedInputStream(is);
//            while(bis.read(buffer) != -1){
//                os.write(buffer);
//            }
//            IOUtils.copy(is, response.getOutputStream());
//            response.getOutputStream().flush();
//        } catch (IOException e) {
//        	e.printStackTrace();
//        }
//        //文件的关闭放在finally中
//        finally
//        {
//            try {
//                if (is != null) {
//                    is.close();
//                }
//            } catch (IOException e) {
//            }
//            try {
//                if (os != null) {
//                    os.close();
//                }
//            } catch (IOException e) {
//            }
//        }
    }
    public static Font getChineseFont() {
        BaseFont simpChinese;
        Font ChineseFont = null;
        try {
            simpChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ChineseFont = new Font(simpChinese, 21, Font.NORMAL, BaseColor.BLACK);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ChineseFont;

    }
    public void setImag(String id,Document doc) throws Exception{
        FlowFileEntity hyzkFileEntity = new FlowFileEntity();
        hyzkFileEntity.setFlowId(id);
        List<FlowFileEntity> hyzkList = hyzkFileEntity.queryListBySelf();
        for (FlowFileEntity forHyzk :hyzkList
        ) {
            FlowFileEntity hyzkFile = new FlowFileEntity();
            hyzkFile.setId(forHyzk.getId());
            Image img = Image.getInstance(hyzkFile.queryBlobBySelf(FlowFileDto.FILE_CONTENT_C));
            doc.add(img);
        }
    }
    public String insertData(List<FlowDataEntity> hkbList,Document doc) throws Exception{
        String flowId = "";
        for (FlowDataEntity flowDataEntity1:hkbList
        ) {
            if(flowDataEntity1.getKeyName().equalsIgnoreCase("真实姓名")){
                StringBuffer xmstring = new StringBuffer();
                xmstring.append("姓名："+flowDataEntity1.getKeyValue());
                Paragraph xm = new Paragraph(xmstring.toString(), getChineseFontContent());
                doc.add(xm);
                flowId = flowDataEntity1.getFlowId();
            }else{
                StringBuffer sfzstring = new StringBuffer();
                sfzstring.append("身份证："+flowDataEntity1.getKeyValue());
                //身份验证
                Paragraph sfz = new Paragraph(sfzstring.toString(), getChineseFontContent());
                doc.add(sfz);
            }

        }
        return flowId;
    }
    //正文设置字体
    public static Font getChineseFontContent() {
        BaseFont simpChinese;
        Font ChineseFont = null;
        try {
            simpChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ChineseFont = new Font(simpChinese, 12, Font.NORMAL, BaseColor.BLUE);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ChineseFont;
    }
    
    /**
	 * 
	 * @param data format:{"身份证":{"身份证"："111111","姓名"："身份证","img"：{"path":"abc.jpg","w":123,"h":123}}}
	 * or data format:{"身份证":{"身份证"："111111","姓名"："身份证","img"：[{"path":"abc.jpg","w":123,"h":123}]}}
	 * @param out
	 */
	@SuppressWarnings("unchecked")
	public void constructPDF(Map<String, Object> data, OutputStream out){
		if(data == null || data.isEmpty() || out == null)
			return;
		Document doc = new Document();
		try{
			PdfWriter.getInstance(doc, out).setStrictImageSequence(true); 
			doc.open();
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
			Font chinese = new Font(bf, 12, Font.NORMAL);
			for(Map.Entry<String, Object> entry:data.entrySet()){
				Paragraph title = new Paragraph(entry.getKey(), chinese);
				doc.add(title);
				Object value = entry.getValue();
				if(value == null)
					doc.add(new Paragraph("无"));
				else{
					if(value instanceof Map){
						Map<String, Object> in_map = (Map<String, Object>)value;
						for(Map.Entry<String, Object> entry_in_map:in_map.entrySet()){
							if(entry_in_map.getKey().equalsIgnoreCase("img")){
								Object imgvalue = entry_in_map.getValue();
								if(imgvalue instanceof List){
									List<Map<String, Object>> list = (List<Map<String, Object>>)imgvalue;
									for(Map<String, Object> img : list)
										if(img.containsKey("path")){
											Object path = img.get("path");
											if(path != null && !path.toString().trim().equals("")){
												Image c_img = (Image)path;
												float w = c_img.getWidth();
												float h = c_img.getHeight();
												if(img.containsKey("w")){
													Object u_w = img.get("w");
													if(u_w != null)
														w = (Integer)u_w;
												}
												if(img.containsKey("h")){
													Object u_h = img.get("h");
													if(u_h != null)
														h = (Integer)u_h;
												}
												c_img.scaleAbsolute(w, h);
												doc.add(c_img);
											}
										}
								}else{
									Map<String, Object> img = (Map<String, Object>)imgvalue;
									if(img.containsKey("path")){
										Object path = img.get("path");
										if(path != null && !path.toString().trim().equals("")){
											//Image c_img = Image.getInstance(path.toString()); 
											Image c_img = (Image)path;
											float w = c_img.getWidth();
											float h = c_img.getHeight();
											if(img.containsKey("w")){
												Object u_w = img.get("w");
												if(u_w != null)
													w = (Integer)u_w;
											}
											if(img.containsKey("h")){
												Object u_h = img.get("h");
												if(u_h != null)
													h = (Integer)u_h;
											}
											c_img.scaleAbsolute(w, h);
											doc.add(c_img);
										}
									}
								}
							}else{
								String text = entry_in_map.getKey()+"："+entry_in_map.getValue();
								doc.add(new Paragraph(text, chinese));
							}
						}
					}
				}
			}
			doc.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 文件下载
	 * @param response
	 * @param data
	 * @param fileName
	 */
	public void download(HttpServletResponse response, Map<String, Object> data, String fileName){	
		try{
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			constructPDF(data, out);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
