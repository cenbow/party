package com.party.mobile.web.dto.activity.output;

import com.party.core.model.activity.Activity;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 活动列表输出视图
 * party
 * Created by wei.li
 * on 2016/9/28 0028.
 */
public class ListOutput {

    //活动编号
    private String id;

    // 名称
    private String title;

    // 封面图
    private String pic;

    // 结束时间
    private Date endTime;

    // 活动场所
    private String place;

    // 区,字符串，由后台录入
    private String area;

    //活动状态:是否正在进行
    private Integer isInProgress;

    //分享链接
    private String shareLink;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getIsInProgress() {
        return isInProgress;
    }

    public void setIsInProgress(Integer isInProgress) {
        this.isInProgress = isInProgress;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    /**
     * 转换列表查询输出视图
     * @param activity 活动实体
     * @return 活动列表输出视图
     */
    public static ListOutput transform(Activity activity){
        ListOutput listOutput = new ListOutput();
        BeanUtils.copyProperties(activity, listOutput);
        return listOutput;
    }
}
