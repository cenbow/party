package com.party.core.service.love.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.love.LoveReadDao;
import com.party.core.dao.write.love.LoveWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.love.Love;
import com.party.core.service.love.ILoveService;
import com.sun.istack.NotNull;

/**
 * 点赞服务实现
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */

@Service
public class LoveService implements ILoveService {

    @Autowired
    LoveReadDao loveReadDao;

    @Autowired
    LoveWriteDao loveWriteDao;


    /**
     * 点赞插入
     * @param love 点赞信息
     * @return 插入结果(true/false)
     */
    @Override
    public String insert(Love love) {
        BaseModel.preInsert(love);
        boolean result = loveWriteDao.insert(love);
        if (result){
            return love.getId();
        }
        return null;
    }


    /**
     * 点赞更新
     * @param love 点赞信息
     * @return 更新结果(true/false)
     */
    @Override
    public boolean update(Love love) {
        love.setUpdateDate(new Date());
        return loveWriteDao.update(love);
    }

    /**
     * 逻辑删除点赞信息
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return loveWriteDao.deleteLogic(id);
    }


    /**
     * 物理删除点赞信息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return loveWriteDao.delete(id);
    }


    /**
     * 批量逻辑删除点赞信息
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return loveWriteDao.batchDeleteLogic(ids);
    }


    /**
     * 批量物理删除点赞信息
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return loveWriteDao.batchDelete(ids);
    }


    /**
     * 根据主键获取点赞信息
     * @param id 主键
     * @return 点赞信息
     */
    @Override
    public Love get(String id) {
        return loveReadDao.get(id);
    }

    /**
     * 分页查询点赞信息
     * @param love 点赞信息
     * @param page 分页信息
     * @return 点赞信息列表
     */
    @Override
    public List<Love> listPage(Love love, Page page) {
        return loveReadDao.listPage(love, page);
    }

    /**
     * 查询所有点赞信息
     * @param love 点赞信息
     * @return 点赞信息列表
     */
    @Override
    public List<Love> list(Love love) {
        return loveReadDao.listPage(love, null);
    }


    /**
     * 批量查询点赞信息
     * @param ids 主键集合
     * @param love 点赞信息
     * @param page 分页信息
     * @return 点赞信息列表
     */
    @Override
    public List<Love> batchList(@NotNull Set<String> ids, Love love, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return loveReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 统计点赞数
     * @param entity
     * @return
     */
    @Override
    public Integer countLove(Love entity) {
        return loveReadDao.countLove(entity);
    }

    /**
     * 根据关联的动态id及创建者会员id，查找一条点赞记录
     * @param dyId 关联的动态id
     * @param memberId 点赞的创建者会员id
     * @return
     */
    @Override
    public Love findByRefId(String dyId, String memberId) {
        if (Strings.isNullOrEmpty(dyId) || Strings.isNullOrEmpty(memberId))
             return null;

        //设置过滤条件
        Love entity = new Love();
        entity.setCreateBy(memberId);
        entity.setRefId(dyId);
        entity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

        List<Love> loveList = this.list(entity);

        if (CollectionUtils.isEmpty(loveList)){
            return null;
        }
        return loveList.get(0);
    }


	@Override
	public List<Love> webListPage(Love love, Map<String, Object> params, Page page) {
		return loveReadDao.webListPage(love, params, page);
	}

    @Override
    public void delByDynamicId(String id) {
        loveWriteDao.delByDynamicId(id);
    }
}
