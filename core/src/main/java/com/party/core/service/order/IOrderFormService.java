package com.party.core.service.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.party.common.paging.Page;
import com.party.core.model.order.OrderForm;
import com.party.core.service.IBaseService;

/**
 * 商品订单服务接口
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public interface IOrderFormService extends IBaseService<OrderForm>{
	
	/**
	 * 获取订单
	 * @param goodsId 商品
	 * @param memberId 用户
	 * @param isPay true表示查已支付的订单 false表示查该商品的有效订单
	 * @return
	 */
	List<OrderForm> getOrder(String goodsId, String memberId, Boolean isPay);
	
	/**
	 * 计算一共购买份数
	 * @param goodsId 商品
	 * @param memberId 用户
	 * @param isPay true表示查已支付的订单 false表示查该商品的有效订单
	 * @return
	 */
	int calculateBuyNum(String goodsId, String memberId, Boolean isPay, Integer type);
	
	int calculateBuyNum(String goodsId, String memberId, Boolean isPay, Integer type, Set<Integer> status);

	List<OrderForm> listPage(OrderForm orderForm, Page page, Set<Integer> status);

	/**
	 * web端
	 * @param orderForm
	 * @param params
	 * @param page
	 * @return
	 */
	List<OrderForm> webListPage(OrderForm orderForm, Map<String, Object> params, Page page);

	/**
	 * 作废订单列表
	 * @return 作废订单列表
	 */
	List<OrderForm> invalidList();

	/**
	 * 获取总金额
	 * @param orderForm
	 * @return
	 */
	Double getTotalPayment(OrderForm orderForm, Map<String, Object> params);

	/**
	 * 分销订单
	 * @param orderForm 订单信息
	 * @param params 查询参数
	 * @param page 分页参数
	 * @return 订单列表
	 */
	List<OrderForm> distributorListPage(OrderForm orderForm, Map<String, Object> params, Page page);

	/**
	 * 统计每天的下单了
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 统计结果
	 */
	List<HashMap<String, Integer>> countByDate(String startDate, String endDate);

	/**
	 * 查询所有统计
	 * @return 统计数
	 */
	Integer allCount();

    OrderForm getOrderByCondition(OrderForm t, Map<String, Object> params);
}
