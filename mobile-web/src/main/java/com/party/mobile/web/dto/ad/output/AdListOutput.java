package com.party.mobile.web.dto.ad.output;

import com.party.core.model.advertise.Advertise;
import org.springframework.beans.BeanUtils;

/**
 * 广告列表输出视图
 * @Author: Wesley
 * @Description:
 * @Date: Created in 17:37 16/11/2
 * @Modified by:
 */
public class AdListOutput {

    //广告图片
    private String pic;

    //广告来源
    private String origin;

    //外部广告URL
    private String link;

    //内部广告类型

    private String tag;

    //内部广告关联id
    private String refId;
    
    //播放秒数
    private Integer playSecond;

    public String getPic() {
        return pic;
    }

    public Integer getPlaySecond() {
		return playSecond;
	}

	public void setPlaySecond(Integer playSecond) {
		this.playSecond = playSecond;
	}

	public void setPic(String pic) {
        this.pic = pic;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     * 转换列表查询输出视图
     * @param advertise 活动实体
     * @return 活动列表输出视图
     */
    public static AdListOutput transform(Advertise advertise){
        AdListOutput adListOutput = new AdListOutput();
        BeanUtils.copyProperties(advertise, adListOutput);
        return adListOutput;
    }
}
