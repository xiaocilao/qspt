package com.zxkj.hc.qspt.orm.hcqspt.bean.dto;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import java.util.*;
import org.setu.framework.bean.BaseDto;
import org.setu.framework.jdbc.core.Column;
import org.setu.framework.utils.DateTimeHelper;

/**
 * 
 * @author: hfl
 */
public class ApplyRecordDto extends BaseDto<ApplyRecordEntity> {

	private static final long serialVersionUID = 1L;

	public ApplyRecordDto() {
	}

	public static final String TABLE_NAME = "apply_record";

	public static final String ID = "id";
	public static final Column ID_C = new Column(ID);

	public static final String NUM = "num";
	public static final Column NUM_C = new Column(NUM);

	public static final String SQR_ID_D = "sqr_id";
	public static final String SQR_ID_J = "sqrId";
	public static final Column SQR_ID_C = new Column(SQR_ID_D);

	public static final String SQR_XM_D = "sqr_xm";
	public static final String SQR_XM_J = "sqrXm";
	public static final Column SQR_XM_C = new Column(SQR_XM_D);

	public static final String SQSJ = "sqsj";
	public static final Column SQSJ_C = new Column(SQSJ);

	public static final String SPR_ID_D = "spr_id";
	public static final String SPR_ID_J = "sprId";
	public static final Column SPR_ID_C = new Column(SPR_ID_D);

	public static final String SPR_XM_D = "spr_xm";
	public static final String SPR_XM_J = "sprXm";
	public static final Column SPR_XM_C = new Column(SPR_XM_D);

	public static final String SPSJ = "spsj";
	public static final Column SPSJ_C = new Column(SPSJ);

	public static final String SPZT = "spzt";
	public static final Column SPZT_C = new Column(SPZT);

	public static final String SFYZ = "sfyz";
	public static final Column SFYZ_C = new Column(SFYZ);

	public static final String HYZK = "hyzk";
	public static final Column HYZK_C = new Column(HYZK);

	public static final String HKBXX = "hkbxx";
	public static final Column HKBXX_C = new Column(HKBXX);

	public static final String GFFP = "gffp";
	public static final Column GFFP_C = new Column(GFFP);

	public static final String GFHT = "gfht";
	public static final Column GFHT_C = new Column(GFHT);

	public static final String SQS = "sqs";
	public static final Column SQS_C = new Column(SQS);

	public static final String CQBC = "cqbc";
	public static final Column CQBC_C = new Column(CQBC);

	public static final String ZLSH = "zlsh";
	public static final Column ZLSH_C = new Column(ZLSH);

	public static final String QSJS = "qsjs";
	public static final Column QSJS_C = new Column(QSJS);

	public static final String QSJSR_ID_D = "qsjsr_id";
	public static final String QSJSR_ID_J = "qsjsrId";
	public static final Column QSJSR_ID_C = new Column(QSJSR_ID_D);

	public static final String QSJSR_XM_D = "qsjsr_xm";
	public static final String QSJSR_XM_J = "qsjsrXm";
	public static final Column QSJSR_XM_C = new Column(QSJSR_XM_D);

	public static final String QSJSSJ = "qsjssj";
	public static final Column QSJSSJ_C = new Column(QSJSSJ);

	public static final String SQKK = "sqkk";
	public static final Column SQKK_C = new Column(SQKK);

	public static final String SQKK_YHZH_D = "sqkk_yhzh";
	public static final String SQKK_YHZH_J = "sqkkYhzh";
	public static final Column SQKK_YHZH_C = new Column(SQKK_YHZH_D);

	public static final String SQKK_KHH_D = "sqkk_khh";
	public static final String SQKK_KHH_J = "sqkkKhh";
	public static final Column SQKK_KHH_C = new Column(SQKK_KHH_D);

	public static final String SQKK_ZHM_D = "sqkk_zhm";
	public static final String SQKK_ZHM_J = "sqkkZhm";
	public static final Column SQKK_ZHM_C = new Column(SQKK_ZHM_D);

