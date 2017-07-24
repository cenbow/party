package com.party.core.dao.write.dynamic;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.dynamic.Dypics;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 动态图片数据写入
 * party
 * Created by Wesley
 * on 2016/11/17
 */

@Repository
public interface DypicsWriteDao extends BaseWriteDao<Dypics> {

	boolean deleteByRefId(@Param(value = "refId") String refId);
}
