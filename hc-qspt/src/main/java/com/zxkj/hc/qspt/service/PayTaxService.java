package com.zxkj.hc.qspt.service;

import java.io.IOException;

/**
 * hxt
 */
public interface PayTaxService {

    //发起缴税请求
    String reqPayTax(String host,int port,String orderId) throws IOException;

    //查看缴税状态
    int queryPayTaxStatus();
}
