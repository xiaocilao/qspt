package com.zxkj.hc.qspt.controller;

import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.DbStatus;
import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.OrderStatus;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyReturnEntityUtil;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.YoujixinxiEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.ApplyReturnEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.OrdercreateEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.TaxReturnEntity;
import com.zxkj.hc.qspt.service.PayTaxService;
import com.zxkj.hc.qspt.service.impl.PayTaxServiceImpl;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.session.controller.SetuSessionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * made by hxt
 */
@RestController()
@RequestMapping("/order/apply")
public class ApplyOrderController extends SetuSessionController{
    Logger log = LoggerFactory.getLogger(ApplyOrderController.class);
    PayTaxService payTaxService = new PayTaxServiceImpl();


    @Value("${urlfortax.host}")
    private String host;

    /**
     * 用于接收支付后的订单状态
     * @param getData
     * @return hxt
     */
    @RequestMapping("/return")
    @ResponseBody
    public String getMessage(ApplyReturnEntityUtil getData) throws BusinessWarnException {
        log.info("======进入订单回调模块======");
        log.info("获得的参数:");
        log.info(String.valueOf(getData));
        String tradeStat = "";
        String outTradeNo = "";
        //格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //回调历史状态数据库
        ApplyReturnEntity applyReturnEntity = new ApplyReturnEntity();
        //获取天府支付唯一订单号,和支付状态
        tradeStat = getData.getTrade_state();
        outTradeNo = getData.getOut_trade_no();
        if (tradeStat == null||tradeStat.equalsIgnoreCase("") || tradeStat.equals("") || tradeStat.isEmpty()) {
            throw new BusinessWarnException(simpleDateFormat.format(new Date()) + ":获取支付回调函数的数据为空！");
        }
        applyReturnEntity.setTradeState(tradeStat);
        applyReturnEntity.setOutTradeNo(outTradeNo);
        //数据库中查询订单号是否存在或者更新
        List<ApplyReturnEntity> applyOrderList = applyReturnEntity.queryListBySelf();
        //将回调函数历史数据存入数据库
        if (applyOrderList.size() == 0) {
            if (insertApplyReturn(getData).equals("0")) {
                throw new BusinessWarnException(simpleDateFormat.format(new Date()) + ":执行回调函数插入订单状态失败！");
            }
            //支付状态流水更新历史数据成功后，修改订单状态
            log.info("历史数据插入数据库成功，更新订支付状态...");
            log.info("接收到的订单号为{}", outTradeNo);
            log.info("接收到的支付状态为{}",tradeStat);
            OrdercreateEntity ordercreateEntity = new OrdercreateEntity();
            ordercreateEntity.setStatus(tradeStat);
            ordercreateEntity.addConditonItem(new ConditionItem("key_id","=",outTradeNo));
            int isUpdateSuccess = ordercreateEntity.updateBySelfCondition();
            if(isUpdateSuccess == DbStatus.UPDATE_SUCCESS){
                log.info("订单状态更新成功,订单号为{},状态为{}",outTradeNo,tradeStat);
                if(tradeStat.equalsIgnoreCase(OrderStatus.PAY_SUCCESS)){
                    //更改支付状态
                    int payStatus = updateOrderStatus(outTradeNo);
                    if(payStatus == DbStatus.UPDATE_SUCCESS){
                        try {
                            String taxIsSuccess = payTaxService.reqPayTax(host, 50001,outTradeNo); //172.32.5.150 50001
                            if(taxIsSuccess.equalsIgnoreCase("SUCCESS")){
                                log.info("缴税成功！");
                            }
                        }catch (IOException e){
                            throw new BusinessWarnException("调用缴税接口失败！"+e.getMessage());
                        }
                    }
                }
            }else{
                throw new BusinessWarnException("订单更新失败，请检查回调函数更新代码!");
            }
            log.info("回调函数历史数据存入数据库操作成功！");
        }
        return "SUCCESS";
    }



