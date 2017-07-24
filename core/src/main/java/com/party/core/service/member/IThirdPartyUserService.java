package com.party.core.service.member;

import java.util.List;
import java.util.Map;

import com.party.core.model.member.ThirdPartyUser;
import com.party.core.service.IBaseService;

/**
 * 第三方用户服务接口
 * party
 * Created by wei.li
 * on 2016/8/12 0012.
 */
public interface IThirdPartyUserService extends IBaseService<ThirdPartyUser>{
    /**
     * 根据openId查询实体
     * @param openId 主键
     * @return 实体信息
     */
    public ThirdPartyUser getByOpenId(String openId);
    /**
     * 根据memberId查询实体
     * @param memberId 主键
     * @return 实体信息
     */
    public List<ThirdPartyUser> getByMemberId(String memberId);
    /**
     * 根据unionId查询实体
     * @param unionId
     * @return
     */
	public ThirdPartyUser getByUnionId(String unionId);
	/**
	 * 获取 member 和指定 type 的授权信息
	 * @param tpUser
	 * @return
	 */
	public List<ThirdPartyUser> getTypeUser(Integer type, String memberId);

    /**
     * 根据unionId 和type查询实体
     * @param unionId
     * @param type
     * @return
     */
	public ThirdPartyUser getByUnionIdType(String unionId, Integer type);

	/**
	 * 获取第三方用户
	 * @param type 类型
	 * @param memberId 会员编号
	 * @param appId 公众号编号
	 * @return 第三方用户列表
	 */
	List<ThirdPartyUser> get(Integer type, String memberId, String appId);

	/**
	 * 根据用户id获取第三方授权信息
	 * @param memberId
	 * @return
	 */
	List<Map<String,Object>> getAuthList(String memberId);
}
