package com.party.mobile.web.dto.activity.output;

import java.util.Date;

import com.party.core.model.member.WithBuyer;

import org.springframework.beans.BeanUtils;

/**
 * 买家输出视图
 * Created by wei.li
 *
 * @date 2017/3/30 0030
 * @time 11:19
 */
public class BuyerOutput {

    //购买者编号
    private String memberId;

    //头像
    private String logo;

    //姓名
    private String realname;

    //创建时间
    private Date createDate; 

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public String getLogo() {
        return logo;
    }

    public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public static BuyerOutput transform(WithBuyer withBuyer){
        BuyerOutput buyerOutput = new BuyerOutput();
        BeanUtils.copyProperties(withBuyer, buyerOutput);
        return buyerOutput;
    }
}
