
package com.zxkj.hc.qspt.service.impl;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zxkj.hc.qspt.service.VerificationCodeService;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.session.controller.SetuSessionController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Random;

@Service("verificationCodeService")
public class VerificationCodeServiceImpl extends SetuSessionController implements VerificationCodeService{
    private Random random=new Random();
    @Value("${aliyun.duanxin.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.duanxin.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.duanxin.signName}")
    private String signName;
    @Value("${aliyun.duanxin.templateCode}")
    private String templateCode;

    @Override
    public boolean sendVerificationCode(String phoneNumber) throws BusinessWarnException {
        SetuSession session=(SetuSession) getSetuSession();
        Long vCodeLatestSendTime=(Long)session.getAttribute("vCodeLatestSendTime");
        if(vCodeLatestSendTime!=null && vCodeLatestSendTime.compareTo(new Date().getTime()-1000*60)>=0){
            throw new BusinessWarnException("请稍后再试");
        }

        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<6;i++){
            stringBuilder.append(random.nextInt(9));
        }
        String code=stringBuilder.toString();

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam("{\"code\":\""+code+"\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                session.setAttribute("verificationCode",code);
                session.setAttribute("vCodeInvalidTime",Long.valueOf(new Date().getTime()+1000*60*5));
                session.setAttribute("vCodePhoneNumber",phoneNumber);
                session.setAttribute("vCodeLatestSendTime",new Date().getTime());
                return true;
            }else{
                System.out.println(sendSmsResponse.getMessage());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean verificate(String phoneNumber, String code) {
        SetuSession session=(SetuSession) getSetuSession();
        String code1=(String)session.getAttribute("verificationCode");
        Long invalidTime=(Long)session.getAttribute("vCodeInvalidTime");
        String phoneNumber1=(String)session.getAttribute("vCodePhoneNumber");
        if(phoneNumber==null || code==null || code1==null || invalidTime==null || phoneNumber1==null){
            return false;
        }
        if(phoneNumber.equals(phoneNumber1) && code.equals(code1) && invalidTime.compareTo(new Date().getTime())>0){
            return true;
        }
        return false;
    }
}

