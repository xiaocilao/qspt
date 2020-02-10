
package com.zxkj.hc.qspt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.service.ApplySdscService;
import com.zxkj.hc.qspt.service.BankService;
import com.zxkj.hc.qspt.service.WxgzhService;
import com.zxkj.hc.qspt.service.impl.BankServiceImpl;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import com.zxkj.hc.qspt.util.AccessTokenManager;
import com.zxkj.hc.qspt.util.HttpClientUtil;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.jdbc.core.Condition;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@RestController()
@RequestMapping("/api/apply")
public class ApplySdscController extends SetuSessionController {
    @Autowired
    AccessTokenManager accessTokenManager;
    @Autowired
    WxgzhService wxgzhService;

    @Resource
    ApplySdscService applySdscService;
    
    @Resource
    BankService bankService;
    

    @RequestMapping("/sdsclb")
    @ResponseBody
    public SetuResult zlshlb(String orderby, int pageOffset, int pageRowSize, String keywords, String filter) {
        SetuSession session = (SetuSession) getSetuSession();
        ApplyRecordEntity entity = new ApplyRecordEntity();

        //huxiaotian 注释掉无用的代码，此处添加以下代码后将无法得到用户列表
//        if(CommonUtil.isNotNull(session.getUserId())){
//            entity.addConditonItem(new ConditionItem("spr_id", "=", session.getUserId()));
//        }else{
//            entity.addConditonItem(new ConditionItem("spr_id", "is", "null"));
//        }

        Condition condition = new Condition();
        if (StringUtils.isEmpty(filter)) {
            condition.add(new ConditionItem("spzt", "=", Spzt.TG.getCode()));
            condition.add("or", new ConditionItem("spzt", "=", Spzt.DJF.getCode()));
        } else {
            condition.add(new ConditionItem("spzt", "=", filter));
        }
        entity.addConditon("and", condition);

        entity.addConditonItem("and", new ConditionItem("xt_zxbz", "=", "0"));

        if(!StringUtils.isEmpty(keywords)){
            entity.addConditonItem("and",new ConditionItem("sqr_xm","like","%"+keywords+"%"));
        }

        SetuResult result = new SetuResult();
        result.setTotal(entity.countBySelf());
        result.setRows(entity.queryListBySelf(orderby, pageOffset, pageRowSize));
        return result;
    }

    @RequestMapping("/ocr")
    @ResponseBody
    public SetuResult ocr(String image) throws UnsupportedEncodingException {
        System.out.println("======进入ocr识别模块======");
        System.out.println("图片数据:");
        System.out.println(image);
        String accessToken=accessTokenManager.getBdyAccessToken();
        System.out.println("图像识别的token="+accessToken);
        String url = "https://aip.baidubce.com:2702/rest/2.0/ocr/v1/general?access_token="+accessToken;
        String data="image=" + URLEncoder.encode(image, "UTF-8");
        String responseStr= HttpClientUtil.sendPost(url,data);
        JSONObject responseJson= JSON.parseObject(responseStr);
        Integer error_code=responseJson.getInteger("error_code");
        if(error_code==null){
            SetuResult setuResult=new SetuResult(200, "识别成功");
            setuResult.setRows(responseStr);
            return setuResult;
        }else{

            return new SetuResult(200, "请手动填写！");
//            return new SetuResult(201, "识别失败");
        }
    }

    @RequestMapping("/scsd")
    @ResponseBody
    public SetuResult scsd(ShuidanEntity shuidan) throws BusinessWarnException {
        if(StringUtils.isEmpty(shuidan.getApplyId())){
            throw new BusinessWarnException("申请ID不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getZsxmmc())){
            throw new BusinessWarnException("征收项目名称不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getSwjgmc())){
            throw new BusinessWarnException("税务机关名称不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getNsrmc())){
            throw new BusinessWarnException("纳税人名称不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getNsrbh())){
            throw new BusinessWarnException("纳税人编号不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getPzxh())){
            throw new BusinessWarnException("凭证序号不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getJehjxx())){
            throw new BusinessWarnException("金额合计小写不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getJehjdx())){
            throw new BusinessWarnException("金额合计大写不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getSkxjrq())){
            throw new BusinessWarnException("税款限缴日期不能为空");
        }
        if(StringUtils.isEmpty(shuidan.getYhs())){
            shuidan.setYhs("0");
        }
        if(StringUtils.isEmpty(shuidan.getKk())){
            shuidan.setKk("0");
        }

        shuidan.setZfzt("0");

        if(applySdscService.scsd(shuidan)==0){
            throw new BusinessWarnException("操作失败");
        }
        //推送通知
        System.out.println("上传税单内容:"+shuidan);
        HashMap<String, WxgzhService.TempleteValue> data=new HashMap<>();
        data.put("first",new WxgzhService.TempleteValue("","#000000"));
        data.put("keyword1",new WxgzhService.TempleteValue(shuidan.getPzxh(),"#000000"));
        data.put("keyword2",new WxgzhService.TempleteValue(shuidan.getJehjxx(),"#000000"));
        data.put("keyword3",new WxgzhService.TempleteValue(shuidan.getZsxmmc(),"#000000"));
        data.put("keyword4",new WxgzhService.TempleteValue("税款限缴日期："+shuidan.getSkxjrq(),"#000000"));
        data.put("remark",new WxgzhService.TempleteValue("","#000000"));
        wxgzhService.sendTempleteMessage(((SetuSession)getSetuSession()).getUserId(),"NgKncC62JyWgmheVuhLMbTfoteauikMSTy9MOhXOjmQ",data);
        return new SetuResult("保存成功");
    }

}
