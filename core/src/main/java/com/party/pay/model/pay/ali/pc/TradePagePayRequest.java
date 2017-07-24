package com.party.pay.model.pay.ali.pc;

import java.util.Map;

/**
 * PC场景下单并支付 请求参数
 */
public class TradePagePayRequest {

    private Map<String, String> appParams;
    private Map<String, String> protocalMustParams;
    private Map<String, String> protocalOptParams;

    /**
     * sha256WithRsa 算法请求类型
     */
    public static final String SIGN_TYPE_RSA = "RSA";

    public static final String SIGN_TYPE_RSA2 = "RSA2";

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    // 支付宝分配给开发者的应用ID
    public static final String APP_ID = "app_id";

    public static final String FORMAT = "format";

    // 接口名称
    public static final String METHOD = "method";

    // 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
    public static final String TIMESTAMP = "timestamp";

    // 调用的接口版本，固定为：1.0
    public static final String VERSION = "version";

    // 商户请求参数的签名串
    public static final String SIGN = "sign";

    // 商户生成签名字符串所使用的签名算法类型
    public static final String SIGN_TYPE = "sign_type";

    // 请求使用的编码格式
    public static final String CHARSET = "charset";

    // 支付宝服务器主动通知商户服务器里指定的页面http/https路径。
    public static final String NOTIFY_URL = "notify_url";

    // 同步返回地址，HTTP/HTTPS开头字符串
    public static final String RETURN_URL = "return_url";

    // 请求参数的集合
    public static final String BIZ_CONTENT_KEY = "biz_content";

    public Map<String, String> getAppParams() {
        return appParams;
    }

    public void setAppParams(Map<String, String> appParams) {
        this.appParams = appParams;
    }

    public Map<String, String> getProtocalMustParams() {
        return protocalMustParams;
    }

    public void setProtocalMustParams(Map<String, String> protocalMustParams) {
        this.protocalMustParams = protocalMustParams;
    }

    public Map<String, String> getProtocalOptParams() {
        return protocalOptParams;
    }

    public void setProtocalOptParams(Map<String, String> protocalOptParams) {
        this.protocalOptParams = protocalOptParams;
    }
}
