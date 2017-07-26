package com.party.core.service.crowdfund;

import com.party.common.paging.Page;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectAnalyze;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.service.IBaseService;

import java.util.HashMap;
import java.util.List;

/**
 * 众筹项目服务接口
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 18:39
 */
public interface IProjectService extends IBaseService<Project> {

    /**
     * 分页查询项目信息
     * @param param 项目信息
     * @param page 分页信息
     * @return 项目列表
     */
    List<Project> listPage(HashMap<String, Object> param, Page page);

    /**
     * 查询所有项目信息
     * @param param 项目信息
     * @return 项目列表
     */
    List<Project> list(HashMap<String, Object> param);


    /**
     * 联合查询创建者
     * @param projectWithAuthor 查询参数
     * @param page 分页参数
     * @return 项目列表
     */
    List<ProjectWithAuthor> listPage(ProjectWithAuthor projectWithAuthor, Page page);

    /**
     * 联合查询所有项目列表
     * @param projectWithAuthor 查询参数
     * @return 项目列表
     */
    List<ProjectWithAuthor> list(ProjectWithAuthor projectWithAuthor);

    /**
     * 获取项目信息包含创建者
     * @param id 编号
     * @return 项目信息
     */
    ProjectWithAuthor getWithAuthor(String id);

    /**
     * 是否筹集成功
     * @param targetAmount 目标筹集资金
     * @param actualAmount 实际筹集资金
     * @return 是否筹集成功（true/false）
     */
    boolean isSuccess(Float targetAmount, Float actualAmount);

    /**
     * 获取实际能筹集的最大金额
     * @param id 众筹编号
     * @return 最大金额
     */
    Float getMaxAmount(String id);

    /**
     * 根据分销编号查询众筹项目
     * @param distributionId 分销编号
     * @return 众筹项目列表
     */
    List<Project> listForDistributionId(String distributionId);

    /**
     * 根据目标查询众筹项目
     * @param targetId 目标编号
     * @return 项目列表
     */
    List<Project> listForTargetId(String targetId);

    /**
     * 查询带起众筹
     * @return 众筹列表
     */
    List<ProjectWithAuthor> endList();


    /**
     * 进行中的众筹
     * @return 众筹列表
     */
    List<ProjectWithAuthor> underwayList();

    /**
     * 众筹成功的
     * @return 众筹列表
     */
    List<ProjectWithAuthor> successList();

    /**
     * 所有众筹
     * @return 众筹列表
     */
    List<ProjectWithAuthor> allList();

    /**
     * 进行中的
     * @param targetId 目标编号
     * @return 众筹列表
     */
    List<ProjectWithAuthor> underwayList(String targetId);

    /**
     * 成功的
     * @param targetId 目标编号
     * @return 众筹列表
     */
    List<ProjectWithAuthor> successList(String targetId);

    /**
     * 所有
     * @param targetId 目标编号
     * @return 众筹列表
     */
    List<ProjectWithAuthor> allList(String targetId);

    /**
     * 众筹是否过期
     * @param project 众筹项目
     * @return 众筹是否过期(true/false)
     */
    boolean isOverdue(Project project);

    /**
     * 项目下众筹数
     * @param targetId 项目编号
     * @return 众筹数
     */
    Integer sizeForTargetId(String targetId);

    /**
     * 项目下的众筹数
     * @param targetId 项目编号
     * @param isSuccess 状态
     * @return 众筹数
     */
    Integer sizeForTargetId(String targetId, Integer isSuccess);

    /**
     * 根据事件编号查询项目
     * @param eventId 事件编号
     * @return 项目数
     */
    Integer countForEvent(String eventId);

    /**
     * 根据事件编号查询项目
     * @param eventId 事件编号
     * @param isSuccess 状态
     * @return 项目数
     */
    Integer countForEvent(String eventId, Integer isSuccess);

    /**
     * 众筹分析列表
     * @param projectAnalyze 众筹分析
     * @param page 分页参数
     * @return 众筹分析列表
     */
    List<ProjectAnalyze> analyzeList(ProjectAnalyze projectAnalyze, Page page);

    /**
     * 众筹分析列表
     * @param projectAnalyze 众筹分析
     * @param groupBy 是否分组
     * @param page 分页参数
     * @return 列表
     */
    List<ProjectAnalyze> analyzeList(ProjectAnalyze projectAnalyze, boolean groupBy, Page page);


    /**
     * 其他的众筹
     * @param projectId 众筹
     * @return 众筹列表
     */
    List<ProjectWithAuthor> otherProject(String projectId);


    /**
     * 项目下众筹支持者总数
     * @param targetId 目标编号
     * @return 支持者总数
     */
    Integer sumfavorerNum(String targetId);
}
