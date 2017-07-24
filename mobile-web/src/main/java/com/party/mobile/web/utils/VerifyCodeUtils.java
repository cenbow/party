package com.party.mobile.web.utils;

import com.party.common.utils.DateUtils;
import com.party.common.utils.DesUtil;
import com.party.core.exception.BusinessException;

import java.util.Random;

/**
 * 核销码工具类
 * @author wei.li
 * @version 2016/7/14 0014
 */
public class VerifyCodeUtils {


    private static final Integer VERIFY_CODE_LENGTH = 8;


    /**
     * 获取核销码
     *
     * @param orderFormId
     * @return
     */
    public static String buildVerifyCode(String orderFormId){
        //生成随机数
        String random = RandomString(VERIFY_CODE_LENGTH);

        //订单号加当前日期作为加密参数
        String keyset = orderFormId + DateUtils.getDays();

        String value;
        try {
            value= DesUtil.encrypt(random, keyset);
        } catch (Exception e) {
            throw new BusinessException("生成核销码异常");
        }

        String result = value.substring(0,VERIFY_CODE_LENGTH);
        return result;
    }


    /**
     * s生成随机数
     * @param length 长度
     * @return 随机数
     */
    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        String value = VerifyCodeUtils.buildVerifyCode("123");
        System.out.println(value);
    }
}
