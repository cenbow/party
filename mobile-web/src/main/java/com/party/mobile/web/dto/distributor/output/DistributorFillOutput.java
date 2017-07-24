package com.party.mobile.web.dto.distributor.output;

import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.article.Article;
import com.party.core.model.goods.Goods;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 分销资料填写
 * Created by wei.li
 *
 * @date 2017/3/22 0022
 * @time 10:52
 */
public class DistributorFillOutput {

    //编号
    private String id;

    //图片
    private String pic;

    //题目
    private String title;

    //开始时间
    private Date startTime;

    //类型
    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static  DistributorFillOutput transform(Activity activity){
        DistributorFillOutput distributorFillOutput = new DistributorFillOutput();
        BeanUtils.copyProperties(activity, distributorFillOutput);
        return distributorFillOutput;
    }

    public static  DistributorFillOutput transform(Goods goods){
        DistributorFillOutput distributorFillOutput = new DistributorFillOutput();
        BeanUtils.copyProperties(goods, distributorFillOutput);
        return distributorFillOutput;
    }

    public static DistributorFillOutput transform(Article article){
        DistributorFillOutput distributorFillOutput = new DistributorFillOutput();
        BeanUtils.copyProperties(article, distributorFillOutput);
        return distributorFillOutput;
    }
}
