package com.party.core.dao.read.crowdfund;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectAnalyze;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;

/**
 * 众筹项目数据读取
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 17:04
 */
@Repository
public interface ProjectReadDao extends BaseReadDao<Project> {

    /**
     * 联合查询支持
     * @param param 查询参数
     * @param page 分页参数
     * @return 众筹列表
     */
    List<Project> listWithSupportPage(@Param(value = "param") HashMap<String, Object> param, Page page);

    /**
     * 联合查询创建者
     * @param projectWithAuthor 查询参数
     * @param page 分页参数
     * @return 众筹列表
     */
    List<ProjectWithAuthor> listWithAuthorPage(ProjectWithAuthor projectWithAuthor, Page page);


    /**
     * 获取包含创建者项目信息
     * @param id 编号
     * @return 项目信息
     */
    ProjectWithAuthor getWithAuthor(String id);

    /**
     * 根据分销编号查询众筹项目
     * @param distributionId 分销编号
     * @return 众筹项目列表
     */
    List<Project> listForDistributionId(@Param(value = "distributionId") String distributionId);

    /**
     * 根据目标查询众筹项目
     * @param targetId 目标编号
     * @return 项目列表
     */
    List<Project> listForTargetId(@Param(value = "targetId") String targetId);


    /**
     * 查询众筹项目列表
     * @param param 参数
     * @param page 分页
     * @return 众筹列表
     */
    List<ProjectWithAuthor> list(@Param(value = "param") HashMap<String, Object> param, Page page);

    /**
     * 项目下众筹数
     * @param targetId 项目编号
     * @return 众筹数
     */
    Integer sizeForTargetId(@Param(value = "targetId") String targetId, @Param(value = "isSuccess") Integer isSuccess);

    /**
     * 根据事件编号查询众筹数
     * @param eventId 事件编号
     * @return 众筹数
     */
    Integer countForEvent(@Param(value = "eventId") String eventId, @Param(value = "isSuccess") Integer isSuccess);

    /**
     * 众筹分析列表
     * @param param 参数
     * @param page 分页参数
     * @return 分析列表
     */
    List<ProjectAnalyze> analyzeList(@Param(value = "param") HashMap<String, Object> param, Page page);


    /**
     * 众筹支持总和
     * @param targetId 目标编号
     * @return 支持者总和
     */
    Integer sumfavorerNum(@Param(value = "targetId") String targetId);
}
