package com.party.core.model.partner;

import com.party.core.model.BaseModel;

/**
 * 合作商私有产品
 * party
 * Created by wei.li
 * on 2016/10/20 0020.
 */
public class PartnerGoods extends BaseModel{
    private static final long serialVersionUID = 8873016512213406818L;

    //商铺商品编号
    private String storeGoodsId;

    //名称
    private String title;

    //图片
    private String pic;

    public String getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(String storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PartnerGoods that = (PartnerGoods) o;

        if (storeGoodsId != null ? !storeGoodsId.equals(that.storeGoodsId) : that.storeGoodsId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return pic != null ? pic.equals(that.pic) : that.pic == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (storeGoodsId != null ? storeGoodsId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PartnerGoods{" +
                "storeGoodsId='" + storeGoodsId + '\'' +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
