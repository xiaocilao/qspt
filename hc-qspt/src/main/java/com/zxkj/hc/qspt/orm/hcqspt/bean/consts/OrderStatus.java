package com.zxkj.hc.qspt.orm.hcqspt.bean.consts;

/**
 * 支付状态
 */
public class OrderStatus {

    /**
     * 支付结果：0—成功1—失败3-处理中4支付成功等待确认收货 6-订单关闭
     */
    public static final String PAY_SUCCESS="0";

    public static final String PAY_FAIL="1";

    public static final String PAY_PROCESS="3";

    public static final String PAY_SUCCESS_BUT_WAIT_PROJECT="4";

    public static final String PAY_ORDER_CLOSE = "6";

    public static final String ORDER_LIVE = "0";
}
