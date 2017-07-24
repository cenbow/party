package com.party.web.biz.crowdfund;

import java.util.List;

import com.party.common.utils.BigDecimalUtils;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.distributor.WithCount;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.distributor.IDistributorDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.DistributorTargetAttache;
import com.party.core.model.member.Member;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.distributor.IDistributorTargetAttacheService;
import com.party.core.service.member.IMemberService;
import com.party.web.web.dto.output.crowdfund.ListForTargetOutput;

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


	/**
	 * 项目下代言
	 * 
	 * @param page 分页参数
	 * @param id 编号
	 * @param withCount 统计实体
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
