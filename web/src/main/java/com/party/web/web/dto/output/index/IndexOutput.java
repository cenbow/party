package com.party.web.web.dto.output.index;

import java.util.List;

import com.party.core.model.advertise.Advertise;
import com.party.core.model.resource.Resource;

/**
 * 主页输出视图
 * Created by wei.li
 *
 * @date 2017/2/22 0022
 * @time 15:44
 */
public class IndexOutput {

	// 条幅广告
	private List<Advertise> advertises;
	
	private List<Resource> resources;

    //大图
    private IndexActivityOutput bigPic;

    //右侧活动
    private List<IndexActivityOutput> rightTopPicList;

    //右下
    private List<IndexActivityOutput> rightBottomPicList;

    //底部活动
    private List<IndexActivityOutput> bottomList;

    //商品列表
    private List<IndexGoodsOutput> goodsTopList;

    //商品下
    private List<IndexGoodsOutput> goodsBottomList;


    public IndexActivityOutput getBigPic() {
        return bigPic;
    }

    public void setBigPic(IndexActivityOutput bigPic) {
        this.bigPic = bigPic;
    }

    public List<IndexActivityOutput> getRightTopPicList() {
        return rightTopPicList;
    }

    public void setRightTopPicList(List<IndexActivityOutput> rightTopPicList) {
        this.rightTopPicList = rightTopPicList;
    }

    public List<IndexActivityOutput> getRightBottomPicList() {
        return rightBottomPicList;
    }

    public void setRightBottomPicList(List<IndexActivityOutput> rightBottomPicList) {
        this.rightBottomPicList = rightBottomPicList;
    }

    public List<IndexActivityOutput> getBottomList() {
        return bottomList;
    }

    public void setBottomList(List<IndexActivityOutput> bottomList) {
        this.bottomList = bottomList;
    }

    public List<IndexGoodsOutput> getGoodsTopList() {
        return goodsTopList;
    }

    public void setGoodsTopList(List<IndexGoodsOutput> goodsTopList) {
        this.goodsTopList = goodsTopList;
    }

    public List<IndexGoodsOutput> getGoodsBottomList() {
        return goodsBottomList;
    }

    public void setGoodsBottomList(List<IndexGoodsOutput> goodsBottomList) {
        this.goodsBottomList = goodsBottomList;
    }

	public List<Advertise> getAdvertises() {
		return advertises;
	}

	public void setAdvertises(List<Advertise> advertises) {
		this.advertises = advertises;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
}
