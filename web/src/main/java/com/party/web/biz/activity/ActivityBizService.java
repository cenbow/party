package com.party.web.biz.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.utils.BigDecimalUtils;
import com.party.core.model.crowdfund.Project;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.circle.ICircleBusinessService;
import com.party.core.service.circle.ICircleMemberService;
import com.party.core.service.circle.ICircleService;
import com.party.core.service.circle.biz.CircleMemberBizService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ITargetProjectService;
import com.party.core.service.member.IMemberActService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.RealmUtils;

/**
 *
 * 活动业务接口 Created by wei.li
 *
 * @date 2017/3/22 0022
 * @time 17:44
 */
@Service
public class ActivityBizService {

	@Autowired
	private FileBizService fileBizService;

	@Autowired
	private ITargetProjectService targetProjectService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private ICircleService circleService;

	@Autowired
	private ICircleBusinessService circleBusinessService;

	@Autowired
	private IMemberActService memberActService;

	@Autowired
	private ICircleMemberService circleMemberService;

	@Autowired
	private CircleMemberBizService circleMemberBizService;

	@Autowired
	private IActivityService activityService;

	/**
	 * 拼接分销连接
	 * 
	 * @param id
	 *            活动编号
	 * @param memberId
	 *            创建者
	 * @return 分销连接
	 */
	public String getDistributionUrl(String id, String memberId) {
		StringBuilder stringBuilder = new StringBuilder("hd/hd_distribution.html?");
		stringBuilder.append("id=").append(id).append("&parentId=").append("0").append("&distributorId=").append(memberId);
		return stringBuilder.toString();
	}

	/**
	 * 获取二维码连接
	 * 
	 * @param id
	 *            活动编号
	 * @param memberId
	 *            创建者
	 * @return 二维码连接
	 */
	public String getQrCode(String id, String memberId) {
		String url = this.getDistributionUrl(id, memberId);
		String path = RealmUtils.getCurrentUser().getId() + "/distribution/";
		String qrCodeUrl = fileBizService.getFileEntity(id, path, url);
		return qrCodeUrl;
	}

	/**
	 * 众筹项目众筹了多少资金
	 * 
	 * @param targetId
	 *            目标编号
	 * @return 资金
	 */
	public float actualAmountForTargetId(String targetId) {
		List<Project> projectList = projectService.listForTargetId(targetId);
		float actualAmount = 0f;
		for (Project project : projectList) {
			actualAmount = BigDecimalUtils.add(actualAmount, project.getActualAmount());
		}
		actualAmount = BigDecimalUtils.round(actualAmount, 2);
		return actualAmount;
	}
}
