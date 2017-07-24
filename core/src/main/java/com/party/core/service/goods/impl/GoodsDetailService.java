package com.party.core.service.goods.impl;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.goods.GoodsDetailReadDao;
import com.party.core.dao.write.goods.GoodsDetailWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.goods.GoodsDetail;
import com.party.core.service.goods.IGoodsDetailService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.WebFault;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * IGoodsService 商品详情接口实现
 *
 * @author Wesley
 * @data 16/9/7 16:57 .
 */
@Service
public class GoodsDetailService implements IGoodsDetailService {
    @Autowired
    private GoodsDetailReadDao readDao;
    @Autowired
    private GoodsDetailWriteDao writeDao;

    /**
     * 根据refID（商品主键）获取商品详情数据
     * @param refId
     * @return
     */
    @Override
    public GoodsDetail getByRefId(String refId) {
        if (StringUtils.isBlank(refId))
            return null;

        return readDao.getByRefId(refId);
    }

    /**
     * 插入商品详情数据
     * @param goodsDetail
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(GoodsDetail goodsDetail) {
        BaseModel.preInsert(goodsDetail);
        boolean result = writeDao.insert(goodsDetail);
        if (result){
            return goodsDetail.getId();
        }
        return null;
    }

    /**
     * 更新商品详情数据
     * @param goodsDetail
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(GoodsDetail goodsDetail) {
        if (null == goodsDetail)
            return false;
        goodsDetail.setUpdateDate(new Date());
        return writeDao.update(goodsDetail);
    }

    /**
     * 逻辑删除商品详情数据
     * @param id 实体主键
     * @return 逻辑删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.deleteLogic(id);
    }

    /**
     * 物理删除商品详情
     * @param id 实体主键
     * @return 物理删除结果（true/false）
     */
    @Override
    public boolean delete(String id) {
        if (StringUtils.isBlank(id))
            return false;

        return writeDao.delete(id);
    }

    /**
     * 批量逻辑删除商品详情数据
     * @param ids 主键集合
     * @return 批量删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return writeDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除商品详情数据
     * @param ids 主键集合
     * @return 批量物理删除结果（true/false）
     */
    @Override
    public boolean batchDelete(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids))
            return false;

        return writeDao.batchDelete(ids);
    }

    /**
     * 根据主键获取商品详情数据
     * @param id 主键
     * @return 商品详情主体
     */
    @Override
    public GoodsDetail get(String id) {
        if (StringUtils.isBlank(id))
            return null;

        return readDao.get(id);
    }

    /**
     * 分页查询商品详情列表
     * @param goodsDetail
     * @param page 分页信息
     * @return 商品详情列表
     */
    @Override
    public List<GoodsDetail> listPage(GoodsDetail goodsDetail, Page page) {
        return readDao.listPage(goodsDetail, page);
    }

    /**
     * 查询所有商品详情列表
     * @param goodsDetail
     * @return 商品详情列表
     */
    @Override
    public List<GoodsDetail> list(GoodsDetail goodsDetail) {
        return readDao.listPage(goodsDetail, null);
    }

    /**
     * 批量分页查询商品详情列表
     * @param ids 主键集合
     * @param goodsDetail
     * @param page 分页信息
     * @return 商品详情列表
     */
    @Override
    public List<GoodsDetail> batchList(Set<String> ids, GoodsDetail goodsDetail, Page page) {
        if (CollectionUtils.isEmpty(ids))
            return Collections.EMPTY_LIST;

        return readDao.batchList(ids, new HashedMap(), page);
    }
}
