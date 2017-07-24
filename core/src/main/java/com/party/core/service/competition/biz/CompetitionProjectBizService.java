package com.party.core.service.competition.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.utils.StringUtils;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.competition.CompetitionProject;
import com.party.core.model.gatherInfo.GatherInfoGroup;
import com.party.core.model.gatherInfo.GatherInfoMember;
import com.party.core.model.member.Member;
import com.party.core.service.competition.ICompetitionBusinessService;
import com.party.core.service.competition.ICompetitionGroupService;
import com.party.core.service.competition.ICompetitionMemberService;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.gatherInfo.IGatherInfoGroupService;
import com.party.core.service.gatherInfo.IGatherInfoMemberService;

/**
 * 赛事项目业务
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionProjectBizService {
	@Autowired
	private IGatherInfoGroupService gatherInfoGroupService;
	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private ICompetitionProjectService competitionProjectService;
	@Autowired
	private ICompetitionBusinessService competitionBusinessService;
	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private IGatherInfoMemberService gatherInfoMemberService;

	@SuppressWarnings("unchecked")
	public void insertCompetitionMember(String cProjectId, String gProjectId, String currentId) {

		// 信息收集小组
		List<GatherInfoGroup> infoGroups = gatherInfoGroupService.list(new GatherInfoGroup(gProjectId));
		List<String> infoGroupNames = (List<String>) CollectionUtils.collect(infoGroups, new Transformer() {

			@Override
			public Object transform(Object input) {
				return ((GatherInfoGroup) input).getGroupName() + "_" + ((GatherInfoGroup) input).getGroupNo();
			}
		});

		// 信息收集项目人员
		List<Map<String, Object>> infoMembers = gatherInfoMemberService.webListPage(new GatherInfoMember(gProjectId), null, null);
		List<String> infoMemberIds = (List<String>) CollectionUtils.collect(infoMembers, new Transformer() {

			@Override
			public Object transform(Object input) {
				return ((Map<String, Object>) input).get("memberId") + "_" + ((Map<String, Object>) input).get("groupName");
			}
		});

		// 赛事项目小组
		List<CompetitionGroup> cGroups = competitionGroupService.list(new CompetitionGroup(cProjectId));
		List<String> cGroupNames = (List<String>) CollectionUtils.collect(cGroups, new Transformer() {

			@Override
			public Object transform(Object input) {
				return ((CompetitionGroup) input).getGroupName() + "_" + ((CompetitionGroup) input).getGroupNo();
			}
		});

		infoGroupNames.removeAll(cGroupNames);

		for (String str : infoGroupNames) {
			String[] array = str.split("_");
			String groupName = array[0];
			String groupNo = array[1];
			CompetitionGroup t = new CompetitionGroup();
			t.setProjectId(cProjectId);
			t.setGroupName(groupName);
			insertCompetitionGroup(cProjectId, currentId, groupName, groupNo, t);
		}

		// 赛事项目人员
		List<CompetitionMember> cMembers = competitionMemberService.webListPage(new CompetitionMember(cProjectId), new Member(), null, null);
		List<String> cMemberIds = (List<String>) CollectionUtils.collect(cMembers, new Transformer() {

			@Override
			public Object transform(Object input) {
				return ((CompetitionMember) input).getMemberId() + "_" + ((CompetitionMember) input).getGroupName();
			}
		});

		infoMemberIds.removeAll(cMemberIds);

		for (String str : infoMemberIds) {
			String[] array = str.split("_");
			String memberId = array[0];
			String groupName = array[1];
			CompetitionMember competitionMember = competitionMemberService.findByProjectAndMember(memberId, cProjectId);
			if (competitionMember == null) {
				competitionMember = new CompetitionMember();
				if (StringUtils.isNotEmpty(groupName) && !groupName.equals("null")) {
					CompetitionGroup t = new CompetitionGroup();
					t.setGroupName(array[1]);
					t.setProjectId(cProjectId);
					String groupId = insertCompetitionGroup(cProjectId, currentId, groupName, "", t);
					competitionMember.setGroupId(groupId);
				}
				competitionMember.setMemberId(memberId);
				competitionMember.setProjectId(cProjectId);
				competitionMember.setCreateBy(currentId);
				competitionMember.setUpdateBy(currentId);
				competitionMemberService.insert(competitionMember);
			}
		}
	}

	/**
	 * 生成赛事项目小组
	 * 
	 * @param competitionProjectId
	 * @param currentId
	 * @param groupName
	 * @param groupNo
	 * @param t
	 * @return
	 */
	public String insertCompetitionGroup(String competitionProjectId, String currentId, String groupName, String groupNo, CompetitionGroup t) {
		CompetitionGroup uniqueGroup = competitionGroupService.getUnique(t);
		if (uniqueGroup == null) {
			uniqueGroup = new CompetitionGroup();
			uniqueGroup.setGroupNo(groupNo);
			uniqueGroup.setProjectId(competitionProjectId);
			uniqueGroup.setGroupName(groupName);
			uniqueGroup.setCreateBy(currentId);
			uniqueGroup.setUpdateBy(currentId);
			return competitionGroupService.insert(uniqueGroup);
		}
		return uniqueGroup.getId();
	}

	/**
	 * 创建赛事项目
	 * 
	 * @param title
	 * @return
	 */
	public String createCompetition(String title, String currentId) {
		CompetitionProject cProject = new CompetitionProject();
		cProject.setCreateBy(currentId);
		cProject.setUpdateBy(currentId);
		cProject.setTitle(title);
		return competitionProjectService.insert(cProject);
	}
}
