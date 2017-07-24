package com.party.pay.model.refund;

import com.party.common.annotation.Ignore;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付宝退款公共请求参数
 * 
 * @author Wesley
 * @version 2016/11/15 0013
 */
public class AliPayRefundRequest {

	// 支付宝分配给开发者的应用ID
	@XStreamAlias("app_id")
	private String appid;

	// 接口名称
	private String method;

	// 请求使用的编码格式
	private String charset = "UTF-8";

	// 商户生成签名字符串所使用的签名算法类型
	@XStreamAlias("sign_type")
	private String signType;

	// 商户请求参数的签名串
	@Ignore
	private String sign;

	// 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
	private String timestamp;

	// 调用的接口版本，固定为：1.0
	private String version = "1.0";

	// 请求参数的集合
	private String biz_content;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBiz_content() {
		return biz_content;
	}

	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}

}
