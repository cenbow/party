package com.party.mobile.web.dto.distributor.input;

/**
 * 分销列表输入视图
 * Created by wei.li
 *
 * @date 2017/3/8 0008
 * @time 15:52
 */
public class DistributorListInput {

    //状态(0:销售中 1：已经下架)
    private Integer status;

    //类型(0: 商品，1：活动， 2.文章)
    private Integer type;

    //排序方式（0：时间最近，1：浏览最多）
    private Integer sort;


    public DistributorListInput() {
        this.status = 0;
        this.sort = 0;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
