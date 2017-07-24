package com.party.mobile.biz.ad;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.advertise.Advertise;
import com.party.core.model.advertise.AdvertiseOrigin;
import com.party.core.model.advertise.AdvertiseTag;
import com.party.core.service.advertise.IAdvertiseService;
import com.party.mobile.biz.activity.ActivityBizService;
import com.party.mobile.biz.goods.GoodsBizService;
import com.party.mobile.web.dto.ad.input.AdListInput;
import com.party.mobile.web.dto.ad.output.AdListOutput;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 广告业务逻辑接口
 * party
 * Created by Wesley
 * on 2016/11/2
 */

@Service
public class AdBizService {

    @Autowired
    IAdvertiseService advertiseService;

    @Autowired
    ActivityBizService activityBizService;

    @Autowired
    GoodsBizService goodsBizService;

    /**
     * 分页查询广告列表
     * @param adListInput 查询参数
     * @param page 分页参数
     * @return 活动输出视图列表
     */
    public List<AdListOutput> list(AdListInput adListInput, Page page){
        Advertise ad = new Advertise();
        ad.setAdPos(adListInput.getAdPos());
        List<Advertise> advertiseList = advertiseService.listPage(ad, page);

        if (!CollectionUtils.isEmpty(advertiseList)){
            List<AdListOutput> adListOutputList = LangUtils.transform(advertiseList, input -> {
                AdListOutput adListOutput = AdListOutput.transform(input);
                if (AdvertiseOrigin.ADVERTISE_ORIGIN_INDOOR.getCode().equals(adListOutput.getOrigin()))
                {
                    adListOutput.setLink(this.setShareLink(input));
                }
                return adListOutput;
            });
            return adListOutputList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 获取广告的分享链接
     * @param ad
     * @return
     */
    private String setShareLink(Advertise ad)
    {
        if (AdvertiseOrigin.ADVERTISE_ORIGIN_OUTER.getCode().equals(ad.getOrigin()))
        {
            return ad.getLink();
        }
        else
        {
            if (AdvertiseTag.ADVERTISE_TAG_ACT.getCode().equals(ad.getTag()))//活动
            {
                return activityBizService.setShareLink(ad.getRefId());
            }
            else//文章(目前微网站没有文章的网页，用之前老的分享页面)
            {
                return "http://3g.tongxingzhe.cn/micWeb/html/article/article_detail.html?id="+ ad.getRefId();
            }

        }
    }




}
