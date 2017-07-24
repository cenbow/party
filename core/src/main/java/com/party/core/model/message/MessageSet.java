package com.party.core.model.message;

import com.party.core.model.BaseModel;
import java.io.Serializable;

/**
 * 用户消息设置实体
 * party
 * Created by wei.li
 * on 2016/8/11 0011.
 */
public class MessageSet extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1242763703630768846L;
    //会员主键
    private String memberId;

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


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MessageSet that = (MessageSet) o;

        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        if (loveTip != null ? !loveTip.equals(that.loveTip) : that.loveTip != null) return false;
        if (commentTip != null ? !commentTip.equals(that.commentTip) : that.commentTip != null) return false;
        if (focusTip != null ? !focusTip.equals(that.focusTip) : that.focusTip != null) return false;
        if (sysTip != null ? !sysTip.equals(that.sysTip) : that.sysTip != null) return false;
        if (actTip != null ? !actTip.equals(that.actTip) : that.actTip != null) return false;
        return goodsTip != null ? goodsTip.equals(that.goodsTip) : that.goodsTip == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (loveTip != null ? loveTip.hashCode() : 0);
        result = 31 * result + (commentTip != null ? commentTip.hashCode() : 0);
        result = 31 * result + (focusTip != null ? focusTip.hashCode() : 0);
        result = 31 * result + (sysTip != null ? sysTip.hashCode() : 0);
        result = 31 * result + (actTip != null ? actTip.hashCode() : 0);
        result = 31 * result + (goodsTip != null ? goodsTip.hashCode() : 0);
        return result;
    }


    @Override
    public java.lang.String toString() {
        return "MessageSet{" +
                "memberId=" + memberId +
                ", loveTip=" + loveTip +
                ", commentTip=" + commentTip +
                ", focusTip=" + focusTip +
                ", sysTip=" + sysTip +
                ", actTip=" + actTip +
                ", goodsTip=" + goodsTip +
                '}';
    }
}
