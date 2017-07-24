package com.party.core.service.sign.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.dao.read.sign.SignApplyReadDao;
import com.party.core.dao.read.sign.SignRecordReadDao;
import com.party.core.dao.write.sign.SignApplyWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignApply;
import com.party.core.model.sign.SignApplyStatus;
import com.party.core.model.sign.SignGradeStatus;
import com.party.core.service.sign.ISignApplyService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 签到报名信息接口实现
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 17:57
 */

@Service
public class SignApplyService implements ISignApplyService {

    @Autowired
    private SignApplyReadDao signApplyReadDao;

    @Autowired
    private SignApplyWriteDao signApplyWriteDao;

    @Autowired
    private SignRecordReadDao signRecordReadDao;

    /**
     * 插入签到报名信息
     * @param signApply 签到报名
     * @return 报名编号
     */
    @Override
    public String insert(SignApply signApply) {

        //是否已经报名
        GroupMember groupMember = this.get(signApply.getCreateBy(), signApply.getProjectId());
        if (null != groupMember){
            throw new BusinessException("该项目已经报名");
        }

        BaseModel.preInsert(signApply);
        boolean result = signApplyWriteDao.insert(signApply);
        if (result){
            return signApply.getId();
        }
        return null;
    }

    /**
     * 报名信息更新
     * @param signApply 签到报名信息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(SignApply signApply) {
        return signApplyWriteDao.update(signApply);
    }

    /**
     * 逻辑删除签到报名
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signApplyWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除签到报名
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signApplyWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除签到报名
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signApplyWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除签到报名
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signApplyWriteDao.batchDelete(ids);
    }

    /**
     * 获取签到报名
     * @param id 主键
     * @return 签到报名信息
     */
    @Override
    public SignApply get(String id) {
        return signApplyReadDao.get(id);
    }

    /**
     * 获取签到报名
     * @param id 编号
     * @return 签到报名信息
     */
    @Override
    public GroupMember getGroupMember(String id) {
        return signApplyReadDao.getGroupMember(id);
    }

    /**
     * 获取报名信息
     * @param authorId 会员编号
     * @param projectId 项目编号
     * @return 报名信息
     */
    @Override
    public GroupMember get(String authorId, String projectId) {
        return signApplyReadDao.getUnique(authorId, projectId);
    }

    /**
     * 根据创建者和小组查询报名
     * @param authorId 创建者
     * @param groupId 小组
     * @return 报名信息
     */
    @Override
    public GroupMember getByAuthorAndGroup(String authorId, String groupId) {
        return signApplyReadDao.getByAuthorAndGroup(authorId, groupId);
    }

    /**
     * 分页查询签到报名信息
     * @param signApply 签到报名
     * @param page 分页信息
     * @return 签到报名列表
     */
    @Override
    public List<SignApply> listPage(SignApply signApply, Page page) {
        return signApplyReadDao.listPage(signApply, page);
    }

    /**
     * 查询所有签到报名
     * @param signApply 签到报名信息
     * @return 签到报名列表
     */
    @Override
    public List<SignApply> list(SignApply signApply) {
        return signApplyReadDao.listPage(signApply, null);
    }

    /**
     * 小组成员列表
     * @param groupMember 小组成员
     * @param page 分页参数
     * @return 小组成员列表
     */
    @Override
    public List<GroupMember> groupMemberList(GroupMember groupMember, Page page) {
        return signApplyReadDao.groupMemberList(groupMember, page);
    }

    @Override
    public List<SignApply> batchList(@NotNull Set<String> ids, SignApply signApply, Page page) {
        return null;
    }


    /**
     * 用户是否报名
     * @param authorId 用户编号
     * @param projectId 项目编号
     * @return 是否报名（true/false）
     */
    @Override
    public boolean isApply(String authorId, String projectId) {
        SignApply signApply = this.get(authorId, projectId);
        if (null == signApply){
            return false;
        }
        return true;
    }

    /**
     * 报名在项目中排行
     * @param projectId 项目编号
     * @param id 报名编号
     * @return 排名
     */
    @Override
    public Integer projectRank(String projectId, String id) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("projectId", projectId);
        param.put("status", SignApplyStatus.PASS_STATUS.getCode());
        param.put("gradeStatus", SignGradeStatus.EFFECTIVE.getCode());
        return signApplyReadDao.rank(id, param);
    }

    /**
     * 报名在小组排行
     * @param groupId 小组编号
     * @param id 报名编号
     * @return 排名
     */
    @Override
    public Integer groupRank(String groupId, String id) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("groupId", groupId);
        param.put("status", SignApplyStatus.PASS_STATUS.getCode());
        return signApplyReadDao.rank(id, param);
    }


    /**
     * 排行列表
     * @param projectId 项目编号
     * @param startTime 开始时间
     * @param endTime 接受时间
     * @param page 分页参数
     * @return 列表数据
     */
    @Override
    public List<GroupMember> rankList(String projectId, String startTime, String endTime, Page page) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("projectId", projectId);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("status", SignApplyStatus.PASS_STATUS.getCode());
        param.put("gradeStatus", SignGradeStatus.EFFECTIVE.getCode());
        List<GroupMember> list = signApplyReadDao.rankList(param, page);
        list = LangUtils.transform(list, new Function<GroupMember, GroupMember>() {
            @Override
            public GroupMember apply(GroupMember input) {
                if (null == input.getStepNum()){
                    input.setStepNum(0l);
                }
                return input;
            }
        });
        return list;
    }
}
