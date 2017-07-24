package com.party.common.constant;

/**
 * 微信常量类
 * party
 * Created by wei.li
 * on 2016/9/24 0024.
 */
public class WechatConstant {

    //微信支付成功
    public static final String SUCCESS = "SUCCESS";

    //交易类型
    public static final String TRADE_TYPE = "JSAPI";
    public static final String TRADE_TYPE_NATIVE = "NATIVE"; // 原生扫码支付

    //应用授权作用域
    public static final String SCOPE_USERINFO = "snsapi_userinfo"; // 需要用户同意
    public static final String SCOPE_BASE = "snsapi_base"; // 静默

    //随机数长度
    public static final int RANDOM_LENGTH = 32;

    //预支付交易会话标识
    public static final String PREPAY_ID = "prepay_id=";

    //MD5 编码
    public static final String MD5_TYPE = "MD5";

    //网页授权交互凭证
    public static final String ACCESS_TOKEN = "accessToken";




    //微信授权请求地址
    public static final String OATHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    //通过code换取网页授权access_token url
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
            "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    //通过code换取网页授权access_token url
    public static final String XCX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=CODE&grant_type=authorization_code";

    //拉取用户信息
    public static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?" +
            "access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    //微信统一下单url
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //微信js api 交互ticket
    public static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public static final String LONG_TO_SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";

    //微信模板消息发送url
    public static String POST_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //微信扫码原生支付模式一中的二维码链接转成短链接
    public static String QR_LONG_TO_SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
    //微信扫码原生支付模式一中的二维码中的内容为链接
    public static String QR_PAY_CONTENT_URL = "weixin://wxpay/bizpayurl?sign=SIGN&appid=APPID&mch_id=MCH_ID&product_id=PRODUCT_ID&time_stamp=TIME_STAMP&nonce_str=NONCE_STR";
}
