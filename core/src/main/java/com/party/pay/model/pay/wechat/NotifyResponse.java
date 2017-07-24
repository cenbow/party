package com.party.pay.model.pay.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信支付响应参数
 * @author wei.li
 * @version 2016/7/15 0015
 */
public class NotifyResponse {

    //返回状态码
    @XStreamAlias("return_code")
    private String returnCode;

    //返回信息
    @XStreamAlias("return_msg")
    private String returnMsg;


    public NotifyResponse(String returnCode, String returnMsg) {
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

    public static NotifyResponse error(String message){
        return new NotifyResponse("FAIL",message);
    }

    public static NotifyResponse success(){
        return new NotifyResponse("SUCCESS","OK");
    }
}
