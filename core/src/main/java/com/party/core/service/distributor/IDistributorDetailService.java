package com.party.core.service.distributor;

import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 分销详情服务接口
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 15:35
 */


public interface IDistributorDetailService extends IBaseService<DistributorDetail> {


    /**
     * 根据分销关系查询分销详情
     * @param relationId 分销关系编号
     * @return 分销关系
     */
    List<DistributorDetail> findByRelationId(String relationId);

    /**
     * 根据目标编号查询
     * @param targetId 目标编号
     * @return 分销详情
     */
    DistributorDetail findByTargetId(String targetId);

    /**
     * 报名列表
     * @param distributorRelationId 分销编号
     * @return 报名列表
     */
    List<DistributorDetail> applyList(String distributorRelationId);

    /**
     * 下单列表
     * @param distributorRelationId 分销编号
     * @return 下单列表
     */
    List<DistributorDetail> buyList(String distributorRelationId);

    /**
     * 众筹量
     * @param distributorRelationId 分销编号
     * @return  下单列表
     */
    List<DistributorDetail> corowdfundList(String distributorRelationId);

    /**
     * 支持量
     * @param distributorRelationId 分销编号
     * @return 下单列表
     */
    List<DistributorDetail> supportList(String distributorRelationId);
}
