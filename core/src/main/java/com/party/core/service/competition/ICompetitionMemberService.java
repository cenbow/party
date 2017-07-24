package com.party.core.service.competition;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.member.Member;
import com.party.core.service.IBaseService;

/**
 * 参赛人员
 * 
 * @author Administrator
 *
 */
public interface ICompetitionMemberService extends IBaseService<CompetitionMember> {

	CompetitionMember findByProjectAndMember(String memberId, String projectId);

	List<CompetitionMember> webListPage(CompetitionMember competitionMember, Member member, Map<String, Object> params, Page page);

	int getCount(CompetitionMember t);

	List<CompetitionMember> checkNumberPai(String cMemberId, String numberPai, String projectId);
	

}
