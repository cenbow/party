package com.party.web.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.party.common.annotation.ListSuffixResult;
import com.party.common.reflect.ReflectUtils;
import com.party.common.utils.EncryptUtil;
import com.party.common.utils.XMLBeanUtils;
import com.party.common.utils.refund.ObjectUtils;
import com.party.common.utils.wechat.MyX509TrustManager;
import com.party.pay.model.pay.wechat.CouponRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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
     * @param data 待签名实体
     * @param apiKey 接口密钥
     * @param <T>
     * @return 签名字符串
     */
    public static <T> String getSign(T data, String apiKey){
        Map<String, Object> fieldValues = Maps.newHashMap();
        if (data instanceof Map){
            fieldValues = (Map<String,Object>)data;
        } else{
            fieldValues = ObjectUtils.getFieldValues(data);
        }
        List<Map.Entry<String, Object>> fieldValueEntries = Lists.newArrayList(fieldValues.entrySet());

        //排序
        Ordering<Map.Entry<String, Object>> ordering = new Ordering<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> stringObjectEntry1, Map.Entry<String, Object> stringObjectEntry2) {
                return stringObjectEntry1.getKey().compareTo(stringObjectEntry2.getKey());
            }
        };
        Collections.sort(fieldValueEntries, ordering);

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : fieldValueEntries) {
            if (null != entry.getValue()){
                stringBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        stringBuilder.append("&key=").append(apiKey);
        String stringSignTemp = stringBuilder.substring(1);
        return EncryptUtil.MD5Hex(stringSignTemp).toUpperCase();
    }



    /**
     * 验证微信签名
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param token 签名令牌
     * @return 签名是否通过(true/false)
     */
    public static boolean checkSignature(String signature, String timestamp,
                                         String nonce, String token) {
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


    public static <T> T deserialize(String xml, Class<T> clazz){
        T t = xmlToBean(xml, clazz);

        //后缀为_$n 的 xml节点反序列化
        Field field = getSuffixField(clazz);
        if (null != field) {
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                //正则
                String rex = field.getAnnotation(ListSuffixResult.class).value();
                List<CouponRequest> list = getCouponRequest(xml);
                try {
                    field.setAccessible(true);
                    field.set(t, list);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }


    public static Field getSuffixField(Class<?> clazz){
        Set<Field> allFields = ReflectUtils.getAllField(clazz);
        ListSuffixResult listSuffixResult = null;
        for (Field field : allFields) {
            listSuffixResult = field.getAnnotation(ListSuffixResult.class);
            if (listSuffixResult != null) {
                return field;
            }
        }
        return null;
    }

    public static List<CouponRequest> getCouponRequest(String xml){
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", CouponRequest.class);  //修改类名
        xstream.registerConverter(new MapConverter());
        List<CouponRequest> list = (List<CouponRequest>) xstream.fromXML(xml);
        return list;
    }

    public static String get(){
        String a = "<xml>\n" +
                "  <appid><![CDATA[wx4a3cd04f189325db]]></appid>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <cash_fee><![CDATA[4]]></cash_fee>\n" +
                "  <coupon_count><![CDATA[1]]></coupon_count>\n" +
                "  <coupon_fee>96</coupon_fee>\n" +
                "  <coupon_fee_0><![CDATA[96]]></coupon_fee_0>\n" +
                "  <coupon_id_0><![CDATA[2000000000077522242]]></coupon_id_0>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[1371766102]]></mch_id>\n" +
                "  <nonce_str><![CDATA[NzjGkyece4kiz0WXqfTsbqpVW1KsqdVc]]></nonce_str>\n" +
                "  <openid><![CDATA[osScduDIicJJRfhmiEEr3_93OZw4]]></openid>\n" +
                "  <out_trade_no><![CDATA[7a1d6f3883cc4d33b5a6517b4331342f]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[D200431FEF46358D5BEAA39848E97C1D]]></sign>\n" +
                "  <time_end><![CDATA[20170525170605]]></time_end>\n" +
                "  <total_fee>100</total_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[4000442001201705252538511166]]></transaction_id>\n" +
                "</xml>";
        return a;
    }
}
