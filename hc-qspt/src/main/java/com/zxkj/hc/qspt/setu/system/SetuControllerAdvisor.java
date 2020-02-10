package com.zxkj.hc.qspt.setu.system;

import com.zxkj.hc.qspt.controller.WxsmdlWebsocketController;
import org.setu.framework.exception.AbstractException;
import org.setu.framework.exception.AbstractRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SetuControllerAdvisor {
    private transient static final Logger logger = LoggerFactory.getLogger(SetuControllerAdvisor.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public SetuResult handle(Exception e) {
        logger.error("",e);
        if (e instanceof AbstractException) {
        	AbstractException  exception= (AbstractException) e;
            return new SetuResult(exception.getCode(), exception.getMessage());
        }if (e instanceof AbstractRuntimeException) {
        	AbstractRuntimeException  exception= (AbstractRuntimeException) e;
            return new SetuResult(exception.getCode(), exception.getMessage());
        }else {
            return new SetuResult(500, e.getMessage());
        }
    }
}
