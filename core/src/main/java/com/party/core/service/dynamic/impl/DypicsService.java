package com.party.core.service.dynamic.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.dynamic.DypicsReadDao;
import com.party.core.dao.write.dynamic.DypicsWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.dynamic.Dypics;
import com.party.core.service.dynamic.IDypicsService;
import com.party.core.service.picCloud.PicCloudBizService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 动态评论服务实现
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Service
public class DypicsService implements IDypicsService{

    @Autowired
    DypicsReadDao dypicsReadDao;

    @Autowired
    DypicsWriteDao dypicsWriteDao;

    @Autowired
    PicCloudBizService picCloudBizService;

    /**
     * 动态图片插入
     * @param dypics 动态评论
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Dypics dypics) {
        BaseModel.preInsert(dypics);
        boolean result = dypicsWriteDao.insert(dypics);
        if (result){
            return dypics.getId();
        }
        return null;
    }

    /**
     * 动态图片更新
     * @param dypics 动态图片
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Dypics dypics) {
        dypics.setUpdateDate(new Date());
        return dypicsWriteDao.update(dypics);
    }

    /**
     * 逻辑删除动态图片
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return dypicsWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除动态图片
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return dypicsWriteDao.delete(id);
    }


    /**
     * 批量逻辑删除动态图片
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return dypicsWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除动态图片
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return dypicsWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取动态图片
     * @param id 主键
     * @return 动态图片
     */
    @Override
    public Dypics get(String id) {
        return dypicsReadDao.get(id);
    }

    /**
     * 分页查询动态图片
     * @param dypics 动态图片
     * @param page 分页信息
     * @return 动态图片列表
     */
    @Override
    public List<Dypics> listPage(Dypics dypics, Page page) {
        return dypicsReadDao.listPage(dypics, page);
    }

    /**
     * 查询所有动态图片
     * @param dypics 动态图片
     * @return 动态图片列表
     */
    @Override
    public List<Dypics> list(Dypics dypics) {
        return dypicsReadDao.listPage(dypics, null);
    }

    /**
     * 批量查询动态图片
     * @param ids 主键集合
     * @param dypics 动态图片
     * @param page 分页信息
     * @return 动态图片列表
     */
    @Override
    public List<Dypics> batchList(@NotNull Set<String> ids, Dypics dypics, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return dypicsReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 统计图片数
     * @param enttity 筛选调条件
     * @return
     */
    @Override
    public Integer countPics(Dypics enttity) {
        return dypicsReadDao.countPics(enttity);
    }

    /**
     * 根据动态id查找统统图片
     * @param dynamicId 关联的动态id
     * @param page
     * @return 动态图片列表
     */
    @Override
    public List<Dypics> findByDynamicId(String dynamicId, Page page) {
        if (Strings.isNullOrEmpty(dynamicId))
        {
            return Collections.EMPTY_LIST;
        }

        Dypics entity = new Dypics();
        entity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        entity.setRefId(dynamicId);
        return this.listPage(entity, page);
    }

    /**
     * 根据会员id主键（图片图片创建者id）查找图片
     * @param memberId
     * @param page
     * @return 返回图片列表
     */
    @Override
    public List<Dypics> findByMemberId(String memberId, Page page) {
        if (Strings.isNullOrEmpty(memberId))
        {
            return Collections.EMPTY_LIST;
        }

        Dypics entity = new Dypics();
        entity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        entity.setCreateBy(memberId);
        return this.listPage(entity, page);
    }

	@Override
	public boolean deleteByRefId(String id) {
        List<Dypics> pics = this.findByDynamicId(id,null);
        for(Dypics pic:pics){
            int status = picCloudBizService.delByUrl(pic.getPicUrl());
            if(status == 0){
                this.delete(pic.getId());
            }else{
                pic.setDelFlag(BaseModel.DEL_FLAG_DELETE);
                this.update(pic);
            }
        }
        return true;
	}


}
