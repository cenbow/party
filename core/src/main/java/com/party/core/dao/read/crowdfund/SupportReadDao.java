package com.party.core.dao.read.crowdfund;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.SupportCount;
import com.party.core.model.crowdfund.SupportWithMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 众筹支持数据读取
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 17:06
 */
@Repository
public interface SupportReadDao extends BaseReadDao<Support> {

    /**
     * 查询支持信息带支持者
     * @param supportWithMember 支持信息带支持者
     * @param page 分页参数
     * @return 支持信息列表
     */
    List<SupportWithMember> listWithMember(SupportWithMember supportWithMember, Page page);

    /**
     * 根据订单号查询支持信息
     * @param orderId 订单号
     * @return 支持信息
     */
    Support findByOrderId(String orderId);

    /**
     * 根据项目编号集合查询支持
     * @param ids 项目编号集合
     * @param param 查询参数
     * @return 支持列表
     */
    List<Support> list(@Param("ids") Set<String> ids, @Param(value = "param") Map<String , Object> param);

    /**
     * 查询支持信息带支持者
     * @param supportWithMember 支持信息带支持者
     * @param page 分页参数
     * @return 支持信息列表
     */
    List<SupportWithMember> distributorList(SupportWithMember supportWithMember, Page page);

    /**
     * 众筹的支持金额
     * @param projectId 众筹编号
     * @return 众筹总额
     */
    Float sumByProjectId(@Param(value = "projectId") String projectId);

    /**
     * 统计支持数据
     * @param param 参数
     * @return 统计数据
     */
    List<SupportCount> count(@Param(value = "param") Map<String , Object> param);


    /**
     * 统计支持数据
     * @param param 参数
     * @return 统计数据
     */
    Float countAll(@Param(value = "param") Map<String , Object> param);
}
