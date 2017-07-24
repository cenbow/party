package com.party.admin.utils;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;


/**
 * 通讯录工具类
 * @author Juliana
 * @version 2017/03/17
 */
public class ContactUtils {

    /**
     * 格式化手机号码字符串
     *
     * @param orderFormId
     * @return
     */
    public static List<Map> formatePhones(String phones){
    	List<Map> responseData = JSONArray.parseArray(phones, Map.class);
    	return responseData;
    }



    public static void main(String[] args) {
        List<Map> phones = ContactUtils.formatePhones("[{\"工作\":\"+8675588295188\"},{\"工作传真\":\"075526718410\"}]");
        System.out.println(phones);
    }
}
