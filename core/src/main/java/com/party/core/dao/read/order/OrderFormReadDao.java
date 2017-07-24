package com.party.core.dao.read.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.order.OrderForm;

/**
 * 商品订单数据读取 party Created by wei.li on 2016/9/18 0018.
 */
@Repository
public interface OrderFormReadDao extends BaseReadDao<OrderForm> {
	/**
	 * 
	 * @param goodsId
	 * @param memberId
	 * @param isPay
	 * @param status
	 * @return
	 */
	List<OrderForm> getOrderSuccess(@Param(value = "goodsId") String goodsId, @Param(value = "memberId") String memberId,
			@Param(value = "isPay") Boolean isPay, @Param(value = "status") Set<Integer> status);

	List<OrderForm> listPage(@Param(value = "order") OrderForm orderForm, @Param(value = "status") Set<Integer> status, Page page);

	/**
	 * web端
	 * @param orderForm
	 * @param params
	 * @param page
	 * @return
	 */
	List<OrderForm> webListPage(@Param(value = "order") OrderForm orderForm, @Param(value = "params") Map<String, Object> params, Page page);
	
	/**
	 * 获取总金额
	 * @param orderForm
	 * @return
	 */
	Double getTotalPayment(@Param(value = "order") OrderForm orderForm, @Param(value = "params") Map<String, Object> params);

	/**
	 * 分销订单
	 * @param orderForm 订单信息
	 * @param params 查询参数
	 * @param page 分页参数
	 * @return 订单列表
	 */
	List<OrderForm> distributorListPage(@Param(value = "order") OrderForm orderForm, @Param(value = "params") Map<String, Object> params, Page page);

	/**
	 * 统计每天的订单数
	 * @param parameter 统计参数
	 * @return 统计结果
	 */
	List<HashMap<String, Integer>> countByDate(@Param(value = "parameter") HashMap<String, Object> parameter);

	/**
	 * 统计所有订单
	 * @param parameter 统计参数
	 * @return 统计结果
	 */
	Integer count(@Param(value = "parameter") HashMap<String, Object> parameter);

    OrderForm getOrderByCondition(@Param("order") OrderForm t, @Param("params") Map<String, Object> params);
}
