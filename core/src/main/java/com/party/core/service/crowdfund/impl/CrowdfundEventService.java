package com.party.core.service.crowdfund.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.crowdfund.CrowdfundEventReadDao;
import com.party.core.dao.write.crowdfund.CrowdfundEventWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.crowdfund.CrowdfundEvent;
import com.party.core.service.crowdfund.ICrowdfundEventService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 众筹事件接口实现
 * Created by wei.li
 *
 * @date 2017/4/25 0025
 * @time 19:25
 */

@Service
public class CrowdfundEventService implements ICrowdfundEventService {

    @Autowired
    private CrowdfundEventReadDao crowdfundEventReadDao;

    @Autowired
    private CrowdfundEventWriteDao crowdfundEventWriteDao;


    /**
     * 插入众筹事件
     * @param crowdfundEvent 众筹事件
     * @return 事件编号
     */
    @Override
    public String insert(CrowdfundEvent crowdfundEvent) {
        BaseModel.preInsert(crowdfundEvent);
        boolean result = crowdfundEventWriteDao.insert(crowdfundEvent);
        if (result){
            return crowdfundEvent.getId();
        }
        return null;
    }

    /**
     * 更新众筹事件
     * @param crowdfundEvent 众筹事件
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(CrowdfundEvent crowdfundEvent) {
        return crowdfundEventWriteDao.update(crowdfundEvent);
    }

    /**
     * 逻辑删除众筹事件
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return crowdfundEventWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除众筹事件
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return crowdfundEventWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除众筹事件
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return crowdfundEventWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除众筹事件
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return crowdfundEventWriteDao.batchDelete(ids);
    }

    /**
     * 根据编号获取
     * @param id 主键
     * @return 众筹事件
     */
    @Override
    public CrowdfundEvent get(String id) {
        return crowdfundEventReadDao.get(id);
    }

    /**
     * 众筹事件列表
     * @param crowdfundEvent 众筹事件
     * @param page 分页信息
     * @return 事件列表
     */
    @Override
    public List<CrowdfundEvent> listPage(CrowdfundEvent crowdfundEvent, Page page) {
        return crowdfundEventReadDao.listPage(crowdfundEvent, page);
    }

    /**
     * 所有众筹事件列表
     * @param crowdfundEvent 众筹事件
     * @return 事件列表
     */
    @Override
    public List<CrowdfundEvent> list(CrowdfundEvent crowdfundEvent) {
        return crowdfundEventReadDao.listPage(crowdfundEvent, null);
    }

    /**
     * 批量查询众筹事件
     * @param ids 主键集合
     * @param crowdfundEvent 众筹事件
     * @param page 分页信息
     * @return 事件列表
     */
    @Override
    public List<CrowdfundEvent> batchList(@NotNull Set<String> ids, CrowdfundEvent crowdfundEvent, Page page) {
        return null;
    }
}
