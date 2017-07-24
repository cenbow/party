package com.party.core.model.dynamic;

import com.party.core.model.BaseModel;

/**
 * 动态评论实体
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public class Comment extends BaseModel {

    private static final long serialVersionUID = 6362421553022845974L;

    //评论内容
    private String content;

    //关联编号
    private String refId;

    //评论类型（1：社区动态；2：圈子动态；）
    private String commentType;

    //排序
    private Integer sort;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (refId != null ? !refId.equals(comment.refId) : comment.refId != null) return false;
        if (commentType != null ? !commentType.equals(comment.commentType) : comment.commentType != null) return false;
        return sort != null ? sort.equals(comment.sort) : comment.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (refId != null ? refId.hashCode() : 0);
        result = 31 * result + (commentType != null ? commentType.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", refId='" + refId + '\'' +
                ", commentType='" + commentType + '\'' +
                ", sort=" + sort +
                '}';
    }
}
