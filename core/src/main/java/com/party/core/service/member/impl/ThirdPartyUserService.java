package com.party.core.service.member.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.member.ThirdPartyUserReadDao;
import com.party.core.dao.write.member.ThirdPartyUserWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.ThirdPartyUser;
import com.party.core.service.member.IThirdPartyUserService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 第三方用户实现
 * party
 * Created by wei.li
 * on 2016/8/12 0012.
 */

@Service
public class ThirdPartyUserService implements IThirdPartyUserService {

    @Autowired
    ThirdPartyUserReadDao thirdPartyUserReadDao;

    @Autowired
    ThirdPartyUserWriteDao thirdPartyUserWriteDao;


    /**
     * 插入第三方用户
     * @param thirdPartyUser 第三方用户信息
     * @return 插入结果（true/false）
     */
    public String insert(ThirdPartyUser thirdPartyUser) {
        BaseModel.preInsert(thirdPartyUser);
        boolean result = thirdPartyUserWriteDao.insert(thirdPartyUser);
        if (result){
            return thirdPartyUser.getId();
        }
        return null;
    }

    /**
     * 更新第三方用户信息
     * @param thirdPartyUser 第三方用户信息
     * @return 更新结果（true/false）
     */
    public boolean update(ThirdPartyUser thirdPartyUser) {
        thirdPartyUser.setUpdateDate(new Date());
        return thirdPartyUserWriteDao.update(thirdPartyUser);
    }

    /**
     * 逻辑删除第三方用户信息
     * @param id 第三方用户主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return thirdPartyUserWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除第三方用户信息
     * @param id 第三方用户主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return thirdPartyUserWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除第三方用户
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return thirdPartyUserWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除第三方用户
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return thirdPartyUserWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取第三方用户信息
     * @param id 主键
     * @return 第三方用户信息
     */
    public ThirdPartyUser get(String id) {
        return thirdPartyUserReadDao.get(id);
    }

    /**
     * 分页查询第三方用户信息
     * @param thirdPartyUser 第三方用户信息
     * @param page 分页信息
     * @return 第三方用户列表
     */
    public List<ThirdPartyUser> listPage(ThirdPartyUser thirdPartyUser, Page page) {
        return thirdPartyUserReadDao.listPage(thirdPartyUser, page);
    }

    /**
     * 查询所以第三方用户信息
     * @param thirdPartyUser 第三方用户信息
     * @return 第三方用户信息列表
     */
    public List<ThirdPartyUser> list(ThirdPartyUser thirdPartyUser) {
        return thirdPartyUserReadDao.listPage(thirdPartyUser, null);
    }

    /**
     * 获取第三方用户
     * @param type 类型
     * @param memberId 会员编号
     * @param appId 公众号编号
     * @return 第三方用户列表
     */
    @Override
    public List<ThirdPartyUser> get(Integer type, String memberId, String appId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("memberId", memberId);
        param.put("type", type);
        param.put("appId", appId);
        return thirdPartyUserReadDao.listForAppId(param);
    }

    @Override
    public List<Map<String, Object>> getAuthList(String memberId) {
        return thirdPartyUserReadDao.getAuthList(memberId);
    }

    /**
     * 批量查询第三方用户信息
     * @param ids 主键集合
     * @param thirdPartyUser 第三方用户信息
     * @param page 分页信息
     * @return 第三方用户列表
     */
    public List<ThirdPartyUser> batchList(@NotNull Set<String> ids, ThirdPartyUser thirdPartyUser, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return thirdPartyUserReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 根据openId查询实体
     * @param openId 主键
     * @return 实体信息
     */
    public ThirdPartyUser getByOpenId(String openId)
    {
        if (Strings.isNullOrEmpty(openId))
        {
            return null;
        }

        return thirdPartyUserReadDao.getByOpenId(openId);
    }
    
    /**
     * 根据UnionId查询实体
     * @param unionId 主键
     * @return 实体信息
     */
    public ThirdPartyUser getByUnionId(String unionId)
    {
        if (Strings.isNullOrEmpty(unionId))
        {
            return null;
        }

        return thirdPartyUserReadDao.getByUnionId(unionId);
    }
    
    /**
     * 根据memberId查询实体
     * @param memberId 主键
     * @return 实体信息
     */
    public List<ThirdPartyUser> getByMemberId(String memberId)
    {
        if (Strings.isNullOrEmpty(memberId))
        {
            return null;
        }

        return thirdPartyUserReadDao.getByMemberId(memberId);
    }

	@Override
	public List<ThirdPartyUser> getTypeUser(Integer type, String memberId) {
		if (Strings.isNullOrEmpty(memberId))
        {
            return null;
        }

        return thirdPartyUserReadDao.getTypeUser(type, memberId);
	}

	@Override
	public ThirdPartyUser getByUnionIdType(String unionId, Integer type) {
		if (Strings.isNullOrEmpty(unionId))
        {
            return null;
        }

        return thirdPartyUserReadDao.getByUnionIdType(unionId,type);
    }
}
