package com.party.core.service.distributor;

import com.party.common.paging.Page;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.WithActivity;
import com.party.core.model.distributor.WithCount;
import com.party.core.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 分销关系服务接口
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 15:33
 */

public interface IDistributorRelationService extends IBaseService<DistributorRelation>{

    /**
     * 获取分销关系
     * @param type 类型
     * @param targetId 目标编号
     * @param distributorId 分销者编号
     * @return
     */
    DistributorRelation get(Integer type, String targetId, String distributorId);

    /**
     * 查询我代言的活动
     * @param memberId 会员编号
     * @param status 状态(0:进行中, 1:已经截止)
     * @return 代言列表
     */
    List<WithActivity> activityList(String memberId, Integer status, Page page);

    /**
     * 我分销的统计
     * @param param 参数
     * @param page 分页
     * @return 统计列表
     */
    List<WithCount> listWithCount(HashMap<String, Object> param, Page page);

    /**
     * 我分销的统计
     * @param status 状态
     * @param type 类型
     * @param sort 排序
     * @param page 分页
     * @return 统计列表
     */
    List<WithCount> listWithCount(Integer status, Integer type, Integer sort, String distributorId, Page page);

    /**
     * 分销统计
     * @param title 题目
     * @param type 类型
     * @param distributorId 分销者
     * @param page 分页参数
     * @return 统计列表
     */
    List<WithCount> listWithCount(String title, Integer type, String distributorId, String startTime, String endTime, Page page);


    /**
     * 分销统计
     * @param type 类型
     * @param title 题目
     * @param distributorName 分销名
     * @param page 分页
     * @return 统计列表
     */
    List<WithCount> listWithCount(Integer type, String title, String distributorName, String startTime, String endTime, Page page);


    /**
     * 分销统计
     * @param targetId 目标编号
     * @param title 题目
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 分页参数
     * @return 统计列表
     */
    List<WithCount> listWithCount(String targetId,String  title, Integer type, String startTime, String endTime, Page page);

    /**
     * 根据事件查询代言
     * @param eventId 事件编号
     * @return 代言数
     */
    Integer countForEvent(String eventId);

    /**
     * 分销统计
     * @param targetId 目标编号
     * @param page 分页参数
     * @return 统计列表
     */
    List<WithCount> listWithCount(String targetId, Page page);

    /**
     * 分销统计
     * @param withCount 统计实体
     * @param page 分页参数
     * @return 统计列表
     */
    List<WithCount> listWithCount(WithCount withCount, Page page);

    /**
     * 查找子级分销
     * @param parentId 父编号
     * @param targetId 目标编号
     * @param title 题目
     * @param distributorName 分销商名称
     * @param type 类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 分页参数
     * @return
     */
    List<WithCount> findByParentId(String parentId, String targetId, String title, String distributorName, Integer type,
                                   String startTime, String endTime, Page page);


    /**
     * 查找子级分销
     * @param parentId 父编号
     * @param targetId 目标编号
     * @return
     */
    List<WithCount> findByParentId(String parentId, String targetId);

    /**
     * 是否已经分销过
     * @param parentId 上级编号
     * @param targetId 分销目标
     * @param distributorId 分销者
     * @return 是否分销（true/false）
     */
    boolean hasDistribution(String parentId, String targetId, String distributorId);

}
