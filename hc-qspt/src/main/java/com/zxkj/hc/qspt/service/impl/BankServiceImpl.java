package com.zxkj.hc.qspt.service.impl;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.setu.framework.exception.BusinessRuntimeException;
import org.setu.framework.extension.sysfield.EntitySysFieldBuilder;
import org.setu.framework.http.controller.SetuBaseController;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.utils.CommonUtil;
import org.springframework.stereotype.Service;

import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyUserEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.FlowDataEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.UploadtableEntity;
import com.zxkj.hc.qspt.service.BankService;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;

@Service("BankService")
public class BankServiceImpl extends SetuBaseController implements BankService{
	
	@Override
	public int setBank(ShuidanEntity shuidan,String isSuccess) {
		// TODO Auto-generated method stub
		int tag = 0;
		SetuSession session = (SetuSession) getSession();
		UploadtableEntity upload = new UploadtableEntity();
		FlowDataEntity flowData = new FlowDataEntity();
		String applyId = shuidan.getApplyId();
		flowData.addConditonItem(new ConditionItem("apply_flow.flow_type","=", "sfyz"));
		flowData.addConditonItem(new ConditionItem("flow_info.sort","=", "1"));
		flowData.addConditonItem("or",new ConditionItem("apply_flow.flow_type","=", "gffp"));
		flowData.addConditonItem(new ConditionItem("apply_flow.apply_id","=", applyId));
		List<FlowDataEntity> UserInfoList = flowData.queryListBySelf(false, "LEFT JOIN flow_info ON(" + 
				"	flow_info.id = flow_data.info_id\r\n" + 
				")LEFT JOIN apply_flow ON (" + 
				"	flow_info.flow_id = apply_flow.id" + 
				")", null, null, null, null);
		for(int i =0;i<UserInfoList.size();i++) {
			if("身份证号".equals(UserInfoList.get(i).getKeyName())) {
				upload.setIdcard(UserInfoList.get(i).getKeyValue());
			}else if("真实姓名".equals(UserInfoList.get(i).getKeyName())){
				upload.setUsername(UserInfoList.get(i).getKeyValue());
			}else if("《房屋所有权证》证号".equals(UserInfoList.get(i).getKeyName())){
				upload.setHouseid(UserInfoList.get(i).getKeyValue());
			}else if("购房地址".equals(UserInfoList.get(i).getKeyName())){
				upload.setHouseaddress(UserInfoList.get(i).getKeyValue());
			}
		}
		upload.setApplyid(applyId);
		upload.setJehjdx(shuidan.getJehjdx());
		upload.setJehjxx(shuidan.getJehjxx());
		upload.setNsrbh(shuidan.getNsrbh());
		upload.setNsrmc(shuidan.getNsrmc());
		upload.setPzxh(shuidan.getPzxh());
		upload.setSkxjrq(shuidan.getSkxjrq());
		upload.setSwjgmc(shuidan.getSwjgmc());
		upload.setZsxmmc(shuidan.getZsxmmc());
		upload.setIssuccess(isSuccess);
		
		ApplyUserEntity user = new ApplyUserEntity();
		user.setId(session.getUserId());
		user.setXtZxbz(EntitySysFieldBuilder.XT_ZXBZ_WZX);
		user.queryBySelf();
		upload.setUserid(user.getWechatId());
		
		ApplyRecordEntity apply = new ApplyRecordEntity();
		apply.setId(applyId);
		apply.setXtZxbz(EntitySysFieldBuilder.XT_ZXBZ_WZX);
		apply.queryBySelf();
		upload.setJnzt((apply.getSpzt().equals("5")||apply.getSpzt().equals("6")||apply.getSpzt().equals("7")
				||apply.getSpzt().equals("8")||apply.getSpzt().equals("9"))?"1":"0");
		
		BeanUtil.setXzField(upload);
		if(!CommonUtil.isNull(upload.insertSelf())) {
			tag = 1;
		}
		return tag;
	}