    /**
     * 交易状态查询
     * @return hxt
     */
    @RequestMapping("/getPayStatus")
    @ResponseBody
    public String getPayStatus(){
        JSONObject jsonObject = new JSONObject();
        //获得缓存内容
        SetuSession session=(SetuSession) getSetuSession();
        String userId = session.getUserId();
        if(userId.equalsIgnoreCase("")){
            return "fail";
        }
        //获取订单
        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
        applyRecordEntity.setSqrId(userId);
        applyRecordEntity.setXtZxbz("0");
        applyRecordEntity.queryBySelf();

        OrdercreateEntity ordercreateEntity = new OrdercreateEntity();
        ordercreateEntity.setApplyId(applyRecordEntity.getId());
        ordercreateEntity.setXtZxbz("0");
        try{
            ordercreateEntity.queryBySelf();
        }catch(Exception e){
            jsonObject.put("userName", session.getUserName());
            jsonObject.put("code","201");
        }
        log.info("订单状态:{}", ordercreateEntity);
        //查询纳税金额
        ShuidanEntity shuidanEntity = new ShuidanEntity();
        shuidanEntity.setApplyId(applyRecordEntity.getId());
        try{
            shuidanEntity.queryBySelf();
        }catch (Exception e){

        }
        //获取缴税状态

        TaxReturnEntity taxReturnEntity = new TaxReturnEntity();
        taxReturnEntity.setOrderId(ordercreateEntity.getKeyId());
        try{
            taxReturnEntity.queryBySelf();
        }catch(Exception e){
        }
        //拼接返回参数
        List list = new ArrayList();
        list.add(ordercreateEntity);
        list.add(taxReturnEntity);
        list.add(shuidanEntity);
        jsonObject.put("context", list);
        jsonObject.put("userName", session.getUserName());
        jsonObject.put("code","200");
        return jsonObject.toJSONString();
    }



    /**
     * 用于插入回调函数流水记录
     * @param res
     * @return hxt
     */
    public String insertApplyReturn(ApplyReturnEntityUtil res){
        ApplyReturnEntity target = new ApplyReturnEntity();
        //28个字段
        target.setService(res.getService());
        target.setServiceVersion(res.getService_version());
        target.setInputCharset(res.getInput_charset());
        target.setSignType(res.getSign_type());
        target.setSign(res.getSign());
        target.setReturnserno(res.getReturnSerNo());
        target.setPartner(res.getPartner());
        target.setSubpartner(res.getSubpartner());
        target.setPayInfo(res.getPay_info());
        target.setTransChannel(res.getTrans_channel());
        target.setOutTradeNo(res.getOut_trade_no());
        target.setTransactionId(res.getTransaction_id());
        target.setBankType(res.getBank_type());
        target.setBankTransno(res.getBank_transno());
        target.setTotalFee(res.getTotal_fee());
        target.setTradeState(res.getTrade_state());
        target.setNotifyId(res.getNotify_id());
        target.setFeeType(res.getFee_type());
        target.setTimeEnd(res.getTime_end());
        target.setTransportFee(res.getTransport_fee());
        target.setProductFee(res.getProduct_fee());
        target.setDiscount(res.getDiscount());
        target.setAttach(res.getAttach());
        target.setBuyerid(res.getId());
        target.setExternalid(res.getId());
        target.setRetcode(res.getRetcode());
        target.setPromotionDetail(res.getPromotion_detail());
        String status = target.insertSelf();
        log.info("插入回调函数历史记录状态："+status);
        return status;
    }
    public int updateOrderStatus(String outTradeNo) throws BusinessWarnException {
        OrdercreateEntity ordercreateEntity = new OrdercreateEntity();
        ordercreateEntity.setKeyId(outTradeNo);
        ordercreateEntity.setXtZxbz(OrderStatus.ORDER_LIVE);
        ordercreateEntity.setXtZxbz(OrderStatus.PAY_SUCCESS);
        ordercreateEntity.queryBySelf();
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSpzt(Spzt.KKCG.getCode());
		entity.setSqsj(getTimeNow());
		BeanUtil.setXgField(entity, getTimeNow());
		if(ordercreateEntity.getApplyId().isEmpty() || ordercreateEntity.getApplyId().equalsIgnoreCase("")){
            throw new BusinessWarnException("订单数据库中未找到相关信息,请检查！");
        }
		entity.addConditonItem(new ConditionItem("id","=",ordercreateEntity.getApplyId()));
		if(entity.updateBySelfCondition()==0){
			throw new BusinessWarnException("支付扣款成功，数据库税单支付状态更新失败!");
		}
        return DbStatus.UPDATE_SUCCESS;
    }
}
