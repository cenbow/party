package com.party.mobile.web.controller.qcloud;

import com.party.common.qcloud.cosapi.sign.Sign;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 腾讯云对象存储获,取签名前端接口
 * Created by Wesley.
 * Version 2016/7/20 0020.
 */
@Controller
@RequestMapping(value = "/cos")
public class CosController {
    public static final int APP_ID = 10052192;
    public static final String SECRET_ID = "AKID0zLb467fRifqb0rsyo5tVJKZqKiebe5B";
    public static final String SECRET_KEY = "UcfDJpkyGi90IDw0YsnxxUcLkWopbrC6";
    public static final String remotePath = "/userfiles/undefine/";
//    public static final long expired = System.currentTimeMillis() / 1000 + 60;
    public static final String bucketName = "tongxingzhe";
    public static final String signType1 = "appSign";
    public static final String signType2 = "appSign_once";

    /**
     * @param sign_type, 签名方式，cos js sdk传值，不为null
     * @param expired，   多次签名使用时长，cos js sdk传值，不为null；如果是单次签名，则为null
     * @param path， 为单次签名所绑定的路径名+文件名，cos js sdk传值，不为null；如果是单次签名，则为null
     * @param bucketName，cos端人工建立的bucket
     * @return 签名，标准json格式字符串
     */
    @RequestMapping(value = "getAppSign")
    @ResponseBody
    public String getAppSign(String sign_type, Long expired, String path, String bucketName)
    {
        if(signType1.equals(sign_type))
        {
            return getAppSignature(expired, bucketName);
        }
        else if(StringUtils.isBlank(path))
        {
            return getAppSignatureNullParam(null);
        }
        return getAppSignatureOnce(path, bucketName);
    }
    /**
     *
     * @param expiredTime，当前系统时间
     * @param bucket，cos创建的bucket name
     * @return 签名，标准json格式
     */
    @RequestMapping(value = "getAppSignature")
    @ResponseBody
    public String getAppSignature(long expiredTime, String bucket) {
        if(StringUtils.isBlank(bucket)) //前端传入的bucket为空字符串，则设置为默认的cos bucket
        {
            bucket = bucketName;
        }

        JSONObject json = new JSONObject();
        String sign = null;
        try {
            sign = Sign.appSignature(APP_ID, SECRET_ID, SECRET_KEY, expiredTime, bucket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != sign)
        {
            HashMap<String, String> updateData = new HashMap<>();
            updateData.put("sign",sign);
            json.put("message","sign success");
            json.put("code",-1);
            json.put("data",updateData);
        }
        else
        {
            json.put("success","sign fail");
            json.put("code",100);

        }
        return json.toString();
    }

    /**
     *
     * @param remoteFilePath，要绑定签名的cos端的目录及当前文件名
     * @param bucket，cos创建的bucket name
     * @return 签名，标准json格式
     */
    @RequestMapping(value = "getAppSignatureOnce")
    @ResponseBody
    public String getAppSignatureOnce(String remoteFilePath, String bucket) {
        if(StringUtils.isBlank(remoteFilePath))
        {
            remoteFilePath = remotePath;
        }
        if(StringUtils.isBlank(bucket)) //前端传入的bucket为空字符串，则设置为默认的cos bucket
        {
            bucket = bucketName;
        }

        JSONObject json = new JSONObject();
        String sign = null;
        try {
            sign = Sign.appSignatureOnce(APP_ID, SECRET_ID, SECRET_KEY, remoteFilePath, bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != sign)
        {
            HashMap<String, String> updateData = new HashMap<>();
            updateData.put("sign",sign);
            json.put("message","sign success");
            json.put("code",-1);
            json.put("data",updateData);
        }
        else
        {
            json.put("success","sign fail");
            json.put("code",100);

        }
        return json.toString();
    }


    @RequestMapping(value = "getAppSignatureNullParam")
    @ResponseBody
    public String getAppSignatureNullParam(String remoteFilePath) {
        if(StringUtils.isBlank(remoteFilePath))
        {
            remoteFilePath = remotePath;
        }
        JSONObject json = new JSONObject();
        String sign = null;
        try {
            sign = Sign.appSignatureOnce(APP_ID, null, null, remoteFilePath, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != sign)
        {
            HashMap<String, String> updateData = new HashMap<>();
            updateData.put("sign",sign);
//			json.setSuccess(true);
//			json.put("data", sign);
//			json.setMsg("获取成功");
            json.put("message","sign success");
            json.put("code",-1);
            json.put("data",updateData);
        }
        else
        {
//			json.setSuccess(false);
//			json.setMsg("操作错误");
            json.put("success","sign fail");
            json.put("code",100);

        }
        return json.toString();
    }
}
