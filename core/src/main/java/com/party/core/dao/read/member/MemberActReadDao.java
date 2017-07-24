package com.party.core.dao.read.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.member.ApplyWithActivity;
import com.party.core.model.member.MemberAct;

import com.party.core.model.member.WithBuyer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 活动报名数据读取 party Created by wei.li on 2016/9/18 0018.
 */
@Repository
public interface MemberActReadDao extends BaseReadDao<MemberAct> {

	/**
	 * 查询当前用户发布的活动的报名数据
	 * 
	 * @param memberAct
	 * @return
	 */
	List<MemberAct> activityListPage(MemberAct memberAct, Page page);
	
	List<MemberAct> getSuccessMemberAct(@Param(value = "memberAct") MemberAct memberAct, @Param(value = "status") Set<Integer> status);
	
	/**
	 * 获取有效的报名数量
	 * @param params
	 * @return
	 */
	Integer getSuccessMemberAct2(@Param(value = "params") Map<String, Object> params);

	/**
	 * 根据主键集合批量查询列表
	 * @param ids 主键集合
	 * @param param 查询参数
	 * @param page 分页信息
	 * @return 实体列表
	 */
	List<ApplyWithActivity> batchWithActivityList(@Param("ids") Set<String> ids, @Param("param")Map<String , Object> param, Page page);
	
	/**
	 * web端
	 * @param memberAct
	 * @param params
	 * @param page
	 * @return
	 */
	List<MemberAct> webListPage(@Param(value = "memberAct") MemberAct memberAct, @Param(value = "params") Map<String, Object> params, Page page);

	MemberAct findByOrderId(String orderId);

	/**
	 * 报名信息列表
	 * @param withBuyer 报名信息
	 * @param page 分页参数
	 * @return 报名信息列表
	 */
	List<WithBuyer> withBuyerList(WithBuyer withBuyer, Page page);
	
	/**
	 * 过滤众筹项目报名
	 * @param memberAct
	 * @param page
	 * @return
	 */
	List<MemberAct> listPageTwo(MemberAct memberAct, Page page);

	/**
	 * 统计每天的报名数
	 * @param parameter 统计参数
	 * @return 统计结果
	 */
	List<HashMap<String, Integer>> countByDate(@Param(value = "parameter") HashMap<String, Object> parameter);

	/**
	 * 统计报名数
	 * @param parameter 统计参数
	 * @return 统计结果
	 */
	Integer count(@Param(value = "parameter") HashMap<String, Object> parameter);

	/**
	 * 报名信息列表
	 * @param withBuyer 报名信息
	 * @param page 分页参数
	 * @return 报名信息列表
	 */
	List<WithBuyer> withActivityList(@Param(value = "withBuyer") WithBuyer withBuyer, @Param(value = "params") Map<String, Object> params, Page page);
}
