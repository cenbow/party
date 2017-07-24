package com.party.core.service.competition.biz;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.YesNoStatus;
import com.party.core.model.competition.CompetitionGroup;
import com.party.core.model.competition.CompetitionMember;
import com.party.core.model.competition.CompetitionResult;
import com.party.core.model.competition.CompetitionSchedule;
import com.party.core.service.competition.ICompetitionGroupService;
import com.party.core.service.competition.ICompetitionMemberService;
import com.party.core.service.competition.ICompetitionProjectService;
import com.party.core.service.competition.ICompetitionResultService;
import com.party.core.service.competition.ICompetitionScheduleService;

/**
 * 参赛人员成绩
 * 
 * @author Administrator
 *
 */
@Service
public class CompetitionResultBizService {

	@Autowired
	private ICompetitionMemberService competitionMemberService;
	@Autowired
	private ICompetitionScheduleService competitionScheduleService;
	@Autowired
	private ICompetitionGroupService competitionGroupService;
	@Autowired
	private ICompetitionProjectService competitionProjectService;
	@Autowired
	private ICompetitionResultService competitionResultService;

	/**
	 * 计算配速
	 * 
	 * @param result
	 *            用时
	 * @param distance
	 *            距离
	 */
	public int[] calculatorPace(String result, String distance) {
		String[] arrays = result.split(":");
		// double hours = Float.parseFloat(arrays[0]); // 小时
		// double minutes = Float.parseFloat(arrays[1]); // 分组
		double seconds = Float.parseFloat(result); // 秒钟

		// 1.先计算一共的分钟数
		// hours = BigDecimalUtils.mul(hours, 60);
		seconds = BigDecimalUtils.div(seconds, 60);
		// minutes = BigDecimalUtils.add(hours, minutes);
		// minutes = BigDecimalUtils.add(minutes, seconds);
		// 2.计算速度
		distance = distance.replace("KM", "");
		double speed = BigDecimalUtils.div(seconds, Double.parseDouble(distance));
		int speed_minutes = (int) Math.floor(speed);
		int speed_seconds = (int) Math.ceil(BigDecimalUtils.mul(speed - speed_minutes, 60.0));

		int[] values = { speed_minutes, speed_seconds };
		return values;
	}

	public String getResult(Double s2) {
		// double h1 = BigDecimalUtils.mul(hours, 3600); // 小时——秒
		// double m1 = BigDecimalUtils.mul(minutes, 60); // 分钟——秒
		// double s1 = BigDecimalUtils.add(h1, m1);
		// double s2 = BigDecimalUtils.add(s1, seconds); // 总秒数

		String _hours = (int) (s2 / 3600) + "";
		_hours = _hours.length() == 1 ? "0" + _hours : _hours;

		String _minutes = (int) ((s2 % 3600) / 60) + "";
		_minutes = _minutes.length() == 1 ? "0" + _minutes : _minutes;

		String _seconds = (int) ((s2 % 3600) % 60) + "";
		_seconds = _seconds.length() == 1 ? "0" + _seconds : _seconds;

		return _hours + ":" + _minutes + ":" + _seconds;
	}

	/**
	 * 获取小组成绩排行
	 * 
	 * @param projectId
	 * @param rankList
	 * @param memberResults
	 */
	public void getGroupResultRank(List<Map<String, Object>> memberResults, String projectId, String scheduleId) {
		Double distanceTotal = competitionScheduleService.getSumDistance(projectId);
		for (int i = 0; i < memberResults.size(); i++) {
			Map<String, Object> map = memberResults.get(i);
			map.put("rankNo", i + 1 + "");
			CompetitionMember t = new CompetitionMember();
			t.setProjectId(projectId);
			t.setGroupId(map.get("groupId").toString());
			int memberCount = competitionMemberService.getCount(t);
			map.put("memberCount", memberCount);

			if (null != distanceTotal) {
				distanceTotal = BigDecimalUtils.mul(distanceTotal, memberCount);
			}

			rank(scheduleId, distanceTotal, map);
		}
	}

