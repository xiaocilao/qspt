package com.zxkj.hc.qspt.service;

import com.zxkj.hc.qspt.setu.system.SetuResult;
import org.setu.framework.exception.BusinessWarnException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

public interface ApplySpService {

    SetuResult tjzl(String applyId) throws BusinessWarnException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
