package com.party.mobile.web.dto.appWechatPay.input;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * app微信支付响应参数
 * @author Wesley
 * @version 2016/11/15 0015
 */
public class AppNotifyResponse {

    //返回状态码
    @XStreamAlias("return_code")
    private String returnCode;

    //返回信息
    @XStreamAlias("return_msg")
    private String returnMsg;


    public AppNotifyResponse(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public static AppNotifyResponse error(String message){
        return new AppNotifyResponse("FAIL",message);
    }

    public static AppNotifyResponse success(){
        return new AppNotifyResponse("SUCCESS","OK");
    }
}
