package com.party.core.dao.read.help;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.help.Help;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 帮助
 */
@Repository
public interface HelpReadDao extends BaseReadDao<Help> {

    List<Help> webListPage(@Param("help") Help help, @Param("params") Map<String, Object> params, Page page);

    List<Help> webListPage2(@Param("help") Help help, @Param("params") Map<String, Object> params, Page page);

    Integer getMaxSort(Help help);
}
