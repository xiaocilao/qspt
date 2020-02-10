package com.zxkj.hc.qspt.service.impl;

import com.thoughtworks.xstream.XStream;
import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.TaxDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.TaxDtoFather;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.OrdercreateEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.TaxCreatEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.TaxReturnEntity;
import com.zxkj.hc.qspt.service.PayTaxService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.setu.framework.exception.BusinessRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("payTaxService")
public class PayTaxServiceImpl implements PayTaxService {
    private transient static final Logger logger = LoggerFactory.getLogger(PayTaxServiceImpl.class);

    @Value("${taxReq.prcscd}")
    String prcscd;
    @Value("${taxReq.brh_no}")
    String brh_no;
    @Value("${taxReq.opr_no}")
    String opr_no;
    @Value("${taxReq.abst_cd}")
    String abst_cd;
    @Value("${taxReq.mid_item}")
    String mid_item;
    @Value("${taxReq.mid_pay_type}")
    String mid_pay_type;
    @Value("${taxReq.jyje}")
    String jyje;
    @Value("${taxReq.vou1_acc_no}")
    String vou1_acc_no;
    @Value("${taxReq.dwmc}")
    String dwmc;
    @Value("${taxReq.zsjgdm}")
    String zsjgdm;
    @Value("${taxReq.nsrbm}")
    String nsrbm;
    @Value("${taxReq.wbsbdzxh}")
    String wbsbdzxh;
    @Value("${taxReq.qydm}")
    String qydm;
    @Value("${taxReq.jydm}")
    String jydm;
    @Value("${taxReq.transcode}")
    String transcode;

    @Value("${taxReq.version}")
    String version; //3 固定
    @Value("${taxReq.reqNode}")
    String reqNode; //9 固定
    @Value("${taxReq.resNode}")
    String resNode;  //9 固定
    @Value("${taxReq.transaction}")
    String transaction;  //10 固定
    @Value("${taxReq.communication}")
    String communication;  //5 固定
    @Value("${taxReq.serverName}")
    String serverName;  //12  //固定


