package com.party.core.model.records;

import com.party.core.model.BaseModel;

/**
 * 跟进记录
 * Created by wei.li
 *
 * @date 2017/7/12 0012
 * @time 9:48
 */
public class UpRecords extends BaseModel{


    //目标编号
    private String targetId;

    //内容
    private String content;

    //图片
    private String pic;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UpRecords upRecords = (UpRecords) o;

        if (targetId != null ? !targetId.equals(upRecords.targetId) : upRecords.targetId != null) return false;
        if (content != null ? !content.equals(upRecords.content) : upRecords.content != null) return false;
        return pic != null ? pic.equals(upRecords.pic) : upRecords.pic == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (targetId != null ? targetId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpRecords{" +
                "targetId='" + targetId + '\'' +
                ", content='" + content + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
