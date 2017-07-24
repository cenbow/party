package com.party.core.service.member.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.member.ExpertReadDao;
import com.party.core.dao.write.member.ExpertWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Expert;
import com.party.core.service.member.IExpertService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 达人实现
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */

@Service
public class ExpertService implements IExpertService {

    @Autowired
    private ExpertReadDao expertReadDao;

    @Autowired
    private ExpertWriteDao expertWriteDao;

    /**
     * 插入达人
     * @param expert 达人信息
     * @return 插入结果(true/false)
     */
    public String insert(Expert expert) {
        BaseModel.preInsert(expert);
        boolean result = expertWriteDao.insert(expert);
        if (result){
            return expert.getId();
        }
        return null;
    }


    /**
     * 更新达人
     * @param expert 达人信息
     * @return 更新结果（true/false）
     */
    public boolean update(Expert expert) {
        expert.setUpdateDate(new Date());
        return expertWriteDao.update(expert);
    }

    /**
     * 逻辑删除 达人
     * @param id 实体主键
     * @return 删除结果 （true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return expertWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除 达人
     * @param id 实体主键
     * @return 删除结果 （true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return expertWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除 达人
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return expertWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除 达人
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return expertWriteDao.batchDelete(ids);
    }


    /**
     * 根据主键获取达人
     * @param id 主键
     * @return 达人信息
     */
    public Expert get(String id) {
        return expertReadDao.get(id);
    }


    /**
     * 根据会员ID查找达人信息
     * @param memberId 会员ID
     * @return 达人信息
     */
    public Expert findByMemberId(String memberId) {
        Expert expert = new Expert();
        expert.setMemberId(memberId);
        List<Expert> expertList = this.list(expert);

        if (!CollectionUtils.isEmpty(expertList)){
            return expertList.get(0);
        }
        return null;
    }

    /**
     * 分页查询 达人信息
     * @param expert 达人信息
     * @param page 分页信息
     * @return 达人列表
     */
    public List<Expert> listPage(Expert expert, Page page) {
        return expertReadDao.listPage(expert,page);
    }

    /**
     * 查询所有达人信息
     * @param expert 达人信息
     * @return 达人列表
     */
    public List<Expert> list(Expert expert) {
        return expertReadDao.listPage(expert, null);
    }


    /**
     * 查询所有达人信息
     * @return 达人列表
     */
    public List<Expert> listAll() {
        return expertReadDao.listPage(null, null);
    }

    /**
     * 批量查询达人信息
     * @param ids 主键集合
     * @param expert 达人信息
     * @param page 分页信息
     * @return 达人信息列表
     */
    public List<Expert> batchList(@NotNull Set<String> ids, Expert expert, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return expertReadDao.batchList(ids, new HashedMap(), page);
    }
}
