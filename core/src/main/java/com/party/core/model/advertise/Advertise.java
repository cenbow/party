package com.party.core.model.advertise;

import com.party.core.model.BaseModel;

/**
 * 广告实体
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */
public class Advertise extends BaseModel{

    private static final long serialVersionUID = -3016478227312616600L;

    //标题
    private String title;

    //图片
    private String pic;

    //链接
    private String link;

    //广告位置(1:活动，2：标准商品，3：社区，4：定制商品)
    private String adPos;

    //广告来源(内部广告1，外部广告0)
    private String origin;

    //内部广告类型
    private String tag;

    //内部广告关联id
    private String refId;
    
    //排序
    private Integer sort;
    
    //播放秒数
    private Integer playSecond;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getPlaySecond() {
		return playSecond;
	}

	public void setPlaySecond(Integer playSecond) {
		this.playSecond = playSecond;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAdPos() {
        return adPos;
    }

    public void setAdPos(String adPos) {
        this.adPos = adPos;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Advertise advertise = (Advertise) o;

        if (title != null ? !title.equals(advertise.title) : advertise.title != null) return false;
        if (pic != null ? !pic.equals(advertise.pic) : advertise.pic != null) return false;
        if (link != null ? !link.equals(advertise.link) : advertise.link != null) return false;
        if (adPos != null ? !adPos.equals(advertise.adPos) : advertise.adPos != null) return false;
        if (origin != null ? !origin.equals(advertise.origin) : advertise.origin != null) return false;
        if (tag != null ? !tag.equals(advertise.tag) : advertise.tag != null) return false;
        return refId != null ? refId.equals(advertise.refId) : advertise.refId == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (adPos != null ? adPos.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (refId != null ? refId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Advertise{" +
                "title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", link='" + link + '\'' +
                ", adPos='" + adPos + '\'' +
                ", origin='" + origin + '\'' +
                ", tag='" + tag + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
}
