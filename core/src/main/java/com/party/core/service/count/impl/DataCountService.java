package com.party.core.service.count.impl;

import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.core.dao.read.count.DataCountReadDao;
import com.party.core.dao.write.count.DataCountWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.count.DataCount;
import com.party.core.service.count.IDataCountService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 数据统计接口实现
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 19:41
 */

@Service
public class DataCountService implements IDataCountService {

    @Autowired
    private DataCountReadDao dataCountReadDao;

    @Autowired
    private DataCountWriteDao dataCountWriteDao;

    /**
     * 数据统计插入
     * @param dataCount
     * @return 更新结果（true/false）
     */
    @Override
    public String insert(DataCount dataCount) {
        BaseModel.preInsert(dataCount);
        boolean result = dataCountWriteDao.insert(dataCount);
        if (result){
            return dataCount.getId();
        }
        return null;
    }

    /**
     * 更新数据统计
     * @param dataCount 数据统计
     * @return 更新结果(true/false)
     */
    @Override
    public boolean update(DataCount dataCount) {
        return dataCountWriteDao.update(dataCount);
    }

    /**
     * 逻辑删除数据统计
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        return dataCountWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除数据统计
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean delete(@NotNull String id) {
        return dataCountWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        return dataCountWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        return dataCountWriteDao.batchDelete(ids);
    }

    /**
     * 获取统计结果
     * @param id 主键
     * @return 统计结果
     */
    @Override
    public DataCount get(String id) {
        return dataCountReadDao.get(id);
    }


    /**
     * 根据目标编号查询
     * @param targetId 目标编号
     * @return 统计
     */
    @Override
    public DataCount findByTargetId(String targetId) {
        DataCount dataCount = dataCountReadDao.findByTargetId(targetId);
        if (null == dataCount){
            dataCount = new DataCount();
            dataCount.setTargetId(targetId);
            this.insert(dataCount);
            return dataCount;
        }
        return dataCount;
    }


    /**
     * 统计销售指标
     * @param targetId 目标编号
     * @param payment 支付金额
     */
    @Override
    public void countSales(String targetId, Float payment){
        DataCount dataCount = this.findByTargetId(targetId);
        dataCount.setSalesNum(dataCount.getSalesNum() + 1);
        Float amount =  BigDecimalUtils.add(dataCount.getSalesAmount(), payment);
        amount = BigDecimalUtils.round(amount, 2);
        dataCount.setSalesAmount(amount);
        this.update(dataCount);
    }


    /**
     * 分页查询
     * @param dataCount 数据统计
     * @param page 分页信息
     * @return 统计结果
     */
    @Override
    public List<DataCount> listPage(DataCount dataCount, Page page) {
        return dataCountReadDao.listPage(dataCount, page);
    }

    /**
     * 查询所有
     * @param dataCount 数据统计
     * @return 统计结果
     */
    @Override
    public List<DataCount> list(DataCount dataCount) {
        return dataCountReadDao.listPage(dataCount, null);
    }

    /**
     * 批量查询
     * @param ids 主键集合
     * @param dataCount
     * @param page 分页信息
     * @return
     */
    @Override
    public List<DataCount> batchList(@NotNull Set<String> ids, DataCount dataCount, Page page) {
        return null;
    }
}
