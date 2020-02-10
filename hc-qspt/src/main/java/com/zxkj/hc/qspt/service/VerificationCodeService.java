package com.zxkj.hc.qspt.service;

import org.setu.framework.exception.BusinessWarnException;

public interface VerificationCodeService {

    boolean sendVerificationCode(String phoneNumber) throws BusinessWarnException;

    boolean verificate(String phoneNumber,String code);

}
