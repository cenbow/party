package com.party.core.service.goods;

import com.party.common.paging.Page;
import com.party.core.model.goods.Goods;
import com.party.core.model.order.OrderForm;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * IGoodsService 商品接口
 *
 * @author Wesley
 * @data 16/9/7 16:57 .
 */
public interface IGoodsService extends IBaseService<Goods>{

    /**
     * 查询所有商品
     * @return 商品列表
     */
    List<Goods> listAll();

    /**
     * web端查询
     * @param goods
     * @param params
     * @param page
     * @return
     */
	List<Goods> webListPage(Goods goods, Map<String, Object> params, Page page);

    /**
     * 释放库存
     * @param orderForm 订单号
     */
    void releaseInventory(OrderForm orderForm);
}


