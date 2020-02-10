package com.zxkj.hc.qspt.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxkj.hc.qspt.orm.hcqspt.bean.consts.QsptConsts;
import com.zxkj.hc.qspt.orm.hcqspt.bean.dto.RoleDto;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ApplyRecordEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.RoleResEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.ShuidanEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.entity.YoujixinxiEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.enums.Spzt;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.ApplyReturnEntity;
import com.zxkj.hc.qspt.orm.hcqspt.bean.hcqspt.bean.entity.OrdercreateEntity;
import com.zxkj.hc.qspt.setu.system.BeanUtil;
import com.zxkj.hc.qspt.setu.system.SetuResult;
import com.zxkj.hc.qspt.setu.system.SetuSession;
import org.setu.framework.exception.BusinessRuntimeException;
import org.setu.framework.exception.BusinessWarnException;
import org.setu.framework.jdbc.core.Condition;
import org.setu.framework.jdbc.core.ConditionItem;
import org.setu.framework.jdbc.core.SQL;
import org.setu.framework.session.controller.SetuSessionController;
import org.setu.framework.utils.encrypt.Md5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import  com.zxkj.hc.qspt.util.OrderUtil;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zxkj.hc.qspt.controller.ApplyZlscController.*;

@RestController()
@Component
@RequestMapping("/api/apply")
public class ApplySdyjController extends SetuSessionController{

	//一级商户号
	@Value("${shanghuhao.partner}")
	private String partner;

	//二级商户号
	@Value("${shanghuhao.subpartner}")
	private String subpartner;

	//支付订单异步回调接口
	@Value("${shanghuhao.returnsyncurl}")
	private String returnsyncurl;


	//支付订同步步回调接口
	@Value("${shanghuhao.returnurl}")
	private String returnurl;
	//tradeMode

	//支付订同步步回调接口
	@Value("${shanghuhao.tradeMode}")
	private String tradeMode;

    //支付订同步步回调接口
    @Value("${urlfrorefund.url}")
    private String url;


	/**
	 * 税单邮寄列表
	 * @param orderby
	 * @param pageOffset
	 * @param pageRowSize
	 * @param keywords
	 * @param filter
	 * @return
	 */
	@RequestMapping("/sdyjlb")
	@ResponseBody
	public SetuResult zlshlb(String orderby, int pageOffset, int pageRowSize,String keywords,String filter){
		SetuSession session=(SetuSession) getSetuSession();
		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.addConditonItem(new ConditionItem("spr_id","=",session.getUserId()));
		Condition condition=new Condition();
		if(StringUtils.isEmpty(filter)){
			condition.add(new ConditionItem("spzt",">",Spzt.KKCG.getCode()));
		}else{
			condition.add(new ConditionItem("spzt","=",filter));
		}
		entity.addConditon("and",condition);
//		entity.addConditonItem("and",new ConditionItem("xt_zxbz","=","0"));
		if(!StringUtils.isEmpty(keywords)){
			entity.addConditonItem("and",new ConditionItem("sqr_xm","like","%"+keywords+"%"));
		}
		SetuResult result=new SetuResult();
		result.setTotal(entity.countBySelf());
		result.setRows(entity.queryListBySelf(orderby,pageOffset,pageRowSize));
		YoujixinxiEntity youjixinxiEntity = new YoujixinxiEntity();

		return result;
	}

	/**
	 * 税单邮寄
	 * @param applyId
	 * @return
	 */
	@RequestMapping("/yjsd")
	@ResponseBody
	public SetuResult yjsd(String applyId) throws BusinessWarnException {
		if(StringUtils.isEmpty(applyId)){
			throw new BusinessWarnException("申请ID不能为空");
		}

		ApplyRecordEntity entity=new ApplyRecordEntity();
		entity.setSpzt(Spzt.YYJ.getCode());
		entity.setYjpz("1");
//		SetuSession session=(SetuSession) getSetuSession();
//		entity.setYjrId(session.getUserId());
//		entity.setYjrXm(session.getUserName());
		BeanUtil.setXgField(entity, getTimeNow());
		entity.addConditonItem(new ConditionItem("id","=",applyId));
//		entity.addConditonItem("and",new ConditionItem("spr_id","=",session.getUserId()));
		entity.addConditonItem("and",new ConditionItem("spzt","=",Spzt.DYJ.getCode()));
		if(entity.updateBySelfCondition()==0){
			throw new BusinessWarnException("操作失败");
		}
		return new SetuResult("邮寄成功");
	}

