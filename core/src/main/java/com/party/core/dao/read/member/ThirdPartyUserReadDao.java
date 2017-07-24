package com.party.core.dao.read.member;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.member.ThirdPartyUser;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 第三方用户数据读取
 * party
 * Created by wei.li
 * on 2016/8/11 0011.
 */
@Repository
public interface ThirdPartyUserReadDao extends BaseReadDao<ThirdPartyUser> {
    public ThirdPartyUser getByOpenId(String openId);

	public List<ThirdPartyUser> getByMemberId(String memberId);

	public ThirdPartyUser getByUnionId(String unionId);

	public List<ThirdPartyUser> getTypeUser(@Param("type")Integer type, @Param("memberId")String memberId);
	
	public ThirdPartyUser getByUnionIdType(@Param("unionId")String unionId, @Param("type")Integer type);

	/**
	 * 根据appid查询第三方用户
	 * @param param 参数
	 * @return 用户列表
	 */
	List<ThirdPartyUser> listForAppId(@Param("param")Map<String , Object> param);

	/**
	 * 根据用户id获取第三方授权信息
	 * @param memberId
	 * @return
	 */
	List<Map<String,Object>> getAuthList(@Param("memberId") String memberId);
}
