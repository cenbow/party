package com.party.core.service.member.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.member.IndustryReadDao;
import com.party.core.dao.write.member.IndustryWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Industry;
import com.party.core.redisDao.Industry.IndustryRedisDao;
import com.party.core.service.member.IIndustryService;
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
 * party
 * Created by wei.li
 * on 2016/8/13 0013.
 */
@Service
public class IndustryService implements IIndustryService {

    @Autowired
    private IndustryReadDao industryReadDao;

    @Autowired
    private IndustryWriteDao industryWriteDao;

    @Autowired
    private IndustryRedisDao industryRedisDao;

    private static final String  REDIS_LIST_KEY = "industryListKey";
    /**
     * 行业插入
     * @param industry 行业信息
     * @return 插入结果（true/false）
     */
    public String insert(Industry industry) {
        BaseModel.preInsert(industry);
        boolean result = industryWriteDao.insert(industry);
        if (result){
            return industry.getId();
        }
        return null;
    }


    /**
     * 行业更新
     * @param industry 行业信息
     * @return 更新结果（true/false）
     */
    public boolean update(Industry industry) {
        industry.setUpdateDate(new Date());
        return industryWriteDao.update(industry);
    }


    /**
     * 行业逻辑删除
     * @param id 行业主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return industryWriteDao.deleteLogic(id);
    }

    /**
     * 行业物理删除
     * @param id 行业主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return industryWriteDao.delete(id);
    }


    /**
     * 行业逻辑批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return industryWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 行业物理批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return industryWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取行业信息
     * @param id 主键
     * @return 行业信息
     */
    public Industry get(String id) {
        return industryReadDao.get(id);
    }

    /**
     * 行业分页查询
     * @param industry 行业信息
     * @param page 分页信息
     * @return 行业列表
     */
    public List<Industry> listPage(Industry industry, Page page) {
        return industryReadDao.listPage(industry, page);
    }

    /**
     * 行业信息所有查询
     * @param industry 行业信息
     * @return 行业列表
     */
    public List<Industry> list(Industry industry) {
        return industryReadDao.listPage(industry, null);
    }


    /**
     * 查询所有行业
     * @return 行业列表
     */
    public List<Industry> listAll() {
        List<Industry> industryList = industryRedisDao.listAll(REDIS_LIST_KEY);
        if (!CollectionUtils.isEmpty(industryList)){
            return industryList;
        }

        industryList = industryReadDao.listPage(null, null);
        industryRedisDao.listPushAll(REDIS_LIST_KEY, industryList);
        return industryList;
    }


    /**
     * 行业批量查询
     * @param ids 主键集合
     * @param industry 行业信息
     * @param page 分页信息
     * @return 行业列表
     */
    public List<Industry> batchList(@NotNull Set<String> ids, Industry industry, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return industryReadDao.batchList(ids, new HashedMap(), page);
    }
}
