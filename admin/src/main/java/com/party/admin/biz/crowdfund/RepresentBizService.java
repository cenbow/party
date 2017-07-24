package com.party.admin.biz.crowdfund;

import java.util.List;
import com.party.admin.biz.asynexport.AsynExportBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.common.constant.Constant;
import com.party.common.redis.StringJedis;
import com.party.common.utils.DateUtils;
import com.party.common.utils.FileUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.service.activity.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.party.admin.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.distributor.WithCount;
import com.party.core.service.distributor.IDistributorRelationService;
/**
 * 代言业务接口 Created by wei.li
 *
 * @date 2017/3/2 0002
 * @time 14:12
 */

@Service
public class RepresentBizService {

	@Autowired
	private IDistributorRelationService distributorRelationService;

	@Autowired
	private AsynExportBizService asynExportBizService;

	@Autowired
	private ProjectBizService projectBizService;

	@Autowired
	private IActivityService activityService;

	@Autowired
	private StringJedis stringJedis;




	/**
	 * 项目下代言
	 * 
	 * @param page
	 *            分页参数
	 * @param withCount 代言信息
	 * @param id
	 *            编号
	 * @return 代言列表
	 */
	public List<ListForTargetOutput> listForTarget(Page page, WithCount withCount, String id) {
		if (null == withCount){
			withCount = new WithCount();
		}
		withCount.setTargetId(id);
		List<WithCount> distributorRelationList = distributorRelationService.listWithCount(withCount, page);

		List<ListForTargetOutput> list = LangUtils.transform(distributorRelationList, new Function<WithCount, ListForTargetOutput>() {
			public ListForTargetOutput apply(WithCount distributorRelation) {
				ListForTargetOutput listForTargetOutput = ListForTargetOutput.transform(distributorRelation);
				listForTargetOutput.setFavorerNum(distributorRelation.getFavorerNum());
				listForTargetOutput.setActualAmount(distributorRelation.getFavorerAmount());
				listForTargetOutput.setCrowdfundNum(distributorRelation.getCrowdfundNum());
				return listForTargetOutput;
			}
		});
		return list;
	}


	/**
	 * 根据事件编号查询
	 * @param page 分页参数
	 * @param withCount 查询参数
	 * @return 输出视图
	 */
	public List<ListForTargetOutput> listForEvent(Page page, WithCount withCount){
		List<WithCount> distributorRelationList = distributorRelationService.listWithCount(withCount, page);
		List<ListForTargetOutput> list = LangUtils.transform(distributorRelationList, new Function<WithCount, ListForTargetOutput>() {
			public ListForTargetOutput apply(WithCount distributorRelation) {
				ListForTargetOutput listForTargetOutput = ListForTargetOutput.transform(distributorRelation);
				listForTargetOutput.setFavorerNum(distributorRelation.getFavorerNum());
				listForTargetOutput.setActualAmount(distributorRelation.getFavorerAmount());
				listForTargetOutput.setCrowdfundNum(distributorRelation.getCrowdfundNum());
				return listForTargetOutput;
			}
		});
		return list;
	}




}
