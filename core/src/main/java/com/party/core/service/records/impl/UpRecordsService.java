package com.party.core.service.records.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.dao.read.records.UpRecordsReadDao;
import com.party.core.dao.write.records.UpRecordsWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.records.UpRecordWithProject;
import com.party.core.model.records.UpRecords;
import com.party.core.service.records.IUpRecordsService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 跟进记录接口实现
 * Created by wei.li
 *
 * @date 2017/7/12 0012
 * @time 10:10
 */
@Service
public class UpRecordsService implements IUpRecordsService {

    @Autowired
    private UpRecordsReadDao upRecordsReadDao;

    @Autowired
    private UpRecordsWriteDao upRecordsWriteDao;

    /**
     * 插入根据记录
     * @param upRecords 根进记录
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(UpRecords upRecords) {
        BaseModel.preInsert(upRecords);
        boolean result = upRecordsWriteDao.insert(upRecords);
        if (result){
            return upRecords.getId();
        }
        return null;
    }

    /**
     * 更新跟进记录
     * @param upRecords 跟进记录
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(UpRecords upRecords) {
        return upRecordsWriteDao.update(upRecords);
    }

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return upRecordsWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return upRecordsWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return upRecordsWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return upRecordsWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键查找
     * @param id 主键
     * @return 跟进记录
     */
    @Override
    public UpRecords get(String id) {
        return upRecordsReadDao.get(id);
    }

    /**
     * 分页查询
     * @param upRecords 跟进记录
     * @param page 分页信息
     * @return 记录列表
     */
    @Override
    public List<UpRecords> listPage(UpRecords upRecords, Page page) {
        return upRecordsReadDao.listPage(upRecords, page);
    }

    /**
     * 查询所有跟进记录
     * @param upRecords 跟进记录
     * @return 记录列表
     */
    @Override
    public List<UpRecords> list(UpRecords upRecords) {
        return upRecordsReadDao.listPage(upRecords, null);
    }


    /**
     * 跟进记录列表
     * @param upRecordWithProject 查询参数
     * @param page 分页参数
     * @return 记录列表
     */
    @Override
    public List<UpRecordWithProject> listWithProject(UpRecordWithProject upRecordWithProject, Page page) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("projectTitle", upRecordWithProject.getProjectTitle());
        param.put("targetId", upRecordWithProject.getTargetId());
        return upRecordsReadDao.listWithProject(param, page);
    }

    @Override
    public List<UpRecords> batchList(@NotNull Set<String> ids, UpRecords upRecords, Page page) {
        return null;
    }
}
