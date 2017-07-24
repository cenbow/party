package com.party.mobile.biz.crowdfund;

import com.party.common.utils.BigDecimalUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.model.distributor.DistributorDetailType;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.web.dto.crowdfund.input.SupportStatisticsInput;
import com.party.mobile.web.dto.distributor.input.ApplyDistributorInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支持业务接口
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 11:03
 */

@Service
public class SupportBizService {

    @Autowired
    private DistributorBizService distributorBizService;

    @Autowired
    private IDistributorRelationService distributorRelationService;

    @Autowired
    private IDistributorCountService distributorCountService;

    /**
     * 统计支持数据
     * @param input 输入视图
     */
    public void statistics(SupportStatisticsInput input){
        ApplyDistributorInput applyDistributorInput = new ApplyDistributorInput();
        applyDistributorInput.setDistributorTargetId(input.getDistributorTargetId());
        applyDistributorInput.setDistributorType(input.getDistributorType());
        applyDistributorInput.setDistributorId(input.getDistributorId());
        applyDistributorInput.setDistributorParentId(input.getDistributorParentId());
        applyDistributorInput.setTargetId(input.getSupportId());
        applyDistributorInput.setType(DistributorDetailType.DISTRIBUTOR_TYPE_SUPPORT.getCode());
        distributorBizService.setDistributorData(applyDistributorInput);

        DistributorRelation distributorRelation
                = distributorRelationService.get(input.getDistributorType(),
                input.getDistributorTargetId(),input.getDistributorId());

        if (null == distributorRelation){
            throw new BusinessException("分销关系不存在");
        }

        DistributorCount distributorCount = distributorCountService.findByRelationId(distributorRelation.getId());
        if (null == distributorCount){
            throw new BusinessException("分销统计不存在");
        }

        Float amount = BigDecimalUtils.add(distributorCount.getFavorerAmount(), input.getPayment());
        amount = BigDecimalUtils.round(amount, 2);
        distributorCount.setFavorerAmount(amount);

        distributorCountService.update(distributorCount);
    }
}
