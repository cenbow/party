package com.party.mobile.web.dto.dynamic.output;

/**
 * 执行评论操作后的输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 14:08 16/11/8
 * @Modified by:
 */
public class HandleCommentOutput {
    //动态主键id
    private String id;

    //动态总评论数
    private Integer commentNum;

    //评论数据
    DyCommentOutput comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public DyCommentOutput getComment() {
        return comment;
    }

    public void setComment(DyCommentOutput comment) {
        this.comment = comment;
    }
}
