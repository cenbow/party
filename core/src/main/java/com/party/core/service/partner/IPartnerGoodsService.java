package com.party.core.service.partner;

import com.party.core.model.partner.PartnerGoods;
import com.party.core.service.IBaseService;

/**
 * 合作商商品服务接口
 * party
 * Created by wei.li
 * on 2016/10/20 0020.
 */

public interface IPartnerGoodsService extends IBaseService<PartnerGoods> {

    /**
     * 根据商铺商品编号查找合作商商品
     * @param id 商铺商品编号
     * @return 合作商商品
     */
    PartnerGoods findByStoreGoodsId(String id);
}
