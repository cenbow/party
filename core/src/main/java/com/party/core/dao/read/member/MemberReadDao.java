package com.party.core.dao.read.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.member.Member;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 会员数据读取
 * party
 * Created by wei.li
 * on 2016/8/11 0011.
 */
@Repository
public interface MemberReadDao extends BaseReadDao<Member> {

    /**
     * 根据登录名查询会员信息
     * @param loginName 登陆名称
     * @return 会员信息
     */
    Member findByLoginName(String loginName);

	List<Member> webListPage(@Param("params") Map<String, Object> params, @Param("member") Member member, Page page);

	/**
	 * 
	 * @param property username或者mobile
	 * @param userId
	 * @param type username或者mobile
	 * @return
	 */
	List<Member> checkUserName(@Param("property") String property, @Param("userId") String userId, @Param("type") String type);

    List<Map<String,Object>> findMemberByPhoneOrName(@Param("property") String property, @Param("value") String value);

	/**
	 * 根据日期统计会员
	 * @param parameter 统计参数
	 * @return 统计数据
	 */
	List<HashMap<String, Integer>> countByDate(@Param(value = "parameter") HashMap<String, Object> parameter);

	/**
	 * 统计所有会员数
	 * @param parameter 统计参数
	 * @return 统计数据
	 */
	Integer count(@Param(value = "parameter") HashMap<String, Object> parameter);

	/**
	 * 获取用户头像是远程路径的用户
	 * @return
	 */
	List<Member> getRemoteLogoList(@Param(value = "limit") Integer limit);
}
