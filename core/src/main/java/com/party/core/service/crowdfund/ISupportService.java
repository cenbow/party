package com.party.core.service.crowdfund;

import com.party.common.paging.Page;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.SupportCount;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Set;

/**
 * 众筹支持服务接口
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 18:52
 */
public interface ISupportService extends IBaseService<Support> {

    /**
     * 支持列表（带支持者信息）
     * @param supportWithMember 支持信息
     * @param page 分页参数
     * @return 支持列表
     */
    List<SupportWithMember> listWithMember(SupportWithMember supportWithMember, Page page);

    /**
     * 根据订单编号查询支持信息
     * @param orderId 订单编号
     * @return 支持信息
     */
    Support findByOrderId(String orderId);

    /**
     * 我支持的金额
     * @param favorerId 支持者编号
     * @param projectId 项目编号
     * @return 支持金额
     */
    Float myFavorerAmount(String favorerId, String projectId);

    /**
     * 根据项目编号查询支持列表
     * @param projectId 项目编号
     * @return 支持列表
     */
    List<SupportWithMember> findByProjectId(String projectId);

    /**
     * 根据项目编号集合查询支持
     * @param ids 项目编号集合
     * @return 支持列表
     */
    List<Support> findByProjectIds(Set<String> ids);

    /**
     * 根据分销关系查询支持列表
     * @param relationId 分销关系
     * @return 查询列表
     */
    List<Support> findByRelationId(String relationId);

    /**
     * 支持列表（带支持者信息）
     * @param supportWithMember 支持信息
     * @param page 分页参数
     * @return 支持列表
     */
    List<SupportWithMember> distributorList(SupportWithMember supportWithMember, Page page);

    /**
     * 众筹下支持是否退完
     * @param projectId 项目编号
     * @return 是否退款（true/false）
     */
    boolean isRefundAll(String projectId);

    /**
     * 众筹的支持金额
     * @param projectId 众筹编号
     * @return 众筹总额
     */
    Float sumByProjectId(String projectId);


    /**
     * 所有支持
     * @param projectId 项目编号
     * @return 支持列表
     */
    List<Support> findSupportByProjectId(String projectId);

    /**
     * 统计时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param projectId 项目编号
     * @return 统计结果
     */
    List<SupportCount> count(String startTime, String endTime, String projectId);

    /**
     * 总数
     * @param endTime 结束时间
     * @param projectId 项目编号
     * @return 总数
     */
    Float countAll(String endTime, String projectId);
}
