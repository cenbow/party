package com.party.core.service.circle.impl;

import java.util.*;

import com.party.core.model.circle.Circle;
import com.party.core.model.circle.ListAllMember;
import com.party.core.model.member.Member;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.circle.CircleMemberReadDao;
import com.party.core.dao.write.circle.CircleMemberWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.circle.CircleMember;
import com.party.core.service.circle.ICircleMemberService;
import com.sun.istack.NotNull;

/**
 * CircleService圈子服务实现
 *
 * @author Juliana
 * @data 2016-12-14
 */

@Service
public class CircleMemberService implements ICircleMemberService {

    @Autowired
    private CircleMemberReadDao circleMemberReadDao;
    @Autowired
    private CircleMemberWriteDao circleMemberWriteDao;

    /**
     * 插入圈子数据
     *
     * @param circleMember 圈子实体
     * @return 插入结果（true/false）
     */
    public String insert(CircleMember circleMember) {
        BaseModel.preInsert(circleMember);
        boolean result = circleMemberWriteDao.insert(circleMember);
        if (result) {
            return circleMember.getId();
        }
        return null;
    }

    /**
     * 更新圈子信息
     *
     * @param circleMember 圈子实体
     * @return 更新结果（true/false）
     */
    public boolean update(CircleMember circleMember) {
        if (null == circleMember)
            return false;
        circleMember.setUpdateDate(new Date());
        return circleMemberWriteDao.update(circleMember);
    }

    /**
     * 逻辑删除圈子数据
     *
     * @param id 圈子实体主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleMemberWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除圈子数据
     *
     * @param id 圈子实体主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (StringUtils.isBlank(id))
            return false;

        return circleMemberWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除圈子数据
     *
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleMemberWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除圈子数据
     *
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return circleMemberWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取圈子实体数据
     *
     * @param id 主键
     * @return 圈子实体
     */
    public CircleMember get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return circleMemberReadDao.get(id);
    }

    /**
     * 分页查询圈子列表
     *
     * @param circleMember 圈子实体
     * @param page         分页信息
     * @return 圈子列表
     */
    public List<CircleMember> listPage(CircleMember circleMember, Page page) {
        return circleMemberReadDao.listPage(circleMember, page);
    }

    /**
     * 分页查询圈子列表
     *
     * @param circleMember 圈子实体
     * @param page         分页信息
     * @return 圈子列表
     */
    public List<CircleMember> listPageSearch(CircleMember circleMember, Map<String, Object> params, Page page) {
        return circleMemberReadDao.listPageSearch(circleMember, params, page);
    }

    /**
     * 查询所有圈子数据
     *
     * @param circleMember 圈子实体
     * @return 圈子列表
     */
    public List<CircleMember> list(CircleMember circleMember) {
        return circleMemberReadDao.listPage(circleMember, null);
    }

    /**
     * 根据圈子主键集合查询圈子数据
     *
     * @param ids          主键集合
     * @param circleMember 圈子实体
     * @param page         分页信息
     * @return 圈子列表
     */
    public List<CircleMember> batchList(@NotNull Set<String> ids, CircleMember circleMember, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return circleMemberReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 查询单个圈子用户关系
     *
     * @param circleMember 圈子实体
     * @return 圈子列表
     */
    public CircleMember getUnique(CircleMember circleMember) {
        return circleMemberReadDao.getUnique(circleMember);
    }

    /**
     * web端圈子成员查询
     *
     * @param circleMember
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> webListPage(CircleMember circleMember, Map<String, Object> params, Page page) {
        return circleMemberReadDao.webListPage(circleMember, params, page);
    }

    /**
     * 查询所有用户
     */
    @Override
    public List<ListAllMember> listAll(CircleMember circleMember, Map<String, Object> params, Set<String> tags) {
        return circleMemberReadDao.listAll(circleMember, params, tags);
    }
    /**
     * 查询所有用户
     * 通过类型排序
     */
    @Override
    public List<ListAllMember> listAllByType(CircleMember circleMember, Map<String, Object> params, Set<String> tags) {
        return circleMemberReadDao.listAllByType(circleMember, params, tags);
    }

    /**
     * 根据手机获取用户
     */
    @Override
    public CircleMember getByMobile(CircleMember circleMember, Map<String, Object> params){
        return circleMemberReadDao.getByMobile(circleMember,params);
    }
}
