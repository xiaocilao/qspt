package com.zxkj.hc.qspt.service;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.WxgzhUserEntity;
import java.util.List;
import java.util.Map;

public interface WxgzhService {

    class TempleteValue{
        private String value;
        private String color;

        public TempleteValue(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    List<WxgzhUserEntity> grabWxgzhUsers();

    boolean sendTempleteMessage(String applyUserId,String templeteId,Map<String,TempleteValue> data);

}