	/**
	 * 税单获取
	 * @param applyId
	 * @return
	 */
	@RequestMapping("/sdhq")
	@ResponseBody
	public ShuidanEntity sdhq(String applyId,String clientKey) throws BusinessWarnException {
		if(StringUtils.isEmpty(applyId)){
			System.out.println("税单获取为空");
		}

		SetuSession session=(SetuSession) getSetuSession();
		ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
//		applyRecordEntity.setId(applyId);
		applyRecordEntity.setSqrId(session.getUserId());
		applyRecordEntity.addConditonItem(new ConditionItem("spzt","=","3"));
		if(applyRecordEntity.countBySelf()==0){
			throw new BusinessWarnException("没有操作此记录的权限");
		}
		applyRecordEntity.queryBySelf();
		ShuidanEntity shuidanEntity=new ShuidanEntity();
		shuidanEntity.addConditonItem(new ConditionItem("apply_id","=",applyRecordEntity.getId()));
		shuidanEntity.queryBySelf();
		return shuidanEntity;
	}

	/**
	 * 申请税单邮寄
	 * @return
	 */
	@RequestMapping("/sqsdyj")
	@ResponseBody
	public SetuResult sqsdyj(String sjr,String sjhm,String lqdz,String post) throws BusinessWarnException {
		System.out.println("进入邮寄模块");
		System.out.println("收件人:" +sjr +";手机号： "+sjhm +"; 邮编: "+post+"; 详细地址： "+lqdz);
		if(StringUtils.isEmpty(sjr)){
			throw new BusinessWarnException("收件人不能为空");
		}
		if(StringUtils.isEmpty(sjhm)){
			throw new BusinessWarnException("手机号码不能为空");
		}
		if(StringUtils.isEmpty(lqdz)){
			throw new BusinessWarnException("领取地址不能为空");
		}

		ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
		applyRecordEntity.setSpzt(Spzt.DYJ.getCode());
		SetuSession session=(SetuSession) getSetuSession();
		System.out.println("用户ID："+session.getUserId());
		applyRecordEntity.addConditonItem(new ConditionItem("sqr_id","=",session.getUserId()));
		applyRecordEntity.addConditonItem(new ConditionItem("xt_zxbz","=","0"));
		applyRecordEntity.addConditonItem("and",new ConditionItem("spzt","=",Spzt.KKCG.getCode()));
		if(applyRecordEntity.updateBySelfCondition()==0){
			return new SetuResult("未查询到您的相关记录，请联系管理员！");
		}
		YoujixinxiEntity youjixinxiEntity=new YoujixinxiEntity();
		YoujixinxiEntity check=new YoujixinxiEntity();

		ApplyRecordEntity applyRecordEntity1 = new ApplyRecordEntity();
		applyRecordEntity1.setSqrId(session.getUserId());
		applyRecordEntity1.setXtZxbz("0");
		applyRecordEntity1.queryBySelf();

		check.setUserId(applyRecordEntity1.getId());
		List<YoujixinxiEntity> list = check.queryListBySelf();
		youjixinxiEntity.setSjr(sjr);//收件人
		youjixinxiEntity.setSjhm(sjhm);//手机号码
		youjixinxiEntity.setLqdz(lqdz);//领取地址
		youjixinxiEntity.setPostalCode(post);
		if(list.size()>0){
			youjixinxiEntity.addConditonItem(new ConditionItem("user_id","=",applyRecordEntity1.getId()));
			if(youjixinxiEntity.updateBySelfCondition()==0){
				return new SetuResult("邮寄地址更新失败！");
			}
		}else{
			youjixinxiEntity.setUserId(applyRecordEntity1.getId());
			youjixinxiEntity.insertSelf();
			return new SetuResult("SUCCESS");
		}
		return new SetuResult("SUCCESS");
	}
	/**
	 * 订单创建
	 * @param applyId
	 * @return hxt
	 */
	@RequestMapping("/orderCreat")
	@ResponseBody
	public SetuResult orderCreat(String token,String applyId,String url) throws Exception {
		System.out.println("======进入缴费模块======");
		//用于存放headers参数
		Map<String, String> headers = new HashMap<String, String>();
		//获得工具类
		OrderUtil orderUtil = new OrderUtil();
		//存订单创建返回的参数
		Map<String, String> jsonMap = null;
		//存请求报文
		String report= "";
		//存放随机生成的8位商品ID,唯一不可重复
		String orderId = "";
		//session
        SetuSession session=(SetuSession) getSetuSession();
		orderId = orderUtil.getOrderId();

		ApplyRecordEntity applyRecordEntity=new ApplyRecordEntity();
		applyRecordEntity.setSqrId(session.getUserId());
		applyRecordEntity.addConditonItem(new ConditionItem("spzt","=","3"));
		applyRecordEntity.queryBySelf();
		report = this.getReport(token,orderId,applyRecordEntity.getId());
		System.out.println("订单创建参数列表：");
		System.out.println("token:" + token);
		System.out.println("applyId:" + applyRecordEntity.getId());
		System.out.println("orderId:" + orderId);
		System.out.println("url:" + url);
        //创建订单前，先检查数据库中是否已存在订单，如果还在支付的允许范围内，直接取订单号返回给前台
		System.out.println("正在检查数据库订单号...");
		List<OrdercreateEntity> orderCheckList = checkOrder(applyRecordEntity.getId(),session.getUserId(),QsptConsts.XT_ZXBZ_WZX);
		if(orderCheckList.size() == 0 || orderCheckList.isEmpty()){
			System.out.println("数据库不存在...");
			System.out.println("创建订单号...");
			JSONObject jsonObject = getOrder(url,headers,report,session.getUserId(),applyRecordEntity.getId(),orderId);
			if(jsonObject.isEmpty() || jsonObject==null){
				throw new BusinessRuntimeException("订单创建失败,返回参数为空!");
			}else{
				return new SetuResult(jsonObject);
			}
		}else{
			OrdercreateEntity orderCheck = orderCheckList.get(0);
			System.out.println("订单参数:"+orderCheck);
			if(orderCheck.getKeyId().isEmpty() || orderCheck.getKeyId().equals("") || orderCheck.getKeyId()==null){
				JSONObject jsonObject = getOrder(url,headers,report,session.getUserId(),applyRecordEntity.getId(),orderId);
				if(jsonObject.isEmpty() || jsonObject==null){
					throw new BusinessRuntimeException("订单创建失败 , 返回参数为空!");
				}else{
					return new SetuResult(jsonObject);
				}
			}else{
				//数据库中已存在订单
				//1.查看订单是否失效
				//2.如果失效则执行插入
				String isSuccess = checkTime(orderCheck.getXtLrsj());
				if(isSuccess.equals(QsptConsts.SUCCESS)){
					System.out.println(new Date()+":订单未失效，可直接支付。");
					//组装返回参数
					Map<String, Object> jsonmap = new HashMap<>();
					Map<String,Object> map  = new HashMap<>();
					map.put("key",orderCheck.getKeyId());
					jsonmap.put("code","00");
					jsonmap.put("message","SUCCESS");
					jsonmap.put("context",map);
					JSONObject jsonObject = new JSONObject(jsonmap);
					return new SetuResult(jsonObject);
				}else{
					System.out.println("订单已超时，正在销毁过时订单号，并启动重新创建...");
					//如果订单失效，注销本条订单
					orderCheck.setXtZxbz(QsptConsts.XT_ZXBZ_YZX);
					//判断注销本条订单是否成功
					if(orderCheck.updateBySelfId()==1){
						System.out.println("注销订单成功!");
						System.out.println("再次请求创建订单号...");
						JSONObject jsonObject = getOrder(url,headers,report,session.getUserId(),applyRecordEntity.getId(),orderId);
						if(jsonObject.isEmpty() || jsonObject==null){
							throw new BusinessRuntimeException("订单创建失败 ,返回参数为空!");
						}else{
							return new SetuResult(jsonObject);
						}
					}else{
						throw new BusinessRuntimeException("订单注销失败，请检查数据库数据是否正确，订单号为:"+orderCheck.getKeyId());
					}
				}
			}

		}

	}



