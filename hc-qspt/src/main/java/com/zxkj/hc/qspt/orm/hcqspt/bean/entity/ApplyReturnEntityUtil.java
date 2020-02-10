package com.zxkj.hc.qspt.orm.hcqspt.bean.entity;

/**
 * hxt
 * 用于存储支付回调回来的参数
 */
public class ApplyReturnEntityUtil {
    private String id;

    private String service;

    private String service_version;

    private String input_charset;

    private String sign_type;

    private String sign;

    private String returnSerNo;

    private String partner;

    private String subpartner;

    private String pay_info;

    private String trans_channel;

    private String out_trade_no;

    private String transaction_id;

    private String bank_type;

    private String bank_transno;

    private String total_fee;

    private String trade_state;

    private String notify_id;

    private String fee_type;

    private String time_end;

    private String transport_fee;

    private String product_fee;

    private String discount;

    private String attach;

    private String buyerId;

    private String externalId;

    private String retcode;

    private String promotion_detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReturnSerNo() {
        return returnSerNo;
    }

    public void setReturnSerNo(String returnSerNo) {
        this.returnSerNo = returnSerNo;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSubpartner() {
        return subpartner;
    }

    public void setSubpartner(String subpartner) {
        this.subpartner = subpartner;
    }

    public String getPay_info() {
        return pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getBank_transno() {
        return bank_transno;
    }

    public void setBank_transno(String bank_transno) {
        this.bank_transno = bank_transno;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTransport_fee() {
        return transport_fee;
    }

    public void setTransport_fee(String transport_fee) {
        this.transport_fee = transport_fee;
    }

    public String getProduct_fee() {
        return product_fee;
    }

    public void setProduct_fee(String product_fee) {
        this.product_fee = product_fee;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getPromotion_detail() {
        return promotion_detail;
    }

    public void setPromotion_detail(String promotion_detail) {
        this.promotion_detail = promotion_detail;
    }

    @Override
    public String toString() {
        return "ApplyReturnEntity{" +
                "id='" + id + '\'' +
                ", service='" + service + '\'' +
                ", service_version='" + service_version + '\'' +
                ", input_charset='" + input_charset + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", sign='" + sign + '\'' +
                ", returnSerNo='" + returnSerNo + '\'' +
                ", partner='" + partner + '\'' +
                ", subpartner='" + subpartner + '\'' +
                ", pay_info='" + pay_info + '\'' +
                ", trans_channel='" + trans_channel + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", bank_type='" + bank_type + '\'' +
                ", bank_transno='" + bank_transno + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", trade_state='" + trade_state + '\'' +
                ", notify_id='" + notify_id + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", time_end='" + time_end + '\'' +
                ", transport_fee='" + transport_fee + '\'' +
                ", product_fee='" + product_fee + '\'' +
                ", discount='" + discount + '\'' +
                ", attach='" + attach + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", externalId='" + externalId + '\'' +
                ", retcode='" + retcode + '\'' +
                ", promotion_detail='" + promotion_detail + '\'' +
                '}';
    }
}
