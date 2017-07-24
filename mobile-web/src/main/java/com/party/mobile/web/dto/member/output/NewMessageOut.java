package com.party.mobile.web.dto.member.output;

/**
 * 最细会员消息输出视图
 * party
 * Created by Wesley
 * on 2016/11/6
 */
public class NewMessageOut {
    private int loveMsgNum;//最新未读点赞消息个数
    private int commentMsgNum;//最新未读评论消息个数
    private int focusMsgNum;//最新未读关注消息个数
    private int sysMsgNum;//最新未读系统消息个数
    private int actMsgNum;//最新未读活动消息个数
    private int goodsMsgNum;//最新未读商品消息个数
    private MessageOut sysMsg;//最新一条未读系统消息
    private MessageOut actMsg;//最新一条未读活动消息
    private MessageOut goodsMsg;//最新一条未读商品消息

    public int getLoveMsgNum() {
        return loveMsgNum;
    }

    public void setLoveMsgNum(int loveMsgNum) {
        this.loveMsgNum = loveMsgNum;
    }

    public int getCommentMsgNum() {
        return commentMsgNum;
    }

    public void setCommentMsgNum(int commentMsgNum) {
        this.commentMsgNum = commentMsgNum;
    }

    public int getFocusMsgNum() {
        return focusMsgNum;
    }

    public void setFocusMsgNum(int focusMsgNum) {
        this.focusMsgNum = focusMsgNum;
    }

    public int getSysMsgNum() {
        return sysMsgNum;
    }

    public void setSysMsgNum(int sysMsgNum) {
        this.sysMsgNum = sysMsgNum;
    }

    public int getActMsgNum() {
        return actMsgNum;
    }

    public void setActMsgNum(int actMsgNum) {
        this.actMsgNum = actMsgNum;
    }

    public int getGoodsMsgNum() {
        return goodsMsgNum;
    }

    public void setGoodsMsgNum(int goodsMsgNum) {
        this.goodsMsgNum = goodsMsgNum;
    }

    public MessageOut getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(MessageOut sysMsg) {
        this.sysMsg = sysMsg;
    }

    public MessageOut getActMsg() {
        return actMsg;
    }

    public void setActMsg(MessageOut actMsg) {
        this.actMsg = actMsg;
    }

    public MessageOut getGoodsMsg() {
        return goodsMsg;
    }

    public void setGoodsMsg(MessageOut goodsMsg) {
        this.goodsMsg = goodsMsg;
    }
}