    @Override
    public String reqPayTax(String host,int port,String orderId) throws IOException {
        // 和服务端进行通信
        System.out.println("请求缴税");
        System.out.println("orderId=" + orderId);
        Socket mSocket =null;
        try {
            System.out.println("连接缴税服务器,host="+host+",port="+port);
            mSocket = new Socket(host, port);
            System.out.println("连接服务器成功");
            OutputStream out = mSocket.getOutputStream();
            String firstData = "010123010033123010034M03421    00000500999  newgm       <?xml version=\"1.0\" encoding=\"GB18030\"?>";
            //获得组装报文
            System.out.println("开始组装报文");
            //转码
            String reportData = getReport(orderId);
            //拼接整体报文
            String message=firstData+reportData;
            //组装前八位
            int len=message.getBytes().length;
            String head = String.format("%08d", len);
            String body = head+message;
            System.out.println("请求缴税!");
            System.out.println("请求参数："+body);
            byte[] bytes = body.getBytes(Charset.forName("GB18030"));
            String reqXml = new String(bytes, "GB18030");
            ByteBuffer buffer = ByteBuffer.allocate(body.getBytes().length);
            buffer.put(reqXml.getBytes(Charset.forName("GB18030")));
            out.write(buffer.array());
            System.out.println("请求缴税成功！");
            //定时请求bdyAccessToken
            Socket finalMSocket = mSocket;
            Thread thread2=new Thread(){
                @Override
                public void run() {
                        InputStream in = null;
                        String returnData = "";
                        try {
                            System.out.println("准备接受输入流");
                            in = finalMSocket.getInputStream();
                        } catch (IOException e) {
                            logger.error("",e);
                        }
                        System.out.println("获得缴税输入流");
                        byte[] buffer = new byte[1024];
                        try {
                            System.out.println("准备读取输入流");
                            if(in != null) {
                                for (int i = 0; i < in.read(buffer); i++) {
                                    String getdata1 = new String(buffer, "GB18030");
                                    System.out.println("缴税返回回来的参数为：" + getdata1);
                                    String[] sList = getdata1.split("\n");
                                    String lastString = sList[sList.length - 1];
                                    System.out.println("切割后的数据：" + lastString);
                                    lastString = lastString.replace(" ", "");
                                    String[] sList1 = lastString.split("</root>");
                                    returnData = sList1[0] + "</root>";
                                    System.out.println("处理结束的数据为：" + returnData);
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("进入异常了，切割字符串出错啦");
                            logger.error("",e);
                        }finally {
//                            try {
//                                if(in!=null)
////                                in.close();
////                                out.close();
//                            } catch (IOException e) {
//                                logger.error("",e);
//                            }
                        }
                        System.out.println("准备装载xml数据");
                        Map<String,String> map = new HashMap<String,String>();
                        try {
                            Document doc1 = DocumentHelper.parseText(returnData);
                            Element root = doc1.getRootElement();
                            List<Element> list = root.elements();
                            System.out.println("去除多余字符串");
                            for (Element e:list
                            ) {
                                Element proc_result = e.element("proc_result");
                                Element err_text = e.element("err_text");
                                map.put(proc_result.getName(),proc_result.getText());
                                map.put(err_text.getName(),err_text.getText());
                            }
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        String taxInsertStatus = taxResponseStatus(map,orderId,returnData);
                        System.out.println("插入数据库状态："+taxInsertStatus);
                }
            };
            thread2.setDaemon(true);
            thread2.start();
            return "SUCCESS";
        }catch (IOException e){
            System.out.println("缴税失败：" +e);
        }finally {
//            if(mSocket != null)
//             mSocket.close();
        }
        return "FAIL";
    }

    @Override
    public int queryPayTaxStatus(){
        return 0;
    }
//
//    public String getReport(String userId){
//        //00000532010123010033123010034M03421
//        //00000500999
//        //newgm
//        String report = "";
//        StringBuffer header= new StringBuffer();  //8
//        StringBuffer reportBuffer = new StringBuffer();
//        StringBuffer headerBuffer = new StringBuffer();
//        reportBuffer.append("<?xml version=\"1.0\" encoding=\"GB18030\"?>");
//        reportBuffer.append("<root>");
//        reportBuffer.append("<req>");
//        reportBuffer.append("<req>");
//        reportBuffer.append("<prcscd>"+this.prcscd+"</prcscd>");
//        reportBuffer.append("<brh_no>"+this.brh_no+"</brh_no>");
//        reportBuffer.append("<opr_no>"+this.opr_no+"</opr_no>");
//        reportBuffer.append("<abst_cd>"+this.abst_cd+"</abst_cd>");
//        reportBuffer.append("<mid_item>"+this.mid_item+"</mid_item>");
//        reportBuffer.append("<mid_pay_type>"+this.mid_pay_type+"</mid_pay_type>");
//        reportBuffer.append("<jyje>"+this.jyje+"</jyje>");
//        reportBuffer.append("<vou1_acc_no>"+this.vou1_acc_no+"</vou1_acc_no>");
//        reportBuffer.append("<dwmc>"+this.dwmc+"</dwmc>");
//        reportBuffer.append("<zsjgdm>"+this.zsjgdm+"</zsjgdm>");
//        reportBuffer.append("<nsrbm>"+this.nsrbm+"</nsrbm>");
//        reportBuffer.append("<wbsbdzxh>"+this.wbsbdzxh+"</wbsbdzxh>");
//        reportBuffer.append("<qydm>"+this.qydm+"</qydm>");
//        reportBuffer.append("<jydm>"+this.jydm+"</jydm>");
//        reportBuffer.append("<transcode>"+this.transcode+"</transcode>");
//        reportBuffer.append("</req></root>");
//        //拼接头部
//        int length = reportBuffer.length()+56;
//        for(int i=0;i<(8-String.valueOf(length).length());i++){
//            header.append("0");
//        }
//        header.append(String.valueOf(length));
//        header.append(this.version);
//        header.append(this.reqNode);
//        header.append(this.resNode);
//        header.append(this.transaction);
//        header.append(this.communication);
//        header.append(this.serverName);
//        header.append(userId);
//        header.append(this.version);
//        System.out.println("头部报文："+header.toString());
//        System.out.println("主报文："+reportBuffer.toString());
//        System.out.println("总体报文："+header.toString()+" "+reportBuffer.toString());
//        return header.toString()+" "+reportBuffer.toString();
//    }

    public String getReport(String orderId) throws UnsupportedEncodingException {
        OrdercreateEntity oe = new OrdercreateEntity();
        oe.setKeyId(orderId);
        oe.setXtZxbz("0");
        oe.setStatus("0");
        oe.queryBySelf();
        ShuidanEntity sd = new ShuidanEntity();
        if(oe.getApplyId().isEmpty() || oe.getApplyId().equalsIgnoreCase("")){
            System.out.println("订单获取失败,请检查订单是否有效!");
            throw new BusinessRuntimeException("订单获取失败,请检查订单是否有效！");
        }
        sd.setApplyId(oe.getApplyId());
        sd.queryBySelf();
        TaxDtoFather taxDtoFather = new TaxDtoFather();
        TaxDto req = new TaxDto();
        req.setPrcscd("M03421");
        req.setBrh_no("0500001");
        req.setOpr_no("500999");
        req.setAbst_cd("M34");
        req.setMid_item("11");
        req.setMid_pay_type("1");
        req.setJyje(sd.getJehjxx());
        req.setVou1_acc_no("0000374555");
        req.setDwmc(new String(sd.getNsrmc().getBytes("GB18030"),"GB18030")); //账户名称sd.getNsrmc())
        req.setZsjgdm(sd.getKk());  //征收机构代码
        req.setNsrbm(sd.getNsrbh());  //纳税人编码
        req.setWbsbdzxh(sd.getPzxh()); //外部申报序号
        req.setQydm(sd.getNsrbh());  //企业代码
        req.setJydm("banktaxpay");
        req.setTranscode("mid_pay");
        XStream xStream = new XStream();
        taxDtoFather.setReq(req);
        xStream.alias("root",taxDtoFather.getClass());
        //将记录插入数据库
        System.out.println("请求税务参数存入本地历史数据库!");
        String status = insertTaxStatus(taxDtoFather,orderId);
        System.out.println("税务税局插入数据库状态为："+status);
        String xml = xStream.toXML(taxDtoFather);
        xml=xml.replace("__", "_");
        return StringUtils.trimAllWhitespace(xml);
    }

    public String insertTaxStatus(TaxDtoFather tf,String orderId){
        TaxCreatEntity te =new TaxCreatEntity();
        TaxDto td = tf.getReq();
        te.setAbstCd(td.getAbst_cd());
        te.setBrhNo(td.getBrh_no());
        te.setOprNo(td.getOpr_no());
        te.setMidItem(td.getMid_item());
        te.setMidPayType(td.getMid_pay_type());
        te.setJyje(td.getJyje());
        te.setVou1AccNo(td.getVou1_acc_no());
        te.setDwmc(td.getDwmc());
        te.setZsjgdm(td.getZsjgdm());
        te.setNsrbm(td.getNsrbm());
        te.setWbsbdzxh(td.getWbsbdzxh());
        te.setQydm(td.getQydm());
        te.setJydm(td.getJydm());
        te.setTranscode(td.getTranscode());
        te.setOrderId(orderId);
        String status = te.insertSelf();
        System.out.println("请求缴税参数存入数据库，参数为："+te);
        return status;
    }
    public String taxResponseStatus(Map<String,String> map,String orderId,String resource)  {
        TaxReturnEntity taxReturnEntity = new TaxReturnEntity();
        if(map.get("proc_result")!=null){
            taxReturnEntity.setProcResult(map.get("proc_result"));
        }
//        if(map.get("trans_date")!=null){
//            taxReturnEntity.setErrTex(map.get("trans_date"));
//        }
//        if(map.get("trans_time")!=null){
//            taxReturnEntity.setTransTime(map.get("trans_time"));
//        }
//        if(map.get("mid_serial_no") !=null){
//            taxReturnEntity.setMidSerialNo(map.get("mid_serial_no"));
//        }
//        if(map.get("ctl_serial_no")!=null){
//            taxReturnEntity.setCtlSerialNo(map.get("ctl_serial_no"));
//        }
//        if(map.get("wjbz")!=null){
//            taxReturnEntity.setWjbz(map.get("wjbz"));
//        }
//        if(map.get("wjmc") !=null){
//            taxReturnEntity.setWjmc(map.get("wjmc"));
//        }
//        if(map.get("jyje")!=null){
//            taxReturnEntity.setJyje(map.get("jyje"));
//        }
//        if(map.get("bz")!=null){
//            taxReturnEntity.setBz(map.get("bz"));
//        }
        if(map.get("err_text")!=null){
            taxReturnEntity.setErrTex(map.get("err_text"));
        }
        taxReturnEntity.setOrderId(orderId);
//        taxReturnEntity.setResource(resource);
        System.out.println("缴税返回的参数为:"+taxReturnEntity);
        String status = taxReturnEntity.insertSelf();
        return status;
    }
}