	public static final String SQKKSJ = "sqkksj";
	public static final Column SQKKSJ_C = new Column(SQKKSJ);

	public static final String KKCG = "kkcg";
	public static final Column KKCG_C = new Column(KKCG);

	public static final String KKCGSJ = "kkcgsj";
	public static final Column KKCGSJ_C = new Column(KKCGSJ);

	public static final String YJPZ = "yjpz";
	public static final Column YJPZ_C = new Column(YJPZ);

	public static final String YJR_ID_D = "yjr_id";
	public static final String YJR_ID_J = "yjrId";
	public static final Column YJR_ID_C = new Column(YJR_ID_D);

	public static final String YJR_XM_D = "yjr_xm";
	public static final String YJR_XM_J = "yjrXm";
	public static final Column YJR_XM_C = new Column(YJR_XM_D);

	public static final String XT_LRSJ_D = "xt_lrsj";
	public static final String XT_LRSJ_J = "xtLrsj";
	public static final Column XT_LRSJ_C = new Column(XT_LRSJ_D);

	public static final String XT_LRRXM_D = "xt_lrrxm";
	public static final String XT_LRRXM_J = "xtLrrxm";
	public static final Column XT_LRRXM_C = new Column(XT_LRRXM_D);

	public static final String XT_LRRID_D = "xt_lrrid";
	public static final String XT_LRRID_J = "xtLrrid";
	public static final Column XT_LRRID_C = new Column(XT_LRRID_D);

	public static final String XT_LRIP_D = "xt_lrip";
	public static final String XT_LRIP_J = "xtLrip";
	public static final Column XT_LRIP_C = new Column(XT_LRIP_D);

	public static final String XT_ZHXGSJ_D = "xt_zhxgsj";
	public static final String XT_ZHXGSJ_J = "xtZhxgsj";
	public static final Column XT_ZHXGSJ_C = new Column(XT_ZHXGSJ_D);

	public static final String XT_ZHXGRXM_D = "xt_zhxgrxm";
	public static final String XT_ZHXGRXM_J = "xtZhxgrxm";
	public static final Column XT_ZHXGRXM_C = new Column(XT_ZHXGRXM_D);

	public static final String XT_ZHXGRID_D = "xt_zhxgrid";
	public static final String XT_ZHXGRID_J = "xtZhxgrid";
	public static final Column XT_ZHXGRID_C = new Column(XT_ZHXGRID_D);

	public static final String XT_ZHXGIP_D = "xt_zhxgip";
	public static final String XT_ZHXGIP_J = "xtZhxgip";
	public static final Column XT_ZHXGIP_C = new Column(XT_ZHXGIP_D);

	public static final String XT_ZXBZ_D = "xt_zxbz";
	public static final String XT_ZXBZ_J = "xtZxbz";
	public static final Column XT_ZXBZ_C = new Column(XT_ZXBZ_D);

	public static final String XT_PHONE_D = "xt_phone";
	public static final String XT_PHONE_J = "xtPhone";
	public static final Column XT_PHONE_C = new Column(XT_PHONE_D);

	public static final String XT_ZXYY_D = "xt_zxyy";
	public static final String XT_ZXYY_J = "xtZxyy";
	public static final Column XT_ZXYY_C = new Column(XT_ZXYY_D);

	public static final String XT_SQBZ_D = "xt_sqbz";
	public static final String XT_SQBZ_J = "xtSqbz";
	public static final Column XT_SQBZ_C = new Column(XT_SQBZ_D);

	public String getXtPhone() {
		return po.getXtPhone();
	}

	public void setXtPhone(String xtPhone) {
		po.setXtPhone(xtPhone);
	}


	public String getId() {
		return po.getId();
	}

	public void setId(String id) {
		po.setId(id);
	}

	public Integer getNum() {
		return Integer.parseInt(po.getNum());
	}

	public void setNum(int num) {
		po.setNum(String.valueOf(num));
	}

	public String getSqrId() {
		return po.getSqrId();
	}

