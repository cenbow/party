package com.party.core.service.feedback.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.feedback.FeedBackReadDao;
import com.party.core.dao.write.feedback.FeedBackWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.feedback.FeedBack;
import com.party.core.service.feedback.IFeedBackService;
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
 * 反馈服务实现
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Service
public class FeedBackService implements IFeedBackService{

    @Autowired
    FeedBackReadDao feedBackReadDao;

    @Autowired
    FeedBackWriteDao feedBackWriteDao;

    /**
     * 反馈插入
     * @param feedBack 反馈信息
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(FeedBack feedBack) {
        BaseModel.preInsert(feedBack);
        boolean result = feedBackWriteDao.insert(feedBack);
        if (result){
            return feedBack.getId();
        }
        return null;
    }

    /**
     * 反馈更新
     * @param feedBack 反馈信息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(FeedBack feedBack) {
        feedBack.setUpdateDate(new Date());
        return feedBackWriteDao.update(feedBack);
    }

    /**
     * 逻辑删除反馈
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return feedBackWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除反馈信息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return feedBackWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除反馈信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return feedBackWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除反馈信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return feedBackWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取反馈信息
     * @param id 主键
     * @return 反馈信息列表
     */
    @Override
    public FeedBack get(String id) {
        return feedBackReadDao.get(id);
    }

    /**
     * 分页查询反馈信息
     * @param feedBack 反馈信息
     * @param page 分页信息
     * @return 反馈列表
     */
    @Override
    public List<FeedBack> listPage(FeedBack feedBack, Page page) {
        return feedBackReadDao.listPage(feedBack, page);
    }

    /**
     * 查询所有反馈信息
     * @param feedBack 反馈信息
     * @return 反馈列表
     */
    @Override
    public List<FeedBack> list(FeedBack feedBack) {
        return feedBackReadDao.listPage(feedBack, null);
    }

    /**
     * 批量查询反馈信息
     * @param ids 主键集合
     * @param feedBack 反馈信息
     * @param page 分页信息
     * @return 反馈信息列表
     */
    @Override
    public List<FeedBack> batchList(@NotNull Set<String> ids, FeedBack feedBack, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return feedBackReadDao.batchList(ids, new HashedMap(), page);
    }
}
