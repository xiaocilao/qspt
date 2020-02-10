package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;

public class TaxDtoFather {
    private TaxDto req;

    public TaxDto getReq() {
        return req;
    }

    public void setReq(TaxDto req) {
        this.req = req;
    }

    @Override
    public String toString() {
        return "TaxDtoFather{" +
                "req=" + req +
                '}';
    }
}