	@Override
	public SetuResult getBank(String time) throws Exception {
		// TODO Auto-generated method stub
		try {
			time = decrypt(time, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJWgYXVdixGiz6tTW920PrZmZ6ZUxJ+NyQlMLw6hhjTVI8XrSqkWg7vPkMIzfDyAdR7+MhrvsoHg0HOlsdHVZ4rExYr4lDc/XWWS2QT4DK2GmKA7+7CpQmuzIlLZdIy3j8nB8gBLqO7p8GsN/TqRs00FMaeQtVD2C+Wr1PkO+rXtAgMBAAECgYEAlMlLwFqJnLrqiLI2qsq+hcBlF1g5wXNLUsyICqOCAe1kSj5EGsoBqvOnPgXWZ49RZ8Vb678pIc99WVKN9VuhPi0QbWH2xXLAO9jXoFf9km2j0A4qfIIcW3/NXca/XOcUru6diu6ALqT3J2zKMkCONZExm1QXuP6IsHWQzv+GaaECQQDUTUqPYQ1jI5hRwa6XSYiQe7I9Mwd9vPhLAYufOBURsodgUgVGXzy3wW6EP2zETVPBOVdvbi7Kqt57nG+jVwClAkEAtGyRNqWJaa/q/KO7dg7B8PJIKRC70ajCajvuk4hXxblY4pZit+elfNEuq6rRiwJWTfJxMPmV6QxAqVvumVrVqQJAMte7xnCYsyhgHxxCQqrykJ9/BRcGcT9jOxtIo73bldxqfU/HQZ4nPV6OqA9EbhKSv1CLNv1tKU4YcAP73GNGnQJAce7xEEoeaQ/+2wTl9xpAnE/TA6OehzoDcyRfduitTJ3LpGKE+k8csKJDz4HyCs8yAszrb5rccKsdFhWIqWyz6QJBALxKuPYWhaeGPTYVcuqggbNUuZS4dEblX639NMUN1oIi5ViekrQs2bYskUGXP/Wo9XaHBRWyRcDKWrx+S7ok/ng=\\r\\n");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessRuntimeException("解密失败！");
		}
		UploadtableEntity upload = new UploadtableEntity();
		upload.addConditonItem(new ConditionItem("xt_zhxgsj", "<", time));
		upload.addConditonItem(new ConditionItem("jnzt", "=", "0"));
		upload.addConditonItem(new ConditionItem("isSuccess", "=", "0"));
		upload.addConditonItem(new ConditionItem("xt_zxbz", "=", "0"));
		return new SetuResult(encryptList(upload.queryListBySelf(),"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbDXdoXene//USHwVNMqUDq0S6tB3zWP1ZOCV88nzP/ks2EsTD/21hmRf+uf4SQsby/TVADnDTJ/uSBOUcVdqH6dRT73VcIOdlqaTNwaQsY8/yuuNiHp3bCnI1wW8eUUbLX3jFHpj0DUzRozg+szMoV0i5o5PWOujLWY8xFIZzOQIDAQAB"));
	}
	
	private static String decrypt(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}
	
	private static List<UploadtableEntity> encryptList( List<UploadtableEntity> uploadList, String publicKey ) throws Exception{
		
		List<UploadtableEntity> returnUpload = new ArrayList<UploadtableEntity>();
		for(UploadtableEntity uploadTemp:uploadList) {
			if(CommonUtil.isNotNull(uploadTemp.getApplyid())) {
				uploadTemp.setApplyid(encrypt(uploadTemp.getApplyid(),publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getHouseaddress())) {
				uploadTemp.setHouseaddress(encrypt(uploadTemp.getHouseaddress(),publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getHouseid())) {
				uploadTemp.setHouseid(encrypt(uploadTemp.getHouseid(),publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getId())) {
				uploadTemp.setId(encrypt(uploadTemp.getId(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getIdcard())) {
				uploadTemp.setIdcard(encrypt(uploadTemp.getIdcard(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getIssuccess())) {
				uploadTemp.setIssuccess(encrypt(uploadTemp.getIssuccess(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getJehjdx())) {
				uploadTemp.setJehjdx(encrypt(uploadTemp.getJehjdx(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getJehjxx())) {
				uploadTemp.setJehjxx(encrypt(uploadTemp.getJehjxx(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getJnzt())) {
				uploadTemp.setJnzt(encrypt(uploadTemp.getJnzt(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getNsrbh())) {
				uploadTemp.setNsrbh(encrypt(uploadTemp.getNsrbh(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getNsrmc())) {
				uploadTemp.setNsrmc(encrypt(uploadTemp.getNsrmc(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getPzxh())) {
				uploadTemp.setPzxh(encrypt(uploadTemp.getPzxh(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getSkxjrq())) {
				uploadTemp.setSkxjrq(encrypt(uploadTemp.getSkxjrq(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getSwjgmc())) {
				uploadTemp.setSwjgmc(encrypt(uploadTemp.getSwjgmc(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getUserid())) {
				uploadTemp.setUserid(encrypt(uploadTemp.getUserid(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getUsername())) {
				uploadTemp.setUsername(encrypt(uploadTemp.getUsername(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtLrip())) {
				uploadTemp.setXtLrip(encrypt(uploadTemp.getXtLrip(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtLrrid())) {
				uploadTemp.setXtLrrid(encrypt(uploadTemp.getXtLrrid(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtLrrxm())) {
				uploadTemp.setXtLrrxm(encrypt(uploadTemp.getXtLrrxm(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtLrsj())) {
				uploadTemp.setXtLrsj(encrypt(uploadTemp.getXtLrsj(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtZhxgip())) {
				uploadTemp.setXtZhxgip(encrypt(uploadTemp.getXtZhxgip(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtZhxgrid())) {
				uploadTemp.setXtZhxgrid(encrypt(uploadTemp.getXtZhxgrid(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtZhxgrxm())) {
				uploadTemp.setXtZhxgrxm(encrypt(uploadTemp.getXtZhxgrxm(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtZhxgsj())) {
				uploadTemp.setXtZhxgsj(encrypt(uploadTemp.getXtZhxgsj(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtZxbz())) {
				uploadTemp.setXtZxbz(encrypt(uploadTemp.getXtZxbz(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getXtZxyy())) {
				uploadTemp.setXtZxyy(encrypt(uploadTemp.getXtZxyy(), publicKey));
			}
			if(CommonUtil.isNotNull(uploadTemp.getZsxmmc())) {
				uploadTemp.setZsxmmc(encrypt(uploadTemp.getZsxmmc(), publicKey));
			}
			returnUpload.add(uploadTemp);
		}
		return returnUpload;
	}

	private static String encrypt( String str, String publicKey ) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}
}
