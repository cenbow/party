package com.party.core.service.goods.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.goods.GoodsReadDao;
import com.party.core.dao.write.goods.GoodsWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.goods.Goods;
import com.party.core.model.order.OrderForm;
import com.party.core.service.goods.IGoodsService;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IGoodsService 商品服务接口实现
 *
 * @author Wesley
 * @data 16/9/7 16:57 .
 */
@Service
public class GoodsService implements IGoodsService {

    @Autowired
    private GoodsReadDao readDao;

    @Autowired
    private GoodsWriteDao writeDao;

    /**
     * 插入商品数据
     * @param goods
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Goods goods) {
        BaseModel.preInsert(goods);
        boolean result = writeDao.insert(goods);
        if (result){
            return goods.getId();
        }
        return null;
    }

    /**
     * 更新商品数据
     * @param goods
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Goods goods) {
        if (null == goods)
            return false;
        goods.setUpdateDate(new Date());
        return writeDao.update(goods);
    }

    /**
     * 逻辑删除商品数据
     * @param id 实体主键
     * @return 逻辑删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.deleteLogic(id);
    }

    /**
     * 物理删除商品数据
     * @param id 实体主键
     * @return 物理删除结果（true/false）
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.delete(id);
    }

    /**
     * 批量逻辑删除商品数据
     * @param ids 主键集合
     * @return 批量逻辑删除结果
     */
    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return writeDao.batchDeleteLogic(ids);
    }

    /**
     * 物理批量删除商品数据
     * @param ids 主键集合
     * @return 物理批量删除结果（true/false）
     */
    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return writeDao.batchDelete(ids);
    }

    /**
     * 根据主键获取商品数据
     * @param id 主键
     * @return 商品实体
     */
    @Override
    public Goods get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return readDao.get(id);
    }

    /**
     * 分页查询商品列表
     * @param goods
     * @param page 分页信息
     * @return 商品礼拜
     */
    @Override
    public List<Goods> listPage(Goods goods, Page page) {
        return readDao.listPage(goods,page);
    }

    /**
     * 查询所有商品列表
     * @param goods
     * @return 商品列表
     */
    @Override
    public List<Goods> list(Goods goods) {
        return readDao.listPage(goods, null);
    }

    /**
     * 查询所有商品
     * @return 商品列表
     */
    @Override
    public List<Goods> listAll() {
        return readDao.listPage(null, null);
    }

    /**
     * 批量查询商品列表
     * @param ids 主键集合
     * @param goods
     * @param page 分页信息
     * @return 商品列表
     */
    @Override
    public List<Goods> batchList(Set<String> ids, Goods goods, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return readDao.batchList(ids, new HashedMap(), page);
    }

	@Override
	public List<Goods> webListPage(Goods goods, Map<String, Object> params, Page page) {
		return readDao.webListPage(goods, params, page);
	}

    /**
     * 释放库存
     * @param orderForm 订单号
     */
    @Override
    public void releaseInventory(OrderForm orderForm) {
        int unit = orderForm.getUnit();
        Goods goods = this.get(orderForm.getGoodsId());
        if (null != goods && null != goods.getLimitNum()){
            goods.setLimitNum(goods.getLimitNum() + unit);
            this.update(goods);
        }
    }
}
