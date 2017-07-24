package com.party.core.service.order.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.order.OrderRefundTradeReadDao;
import com.party.core.dao.write.order.OrderRefundTradeWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.order.OrderRefundTrade;
import com.party.core.service.order.IOrderRefundTradeService;
import com.sun.istack.NotNull;

/**
 * 订单交易服务实现 party Created by wei.li on 2016/9/18 0018.
 */

@Service
public class OrderRefundTradeService implements IOrderRefundTradeService {

	@Autowired
	OrderRefundTradeReadDao orderRefundTradeReadDao;

	@Autowired
	OrderRefundTradeWriteDao orderRefundTradeWriteDao;

	/**
	 * 订单交易插入
	 * 
	 * @param orderRefundTrade
	 *            订单交易
	 * @return 插入结果（true/false）
	 */
	@Override
	public String insert(OrderRefundTrade orderRefundTrade) {
		BaseModel.preInsert(orderRefundTrade);
		boolean result = orderRefundTradeWriteDao.insert(orderRefundTrade);
		if (result) {
			return orderRefundTrade.getId();
		}
		return null;
	}

	/**
	 * 订单交易更新
	 * 
	 * @param orderRefundTrade
	 *            订单交易
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(OrderRefundTrade orderRefundTrade) {
		orderRefundTrade.setUpdateDate(new Date());
		return orderRefundTradeWriteDao.update(orderRefundTrade);
	}

	/**
	 * 逻辑删除订单交易
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果(true/false)
	 */
	@Override
	public boolean deleteLogic(@NotNull String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return orderRefundTradeWriteDao.deleteLogic(id);
	}

	/**
	 * 物理删除订单交易
	 * 
	 * @param id
	 *            实体主键
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean delete(@NotNull String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return orderRefundTradeWriteDao.delete(id);
	}

	/**
	 * 批量逻辑删除订单交易
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean batchDeleteLogic(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return orderRefundTradeWriteDao.batchDeleteLogic(ids);
	}

	/**
	 * 批量物理删除订单交易
	 * 
	 * @param ids
	 *            主键集合
	 * @return 删除结果（true/false）
	 */
	@Override
	public boolean batchDelete(@NotNull Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return orderRefundTradeWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取订单交易信息
	 * 
	 * @param id
	 *            主键
	 * @return 订单交易信息
	 */
	@Override
	public OrderRefundTrade get(String id) {
		return orderRefundTradeReadDao.get(id);
	}

	/**
	 * 分页查询订单交易信息
	 * 
	 * @param orderRefundTrade
	 *            订单交易信息
	 * @param page
	 *            分页信息
	 * @return 订单交易列表
	 */
	@Override
	public List<OrderRefundTrade> listPage(OrderRefundTrade orderRefundTrade, Page page) {
		return orderRefundTradeReadDao.listPage(orderRefundTrade, page);
	}

	/**
	 * 查询所有订单交易信息
	 * 
	 * @param orderRefundTrade
	 *            订单交易信息
	 * @return 订单交易列表
	 */
	@Override
	public List<OrderRefundTrade> list(OrderRefundTrade orderRefundTrade) {
		return orderRefundTradeReadDao.listPage(orderRefundTrade, null);
	}

	/**
	 * 批量查询订单交易信息
	 * 
	 * @param ids
	 *            主键集合
	 * @param orderRefundTrade
	 *            订单交易信息
	 * @param page
	 *            分页信息
	 * @return 订单交易列表
	 */
	@Override
	public List<OrderRefundTrade> batchList(@NotNull Set<String> ids, OrderRefundTrade orderRefundTrade, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return orderRefundTradeReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public OrderRefundTrade findByOrderId(String id) {
		return orderRefundTradeReadDao.findByOrderId(id);
	}


	/**
	 * 获取最近的交易信息
	 * @param orderId 订单号
	 * @return 交易信息
	 */
	@Override
	public OrderRefundTrade recentlyByOrderId(String orderId) {
		return orderRefundTradeReadDao.recentlyByOrderId(orderId);
	}
}
