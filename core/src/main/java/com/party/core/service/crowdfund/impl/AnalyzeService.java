package com.party.core.service.crowdfund.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.crowdfund.AnalyzeReadDao;
import com.party.core.dao.write.crowdfund.AnalyzeWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.crowdfund.Analyze;
import com.party.core.service.crowdfund.IAnalyzeService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 众筹分析接口实现
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 15:17
 */

@Service
public class AnalyzeService implements IAnalyzeService {

    @Autowired
    private AnalyzeReadDao analyzeReadDao;

    @Autowired
    private AnalyzeWriteDao analyzeWriteDao;

    /**
     * 插入众筹分析
     * @param analyze 众筹分析
     * @return 编号
     */
    @Override
    public String insert(Analyze analyze) {
        BaseModel.preInsert(analyze);
        boolean result = analyzeWriteDao.insert(analyze);
        if (result){
            return analyze.getId();
        }
        return null;
    }

    /**
     * 更新众筹分析
     * @param analyze 众筹分析
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Analyze analyze) {
        return analyzeWriteDao.update(analyze);
    }

    /**
     * 逻辑删除众筹分析
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return analyzeWriteDao.deleteLogic(id);
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
        return analyzeWriteDao.delete(id);
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
        return analyzeWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return analyzeWriteDao.batchDelete(ids);
    }

    /**
     * 获取众筹分析
     * @param id 主键
     * @return 众筹分析
     */
    @Override
    public Analyze get(String id) {
        return analyzeReadDao.get(id);
    }

    /**
     * 分页查询
     * @param analyze 众筹分析
     * @param page 分页信息
     * @return 列表数据
     */
    @Override
    public List<Analyze> listPage(Analyze analyze, Page page) {
        return analyzeReadDao.listPage(analyze, page);
    }

    /**
     * 查询所有
     * @param analyze 众筹分析
     * @return 列表数据
     */
    @Override
    public List<Analyze> list(Analyze analyze) {
        return analyzeReadDao.listPage(analyze, null);
    }


    @Override
    public List<Analyze> batchList(@NotNull Set<String> ids, Analyze analyze, Page page) {
        return null;
    }

    /**
     * 根据目标编号查询
     * @param targetId 目标编号
     * @return 众筹分析
     */
    @Override
    public Analyze findByTargetId(String targetId) {
        return analyzeReadDao.findByTargetId(targetId);
    }
}
