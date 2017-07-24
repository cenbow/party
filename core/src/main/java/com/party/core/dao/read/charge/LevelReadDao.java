package com.party.core.dao.read.charge;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.charge.Level;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 等级
 */
@Repository
public interface LevelReadDao extends BaseReadDao<Level> {

    List<Level> webListPage(@Param("level") Level level, @Param("params") Map<String, Object> params, Page page);
}
