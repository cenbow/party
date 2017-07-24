
package com.party.core.model.qcloud;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 万象优图签名
 * @author Juliana
 *
 */
public class PicCloudSign extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = -9189492127438725378L;
	private String sign;
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}