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
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.order.OrderFormInfoReadDao;
import com.party.core.dao.write.order.OrderFormInfoWriteDao;
import com.party.core.model.order.OrderFormInfo;
import com.party.core.service.order.IOrderFormInfoService;
import com.sun.istack.NotNull;

/**
 * 订单附属信息
 * 
 * @author Administrator
 *
 */
@Service
public class OrderFormInfoService implements IOrderFormInfoService {

	@Autowired
	OrderFormInfoReadDao orderFormInfoReadDao;

	@Autowired
	OrderFormInfoWriteDao orderFormInfoWriteDao;

	/**
	 * 订单交易插入
	 * 
	 * @param orderFormInfo
	 *            订单交易
	 * @return 插入结果（true/false）
	 */
	@Override
	public String insert(OrderFormInfo orderFormInfo) {
		String uuid = UUIDUtils.generateRandomUUID();
		if (Strings.isNullOrEmpty(orderFormInfo.getId())) {// 如果业务层没有指定主键id
			orderFormInfo.setId(uuid);
		}
		if (orderFormInfo.getCreateDate() == null) {
			orderFormInfo.setCreateDate(new Date());
		}
		boolean result = orderFormInfoWriteDao.insert(orderFormInfo);
		if (result) {
			return orderFormInfo.getId();
		}
		return null;
	}

	/**
	 * 订单交易更新
	 * 
	 * @param orderFormInfo
	 *            订单交易
	 * @return 更新结果（true/false）
	 */
	@Override
	public boolean update(OrderFormInfo orderFormInfo) {
		return orderFormInfoWriteDao.update(orderFormInfo);
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
		return orderFormInfoWriteDao.deleteLogic(id);
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
		return orderFormInfoWriteDao.delete(id);
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
		return orderFormInfoWriteDao.batchDeleteLogic(ids);
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
		return orderFormInfoWriteDao.batchDelete(ids);
	}

	/**
	 * 根据主键获取订单交易信息
	 * 
	 * @param id
	 *            主键
	 * @return 订单交易信息
	 */
	@Override
	public OrderFormInfo get(String id) {
		return orderFormInfoReadDao.get(id);
	}

	/**
	 * 分页查询订单交易信息
	 * 
	 * @param orderFormInfo
	 *            订单交易信息
	 * @param page
	 *            分页信息
	 * @return 订单交易列表
	 */
	@Override
	public List<OrderFormInfo> listPage(OrderFormInfo orderFormInfo, Page page) {
		return orderFormInfoReadDao.listPage(orderFormInfo, page);
	}

	/**
	 * 查询所有订单交易信息
	 * 
	 * @param orderFormInfo
	 *            订单交易信息
	 * @return 订单交易列表
	 */
	@Override
	public List<OrderFormInfo> list(OrderFormInfo orderFormInfo) {
		return orderFormInfoReadDao.listPage(orderFormInfo, null);
	}

	/**
	 * 批量查询订单交易信息
	 * 
	 * @param ids
	 *            主键集合
	 * @param orderFormInfo
	 *            订单交易信息
	 * @param page
	 *            分页信息
	 * @return 订单交易列表
	 */
	@Override
	public List<OrderFormInfo> batchList(@NotNull Set<String> ids, OrderFormInfo orderFormInfo, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return orderFormInfoReadDao.batchList(ids, new HashedMap(), page);
	}

	public OrderFormInfo findByOrderId(String orderId) {
		return orderFormInfoReadDao.findByOrderId(orderId);
	}

	@Override
	public void saveOrderFormInfo(String appid, String mchId, String apiKey, String orderFormId) {
		OrderFormInfo orderFormInfo = findByOrderId(orderFormId);
		if (orderFormInfo == null) {
			orderFormInfo = new OrderFormInfo();
			orderFormInfo.setApiKey(apiKey);
			orderFormInfo.setAppId(appid);
			orderFormInfo.setMchId(mchId);
			orderFormInfo.setOrderFormId(orderFormId);
			insert(orderFormInfo);
		} else {
			orderFormInfo.setApiKey(apiKey);
			orderFormInfo.setAppId(appid);
			orderFormInfo.setMchId(mchId);
			update(orderFormInfo);
		}
	}
}
