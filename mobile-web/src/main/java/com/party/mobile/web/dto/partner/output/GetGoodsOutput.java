package com.party.mobile.web.dto.partner.output;

import com.party.core.model.store.StoreGoods;
import com.party.mobile.web.dto.member.output.CreateByOutput;
import org.springframework.beans.BeanUtils;

/**
 * 获取商品输出视图
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */
public class GetGoodsOutput extends CreateByOutput{

    //商品主键
    private String id;


    //商品标题
    private String title;

    //商品销售量
    private Integer salesNum;

    //报名量
    private Integer applyNum;

    //商品分享量
    private Integer shareNum;

    //商品浏览量
    private Integer viewNum;

    //商品销售金额
    private Float salesAmount;

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

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Float getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Float salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    /**
     * 商铺商品转合作商商品
     * @param storeGoods 商铺商品
     * @return 合作商商品视图
     */
    public static GetGoodsOutput transform(StoreGoods storeGoods){
        GetGoodsOutput getGoodsOutput = new GetGoodsOutput();
        BeanUtils.copyProperties(storeGoods, getGoodsOutput);
        return getGoodsOutput;
    }
}