	public void setSqrId(String sqrId) {
		po.setSqrId(sqrId);
	}

	public String getSqrXm() {
		return po.getSqrXm();
	}

	public void setSqrXm(String sqrXm) {
		po.setSqrXm(sqrXm);
	}

	public Date getSqsj() {
		return DateTimeHelper.parseToDate(po.getSqsj());
	}

	public void setSqsj(Date sqsj) {
		po.setSqsj(DateTimeHelper.formatDateTimetoString(sqsj));
	}

	public String getSprId() {
		return po.getSprId();
	}

	public void setSprId(String sprId) {
		po.setSprId(sprId);
	}

	public String getSprXm() {
		return po.getSprXm();
	}

	public void setSprXm(String sprXm) {
		po.setSprXm(sprXm);
	}

	public Date getSpsj() {
		return DateTimeHelper.parseToDate(po.getSpsj());
	}

	public void setSpsj(Date spsj) {
		po.setSpsj(DateTimeHelper.formatDateTimetoString(spsj));
	}

	public Integer getSpzt() {
		return Integer.parseInt(po.getSpzt());
	}

	public void setSpzt(int spzt) {
		po.setSpzt(String.valueOf(spzt));
	}

	public Integer getSfyz() {
		return Integer.parseInt(po.getSfyz());
	}

	public void setSfyz(int sfyz) {
		po.setSfyz(String.valueOf(sfyz));
	}

	public Integer getHyzk() {
		return Integer.parseInt(po.getHyzk());
	}

	public void setHyzk(int hyzk) {
		po.setHyzk(String.valueOf(hyzk));
	}

	public Integer getHkbxx() {
		return Integer.parseInt(po.getHkbxx());
	}

	public void setHkbxx(int hkbxx) {
		po.setHkbxx(String.valueOf(hkbxx));
	}

	public Integer getGffp() {
		return Integer.parseInt(po.getGffp());
	}

	public void setGffp(int gffp) {
		po.setGffp(String.valueOf(gffp));
	}

	public Integer getGfht() {
		return Integer.parseInt(po.getGfht());
	}

	public void setGfht(int gfht) {
		po.setGfht(String.valueOf(gfht));
	}

	public Integer getSqs() {
		return Integer.parseInt(po.getSqs());
	}

	public void setSqs(int sqs) {
		po.setSqs(String.valueOf(sqs));
	}

	public Integer getCqbc() {
		return Integer.parseInt(po.getCqbc());
	}

	public void setCqbc(int cqbc) {
		po.setCqbc(String.valueOf(cqbc));
	}

	public Integer getZlsh() {
		return Integer.parseInt(po.getZlsh());
	}

	public void setZlsh(int zlsh) {
		po.setZlsh(String.valueOf(zlsh));
	}

	public String getQsjs() {
		return po.getQsjs();
	}

	public void setQsjs(String qsjs) {
		po.setQsjs(qsjs);
	}

	public String getQsjsrId() {
		return po.getQsjsrId();
	}

	public void setQsjsrId(String qsjsrId) {
		po.setQsjsrId(qsjsrId);
	}

	public String getQsjsrXm() {
		return po.getQsjsrXm();
	}

	public void setQsjsrXm(String qsjsrXm) {
		po.setQsjsrXm(qsjsrXm);
	}

	public Date getQsjssj() {
		return DateTimeHelper.parseToDate(po.getQsjssj());
	}

	public void setQsjssj(Date qsjssj) {
		po.setQsjssj(DateTimeHelper.formatDateTimetoString(qsjssj));
	}

	public Integer getSqkk() {
		return Integer.parseInt(po.getSqkk());
	}

	public void setSqkk(int sqkk) {
		po.setSqkk(String.valueOf(sqkk));
	}

	public String getSqkkYhzh() {
		return po.getSqkkYhzh();
	}

	public void setSqkkYhzh(String sqkkYhzh) {
		po.setSqkkYhzh(sqkkYhzh);
	}

