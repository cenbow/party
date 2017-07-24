package com.party.core.dao.read.distributor;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.distributor.DistributorDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分销详情数据读取
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 14:16
 */

@Repository
public interface DistributorDetailReadDao extends BaseReadDao<DistributorDetail> {

    /**
     * 根据目标查询
     * @param targetId 目标编号
     * @return 分销详情
     */
    DistributorDetail findByTargetId(@Param(value = "targetId") String targetId);

    /**
     * 查询报名情况
     * @param distributorDetail 分销详情
     * @return 分销详情列表
     */
    List<DistributorDetail> applyList(DistributorDetail distributorDetail);

    /**
     * 下单详情
     * @param distributorDetail 分销详情
     * @return 分销详情列表
     */
    List<DistributorDetail> buyList(DistributorDetail distributorDetail);

    /**
     * 众筹详情
     * @param distributorDetail 分销详情
     * @return 分销详情列表
     */
    List<DistributorDetail> corowdfundList(DistributorDetail distributorDetail);

    /**
     * 众筹详情
     * @param distributorDetail 分销详情
     * @return 分销详情列表
     */
    List<DistributorDetail> supportList(DistributorDetail distributorDetail);
}
