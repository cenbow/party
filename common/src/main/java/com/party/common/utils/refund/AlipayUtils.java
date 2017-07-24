package com.party.common.utils.refund;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.party.common.utils.Base64;

/**
 * 支付宝支付工具类
 * 
 * @author wei.li
 * @version 2016/7/13 0013
 */
public class AlipayUtils {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	// 支付宝消息验证地址
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	private static final Logger logger = LoggerFactory.getLogger(AlipayUtils.class);

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, Object> params) {

		List<String> keys = new ArrayList<String>(params.keySet());

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				stringBuilder = stringBuilder.append(key).append("=").append("\"").append(params.get(key)).append("\"");
			} else {
				stringBuilder = stringBuilder.append(key).append("=").append("\"").append(params.get(key)).append("\"").append("&");
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String returnLinkString(Map<String, Object> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				stringBuilder = stringBuilder.append(key).append("=").append(params.get(key));
			} else {
				stringBuilder = stringBuilder.append(key).append("=").append(params.get(key)).append("&");
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * 待签名的json字符串
	 * 
	 * @param params
	 * @return
	 */
	public static Map<String, Object> returnWaitSignString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (String key : keys) {
			map.put(key, params.get(key));
		}
		return map;
	}

	/**
	 * 得到签名字符串
	 * 
	 * @param data
	 *            代签名实体
	 * @param privateKey
	 *            私钥
	 * @param inputCharset
	 *            编码
	 * @param <T>
	 * @return 签名后的字符串
	 */
	public static <T> String getSign(T data, String privateKey, String inputCharset, boolean payOrRefund, boolean isBase64) {
		Map<String, Object> params = Maps.newHashMap();
		if (data instanceof Map){
			params = (Map<String,Object>)data;
		} else{
			params = ObjectUtils.getFieldValues(data);
		}
		String content = null;
		if (payOrRefund) {
			content = createLinkString(params);
		} else {
			content = returnLinkString(params);
		}

		logger.info("待签名内容：" + content);

		try {
			byte [] decode = null;
			if (isBase64){
				decode = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey);
			} else {
				decode = Base64.decode(privateKey);
			}
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decode);
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(inputCharset));

			byte[] signed = signature.sign();

			if (isBase64){
				return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
			} else {
				return new String(URLEncoder.encode(Base64.encode(signed), inputCharset));
			}
		} catch (Exception e) {
			logger.info("支付宝签名异常", e);
		}

		return null;

	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param ali_public_key
	 *            支付宝公钥
	 * @param input_charset
	 *            编码格式
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset, boolean isBase64) {
		try {
			byte [] encodedKey = null;
			if (isBase64){
				encodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(ali_public_key);
			} else {
				encodedKey = Base64.decode(ali_public_key);
			}
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(input_charset));

			if (isBase64) {
				return signature.verify(org.apache.commons.codec.binary.Base64.decodeBase64(sign));
			} else {
				return signature.verify(Base64.decode(sign));
			}
		} catch (Exception e) {
			logger.info("支付宝签名验签异常", e);
		}

		return false;
	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notifyId
	 *            通知校验ID
	 * @param alipayPartner
	 *            支付宝支付合作者身份ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	public static String verifyResponse(String notifyId, String alipayPartner) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String veryfyUrl = HTTPS_VERIFY_URL + "partner=" + alipayPartner + "&notify_id=" + notifyId;
		return checkUrl(veryfyUrl);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String checkUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;
	}

}
