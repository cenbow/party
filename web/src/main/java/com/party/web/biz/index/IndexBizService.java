package com.party.web.biz.index;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.advertise.Advertise;
import com.party.core.model.goods.Category;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.resource.Resource;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.advertise.IAdvertiseService;
import com.party.core.service.goods.ICategoryService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.resource.IResourceService;
import com.party.core.service.thirdParty.IThirdPartyService;
import com.party.web.web.dto.output.index.IndexActivityOutput;
import com.party.web.web.dto.output.index.IndexGoodsOutput;
import com.party.web.web.dto.output.index.IndexOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 首页业务接口
 * Created by wei.li
 *
 * @date 2017/2/22 0022
 * @time 15:40
 */
@Service
public class IndexBizService {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IThirdPartyService thirdPartyService;

    @Autowired
    private ICategoryService categoryService;
    
    @Autowired
    private IAdvertiseService advertiseService;
    
    @Autowired
    IResourceService resourceService;
    
    public List<Resource> getPartner(){
    	// 合作伙伴
		Resource resource = new Resource();
		resource.setType("partner");
		List<Resource> resources = resourceService.webListPage(resource, null, new Page(1, 16));
		return resources;
    }


    /**
     * 获取主页数据
     * @return 主页数据
     */
    public IndexOutput getIndex(){
    	// 首页条幅广告
    	Advertise advertiset = new Advertise();
    	advertiset.setAdPos("6");
		List<Advertise> advertises = advertiseService.list(advertiset);
        Activity activity = new Activity();
        activity.setIsCrowdfunded(Constant.NOT_CROWDFUNDED);
        List<Activity> activityList = activityService.listPage(activity, new Page(1, 9));
        Goods goods = new Goods();
        goods.setType(GoodsType.GOODS_CUSTOMIZED.getCode());
        List<Goods> goodsList = goodsService.listPage(goods, new Page(1, 6));

        //活动列表
        List<IndexActivityOutput> indexActivityOutputList = LangUtils.transform(activityList, new Function<Activity, IndexActivityOutput>() {
            @Override
            public IndexActivityOutput apply(Activity activity) {
                IndexActivityOutput indexActivityOutput = IndexActivityOutput.transform(activity);
                ThirdParty thirdParty = thirdPartyService.get(activity.getThirdPartyId());
                if (null != thirdParty){
                    indexActivityOutput.setThirdPartyName(thirdParty.getComName());
                }
                return indexActivityOutput;
            }
        });

        //商品列表
        List<IndexGoodsOutput> indexGoodsOutputList = LangUtils.transform(goodsList, new Function<Goods, IndexGoodsOutput>() {
            @Override
            public IndexGoodsOutput apply(Goods goods) {
                IndexGoodsOutput indexGoodsOutput = IndexGoodsOutput.transform(goods);
                ThirdParty thirdParty = thirdPartyService.get(goods.getThirdPartyId());
                if (null != thirdParty){
                    indexGoodsOutput.setThirdPartyName(thirdParty.getComName());
                }
                Category category = categoryService.get(goods.getCategoryId());
                if (null != category){
                    indexGoodsOutput.setCategoryName(category.getName());
                }
                indexGoodsOutput.setPic(goods.getPicsURL());
                return indexGoodsOutput;
            }
        });

        IndexOutput indexOutput = new IndexOutput();
        indexOutput.setAdvertises(advertises);
        indexOutput.setResources(getPartner());
        this.setActivity(indexOutput, indexActivityOutputList);
        this.setGoods(indexOutput, indexGoodsOutputList);
        return indexOutput;
    }

    /**
     * 设置活动
     * @param indexOutput 输出视图
     * @param indexActivityOutputList 活动列表
     * @return 输出视图
     */
    public IndexOutput setActivity(IndexOutput indexOutput, List<IndexActivityOutput> indexActivityOutputList){
        if (!CollectionUtils.isEmpty(indexActivityOutputList)){
            indexOutput.setBigPic(indexActivityOutputList.get(0));
        }

        if (indexActivityOutputList.size() >1){
            List<IndexActivityOutput> rightTopList = Lists.newArrayList();
            if (indexActivityOutputList.size()>= 3){
                for (int i=1; i<3 ; i++){
                    rightTopList.add(indexActivityOutputList.get(i));
                }
            }
            else {
                for (int i=1; i<indexActivityOutputList.size(); i++){
                    rightTopList.add(indexActivityOutputList.get(i));
                }
            }
            indexOutput.setRightTopPicList(rightTopList);
        }

        if (indexActivityOutputList.size() >3){
            List<IndexActivityOutput> rightBottomList = Lists.newArrayList();
            if (indexActivityOutputList.size()>= 5){
                for (int i=3; i<5 ; i++){
                    rightBottomList.add(indexActivityOutputList.get(i));
                }
            }
            else {
                for (int i=3; i<indexActivityOutputList.size(); i++){
                    rightBottomList.add(indexActivityOutputList.get(i));
                }
            }
            indexOutput.setRightBottomPicList(rightBottomList);
        }

        if (indexActivityOutputList.size() >5){
            List<IndexActivityOutput> bottomList = Lists.newArrayList();
            if (indexActivityOutputList.size() == 9){
                for (int i=5; i<9 ; i++){
                    bottomList.add(indexActivityOutputList.get(i));
                }
            }
            else {
                for (int i=5; i<indexActivityOutputList.size() ; i++){
                    bottomList.add(indexActivityOutputList.get(i));
                }
            }
            indexOutput.setBottomList(bottomList);
        }
        return indexOutput;
    }

    /**
     * 设置商品
     * @param indexOutput 输出视图
     * @param indexGoodsOutputList 商品列表
     * @return 输出视图
     */
    public IndexOutput setGoods(IndexOutput indexOutput, List<IndexGoodsOutput> indexGoodsOutputList){
        if (indexGoodsOutputList.size() > 1){
            List<IndexGoodsOutput> goodsTopList = Lists.newArrayList();
            if (indexGoodsOutputList.size() > 3){
                for (int i=0; i<3 ; i++){
                    goodsTopList.add(indexGoodsOutputList.get(i));
                }
            }
            else {
                for (int i=0; i<indexGoodsOutputList.size() ; i++){
                    goodsTopList.add(indexGoodsOutputList.get(i));
                }
            }
            indexOutput.setGoodsTopList(goodsTopList);
        }
        if (indexGoodsOutputList.size() > 3){
            List<IndexGoodsOutput> goodsBottomList = Lists.newArrayList();
            if (indexGoodsOutputList.size() == 6){
                for (int i=3; i<6 ; i++){
                    goodsBottomList.add(indexGoodsOutputList.get(i));
                }
            }
            else {
                for (int i=3; i<indexGoodsOutputList.size() ; i++){
                    goodsBottomList.add(indexGoodsOutputList.get(i));
                }
            }
            indexOutput.setGoodsBottomList(goodsBottomList);
        }
        return indexOutput;
    }
}
