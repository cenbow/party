package com.party.web.biz.activity;

import com.party.core.model.distributor.DistributorCount;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报名业务接口
 * Created by wei.li
 *
 * @date 2017/3/10 0010
 * @time 13:57
 */

@Service
public class ApplyBizService {

    @Autowired
    private IDistributorDetailService distributorDetailService;

    @Autowired
    private IDistributorCountService distributorCountService;


    /**
     * 设置分销信息
     * @param applyId 报名编号
     * @param orderId 订单号
     */
    @Transactional
    public void setDistribution(String applyId, String orderId){
        DistributorDetail distributorDetail = distributorDetailService.findByTargetId(applyId);
        if (null != distributorDetail){
            //分销详情
            DistributorDetail newDistributorDeatil = new DistributorDetail();
            newDistributorDeatil.setType(0);
            newDistributorDeatil.setTargetId(orderId);
            newDistributorDeatil.setDistributorRelationId(distributorDetail.getDistributorRelationId());
            distributorDetailService.insert(newDistributorDeatil);

            //增加订单量
            DistributorCount distributorCount = distributorCountService.findByRelationId(distributorDetail.getDistributorRelationId());
            distributorCount.setSalesNum(distributorCount.getSalesNum() + 1);
            distributorCountService.update(distributorCount);
        }
    }


}
