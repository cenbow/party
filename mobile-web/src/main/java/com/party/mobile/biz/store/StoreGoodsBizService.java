package com.party.mobile.biz.store;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.goods.Goods;
import com.party.core.model.store.StoreCount;
import com.party.core.model.store.StoreGoods;
import com.party.core.model.store.StoreGoodsType;
import com.party.core.model.store.TimeType;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.store.IStoreCountService;
import com.party.core.service.store.IStoreGoodsService;
import com.party.mobile.web.dto.store.input.AddToStoreInput;
import com.party.mobile.web.dto.store.input.GetShareUrlInput;
import com.party.mobile.web.dto.store.input.ListInput;
import com.party.mobile.web.dto.store.output.CountOutput;
import com.party.mobile.web.dto.store.output.ListOutput;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 商铺商品业务逻辑服务
 * party
 * Created by wei.li
 * on 2016/10/11 0011.
 */
@Service
public class StoreGoodsBizService {

    //微网站域名
    @Value("${mobile.domain}")
    private String domain;

    //活动详情地址
    @Value("${activity.url}")
    private String activityUrl;

    //商品详情地址
    @Value("${goods.url}")
    private String goodsUrl;

    @Value("${article.url}")
    private String article;



    @Autowired
    IStoreGoodsService storeGoodsService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    IActivityService activityService;

    @Autowired
    IStoreCountService storeCountService;


    /**
     * 查询在线商品列表
     * @param input 输入视图
     * @param memberId 会员编号
     * @param page 分页参数
     * @return 商品列表
     */
    public List<ListOutput> inSalesList(ListInput input,String memberId, Page page){
        List<StoreGoods> storeGoodsList
                = storeGoodsService.listSelect(input.getGoodsType(), input.getSort(), memberId, true, page);
       return list(storeGoodsList);
    }


    /**
     * 查询下线商品列表
     * @param input 输入视图
     * @param memberId 会员编号
     * @param page 分页参数
     * @return 商品列表
     */
    public List<ListOutput> soldOutList(ListInput input,String memberId, Page page){
        List<StoreGoods> storeGoodsList
                = storeGoodsService.listSelect(input.getGoodsType(), input.getSort(), memberId, false, page);
        return list(storeGoodsList);
    }

    /**
     * 商铺商品转换为输出视图列表
     * @param storeGoodsList 商铺列表
     * @return 输出视图列表
     */
    public List<ListOutput> list(List<StoreGoods> storeGoodsList){
        List<Goods> goodsList = goodsService.listAll();
        List<Activity> activityList = activityService.listAll();

        //转换输出视图
        if (!CollectionUtils.isEmpty(storeGoodsList)){
            List<ListOutput> listOutputList = Lists.newArrayList();
            for (StoreGoods storeGoods : storeGoodsList){
                ListOutput listOutput = ListOutput.transform(storeGoods);
                //如果是商品类型
                if (storeGoods.getGoodsType().equals(StoreGoodsType.GOODS.getCode())){
                    for (Goods goods : goodsList){
                        if (storeGoods.getGoodsId().equals(goods.getId())){
                            listOutput.setPic(goods.getPicsURL());
                            listOutput.setTitle(goods.getTitle());
                            listOutput.setCreateDate(goods.getCreateDate());
                            listOutput.setPrice(goods.getPrice());
                            listOutput.setType(StoreGoodsType.GOODS.getCode());
                            listOutputList.add(listOutput);
                        }
                    }
                }
                //如果是活动类型
                else if (storeGoods.getGoodsType().equals(StoreGoodsType.ACTIVITY.getCode())){
                    activityList.stream().filter(activity -> storeGoods.getGoodsId().equals(activity.getId())).forEachOrdered(activity -> {
                        listOutput.setPic(activity.getPic());
                        listOutput.setTitle(activity.getTitle());
                        listOutput.setCreateDate(activity.getCreateDate());
                        listOutput.setPrice(activity.getPrice());
                        listOutput.setType(StoreGoodsType.ACTIVITY.getCode());
                        listOutputList.add(listOutput);
                    });
                }
            }

            return listOutputList;
        }
        return Collections.EMPTY_LIST;
    }


    /**
     * 生成活动分享连接
     * @param input 输入视图
     * @return 分享连接
     */
    public String getShareUrl(GetShareUrlInput input){

        String url = null;
        StoreGoods storeGoods = storeGoodsService.get(input.getId());
        if (StoreGoodsType.GOODS.getCode().equals(input.getType())){
            url = new StringBuilder(domain + goodsUrl)
                    .append("?")
                    .append("id=")
                    .append(storeGoods.getGoodsId())
                    .append("&storeGoodsId=")
                    .append(input.getId()).toString();
        }
        else if (StoreGoodsType.ACTIVITY.getCode().equals(input.getType())){
            url =  new StringBuilder(domain + activityUrl)
                    .append("?")
                    .append("id=")
                    .append(storeGoods.getGoodsId())
                    .append("&storeGoodsId=")
                    .append(input.getId()).toString();
        }
        else if (StoreGoodsType.ARTICLE.getCode().equals(input.getType())){
            url =  new StringBuilder(domain + article)
                    .append("?")
                    .append("id=")
                    .append(storeGoods.getGoodsId())
                    .append("&storeGoodsId=")
                    .append(input.getId()).toString();
        }
        //写入分享数
        Integer num = storeGoods.getShareNum();
        storeGoods.setShareNum(num + 1);
        storeGoodsService.update(storeGoods);
        return url;
    }


    /**
     * 商铺统计数据
     * @param memberId 会员编号
     * @return 统计数据
     */
    public Map<String, CountOutput> count(String memberId){
        StoreGoods today = storeGoodsService.countByMemberId(memberId);
        StoreCount yesterday = storeCountService.countByMemberId(memberId, TimeType.BY_YESTERDAY.getCode());
        StoreCount lastWeek = storeCountService.countByMemberId(memberId, TimeType.BY_LAST_WEEK.getCode());
        StoreCount lastMonth = storeCountService.countByMemberId(memberId, TimeType.BY_LAST_MONTH.getCode());

        Map<String , CountOutput> map = Maps.newHashMap();
        map.put("today", CountOutput.transform(today));
        map.put("yesterday", CountOutput.transform(yesterday));
        map.put("lastWeek", CountOutput.transform(lastWeek));
        map.put("lastMonth", CountOutput.transform(lastMonth));
        return map;
    }


    /**
     * 把商品添加到店铺
     * @param input 输入视图
     * @param memberId 会员编号
     */
    public void addToStore(AddToStoreInput input ,String memberId){
        StoreGoods dbStoreGoods = storeGoodsService.findByMemberGoods(memberId, input.getGoodsId());
        if (null == dbStoreGoods){
            StoreGoods storeGoods = new StoreGoods();
            BaseModel.preByInfo(memberId, storeGoods);
            storeGoods.setMemberId(memberId);
            storeGoods.setGoodsType(input.getType());
            storeGoods.setGoodsId(input.getGoodsId());
            storeGoodsService.insert(storeGoods);
        }
    }

}
