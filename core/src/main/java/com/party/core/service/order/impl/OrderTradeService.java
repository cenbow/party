package com.party.core.service.order.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.order.OrderTradeReadDao;
import com.party.core.dao.write.order.OrderTradeWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.order.OrderTrade;
import com.party.core.service.order.IOrderTradeService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 订单交易服务实现
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Service
public class OrderTradeService implements IOrderTradeService {

    @Autowired
    OrderTradeReadDao orderTradeReadDao;

    @Autowired
    OrderTradeWriteDao orderTradeWriteDao;

    /**
     * 订单交易插入
     * @param orderTrade 订单交易
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(OrderTrade orderTrade) {
        BaseModel.preInsert(orderTrade);
        boolean result = orderTradeWriteDao.insert(orderTrade);
        if (result){
            return orderTrade.getId();
        }
        return null;
    }

    /**
     * 订单交易更新
     * @param orderTrade 订单交易
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(OrderTrade orderTrade) {
        orderTrade.setUpdateDate(new Date());
        return orderTradeWriteDao.update(orderTrade);
    }

    /**
     * 逻辑删除订单交易
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return orderTradeWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除订单交易
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return orderTradeWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除订单交易
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return orderTradeWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除订单交易
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return orderTradeWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取订单交易信息
     * @param id 主键
     * @return 订单交易信息
     */
    @Override
    public OrderTrade get(String id) {
        return orderTradeReadDao.get(id);
    }

    /**
     * 分页查询订单交易信息
     * @param orderTrade 订单交易信息
     * @param page 分页信息
     * @return 订单交易列表
     */
    @Override
    public List<OrderTrade> listPage(OrderTrade orderTrade, Page page) {
        return orderTradeReadDao.listPage(orderTrade, page);
    }

    /**
     * 查询所有订单交易信息
     * @param orderTrade 订单交易信息
     * @return 订单交易列表
     */
    @Override
    public List<OrderTrade> list(OrderTrade orderTrade) {
        return orderTradeReadDao.listPage(orderTrade, null);
    }


    /**
     * 批量查询订单交易信息
     * @param ids 主键集合
     * @param orderTrade 订单交易信息
     * @param page 分页信息
     * @return 订单交易列表
     */
    @Override
    public List<OrderTrade> batchList(@NotNull Set<String> ids, OrderTrade orderTrade, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return orderTradeReadDao.batchList(ids, new HashedMap(), page);
    }

	public OrderTrade findByOrderId(String orderId) {
		return orderTradeReadDao.findByOrderId(orderId);
	}
}
