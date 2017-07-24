package com.party.web.web.dto.output.index;

import com.party.core.model.activity.Activity;
import org.springframework.beans.BeanUtils;

/**
 * 首页活动输出视图
 * Created by wei.li
 *
 * @date 2017/2/22 0022
 * @time 15:45
 */
public class IndexActivityOutput {

    //商品编号
    private String id;

    //标题
    private String title;

    //区
    private String area;

    //第三方公司名称
    private String thirdPartyName;

    //图片地址
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public static IndexActivityOutput transform(Activity activity){
        IndexActivityOutput indexActivityOutput = new IndexActivityOutput();
        BeanUtils.copyProperties(activity, indexActivityOutput);
        return indexActivityOutput;
    }
}