	public List<Map<String, Object>> getPersonResult(String projectId, String cMemberId, List<CompetitionSchedule> schedules) {
		List<Map<String, Object>> memberResults = new ArrayList<Map<String, Object>>();
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("#.#");
		for (CompetitionSchedule schedule : schedules) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("playDay", schedule.getPlayDay()); // 日程
			map.put("place", schedule.getPlace()); // 打卡点
			map.put("distance", schedule.getDistance()); // 里程
			// 日程排名
			CompetitionResult cResult = new CompetitionResult();
			cResult.setIsComplete(1);
			cResult.setScheduleId(schedule.getId());
			cResult.setMemberId(cMemberId);
			List<Map<String, Object>> scheduleResult = competitionResultService.getScheduleRank(cResult);
			if (scheduleResult.size() > 0) {
				Map<String, Object> resultMap = scheduleResult.get(0);
				map.put("rankNo", resultMap.get("rowno")); // 排名
				map.put("isComplete", resultMap.get("isComplete")); // 是否完赛
				map.put("actualRange", df.format(Double.valueOf(resultMap.get("actualRange").toString()))); // 实际里程

				if (null != resultMap.get("secondsResult")) {
					Double secondsResult = Double.valueOf(resultMap.get("secondsResult").toString());
					String result = getResult(secondsResult);
					map.put("result", result);

					// 配速
					int[] pace = calculatorPace(secondsResult.toString(), schedule.getDistance());
					map.put("paceMinutes", pace[0]);
					map.put("paceSeconds", pace[1]);
				}
			} else {
				map.put("rankNo", "");
				map.put("isComplete", "");
				map.put("actualRange", "");
				map.put("paceMinutes", "");
				map.put("paceSeconds", "");
				map.put("result", "");
			}

			memberResults.add(map);
		}

		Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Date playDay1 = (Date) o1.get("playDay");
				Date playDay2 = (Date) o2.get("playDay");
				return playDay1.compareTo(playDay2);
			}

		};

		Collections.sort(memberResults, comparator);
		return memberResults;
	}

	/**
	 * 获取人员成绩排行
	 * 
	 * @param rankList
	 * @param memberResults
	 * @param projectId
	 * @param scheduleId
	 */
	public void getMemberResultRank(List<Map<String, Object>> memberResults, String projectId, String scheduleId) {
		Double distanceTotal = competitionScheduleService.getSumDistance(projectId);
		for (Map<String, Object> map : memberResults) {
			rank(scheduleId, distanceTotal, map);
		}
	}

	private void rank(String scheduleId, Double distanceTotal, Map<String, Object> map) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("#.#");
		// 总里程 scheduleId为空表示总赛程
		if (StringUtils.isEmpty(scheduleId)) {
			if (null == distanceTotal && null == map.get("distance")) {
				map.put("isComplete", "1");
			} else if (null != map.get("distance")) {
				Double distance = BigDecimalUtils.round(Double.valueOf(map.get("distance").toString()), 1);
				map.put("distance", df.format(distance));
				if (distanceTotal.equals(distance)) {
					map.put("isComplete", "1");
				} else {
					map.put("isComplete", "0");
				}
			} else {
				map.put("isComplete", "0");
			}
		}
		
		if (null != map.get("secondsResult")) {
			Double secondsResult = Double.valueOf(map.get("secondsResult").toString());
			String result = getResult(secondsResult);
			map.put("result", result);

			// 配速
			int[] pace = calculatorPace(secondsResult.toString(), map.get("distance").toString());
			map.put("paceMinutes", pace[0]);
			map.put("paceSeconds", pace[1]);
		}
	}

	/**
	 * 更新人员成绩
	 * 
	 * @param cMemberId
	 */
	public String updateMemberResult(String cMemberId) {
		if (StringUtils.isNotEmpty(cMemberId)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("memberId", cMemberId);
			Map<String, Object> resultMap = competitionResultService.getTotalDistanceAndResult(params);

			CompetitionMember competitionMember = competitionMemberService.get(cMemberId);
			if (resultMap != null) {
				if (resultMap.get("totalDistance") != null) {
					Double round = BigDecimalUtils.round(Double.valueOf(resultMap.get("totalDistance").toString()), 2);
					competitionMember.setTotalDistance(round.toString());
				} else {
					competitionMember.setTotalDistance("0");
				}
				if (resultMap.get("totalResult") != null) {
					competitionMember.setTotalSecondsResult(resultMap.get("totalResult").toString());
				} else {
					competitionMember.setTotalSecondsResult("0");
				}
				competitionMemberService.update(competitionMember);
			} else {
				competitionMember.setTotalDistance("0");
				competitionMember.setTotalSecondsResult("0");
				competitionMemberService.update(competitionMember);
			}
			return competitionMember.getGroupId();
		}
		return "";
	}

	/**
	 * 更新小组成绩
	 * 
	 * @param groupId
	 */
	public void updateGroupResult(String groupId) {
		if (StringUtils.isNotEmpty(groupId)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("groupId", groupId);
			Map<String, Object> resultMap = competitionResultService.getTotalDistanceAndResult(params);

			CompetitionGroup competitionGroup = competitionGroupService.get(groupId);
			if (resultMap != null) {
				if (resultMap.get("totalDistance") != null) {
					Double round = BigDecimalUtils.round(Double.valueOf(resultMap.get("totalDistance").toString()), 2);
					competitionGroup.setTotalDistance(round.toString());
				} else {
					competitionGroup.setTotalDistance("0");
				}
				if (resultMap.get("totalResult") != null) {
					competitionGroup.setTotalSecondsResult(resultMap.get("totalResult").toString());
				} else {
					competitionGroup.setTotalSecondsResult("0");
				}
				competitionGroupService.update(competitionGroup);
			} else {
				competitionGroup.setTotalDistance("0");
				competitionGroup.setTotalSecondsResult("0");
				competitionGroupService.update(competitionGroup);
			}
		}
	}

	/**
	 * 更新赛程所有的人员成绩
	 * 
	 * @param scheduleId
	 */
	@SuppressWarnings("unchecked")
	public void updateScheduleAllMember(String scheduleId) {
		CompetitionSchedule schedule = competitionScheduleService.get(scheduleId);
		List<CompetitionResult> results = competitionResultService.list(new CompetitionResult(scheduleId));
		for (CompetitionResult competitionResult : results) {
			if (competitionResult.getIsComplete().equals(YesNoStatus.YES.getCode())) {
				competitionResult.setActualRange(schedule.getDistance());
				competitionResultService.update(competitionResult);
			}
		}
		List<String> cMemberIds = (List<String>) CollectionUtils.collect(results, new Transformer() {

			@Override
			public Object transform(Object input) {
				return ((CompetitionResult) input).getMemberId();
			}
		});

		Set<String> cMemberIds2 = new HashSet<String>(cMemberIds);
		for (String cMemberId : cMemberIds2) {
			String groupId = updateMemberResult(cMemberId);
			updateGroupResult(groupId);
		}
	}
}
