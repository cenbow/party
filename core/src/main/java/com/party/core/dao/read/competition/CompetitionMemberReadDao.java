package com.party.core.dao.read.competition;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.member.Member;

/**
 * 参赛人员
 * 
 * @author Administrator
 *
 */
@Repository
public interface CompetitionMemberReadDao extends BaseReadDao<CompetitionMember> {
	/**
	 * web端查询
	 * 
	 * @param member
	 * @param member2
	 * @param params
	 * @param page
	 * @return
	 */
	List<CompetitionMember> webListPage(@Param(value = "cMember") CompetitionMember cMember, @Param("member") Member member,
			@Param(value = "params") Map<String, Object> params, Page page);

	int getCount(CompetitionMember t);
	
	List<CompetitionMember> checkNumberPai(@Param(value = "numberPai") String numberPai, @Param(value = "projectId") String projectId,
			@Param(value = "id") String id);
}
