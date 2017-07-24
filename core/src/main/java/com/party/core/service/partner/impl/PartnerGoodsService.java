package com.party.core.service.partner.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.core.dao.read.partner.PartnerGoodsReadDao;
import com.party.core.dao.write.partner.PartnerGoodsWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.partner.PartnerGoods;
import com.party.core.service.partner.IPartnerGoodsService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 合作商商品服务实现
 * party
 * Created by wei.li
 * on 2016/10/20 0020.
 */
@Service
public class PartnerGoodsService implements IPartnerGoodsService {

    @Autowired
    PartnerGoodsReadDao partnerGoodsReadDao;

    @Autowired
    PartnerGoodsWriteDao partnerGoodsWriteDao;


    /**
     * 插入合作商商品
     * @param partnerGoods 合作商商品
     * @return 商品主键
     */
    @Override
    public String insert(PartnerGoods partnerGoods) {
        BaseModel.preInsert(partnerGoods);
        boolean result = partnerGoodsWriteDao.insert(partnerGoods);
        if (result){
            return partnerGoods.getId();
        }
        return null;
    }

    /**
     * 更新合作商商品
     * @param partnerGoods 合作商商品
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(PartnerGoods partnerGoods) {
        return partnerGoodsWriteDao.update(partnerGoods);
    }

    /**
     * 逻辑删除合作商商品
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return partnerGoodsWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除合作商商品
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return partnerGoodsWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除合作商商品
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return partnerGoodsWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除合作商商品
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return partnerGoodsWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键查找合作商商品
     * @param id 主键
     * @return 合作商商品
     */
    @Override
    public PartnerGoods get(String id) {
        return partnerGoodsReadDao.get(id);
    }


    /**
     * 根据商铺商品编号查找合作商商品
     * @param id 商铺商品编号
     * @return 合作商商品
     */
    @Override
    public PartnerGoods findByStoreGoodsId(String id) {
        if (!Strings.isNullOrEmpty(id)){
            PartnerGoods partnerGoods = new PartnerGoods();
            partnerGoods.setStoreGoodsId(id);
            List<PartnerGoods> partnerGoodsList = this.list(partnerGoods);
            if (!CollectionUtils.isEmpty(partnerGoodsList)){
                return partnerGoodsList.get(0);
            }
        }
        return null;
    }

    /**
     * 分页查询合作商商品
     * @param partnerGoods 合作商商品
     * @param page 分页信息
     * @return 合作商商品列表
     */
    @Override
    public List<PartnerGoods> listPage(PartnerGoods partnerGoods, Page page) {
        return partnerGoodsReadDao.listPage(partnerGoods, page);
    }

    /**
     * 查询所有合作商商品
     * @param partnerGoods 合作商商品
     * @return 合作商商品列表
     */
    @Override
    public List<PartnerGoods> list(PartnerGoods partnerGoods) {
        return partnerGoodsReadDao.listPage(partnerGoods, null);
    }

    /**
     * 批量查询合作商商品
     * @param ids 主键集合
     * @param partnerGoods 合作商商品
     * @param page 分页信息
     * @return 合作商商品列表
     */
    @Override
    public List<PartnerGoods> batchList(@NotNull Set<String> ids, PartnerGoods partnerGoods, Page page) {
        Map<String, Object> query = Maps.newHashMap();
        return partnerGoodsReadDao.batchList(ids, new HashedMap(), page);
    }
}