	/**
	 * 退款接口
	 * @return hxt
	 */
	@RequestMapping("/refund")
	@ResponseBody
	public String refund(String passNumber,String orderId,String host){
		Map map = new HashMap();
		//用于身份验证秘钥
		String passDefault="123qweasdZXC";
		if(passNumber==null||!passNumber.equals(passDefault)){
			map.put("code","500");
			map.put("message","秘钥匹配错误,请联系管理员！");
			return map.toString();
		}
		if(orderId==null){
			map.put("code","500");
			map.put("message","订单号错误，请联系管理员！");
			return map.toString();
		}
		ApplyReturnEntity applyReturnEntity = new ApplyReturnEntity();
		applyReturnEntity.setOutTradeNo(orderId);
		applyReturnEntity.queryBySelf();
		if(applyReturnEntity.getTotalFee().isEmpty()){
			map.put("code","500");
			map.put("message","系统未找到任何金额信息！");
			return map.toString();
		}

		//用于存放headers参数
		Map<String, String> headers = new HashMap<String, String>();
		//特约商户key
		String key = "ff3f4ed8edbe4d429a1bd0b1cb7a1d8a";
		//1，获取请求报文主体
		Map<String, String> map1 = getRefundMap(orderId,applyReturnEntity.getTotalFee());


		//4.除去数组中的空值和签名参数
		Map<String, String> map3 = paraRefundFilter(map1);

		//5.把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串 + key
		String str = createLinkString(map3)+key;
		System.out.println("【MD5】加密前原始串：↓");
		System.out.println(str);

		//6.生成签名串MD5
		String sign = Md5Util.encode(str);
		System.out.println("【MD5】加密后sgin:↓");
		System.out.println(sign);

		//7.请求报文加上 sign和sign_type
		map3.put("sign", sign);
		map3.put("sign_type", "MD5");
		//8.生成请求报文
		String jsonString = JSONArray.toJSONString(map3);
		JSONObject jsonObject = JSONObject.parseObject(httpRequestBodyConnectionPOST(this.url,headers,jsonString).get("body"));
		String retCode = jsonObject.getString("retCode");
		String returnSerNo = jsonObject.getString("returnSerNo");
		String retMsg = jsonObject.getString("retMsg");
		map.put("retCode",retCode);
		map.put("returnSerNo",returnSerNo);
		map.put("message",retMsg);
		map.put("code","200");
		map.put("status","fail");
		if(retCode.equalsIgnoreCase("0000")){
			OrdercreateEntity ordercreateEntity = new OrdercreateEntity();
			ordercreateEntity.setStatus("2"); //2,退款成功 3失败
			ordercreateEntity.addConditonItem(new ConditionItem("key_id","=",orderId));
			int isUpdateSuccess = ordercreateEntity.updateBySelfCondition();
			if(isUpdateSuccess == 1){
				map.put("status","success");
			}
		}
		 return map.toString();


	}

