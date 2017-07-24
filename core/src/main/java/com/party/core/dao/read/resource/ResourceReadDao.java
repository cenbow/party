package com.party.core.dao.read.resource;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.resource.Resource;

/**
 * 资源数据读取
 * 
 * @author Administrator
 *
 */
@Repository
public interface ResourceReadDao extends BaseReadDao<Resource> {

	List<Resource> webListPage(@Param("resource") Resource resource, @Param(value = "params") Map<String, Object> params, Page page);
}
