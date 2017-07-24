package com.party.mobile.web.utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.party.common.reflect.ReflectUtils;
import com.party.pay.model.pay.wechat.CouponRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: wei.li
 * Date: 2017/5/26
 * Time: 23:05
 */
public class MapConverter implements Converter {

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        CouponRequest couponRequest = (CouponRequest) source;
        writer.startNode("name");
        writer.setValue(couponRequest.getCouponId());
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

        Field[] fields = FieldUtils.getAllFields(CouponRequest.class);
        Set<String> suffixIndex = Sets.newHashSet();
        Map<String, String> map = Maps.newHashMap();
        List<CouponRequest> list = Lists.newArrayList();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            Pattern p = Pattern.compile("_\\d*$");
            Matcher m = p.matcher(reader.getNodeName());
            boolean result = m.find();
            if (result){
                String value = getSuffix(reader.getNodeName());
                suffixIndex.add(value);
                map.put(reader.getNodeName(), reader.getValue());
            }
            reader.moveUp();
        }

        for (String index : suffixIndex){
            CouponRequest couponRequest = new CouponRequest();
            for (Field field : fields){
                String key = ReflectUtils.getKey(field);
                for (Map.Entry<String, String> entry : map.entrySet()){
                    if ((key + "_" + index).equals(entry.getKey())){
                        try {
                            field.setAccessible(true);
                            field.set(couponRequest, entry.getValue());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            list.add(couponRequest);
        }
        return list;
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(CouponRequest.class);
    }


    public String getSuffix(String value){
        Pattern p = Pattern.compile("\\d*$");
        Matcher m = p.matcher(value);
        boolean result = m.find();
        if (result){
            return m.group();
        }
        return null;
    }

}