	private Map<String, String> getRefundMap(String orderId, String money) {
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("service", "refund_service");
		map2.put("partner", this.partner);
		map2.put("subpartner", this.subpartner);
		map2.put("out_trade_no", orderId);
		map2.put("out_refund_no", orderId);
		map2.put("total_fee", money);
		map2.put("refund_fee",money);
		map2.put("refund_reason", "契税系统缴税扣款失败退款");
		return map2;
	}

	/**
	 * 除去数组中的空值和签名参数
	 * @param sArray 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraRefundFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = String.valueOf(sArray.get(key));
			if ("null".equals(value)||value == null || value.equals("") || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}
	/**
	 * 比较订单创建时间，如果超过30分钟，则订单重新创建
	 * @param lastInsertTime
	 * @return hxt
	 */
	public String checkTime(String lastInsertTime){
		SimpleDateFormat simpleDateFormat
				= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date nowTime = new Date();
			System.out.println("订单在数据库中存在，进入时间校验中...");
			System.out.println("当前时间:" + simpleDateFormat.format(nowTime));
			System.out.println("数据库时间:" + lastInsertTime);
			System.out.println("比较的时间:"+ (nowTime.getTime()-simpleDateFormat.parse(lastInsertTime).getTime())/60*1000);
			if((nowTime.getTime()-simpleDateFormat.parse(lastInsertTime).getTime())/60*1000>30){
				System.out.println("验证不通过！");
				return QsptConsts.FAIL;
			}
			System.out.println("验证通过！");
		} catch (ParseException e) {
			throw new BusinessRuntimeException("校验订单是否超时错误，检查从数据库中获取到的订单创建时间格式。转换时间："+lastInsertTime);
		}
		return QsptConsts.SUCCESS;
	}
	/**
	 * 获取订单，同时插入数据库
	 * @param url
	 * @param headers
	 * @param report
	 * @param userId
	 * @param applyId
	 * @param orderId
	 * @return hxt
	 * @throws UnknownHostException
	 */
	public JSONObject getOrder(String url,Map<String, String> headers,String report,String userId,String applyId,String orderId) throws UnknownHostException {
		System.out.println("获取订单号...");
		JSONObject jsonObject = JSONObject.parseObject(httpRequestBodyConnectionPOST(url,headers,report).get("body"));
		if(jsonObject.isEmpty() || jsonObject==null){
			throw new BusinessRuntimeException("订单创建失败,获取天府订单号失败！请检查天府订单创建接口数据是否异常!");
		}
		//todo
		JSONObject jsonNode = JSONObject.parseObject(jsonObject.getString("context"));
		String status = jsonObject.getString("code");
		if(!status.equalsIgnoreCase("00")){
			return jsonObject;
		}
		System.out.println("订单号参数:"+jsonObject.getString("context"));
		System.out.println("key:"+jsonNode.getString("key"));
		InetAddress address = InetAddress.getLocalHost();
		String ip = address.getHostAddress();//获得本机IP
		String isSuccess = insertOrder(userId,applyId,ip,QsptConsts.PAY_LOADING,jsonNode.getString("key"),orderId);
		if(isSuccess.equals(QsptConsts.SUCCESS)){
			return jsonObject;
		}
		return null;
	}

	/**
	 * 往数据库中插订单数据
	 * @param userId
	 * @param applyId
	 * @param ip
	 * @param status
	 * @param key
	 * @param orderId
	 * @return hxt
	 */
	public String insertOrder(String userId,String applyId,String ip,String status,String key,String orderId){
		OrdercreateEntity ordercreateEntity = new OrdercreateEntity();
		ordercreateEntity.setApplyUserId(userId);
		ordercreateEntity.setApplyId(applyId);  //支付id
		ordercreateEntity.setXtLrip(ip);   //设备ip
		ordercreateEntity.setXtLrsj(getTimeNow()); //录入时间
		ordercreateEntity.setStatus(status); //状态4表示处理中...
		ordercreateEntity.setKeyId(key);  //key是天府银行传来的订单号
		ordercreateEntity.setProductId(orderId);  //8位不重复id
		if(ordercreateEntity.insertSelf() == "0"){
			throw new BusinessRuntimeException("往数据库中插入订单失败！");
		}
		return "SUCCESS";
	}


	/**
	 * 创建订单，先查看数据库是否已经存在
	 * @param applyId
	 * @param userId
	 * @param isDelete
	 * @return hxt
	 */
	public List<OrdercreateEntity> checkOrder(String applyId,String userId,String isDelete){
		OrdercreateEntity ordercreateEntity =new OrdercreateEntity();
		ordercreateEntity.setApplyId(applyId);
		ordercreateEntity.setApplyUserId(userId);
		ordercreateEntity.setXtZxbz(isDelete);  //注销标志,0表示未注销
		List<OrdercreateEntity> listOrder = ordercreateEntity.queryListBySelf();
		return listOrder;
	}

	/**
	 * 获得订单创建需要的报文
	 * @param token
	 * @return hxt
	 * @throws Exception
	 */

	public String getReport(String token,String orderId,String applyId) throws Exception {
//		//特约商户key
//		String key = "23a247d596c24512aeeba7432ab94c66";
		//1，获取请求报文主体
		Map<String, Object> map1 = getMap(token,orderId,applyId);
		//2.除去数组中的空值和签名参数
		Map<String, String> map3 = paraFilter(map1);
		//6.生成请求报文
		String jsonString = JSONArray.toJSONString(map3);
		System.out.println("请求报文："+jsonString);

		return jsonString;
	}

	private Map<String, Object> getMap(String token,String orderId,String applyId) {
	    ShuidanEntity shuidanEntity =new ShuidanEntity();
	    shuidanEntity.setApplyId(applyId);
	    shuidanEntity.queryBySelf();
		double num1;
		double num2;
		double total;
		java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.00");
		String yhs = shuidanEntity.getYhs();
		String qishui = shuidanEntity.getJehjxx();

		num1 = Double.parseDouble(yhs);
		num2 = Double.parseDouble(qishui);
		total = Double.parseDouble(myformat.format(num1))+
				Double.parseDouble(myformat.format(num2));
		String money =String.valueOf(total);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("orderId", orderId);
		map2.put("subject", "契税系统扣费");
		map2.put("body", "契税银行代扣");
		map2.put("transAmount", money);  //金额
		map2.put("thirdPartChannel", "deed"); //客户号
		map2.put("tradeMode", this.tradeMode); //交易类型
		map2.put("partner", this.partner); //一级商户号
		map2.put("subpartner", this.subpartner); //二级商户号
		map2.put("returnUrl", this.returnurl); //同步回调地址
		map2.put("notifyUrl", this.returnsyncurl); //异步回调地址
		map2.put("tokenId", token); //token
		return map2;
	}



	/**
	 * 除去数组中的空值和签名参数
	 * @param sArray 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, Object> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			Object obj = sArray.get(key);
			String value = null;
			if(obj instanceof String){
				value = String.valueOf(obj);
			} else {
				value = JSONArray.toJSONString(obj);
			}
			if ("null".equals(value)||value == null || value.equals("") || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 *
	 * 修改授权
	 *
	 */
	@RequestMapping("/sqtg")
	@ResponseBody
	public String sqtg(String id){
		SetuResult result = new SetuResult();
		Map map = new HashMap();

		if(id.equalsIgnoreCase("")){
			map.put("code","500");
			return map.toString();
		}
		ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
		applyRecordEntity.setId(id);
		applyRecordEntity.setXtSqbz("1");
		int status = applyRecordEntity.updateBySelfCondition();
		map.put("code","200");
		map.put("status",status);
		 return map.toString();
	}

    /**
     * 交易状态查询
     * @return hxt
     */
    @RequestMapping("/getPostNumber")
    @ResponseBody
    public Map getPostNumber(){
        Map map = new HashMap();
        SetuSession session=(SetuSession) getSetuSession();
        String userId = session.getUserId();
        ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
        applyRecordEntity.setSqrId(userId);
		applyRecordEntity.setXtZxbz("0");
		applyRecordEntity.queryBySelf();
        YoujixinxiEntity youjixinxiEntity = new YoujixinxiEntity();
        youjixinxiEntity.setUserId(applyRecordEntity.getId());
        try{
			youjixinxiEntity.queryBySelf();
		}catch (Exception e){
			map.put("code","201");
			return map;
		}
        map.put("code","200");
        map.put("data",youjixinxiEntity);
        return map;
    }

	/**
	 * 交易状态查询
	 * @return hxt
	 */
	@RequestMapping("/payAgain")
	@ResponseBody
	public Map payAgain(){
		Map map = new HashMap();
		SetuSession session=(SetuSession) getSetuSession();
		String userId = session.getUserId();
		ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
		Condition condition = new Condition();
		condition.add(new ConditionItem("xt_zxbz","=","0"));
		condition.add(new ConditionItem("sqr_id","=",userId));
		condition.add(new ConditionItem("spzt",">=","8"));
		condition.add(new ConditionItem("spzt","<=","9"));
		List<ApplyRecordEntity> list = applyRecordEntity.queryListByCondition(condition);
		if(list.size()>0){
			ApplyRecordEntity applyRecordEntity1 = new ApplyRecordEntity();
			applyRecordEntity1.setXtZxbz("1");
			applyRecordEntity1.addConditonItem(new ConditionItem("sqr_id","=",userId));
			int status = applyRecordEntity1.updateBySelfCondition();
			if(status>0){
				map.put("code","200");
				return map;
			}
		}
		map.put("code","201");
		return map;
	}

	/**
	 * 交易状态查询
	 * @return hxt
	 */
	@RequestMapping("/changPostStatus")
	@ResponseBody
	public Map changPostStatus(){
		Map map = new HashMap();
		SetuSession session=(SetuSession) getSetuSession();
		String userId = session.getUserId();
		ApplyRecordEntity applyRecordEntity1 = new ApplyRecordEntity();
		applyRecordEntity1.setXtZxbz("0");
		applyRecordEntity1.setSqrId(userId);
		try{
			applyRecordEntity1.queryBySelf();
		}catch (Exception e){
			map.put("code","202");
			return map;
		}
		if(!applyRecordEntity1.getSpzt().equalsIgnoreCase("7")){
			map.put("code","201");
			return map;
		}
		ApplyRecordEntity applyRecordEntity = new ApplyRecordEntity();
		applyRecordEntity.addConditonItem( new ConditionItem("xt_zxbz", "=", "0"));
		applyRecordEntity.addConditonItem("and", new ConditionItem("sqr_id", "=", userId));
		applyRecordEntity.setSpzt("8");
		int updateStatus = applyRecordEntity.updateBySelfCondition();
		YoujixinxiEntity youjixinxiEntity = new YoujixinxiEntity();
		youjixinxiEntity.setStatus("1");
		youjixinxiEntity.addConditonItem( new ConditionItem("user_id", "=", applyRecordEntity1.getId()));
		int youjiStatus = youjixinxiEntity.updateBySelfCondition();
		if(updateStatus == youjiStatus)
		map.put("code","200");
		return map;
	}
}
