package com.party.mobile.web.dto.crowdfund.output;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.party.core.model.crowdfund.SupportWithMember;

/**
 * 众筹支持输出视图
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 10:07
 */
public class SupportOutput {

    //支持编号
    private String id;

    //支持者名称
    private String favorerName;

    //支持者图像
    private String favorerLogo;

    //支持评论
    private String comment;

    //创建时间
    private Date createDate;

    //付款金额
    private Float payment;
    
    //回复列表
    private List<ProjectCommentOutput> replyList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavorerName() {
        return favorerName;
    }

    public void setFavorerName(String favorerName) {
        this.favorerName = favorerName;
    }

    public String getFavorerLogo() {
        return favorerLogo;
    }

    public void setFavorerLogo(String favorerLogo) {
        this.favorerLogo = favorerLogo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public List<ProjectCommentOutput> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ProjectCommentOutput> replyList) {
		this.replyList = replyList;
	}

	/**
     * 众筹支持转输出视图
     * @param supportWithMember 众筹支持
     * @return 支持输出视图
     */
    public static SupportOutput transform(SupportWithMember supportWithMember){
        SupportOutput supportOutput = new SupportOutput();
        BeanUtils.copyProperties(supportWithMember, supportOutput);
        return supportOutput;
    }
}
