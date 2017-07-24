package com.party.mobile.web.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.annotation.Ignore;
import com.party.common.annotation.ListSuffixResult;
import com.party.common.reflect.ReflectUtils;
import com.party.pay.model.pay.wechat.CouponRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 对象工具类
 * @author wei.li
 * @version 2016/7/15 0015
 */
public class ObjectUtils {


    /**
     * 把待签名实体转换成map
     * @param data 待签名实体
     * @return 待签名map
     */
    public static Map<String, Object> getFieldValues(Object data){

        Map<String, Object> stringObjectMap = Maps.newHashMap();

        Field[] fields = FieldUtils.getAllFields(data.getClass());
        try {
            for (Field field : fields) {

                //属性忽略,直接跳过
                Ignore ignore = field.getAnnotation(Ignore.class);
                if (ignore != null) {
                    continue;
                }

                //反射非静态字段
                if (!Modifier.isStatic(field.getModifiers())) {
                    String fileName = ReflectUtils.getKey(field);

                    //处理后缀的属性
                    ListSuffixResult listSuffixResult = field.getAnnotation(ListSuffixResult.class);
                    if (null != listSuffixResult){
                        List<CouponRequest> couponRequestList = (List<CouponRequest>)FieldUtils.readField(data, field.getName(), true);
                        for (int i=0; i<couponRequestList.size(); i++){
                            CouponRequest couponRequest = couponRequestList.get(i);
                            Set<Field> allFields = ReflectUtils.getAllField(CouponRequest.class);
                            for (Field cf : allFields) {
                                String cName = ReflectUtils.getKey(cf);
                                Object cvalue = FieldUtils.readField(couponRequest, cf.getName(), true);
                                if (null != cvalue && !Strings.isNullOrEmpty(cvalue.toString())){
                                    stringObjectMap.put(cName+"_"+i, cvalue);
                                }
                            }
                        }
                    }
                    else {
                        Object value = FieldUtils.readField(data, field.getName(), true);
                        //过滤空值属性
                        if (value != null
                                && StringUtils.isNotBlank(value.toString())) {
                            stringObjectMap.put(fileName, value);
                        }
                    }
                }
            }
        }
        catch (IllegalAccessException iae) {
        }
        return stringObjectMap;
    }

}
