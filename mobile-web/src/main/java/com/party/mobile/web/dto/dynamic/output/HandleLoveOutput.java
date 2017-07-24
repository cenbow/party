package com.party.mobile.web.dto.dynamic.output;

/**
 * 执行点赞，取消点赞后的输出视图
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 14:08 16/11/8
 * @Modified by:
 */
public class HandleLoveOutput {
    //动态主键id
    private String id;

    //动态总点赞数
    private Integer loveNum;

    //当前用户对该动态是否已经点赞(0：否，1：是)
    private Integer isLove;

    //点赞数据(取消操作，则为null)
    DyLoveOutput love;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLoveNum() {
        return loveNum;
    }

    public void setLoveNum(Integer loveNum) {
        this.loveNum = loveNum;
    }

    public Integer getIsLove() {
        return isLove;
    }

    public void setIsLove(Integer isLove) {
        this.isLove = isLove;
    }

    public DyLoveOutput getLove() {
        return love;
    }

    public void setLove(DyLoveOutput love) {
        this.love = love;
    }
}
