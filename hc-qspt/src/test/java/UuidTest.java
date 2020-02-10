
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import org.setu.framework.utils.encrypt.Md5Util;


class SignDamo {
    public static void main(String[] args) throws Exception {
        //特约商户key
        String key = "23a247d596c24512aeeba7432ab94c66";

        //1，获取请求报文主体
        Map<String, String> map1 = getMap();

        //2.获取请求报文中集合类型参数 JSON格式
        String jsonmap2 = getMap2ToJSON();

        //4.除去数组中的空值和签名参数
        Map<String, String> map3 = paraFilter(map1);

        //5.把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串 + key
        String str = createLinkString(map3)+key;
        System.out.println("【MD5】加密前原始串：↓");
        System.out.println(str);

        //6.生成签名串MD5
        String sign = Md5Util.encode(str);
//                Md5Utils.MD5Encode(str);//16进制  UTF-8  小写
        System.out.println("【MD5】加密后sgin:↓");
        System.out.println(sign);

        //7.请求报文加上 sign和sign_type
        map3.put("sign", sign);
        map3.put("sign_type", "MD5");

        //8.生成请求报文
        String jsonString = JSONArray.toJSONString(map3);
        System.out.println("请求报文：↓");
        System.out.println(jsonString);
    }

    private static Map<String, String> getMap() {
        Map<String,String> map2 = new HashMap<String,String>();
        map2.put("service", "refund_service");
        map2.put("partner", "1202836343");
        map2.put("subpartner", "1202839111");
        map2.put("out_trade_no", "OjpQhtz");
        map2.put("out_refund_no", "OjpQhtz");
        map2.put("total_fee", "23");
        map2.put("refund_fee", "23");
        map2.put("refund_reason", "测试");
        return map2;
    }



    private static String getMap2ToJSON() {
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("buyer_id", "2088012237915062");
        map1.put("auth_code", "135030195867520541");
        map1.put("openid", "oZnrsjqJOimY_Z9FHV_6WzhYAnpc");
        map1.put("store_id", "1323728");
        map1.put("bankcode", "496");
        map1.put("qr_return_type", "1");
        String jsonmap1 = JSONArray.toJSONString(map1);
        return jsonmap1;
    }


    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

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


}
