package com.party.core.dao.read.goods;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.goods.Goods;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * GoodsReadDao
 *
 * @author Wesley
 * @data 16/9/7 16:02 .
 */
@Repository
public interface GoodsReadDao extends BaseReadDao<Goods> {

	List<Goods> webListPage(@Param(value = "goods") Goods goods, @Param(value = "params") Map<String, Object> params, Page page);
}
