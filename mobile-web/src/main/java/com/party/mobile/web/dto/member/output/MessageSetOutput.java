package com.party.mobile.web.dto.member.output;

import com.party.core.model.message.MessageSet;
import org.springframework.beans.BeanUtils;

/**
 * 消息设置输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 15:56 16/11/11
 * @Modified by:
 */
public class MessageSetOutput {
    //点赞开关
    private Integer loveTip;

    //评论开关
    private Integer commentTip;

    //关注开关
    private Integer focusTip;

    //系统通知开关
    private Integer sysTip;

    //活动开关
    private Integer actTip;

    //商品开关
    private Integer goodsTip;

    public Integer getLoveTip() {
        return loveTip;
    }

    public void setLoveTip(Integer loveTip) {
        this.loveTip = loveTip;
    }

    public Integer getCommentTip() {
        return commentTip;
    }

    public void setCommentTip(Integer commentTip) {
        this.commentTip = commentTip;
    }

    public Integer getFocusTip() {
        return focusTip;
    }

    public void setFocusTip(Integer focusTip) {
        this.focusTip = focusTip;
    }

    public Integer getSysTip() {
        return sysTip;
    }

    public void setSysTip(Integer sysTip) {
        this.sysTip = sysTip;
    }

    public Integer getActTip() {
        return actTip;
    }

    public void setActTip(Integer actTip) {
        this.actTip = actTip;
    }

    public Integer getGoodsTip() {
        return goodsTip;
    }

    public void setGoodsTip(Integer goodsTip) {
        this.goodsTip = goodsTip;
    }

    /**
     * 消息设置转输出视图
     * @param messageSet 会员
     * @return 交互数据
     */
    public static MessageSetOutput transform(MessageSet messageSet){
        MessageSetOutput messageSetOutput = new MessageSetOutput();
        BeanUtils.copyProperties(messageSet, messageSetOutput);
        return messageSetOutput;
    }
}
