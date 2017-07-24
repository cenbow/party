package com.party.mobile.web.utils;
import com.party.mobile.web.dto.wechat.input.JsConfigSign;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;


/**
 * 微信js 接口 工具类
 * party
 * Created by wei.li
 * on 2016/10/13 0013.
 */
public class WechatJsUtils {

    public static String sign(JsConfigSign jsConfigSign) {
        Map<String, Object> map = ObjectUtils.getFieldValues(jsConfigSign);
        String content;
        String signature = "";


        //注意这里参数名必须全部小写，且必须有序
        content = "jsapi_ticket=" + map.get("jsapi_ticket") +
                "&noncestr=" + map.get("noncestr") +
                "&timestamp=" + map.get("timestamp") +
                "&url=" + map.get("url");
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(content.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return signature;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


}
