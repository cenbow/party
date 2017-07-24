package com.party.core.service.system.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.system.LogReadDao;
import com.party.core.dao.write.system.LogWriteDao;
import com.party.core.model.system.Log;
import com.party.core.service.system.ILogService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 系统日志服务实现
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
@Service
public class LogService implements ILogService {

    @Autowired
    LogReadDao logReadDao;

    @Autowired
    LogWriteDao logWriteDao;


    /**
     * 系统日志插入
     * @param log 系统日志
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Log log) {
        log.setId(UUIDUtils.generateRandomUUID());
        boolean result = logWriteDao.insert(log);
        if (result){
            return log.getId();
        }
        return null;
    }


    /**
     * 系统日志更新
     * @param log 系统日志
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Log log) {
        return logWriteDao.update(log);
    }


    /**
     * 逻辑删除系统日志
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return logWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除系统日志
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return logWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除系统日志
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return logWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除系统日志
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return logWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取系统日志
     * @param id 主键
     * @return 系统日志
     */
    @Override
    public Log get(String id) {
        return logReadDao.get(id);
    }

    /**
     * 分页查询系统日志
     * @param log 系统日志
     * @param page 分页信息
     * @return 系统日志列表
     */
    @Override
    public List<Log> listPage(Log log, Page page) {
        return logReadDao.listPage(log, page);
    }

    /**
     * 查询所有系统日志
     * @param log 系统日志
     * @return 系统日志列表
     */
    @Override
    public List<Log> list(Log log) {
        return logReadDao.listPage(log, null);
    }


    /**
     * 批量查询系统日志
     * @param ids 主键集合
     * @param log 系统日志
     * @param page 分页信息
     * @return 系统日志列表
     */
    @Override
    public List<Log> batchList(@NotNull Set<String> ids, Log log, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return logReadDao.batchList(ids, new HashedMap(), page);
    }
}
