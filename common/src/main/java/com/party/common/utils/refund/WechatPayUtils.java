package com.party.common.utils.refund;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.party.common.utils.EncryptUtil;
import com.party.common.utils.XMLBeanUtils;
import com.party.common.utils.wechat.MyX509TrustManager;

/**
 * 微信支付工具类
 * 
 * @author wei.li
 * @version 2016/7/15 0015
 */
public class WechatPayUtils {

	protected static Logger logger = LoggerFactory.getLogger(WechatPayUtils.class);

	/**
	 * 微信签名工具类
	 * 
	 * @param data
	 *            待签名实体
	 * @param apiKey
	 *            接口密钥
	 * @param <T>
	 * @return 签名字符串
	 */
	public static <T> String getSign(T data, String apiKey) {

		Map<String, Object> fieldValues = ObjectUtils.getFieldValues(data);
		List<Map.Entry<String, Object>> fieldValueEntries = Lists.newArrayList(fieldValues.entrySet());

		// 排序
		Ordering<Map.Entry<String, Object>> ordering = new Ordering<Map.Entry<String, Object>>() {
			@Override
			public int compare(Map.Entry<String, Object> stringObjectEntry1, Map.Entry<String, Object> stringObjectEntry2) {
				return stringObjectEntry1.getKey().compareTo(stringObjectEntry2.getKey());
			}
		};
		Collections.sort(fieldValueEntries, ordering);

		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : fieldValueEntries) {
			stringBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}

		stringBuilder.append("&key=").append(apiKey);
		String stringSignTemp = stringBuilder.substring(1);
		return EncryptUtil.MD5Hex(stringSignTemp).toUpperCase();
	}

	/**
	 * 验证微信签名
	 * 
	 * @param signature
	 *            微信加密签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param token
	 *            签名令牌
	 * @return 签名是否通过(true/false)
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 *
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	public static String httpsPost(String requestUrl, String dataSend) {

		StringBuilder buffer = new StringBuilder();

		try {
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("POST");

			// 当有数据需要提交时
			if (null != dataSend) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(dataSend.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			// 释放资源
			inputStream.close();
			httpUrlConn.disconnect();

		} catch (Exception e) {
			logger.error("https request error", e);
		}

		return buffer.toString();
	}

	public static String httpsPost(String requestUrl, String dataSend, String mchId, String certPath) {
		StringBuilder buffer = new StringBuilder();
		try {
			// 指定读取证书格式为PKCS12
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			// 读取本机存放的PKCS12证书文件
			FileInputStream instream = new FileInputStream(new File(certPath));
			try {
				// 指定PKCS12的密码(商户ID)
				keyStore.load(instream, mchId.toCharArray());
			} finally {
				instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
			// 指定TLS版本
			@SuppressWarnings("deprecation")
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			// 设置httpclient的SSLSocketFactory
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

			try {
				HttpPost httpPost = new HttpPost(requestUrl);// 退款接口
				StringEntity reqEntity = new StringEntity(dataSend);
				// 设置类型
				reqEntity.setContentType("application/x-www-form-urlencoded");
				httpPost.setEntity(reqEntity);
				CloseableHttpResponse response = httpclient.execute(httpPost);
				try {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
						String str = null;
						while ((str = bufferedReader.readLine()) != null) {
							buffer.append(str);
						}
						bufferedReader.close();
					}
					EntityUtils.consume(entity);
				} finally {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 微信Bean转xml报文
	 */
	public static <T> String beanToXml(T bean) {
		Map<String, Class> alias = getRootAlias(bean.getClass());

		return XMLBeanUtils.bean2xml(alias, bean);
	}

	/**
	 * 微信xml报文转Bean
	 */
	public static <T> T xmlToBean(String xml, Class<T> clazz) {
		Map<String, Class> alias = getRootAlias(clazz);

		Object result = XMLBeanUtils.xml2Bean(alias, xml);
		if (result.getClass().isAssignableFrom(clazz)) {
			return (T) result;
		}
		return null;
	}

	private static Map<String, Class> getRootAlias(Class clazz) {
		Map<String, Class> stringClassMap = Maps.newHashMap();
		stringClassMap.put("xml", clazz);
		return stringClassMap;
	}
}
