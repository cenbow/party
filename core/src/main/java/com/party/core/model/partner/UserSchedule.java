package com.party.core.model.partner;

import com.party.core.model.BaseModel;

/**
 * 用户附加表
 * party
 * Created by wei.li
 * on 2016/10/20 0020.
 */
public class UserSchedule extends BaseModel {


    private static final long serialVersionUID = 4828923570030880544L;

    //会员编号
    private String userId;

    //标题
    private String title;

    //分享图片
    private String sharePic;

    //封面图片
    private String coverPic;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserSchedule that = (UserSchedule) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (sharePic != null ? !sharePic.equals(that.sharePic) : that.sharePic != null) return false;
        return coverPic != null ? coverPic.equals(that.coverPic) : that.coverPic == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (sharePic != null ? sharePic.hashCode() : 0);
        result = 31 * result + (coverPic != null ? coverPic.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSchedule{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", sharePic='" + sharePic + '\'' +
                ", coverPic='" + coverPic + '\'' +
                '}';
    }
}
