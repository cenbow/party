package com.party.core.service.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.member.Member;
import com.party.core.service.IBaseService;
import com.sun.istack.NotNull;

/**
 * 会员服务接口 party Created by wei.li on 2016/8/12 0012.
 */
public interface IMemberService extends IBaseService<Member> {

	/**
	 * 根据电话号码查找会员
	 * 
	 * @param phone
	 *            电话号码
	 * @return 会员信息
	 */
	Member findByPhone(@NotNull String phone);

	/**
	 * 根据登录名查询会员信息
	 * 
	 * @param loginName
	 *            登陆名
	 * @return 会员信息
	 */
	Member findByLoginName(@NotNull String loginName);

	List<Member> listPage(Member member, Map<String, Object> params, Page page);

	/**
	 * 
	 * @param property username或者mobile
	 * @param id 
	 * @param type username或者mobile
	 * @return
	 */
	List<Member> checkUserName(String property, String id, String type);

    /**
     * 判断会员是否拥有角色
     * @param memberId 会员编号
     * @param roleCode 角色代码
     * @return 是否拥有（true/false）
     */
    boolean hasRole(String memberId, String roleCode);
	/**
	 * 查找用户列表
	 * @return
	 */
    List<Map<String,Object>> findMemberByPhoneOrName(String property, String value);


	/**
	 * 统计每天的会员数
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 统计数据
	 */
	List<HashMap<String, Integer>> countByDate(String startDate, String endDate);

	/**
	 * 所有会员
	 * @return 数量
	 */
	Integer allCount();

	/**
	 * 所有未审核的会员
	 * @return 数量
	 */
	Integer unauditedCount();

	/**
	 * 隐藏用户重要信息
	 * @param member
	 * @return
	 */
	public Member hideImportantInfo(Member member);

	/**
	 * 获取头像不是万象优图路径的用户
	 * @return
	 */
	List<Member> getRemoteLogoList(Integer limit);
}
