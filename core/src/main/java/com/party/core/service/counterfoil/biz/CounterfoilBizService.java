package com.party.core.service.counterfoil.biz;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.counterfoil.Counterfoil;
import com.party.core.model.counterfoil.CounterfoilBusiness;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.counterfoil.ICounterfoilBusinessService;
import com.party.core.service.counterfoil.ICounterfoilService;
import com.party.core.service.member.IMemberActService;

@Service
public class CounterfoilBizService {
	@Autowired
	private ICounterfoilService counterfoilService;
	@Autowired
	private IActivityService activityService;
	@Autowired
	private IMemberActService memberActService;
	@Autowired
	private ICounterfoilBusinessService counterfoilBusinessService;

	/**
	 * 保存票据业务
	 * 
	 * @param counterfoils
	 * @param businessId
	 * @param businessType
	 * @param memberId
	 */
	public void saveBiz(List<Counterfoil> counterfoils, String businessId, String businessType, String memberId) {
		if (counterfoils != null) {
			for (Counterfoil counterfoil : counterfoils) {
				if (StringUtils.isNotEmpty(counterfoil.getId())) {
					// delFlag为1表示删除
					if (StringUtils.isNotEmpty(counterfoil.getDelFlag()) && counterfoil.getDelFlag().equals(BaseModel.DEL_FLAG_DELETE)) {
						List<CounterfoilBusiness> businesses = counterfoilBusinessService.findByCounterfoilId(counterfoil.getId());
						for (CounterfoilBusiness counterfoilBusiness : businesses) {
							counterfoilBusinessService.delete(counterfoilBusiness.getId());
						}
						counterfoilService.delete(counterfoil.getId());
					} else {
						Counterfoil t = counterfoilService.get(counterfoil.getId());
						t.setPayment(counterfoil.getPayment());
						t.setName(counterfoil.getName());
						t.setLimitNum(counterfoil.getLimitNum());
						counterfoilService.update(t);
					}
				} else {
					saveCounterfoil(businessId, businessType, counterfoil, memberId);
				}
			}
		}
	}

	/**
	 * 保存票据
	 * 
	 * @param businessId
	 * @param businessType
	 * @param t
	 * @param memberId
	 */
	public void saveCounterfoil(String businessId, String businessType, Counterfoil t, String memberId) {
		Counterfoil counterfoil = new Counterfoil();
		counterfoil.setCreateBy(memberId);
		counterfoil.setUpdateBy(memberId);
		counterfoil.setPayment(t.getPayment());
		counterfoil.setLimitNum(t.getLimitNum());
		counterfoil.setBusinessId(businessId);
		counterfoil.setMaxBuyNum(t.getMaxBuyNum());
		counterfoil.setBusinessType(businessType);
		counterfoil.setJoinNum(t.getJoinNum() == null ? 0 : t.getJoinNum());
		counterfoil.setSort(t.getSort() == null ? 0 : t.getSort());
		counterfoil.setName(t.getName());
		counterfoilService.insert(counterfoil);
	}

	/**
	 * 显示的票价
	 * 
	 * @param counterfoils
	 * @param actId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getShowPrice(List<Counterfoil> counterfoils, String actId) {
		if (counterfoils == null) {
			Counterfoil t = new Counterfoil();
			t.setBusinessId(actId);
			counterfoils = counterfoilService.list(t);
		}
		if (counterfoils.size() > 0) {
			List<Float> prices = (List<Float>) CollectionUtils.collect(counterfoils, new Transformer() {
				
				@Override
				public Object transform(Object input) {
					Counterfoil mm = (Counterfoil) input;
					return mm.getPayment();
				}
			});
			Float min = Collections.min(prices); // 最小票价
			Float max = Collections.max(prices); // 最大票价
			if (min.equals(max)) {
				if (min.equals(new Float("0.0"))) {
					return "免费";
				} else {
					return min.toString();
				}
			} else {
				return min.toString() + "-" + max.toString();
			}
		}
		return "";
	}

	/**
	 * 获取活动的票据，同时更新相关数据joinNum limitNum
	 * 
	 * @param activityId
	 * @return
	 */
	public List<Counterfoil> getActivityCounterfoils(String activityId) {
		Counterfoil t = new Counterfoil();
		t.setBusinessId(activityId);
		List<Counterfoil> counterfoils = counterfoilService.list(t);
		if (counterfoils.size() > 0) {
			Integer joinNum = 0;
			Integer limitNum = 0;
			Activity activity = activityService.get(activityId);
			for (Counterfoil counterfoil : counterfoils) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("actId", activityId);
				params.put("counterfoilId", counterfoil.getId());
				Integer join = memberActService.getSuccessMemberAct(params);
				Counterfoil dbCounterfoil = counterfoilService.get(counterfoil.getId());
				if (dbCounterfoil.getJoinNum() == null || !dbCounterfoil.getJoinNum().equals(join)) {
					dbCounterfoil.setJoinNum(join);
					counterfoilService.update(dbCounterfoil);
					counterfoil = dbCounterfoil;
				}
				joinNum += join; // 报名数
			}

			for (Counterfoil counterfoil : counterfoils) {
				if (counterfoil.getLimitNum().equals(new Integer(0))) {
					limitNum = 0;
					break;
				}
				limitNum += counterfoil.getLimitNum();
			}

			boolean flag = false;
			// 购买人数
			if (activity.getJoinNum() == null) {
				activity.setJoinNum(0);
				flag = true;
			} else if (!joinNum.equals(activity.getJoinNum())) {
				activity.setJoinNum(joinNum);
				flag = true;
			}
			// 限制人数
			if (activity.getLimitNum() == null) {
				activity.setLimitNum(0);
				flag = true;
			} else if (!activity.getLimitNum().equals(limitNum)) {
				activity.setLimitNum(limitNum);
				flag = true;
			}
			if (flag) {
				activityService.update(activity);
			}
		}
		return counterfoils;
	}
}
