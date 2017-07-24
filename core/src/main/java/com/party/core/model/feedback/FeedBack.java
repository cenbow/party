package com.party.core.model.feedback;

import com.party.core.model.BaseModel;

/**
 * 反馈实体
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public class FeedBack extends BaseModel{

    private static final long serialVersionUID = 7977972078490596334L;

    //反馈标题
    private String title;

    //反馈内容
    private String content;

    //反馈图片
    private String imageUrl;

    //用户编码
    private String userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FeedBack feedBack = (FeedBack) o;

        if (title != null ? !title.equals(feedBack.title) : feedBack.title != null) return false;
        if (content != null ? !content.equals(feedBack.content) : feedBack.content != null) return false;
        if (imageUrl != null ? !imageUrl.equals(feedBack.imageUrl) : feedBack.imageUrl != null) return false;
        return userId != null ? userId.equals(feedBack.userId) : feedBack.userId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
