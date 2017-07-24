package com.party.core.dao.read.gatherInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.gatherInfo.GatherInfoGroup;

/**
 * 人员小组
 * 
 * @author Administrator
 *
 */
@Repository
public interface GatherInfoGroupReadDao extends BaseReadDao<GatherInfoGroup> {

	List<GatherInfoGroup> webListPage(@Param(value = "group") GatherInfoGroup group, @Param(value = "params") Map<String, Object> params, Page page);

}
