package com.party.core.service.crowdfund.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.BigDecimalUtils;
import com.party.common.utils.DateUtils;
import com.party.common.utils.LangUtils;
import com.party.core.dao.read.activity.ActivityReadDao;
import com.party.core.dao.read.crowdfund.ProjectReadDao;
import com.party.core.dao.read.crowdfund.SupportReadDao;
import com.party.core.dao.read.crowdfund.TargetProjectReadDao;
import com.party.core.dao.write.crowdfund.AnalyzeWriteDao;
import com.party.core.dao.write.crowdfund.ProjectWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectAnalyze;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.service.crowdfund.IProjectService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.map.HashedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 众筹项目服务接口实现
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 18:40
 */
@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectReadDao projectReadDao;

    @Autowired
    private ProjectWriteDao projectWriteDao;

    @Autowired
    private SupportReadDao supportReadDao;

    @Autowired
    private TargetProjectReadDao targetProjectReadDao;

    @Autowired
    private ActivityReadDao activityReadDao;


    /**
     * 项目插入
     * @param project 项目信息
     * @return 项目编号
     */
    @Override
    public String insert(Project project) {
        BaseModel.preInsert(project);
        boolean result = projectWriteDao.insert(project);
        if (result){
            return project.getId();
        }
        return null;
    }

    /**
     * 项目更新
     * @param project 项目信息
     * @return 是否更新成功（true/false）
     */
    @Override
    public boolean update(Project project) {
        return projectWriteDao.update(project);
    }

    /**
     * 逻辑删除项目
     * @param id 实体主键
     * @return 是否删除成功（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return projectWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除项目信息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return projectWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除项目信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return projectWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除项目信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return projectWriteDao.batchDelete(ids);
    }

    /**
     * 根据编号获取项目信息
     * @param id 主键
     * @return 项目信息
     */
    @Override
    public Project get(String id) {
        return projectReadDao.get(id);
    }

    /**
     * 获取项目信息包含创建者
     * @param id 编号
     * @return 项目信息
     */
    @Override
    public ProjectWithAuthor getWithAuthor(String id) {
        return projectReadDao.getWithAuthor(id);
    }

    /**
     * 分页查询项目信息
     * @param param 项目信息
     * @param page 分页信息
     * @return 项目列表
     */
    @Override
    public List<Project> listPage(HashMap<String, Object> param, Page page) {
        return projectReadDao.listWithSupportPage(param, page);
    }

    /**
     * 查询所有项目信息
     * @param param 项目信息
     * @return 项目列表
     */
    @Override
    public List<Project> list(HashMap<String, Object> param) {
        return projectReadDao.listWithSupportPage(param, null);
    }

    /**
     * 分页查询项目信息
     * @param project 项目信息
     * @param page 分页信息
     * @return 项目列表
     */
    @Override
    public List<Project> listPage(Project project, Page page) {
        return projectReadDao.listPage(project, page);
    }

    /**
     * 查询所有项目信息
     * @param project 项目信息
     * @return 项目列表
     */
    @Override
    public List<Project> list(Project project) {
        return projectReadDao.listPage(project,null);
    }

    /**
     * 联合创建者查询
     * @param projectWithAuthor 查询参数
     * @param page 分页参数
     * @return 项目列表
     */
    @Override
    public List<ProjectWithAuthor> listPage(ProjectWithAuthor projectWithAuthor, Page page) {
        return projectReadDao.listWithAuthorPage(projectWithAuthor, page);
    }

    /**
     * 联合创建者查询所有
     * @param projectWithAuthor 查询参数
     * @return 项目列表
     */
    @Override
    public List<ProjectWithAuthor> list(ProjectWithAuthor projectWithAuthor) {
        return projectReadDao.listWithAuthorPage(projectWithAuthor, null);
    }

    /**
     * 批量查询项目信息
     * @param ids 主键集合
     * @param project 项目信息
     * @param page 分页信息
     * @return
     */
    @Override
    public List<Project> batchList(@NotNull Set<String> ids, Project project, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return projectReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 是否众筹成功
     * @param targetAmount 目标筹集资金
     * @param actualAmount 实际筹集资金
     * @return 是否成功（true/false）
     */
    public boolean isSuccess(Float targetAmount, Float actualAmount){
        if (BigDecimalUtils.round(actualAmount, 2) >= BigDecimalUtils.round(targetAmount, 2)){
            return true;
        }
        return false;
    }

    /**
     * 获取可筹集最大金额
     * @param id 众筹编号
     * @return 最大金额
     */
    @Override
    public Float getMaxAmount(String id) {
        Project project = this.get(id);
        Float realTimeAmount = supportReadDao.sumByProjectId(id);
        realTimeAmount = realTimeAmount == null ? 0 : realTimeAmount;
        float num = BigDecimalUtils.sub(project.getTargetAmount(), realTimeAmount);
        num = BigDecimalUtils.round(num, 2);
        num = num < 0 ? 0 : num;
        return num;
    }


    /**
     * 根据分销关系查询众筹项目
     * @param distributionId 分销编号
     * @return 项目列表
     */
    @Override
    public List<Project> listForDistributionId(String distributionId) {
        return projectReadDao.listForDistributionId(distributionId);
    }

    /**
     * 根据目标查询众筹项目
     * @param targetId 目标编号
     * @return 项目列表
     */
    @Override
    public List<Project> listForTargetId(String targetId) {
        return projectReadDao.listForTargetId(targetId);
    }


    /**
     * 到期众筹
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> endList() {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("end", true);
        param.put("success", Constant.IS_CROWFUND_ING); //众筹中的
        return projectReadDao.list(param, null);
    }

    /**
     * 众筹中
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> underwayList() {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("success", Constant.IS_CROWFUND_ING); //众筹中的
        param.put("crowdfundHintSwitch", YesNoStatus.YES.getCode());//允许通知
        return projectReadDao.list(param, null);
    }

    /**
     * 众筹成功的
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> successList() {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("success", Constant.IS_CROWFUND_SUCCESS); //众筹成功
        param.put("crowdfundHintSwitch", YesNoStatus.YES.getCode());//允许通知
        return projectReadDao.list(param, null);
    }


    /**
     * 所有众筹
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> allList() {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("crowdfundHintSwitch", YesNoStatus.YES.getCode());//允许通知
        return projectReadDao.list(param, null);
    }

    /**
     * 进行中的
     * @param targetId 目标编号
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> underwayList(String targetId) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("success", Constant.IS_CROWFUND_ING); //众筹中的
        param.put("targetId", targetId);
        return projectReadDao.list(param, null);
    }

    /**
     * 成功的
     * @param targetId 目标编号
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> successList(String targetId) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("success", Constant.IS_CROWFUND_SUCCESS); //众筹成功
        param.put("targetId", targetId);
        return projectReadDao.list(param, null);
    }

    /**
     * 所有众筹
     * @param targetId 目标编号
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> allList(String targetId) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("targetId", targetId);
        return projectReadDao.list(param, null);
    }

    /**
     * 判断众筹是否过期
     * @param project 众筹项目
     * @return 是否过期(true/false)
     */
    @Override
    public boolean isOverdue(Project project) {
        int result = DateUtils.compareDate(new Date(), project.getEndDate());
        if (result == 1){
            return false;
        }
        return true;
    }

    /**
     * 项目下的众筹数
     * @param targetId 项目编号
     * @return 众筹数
     */
    @Override
    public Integer sizeForTargetId(String targetId) {
        return projectReadDao.sizeForTargetId(targetId, null);
    }

    /**
     * 项目下的众筹数
     * @param targetId 项目编号
     * @param isSuccess 状态
     * @return 众筹数
     */
    @Override
    public Integer sizeForTargetId(String targetId, Integer isSuccess) {
        Integer size = projectReadDao.sizeForTargetId(targetId, isSuccess);
        return null == size ? 0 : size;
    }

    /**
     * 根据事件编号查询众筹数
     * @param eventId 事件编号
     * @return 众筹数
     */
    @Override
    public Integer countForEvent(String eventId) {
        return projectReadDao.countForEvent(eventId, null);
    }

    /**
     * 根据事件编号查询项目
     * @param eventId 事件编号
     * @param isSuccess 状态
     * @return 项目数
     */
    @Override
    public Integer countForEvent(String eventId, Integer isSuccess) {
        Integer size = projectReadDao.countForEvent(eventId, isSuccess);
        return null == size ? 0 : size;
    }

    /**
     * 众筹分析列表
     * @param projectAnalyze 众筹分析
     * @param page 分页参数
     * @return 众筹分析列表
     */
    @Override
    public List<ProjectAnalyze> analyzeList(ProjectAnalyze projectAnalyze, Page page) {
        List<ProjectAnalyze> list = analyzeList(projectAnalyze, true, page);
        List<String> ids = LangUtils.transform(list, new Function<ProjectAnalyze, String>() {
            @Override
            public String apply(ProjectAnalyze projectAnalyze) {
                return projectAnalyze.getId();
            }
        });
        projectAnalyze.setIds(ids);
        return analyzeList(projectAnalyze, false, null);
    }

    /**
     * 众筹分析列表
     * @param projectAnalyze 众筹分析
     * @param groupBy 是否分组
     * @param page 分页参数
     * @return 众筹分析列表
     */
    @Override
    public List<ProjectAnalyze> analyzeList(ProjectAnalyze projectAnalyze, boolean groupBy, Page page) {
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("authorName", projectAnalyze.getAuthorName());
        param.put("parentName", projectAnalyze.getParentName());
        param.put("isFriend", projectAnalyze.getIsFriend());
        param.put("isGroup", projectAnalyze.getIsGroup());
        param.put("isSuccess", projectAnalyze.getIsSuccess());
        param.put("labelId", projectAnalyze.getLabelId());
        param.put("sort", projectAnalyze.getSort());
        param.put("operator", projectAnalyze.getOperator());
        param.put("operatorNum",projectAnalyze.getOperatorNum());
        param.put("groupBy", groupBy);
        param.put("ids", projectAnalyze.getIds());
        param.put("targetId", projectAnalyze.getTargetId());
        param.put("eventId", projectAnalyze.getEventId());
        return projectReadDao.analyzeList(param, page);
    }

    /**
     * 其他的众筹
     * @param projectId 众筹
     * @return 众筹列表
     */
    @Override
    public List<ProjectWithAuthor> otherProject(String projectId) {
        TargetProject targetProject = targetProjectReadDao.findByProjectId(projectId);
        Activity activity = activityReadDao.get(targetProject.getTargetId());
        ProjectWithAuthor projectWithAuthor = getWithAuthor(projectId);
        HashMap<String, Object> param = Maps.newHashMap();
        param.put("targetAuthorId", activity.getMember());
        param.put("authorId", projectWithAuthor.getAuthorId());
        List<ProjectWithAuthor> list = projectReadDao.list(param, null);

        Integer removeId = null;
        for (int i = 0 ; i<list.size(); i++){
            ProjectWithAuthor p = list.get(i);
            if (p.getId().equals(projectWithAuthor.getId())){
                removeId = i;
            }
        }
        if (null != removeId){
            list.remove(removeId.intValue());
        }

        return list;
    }


    /**
     * 众筹下支持者总数
     * @param targetId 目标编号
     * @return 支持者总数
     */
    @Override
    public Integer sumfavorerNum(String targetId) {
        return projectReadDao.sumfavorerNum(targetId);
    }
}
