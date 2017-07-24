package com.party.common.utils.refund;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 对象工具类
 * 
 * @author wei.li
 * @version 2016/7/15 0015
 */
public class ObjectUtils {

	/**
	 * 把待签名实体转换成map
	 * 
	 * @param data
	 *            待签名实体
	 * @return 待签名map
	 */
	public static Map<String, Object> getFieldValues(Object data) {

		Map<String, Object> stringObjectMap = new LinkedHashMap<String, Object>();

		Field[] fields = FieldUtils.getAllFields(data.getClass());
		try {
			for (Field field : fields) {

				// 属性忽略,直接跳过
				Ignore ignore = field.getAnnotation(Ignore.class);
				if (ignore != null) {
					continue;
				}

				// 反射非静态字段
				if (!Modifier.isStatic(field.getModifiers())) {

					String fileName = field.getName();

					XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
					if (xStreamAlias != null) {
						fileName = xStreamAlias.value();
					}

					Object value = FieldUtils.readField(data, field.getName(), true);

					// 过滤空值属性
					if (value != null && StringUtils.isNotBlank(value.toString())) {
						if (!(value instanceof List)){
							stringObjectMap.put(fileName, value);
						} else if (value instanceof List && ((List) value).size() > 0){
							stringObjectMap.put(fileName, value);
						}
					}
				}
			}
		} catch (IllegalAccessException iae) {
		}
		return stringObjectMap;
	}

}
