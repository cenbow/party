package com.party.admin.biz.distribution;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.party.admin.web.dto.output.distribution.OrderListOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderForm;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分销订单业务接口
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 14:53
 */

@Service
public class DistributionOrderBizService {

    @Autowired
    private IDistributorDetailService distributorDetailService;

    @Autowired
    private IOrderFormService orderFormService;

    @Autowired
    private IMemberService memberService;
    /**
     * 订单列表
     * @param distributionId 分销关系编号
     * @param orderForm 订单信息
     * @param page 分页参数
     * @return 订单列表
     */
    public List<OrderListOutput> list(String distributionId, OrderForm orderForm, Page page){
        List<DistributorDetail> distributorDetailList = distributorDetailService.findByRelationId(distributionId);
        List<String> orderIdList = LangUtils.transform(distributorDetailList, new Function<DistributorDetail, String>() {
            @Override
            public String apply(DistributorDetail distributorDetail) {
                return distributorDetail.getTargetId();
            }
        });
        List<OrderForm> orderFormList = orderFormService.batchList(Sets.newHashSet(orderIdList), orderForm, page);
        List<OrderListOutput> orderListOutputList = LangUtils.transform(orderFormList, new Function<OrderForm, OrderListOutput>() {
            @Override
            public OrderListOutput apply(OrderForm orderForm) {
                OrderListOutput orderListOutput = OrderListOutput.transform(orderForm);
                Member member = memberService.get(orderForm.getMemberId());
                orderListOutput.setBuyer(member.getRealname());
                return orderListOutput;
            }
        });
        return orderListOutputList;
    }
}
