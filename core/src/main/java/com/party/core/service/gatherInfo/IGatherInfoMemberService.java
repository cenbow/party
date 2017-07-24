package com.party.core.service.gatherInfo;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.gatherInfo.GatherInfoMemberOutput;
import com.party.core.service.IBaseService;

/**
 * 人员信息
 * 
 * @author Administrator
 *
 */
public interface IGatherInfoMemberService extends IBaseService<GatherInfoMember> {

	List<GatherInfoMember> findByProject(String id);

	List<Map<String, Object>> webListPage(GatherInfoMember memberInfo, Map<String, Object> params, Page page);

	int getCountByProject(String id);

	GatherInfoMember findByProjectAndMember(String memberId, String projectId);

	List<GatherInfoMember> findByGroup(String id);

	int getCountByGroup(String id);

	List<GatherInfoMemberOutput> exportList(GatherInfoMember memberInfo, Map<String, Object> params);

}
