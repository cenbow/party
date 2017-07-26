package com.party.web.utils;

import com.google.common.collect.Maps;
import com.party.common.annotation.ListSuffixResult;
import com.party.common.reflect.ReflectUtils;
import com.party.common.utils.XMLBeanUtils;
import com.party.pay.model.pay.wechat.CouponRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 微信支付工具类
 *
 * @author wei.li
 * @version 2016/7/15 0015
 */
public class WechatPayUtils {

    protected static Logger logger = LoggerFactory.getLogger(WechatPayUtils.class);

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
}
