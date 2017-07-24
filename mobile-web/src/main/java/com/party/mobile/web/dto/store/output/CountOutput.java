package com.party.mobile.web.dto.store.output;

import com.party.core.model.store.StoreCount;
import com.party.core.model.store.StoreGoods;
import org.springframework.beans.BeanUtils;

/**
 * 统计输出视图
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */
public class CountOutput {

    //销售量
    private Integer salesNum;

    //分享量
    private Integer shareNum;

    //浏览量
    private Integer viewNum;

    //销售额
    private Float salesAmount;

    public CountOutput() {
    }

    public CountOutput(Integer salesNum, Integer shareNum, Integer viewNum, Float salesAmount) {
        this.salesNum = salesNum;
        this.shareNum = shareNum;
        this.viewNum = viewNum;
        this.salesAmount = salesAmount;
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

    /**
     * 商铺商品转统计视图
     * @param storeGoods 商铺商品
     * @return 商铺统计视图
     */
    public static CountOutput transform(StoreGoods storeGoods){
        CountOutput countOutput = new CountOutput();
        BeanUtils.copyProperties(storeGoods, countOutput);
        return countOutput;
    }

    /**
     * 商铺统计转统计视图
     * @param storeCount 商铺统计
     * @return 商铺统计视图
     */
    public static CountOutput transform(StoreCount storeCount){
        CountOutput countOutput = new CountOutput();
        BeanUtils.copyProperties(storeCount, countOutput);
        return countOutput;
    }
}
