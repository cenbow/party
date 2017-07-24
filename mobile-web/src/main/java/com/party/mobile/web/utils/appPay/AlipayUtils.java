package com.party.mobile.web.utils.appPay;


import com.party.mobile.web.dto.appAlipay.output.AppPayRequest;
import com.party.mobile.web.utils.Base64;
import com.party.mobile.web.utils.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 支付宝支付工具类
 * @author wei.li
 * @version 2016/7/13 0013
 */
public class AlipayUtils {




    public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";

    //支付宝消息验证地址
    private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";





    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                stringBuilder = stringBuilder.append(key).append("=").append("\"").append(params.get(key)).append("\"");
            } else {
                stringBuilder = stringBuilder.append(key).append("=").append("\"").append(params.get(key)).append("\"").append("&");
            }
        }

        return stringBuilder.toString();
    }


    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String returnLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                stringBuilder = stringBuilder.append(key).append("=").append(params.get(key));
            } else {
                stringBuilder = stringBuilder.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 获取支付宝请求参数
     * @param payRequest 请求参数实体
     * @return 请求参数字符串
     */
    public static  String getRequestStr(AppPayRequest payRequest){
        Map<String, Object> params = ObjectUtils.getFieldValues(payRequest);
        String content = createLinkString(params);
        //拼接不参数签名的字段
        content = content+"&sign="+ "\""+payRequest.getSign()+ "\""+"&sign_type="+ "\""+payRequest.getSignType()+ "\"";
        return content;
    }


    /**
     * 得到签名字符串
     * @param data 代签名实体
     * @param privateKey 私钥
     * @param inputCharset 编码
     * @param <T>
     * @return 签名后的字符串
     */
    public static <T> String getSign(T data, String privateKey, String inputCharset){
        Map<String, Object> params = ObjectUtils.getFieldValues(data);
        String content = createLinkString(params);

        try
        {
            PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) );
            KeyFactory keyf 				= KeyFactory.getInstance("RSA");
            PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(inputCharset) );

            byte[] signed = signature.sign();

            String sign = Base64.encode(signed);
            sign = URLEncoder.encode(sign,"utf-8");
            return sign;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * RSA验签名检查
     * @param content 待签名数据
     * @param sign 签名值
     * @param ali_public_key 支付宝公钥
     * @param input_charset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
    {
        try
        {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(ali_public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update( content.getBytes(input_charset) );

            boolean bverify = signature.verify( Base64.decode(sign) );
            return bverify;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 获取远程服务器ATN结果,验证返回URL
     * @param notifyId 通知校验ID
     * @param alipayPartner 支付宝支付合作者身份ID
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    public static String verifyResponse(String notifyId, String alipayPartner) {
        //获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
        String veryfyUrl = HTTPS_VERIFY_URL + "partner=" + alipayPartner + "&notify_id=" + notifyId;
        return checkUrl(veryfyUrl);
    }


    /**
     * 获取远程服务器ATN结果
     * @param urlvalue 指定URL路径地址
     * @return 服务器ATN结果
     * 验证结果集：
     * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
     * true 返回正确信息
     * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";

        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
                    .getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }

        return inputLine;
    }

}
