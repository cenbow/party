package com.party.common.utils.refund;

import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		Map<String, Object> fieldValues = Maps.newHashMap();
        if (data instanceof Map){
            fieldValues = (Map<String,Object>)data;
        } else{
            fieldValues = ObjectUtils.getFieldValues(data);
        }

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

	/**
	 * 获取微信通知数据
	 *
	 * @param request 请求参数
	 * @return 通知数据
	 */
	public static String getNotifyXml(HttpServletRequest request) {

		StringBuilder requestStr = new StringBuilder();

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				requestStr.append(line);
			}
		} catch (IOException e) {
			logger.error("支付结果通知异常", e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
				}
			}
		}

		return requestStr.toString();
	}

	/**
	 * Map转xml
	 * @param map
	 * @return
	 */
	/**
	 * 将Map转换为XML格式的字符串
	 *
	 * @param data Map类型数据
	 * @return XML格式的字符串
	 * @throws Exception
	 */
	public static String mapToXml(Map<String, String> data) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		org.w3c.dom.Document document = documentBuilder.newDocument();
		org.w3c.dom.Element root = document.createElement("xml");
		document.appendChild(root);
		for (String key : data.keySet()) {
			String value = data.get(key);
			if (value == null) {
				value = "";
			}
			value = value.trim();
			org.w3c.dom.Element filed = document.createElement(key);
			filed.appendChild(document.createTextNode(value));
			root.appendChild(filed);
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(document);
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);
		String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
		try {
			writer.close();
		} catch (Exception ex) {
		}
		return output;
	}

	/**
	 * XML格式字符串转换为Map
	 *
	 * @param strXML XML字符串
	 * @return XML数据转换后的Map
	 * @throws Exception
	 */
	public static Map<String, String> xmlToMap(String strXML) throws Exception {
		try {
			Map<String, String> data = new HashMap<String, String>();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
			org.w3c.dom.Document doc = documentBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int idx = 0; idx < nodeList.getLength(); ++idx) {
				Node node = nodeList.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					org.w3c.dom.Element element = (org.w3c.dom.Element) node;
					data.put(element.getNodeName(), element.getTextContent());
				}
			}
			try {
				stream.close();
			} catch (Exception ex) {
				// do nothing
			}
			return data;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
     * 截取字符串
     *
     * @param sourceTitle 字符串
     * @param maxNum      最大长度
     * @return
     */
    public static String subTitle(String sourceTitle, int maxNum) {
        try {
            String newTitle = new String(sourceTitle.getBytes("UTF-8"), "UTF-8");
            byte[] charset_bytes = newTitle.getBytes("UTF-8");
            logger.info("newTitle:" + newTitle);
            int length = charset_bytes.length;
            logger.info("newTitle byte length:" + length);

            if (length > maxNum) {
                newTitle = newTitle.substring(0, newTitle.length() - 1);
                newTitle = catString(newTitle, maxNum);
            }
            return newTitle;
        } catch (Exception e) {
            logger.error("截取字符串异常：{}", e);
        }
        return sourceTitle;
    }

    public static String catString(String sourceTitle, int maxNum) throws UnsupportedEncodingException {
        int length = sourceTitle.getBytes("UTF-8").length;

        if (length > maxNum) {
            sourceTitle = sourceTitle.substring(0, sourceTitle.length() - 1);
            sourceTitle = catString(sourceTitle, maxNum);
        }
        return sourceTitle;
    }
}
