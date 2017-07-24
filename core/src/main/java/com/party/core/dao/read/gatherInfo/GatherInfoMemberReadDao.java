package com.party.core.dao.read.gatherInfo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.gatherInfo.GatherInfoMemberOutput;

/**
 * 人员信息
 * 
 * @author Administrator
 *
 */
@Repository
public interface GatherInfoMemberReadDao extends BaseReadDao<GatherInfoMember> {

	/**
	 * web端列表查询
	 * 
	 * @param project
	 * @param params
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> webListPage(@Param(value = "memberInfo") GatherInfoMember memberInfo, @Param(value = "params") Map<String, Object> params, Page page);

	int getCountByProject(String projectId);

	int getCountByGroup(String groupId);

	List<GatherInfoMemberOutput> exportList(@Param(value = "memberInfo") GatherInfoMember memberInfo, @Param(value = "params") Map<String, Object> params);
}
