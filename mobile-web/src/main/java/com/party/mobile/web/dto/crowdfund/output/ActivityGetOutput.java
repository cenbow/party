package com.party.mobile.web.dto.crowdfund.output;

import com.party.core.model.activity.Activity;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 获取活动信息输出视图
 * Created by wei.li
 *
 * @date 2017/3/6 0006
 * @time 16:50
 */
public class ActivityGetOutput {

    //编号
    private String id;

    //标题
    private String title;

    //图片
    private String pic;

    //活动开始时间
    private Date startTime;

    // 众筹宣言
    private String crowdDeclaration;

    // 代言宣言
    private String representDeclaration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCrowdDeclaration() {
        return crowdDeclaration;
    }

    public void setCrowdDeclaration(String crowdDeclaration) {
        this.crowdDeclaration = crowdDeclaration;
    }

    public String getRepresentDeclaration() {
        return representDeclaration;
    }

    public void setRepresentDeclaration(String representDeclaration) {
        this.representDeclaration = representDeclaration;
    }

    public static ActivityGetOutput transform(Activity activity){
        ActivityGetOutput activityGetOutput = new ActivityGetOutput();
        BeanUtils.copyProperties(activity, activityGetOutput);
        return activityGetOutput;
    }
}