	public String getSqkkKhh() {
		return po.getSqkkKhh();
	}

	public void setSqkkKhh(String sqkkKhh) {
		po.setSqkkKhh(sqkkKhh);
	}

	public String getSqkkZhm() {
		return po.getSqkkZhm();
	}

	public void setSqkkZhm(String sqkkZhm) {
		po.setSqkkZhm(sqkkZhm);
	}

	public Date getSqkksj() {
		return DateTimeHelper.parseToDate(po.getSqkksj());
	}

	public void setSqkksj(Date sqkksj) {
		po.setSqkksj(DateTimeHelper.formatDateTimetoString(sqkksj));
	}

	public Integer getKkcg() {
		return Integer.parseInt(po.getKkcg());
	}

	public void setKkcg(int kkcg) {
		po.setKkcg(String.valueOf(kkcg));
	}

	public Date getKkcgsj() {
		return DateTimeHelper.parseToDate(po.getKkcgsj());
	}

	public void setKkcgsj(Date kkcgsj) {
		po.setKkcgsj(DateTimeHelper.formatDateTimetoString(kkcgsj));
	}

	public Integer getYjpz() {
		return Integer.parseInt(po.getYjpz());
	}

	public void setYjpz(int yjpz) {
		po.setYjpz(String.valueOf(yjpz));
	}

	public String getYjrId() {
		return po.getYjrId();
	}

	public void setYjrId(String yjrId) {
		po.setYjrId(yjrId);
	}

	public String getYjrXm() {
		return po.getYjrXm();
	}

	public void setYjrXm(String yjrXm) {
		po.setYjrXm(yjrXm);
	}

	public Date getXtLrsj() {
		return DateTimeHelper.parseToDate(po.getXtLrsj());
	}

	public void setXtLrsj(Date xtLrsj) {
		po.setXtLrsj(DateTimeHelper.formatDateTimetoString(xtLrsj));
	}

	public String getXtLrrxm() {
		return po.getXtLrrxm();
	}

	public void setXtLrrxm(String xtLrrxm) {
		po.setXtLrrxm(xtLrrxm);
	}

	public String getXtLrrid() {
		return po.getXtLrrid();
	}

	public void setXtLrrid(String xtLrrid) {
		po.setXtLrrid(xtLrrid);
	}

	public String getXtLrip() {
		return po.getXtLrip();
	}

	public void setXtLrip(String xtLrip) {
		po.setXtLrip(xtLrip);
	}

	public Date getXtZhxgsj() {
		return DateTimeHelper.parseToDate(po.getXtZhxgsj());
	}

	public void setXtZhxgsj(Date xtZhxgsj) {
		po.setXtZhxgsj(DateTimeHelper.formatDateTimetoString(xtZhxgsj));
	}

	public String getXtZhxgrxm() {
		return po.getXtZhxgrxm();
	}

	public void setXtZhxgrxm(String xtZhxgrxm) {
		po.setXtZhxgrxm(xtZhxgrxm);
	}

	public String getXtZhxgrid() {
		return po.getXtZhxgrid();
	}

	public void setXtZhxgrid(String xtZhxgrid) {
		po.setXtZhxgrid(xtZhxgrid);
	}

	public String getXtZhxgip() {
		return po.getXtZhxgip();
	}

	public void setXtZhxgip(String xtZhxgip) {
		po.setXtZhxgip(xtZhxgip);
	}

	public Integer getXtZxbz() {
		return Integer.parseInt(po.getXtZxbz());
	}

	public void setXtZxbz(int xtZxbz) {
		po.setXtZxbz(String.valueOf(xtZxbz));
	}

	public String getXtZxyy() {
		return po.getXtZxyy();
	}

	public void setXtZxyy(String xtZxyy) {
		po.setXtZxyy(xtZxyy);
	}


	public Integer getXtSqbz() {
		return Integer.parseInt(po.getXtSqbz());
	}

	public void setXtSqbz(int xtSqbz) {
		po.setXtSqbz(String.valueOf(xtSqbz));
	}

}
