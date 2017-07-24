package com.party.mobile.biz.partner;

import com.party.core.exception.BusinessException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.goods.Goods;
import com.party.core.model.partner.PartnerGoods;
import com.party.core.model.store.StoreGoods;
import com.party.core.model.store.StoreGoodsType;
import com.party.core.service.activity.IActivityDetailService;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.partner.IPartnerGoodsService;
import com.party.core.service.store.IStoreGoodsService;
import com.party.mobile.biz.member.CreateByBizService;
import com.party.mobile.web.dto.partner.output.GetDetailsOutput;
import com.party.mobile.web.dto.partner.output.GetGoodsOutput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 合作商业务接口服务
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */

@Service
public class PartnerBizService {

    @Autowired
    IStoreGoodsService storeGoodsService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    IActivityService activityService;

    @Autowired
    IPartnerGoodsService partnerGoodsService;

    @Autowired
    IActivityDetailService activityDetailService;

    @Autowired
    CreateByBizService createByBizService;

    /**
     * 获取合作商商品信息
     * @param id 商铺编号
     * @return 合作商商品输出视图
     * @throws BusinessException
     */
    public GetGoodsOutput getGoods(String id) throws BusinessException{

        StoreGoods storeGoods = storeGoodsService.get(id);
        if (null == storeGoods){
            throw new BusinessException(PartyCode.STORE_GOODS_UNEXIST, "商铺商品不存在");
        }
        GetGoodsOutput getGoodsOutput = GetGoodsOutput.transform(storeGoods);
        //如果是商品
        if (StoreGoodsType.GOODS.getCode().equals(storeGoods.getGoodsType())){
            Goods goods = goodsService.get(storeGoods.getGoodsId());
            getGoodsOutput.setTitle(goods.getTitle());
            //获取创建者信息
            getGoodsOutput = createByBizService.getCreateBy(getGoodsOutput, goods.getCreateBy());
        }
        //如果是活动
        else {
            Activity activity = activityService.get(storeGoods.getGoodsId());
            getGoodsOutput.setTitle(activity.getTitle());
            //获取创建者信息
            getGoodsOutput = createByBizService.getCreateBy(getGoodsOutput, activity.getCreateBy());
        }
        return getGoodsOutput;
    }


    /**
     * 获取合作商商品详情
     * @param id 商铺商品编号
     * @return 商品详情输出视图
     * @throws BusinessException
     */
    public GetDetailsOutput getDetails(String id) throws BusinessException{
        StoreGoods storeGoods = storeGoodsService.get(id);
        if (null == storeGoods){
            throw new BusinessException(PartyCode.STORE_GOODS_UNEXIST, "商铺商品不存在");
        }

        //如果是活动
        if (StoreGoodsType.ACTIVITY.getCode().equals(storeGoods.getGoodsType())){
            Activity activity = activityService.get(storeGoods.getGoodsId());
            GetDetailsOutput getDetailsOutput = GetDetailsOutput.transform(activity);
            if (YesNoStatus.YES.getCode() == storeGoods.getIsPrivate()){
                PartnerGoods partnerGoods = partnerGoodsService.findByStoreGoodsId(storeGoods.getId());
                ActivityDetail activityDetail = activityDetailService.getByRefId(partnerGoods.getId());
                getDetailsOutput.setTitle(partnerGoods.getTitle());
                getDetailsOutput.setPic(partnerGoods.getPic());
                getDetailsOutput.setContent(activityDetail.getContent());
            }
            //获取创建者信息
            getDetailsOutput = createByBizService.getCreateBy(getDetailsOutput, activity.getCreateBy());
            return getDetailsOutput;
        }
        return new GetDetailsOutput();
    }
}
