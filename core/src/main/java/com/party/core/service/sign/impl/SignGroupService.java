package com.party.core.service.sign.impl;


import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.dao.read.sign.SignApplyReadDao;
import com.party.core.dao.read.sign.SignGroupReadDao;
import com.party.core.dao.write.sign.SignGroupWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.sign.*;
import com.party.core.service.sign.ISignGroupService;
import com.party.core.service.sign.ISignProjectService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 签到分组业务实现
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 17:36
 */

@Service
public class SignGroupService implements ISignGroupService {

    @Autowired
    private SignGroupReadDao signGroupReadDao;

    @Autowired
    private SignGroupWriteDao signGroupWriteDao;

    @Autowired
    private SignApplyReadDao signApplyReadDao;

    /**
     * 插入签到分组
     * @param signGroup 签到分组
     * @return 分组编号
     */
    @Override
    public String insert(SignGroup signGroup) {
        BaseModel.preInsert(signGroup);
        boolean result = signGroupWriteDao.insert(signGroup);
        if (result){
            return signGroup.getId();
        }
        return null;
    }

    /**
     * 更新签到分组
     * @param signGroup 签到分组
     * @return 是否更新成功（true/false）
     */
    @Override
    public boolean update(SignGroup signGroup) {
        return signGroupWriteDao.update(signGroup);
    }

    /**
     * 逻辑删除签到分组
     * @param id 实体主键
     * @return 是否删除成功（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signGroupWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除签到分组
     * @param id 实体主键
     * @return 是否删除成功（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        //是否有人报名
        SignApply signApply = new SignApply();
        signApply.setGroupId(id);
        List<SignApply> list = signApplyReadDao.listPage(signApply, null);
        if (!CollectionUtils.isEmpty(list)){
            throw new BusinessException("该队伍有人报名,不能删除");
        }
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signGroupWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除签到分组
     * @param ids 主键集合
     * @return 是否删除成功（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signGroupWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除签到分组
     * @param ids 主键集合
     * @return 是否删除成功（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signGroupWriteDao.batchDelete(ids);
    }

    /**
     * 获取签到分组
     * @param id 主键
     * @return 签到分组
     */
    @Override
    public SignGroup get(String id) {
        return signGroupReadDao.get(id);
    }

    /**
     * 获取签到小组
     * @param id 编号
     * @return 签到小组
     */
    @Override
    public GroupAuthor getGroupAuthor(String id) {
        return signGroupReadDao.getGroupAuthor(id);
    }

    /**
     * 签到分组分页查询
     * @param signGroup
     * @param page 分页信息
     * @return 分组列表
     */
    @Override
    public List<SignGroup> listPage(SignGroup signGroup, Page page) {
        return signGroupReadDao.listPage(signGroup, page);
    }

    /**
     * 签到分组列表
     * @param signGroup 签到分组
     * @return 列表信息
     */
    @Override
    public List<SignGroup> list(SignGroup signGroup) {
        return signGroupReadDao.listPage(signGroup, null);
    }


    /**
     * 小组列表
     * @param groupAuthor 签到小组
     * @param page 分页参数
     * @return 小组列表
     */
    @Override
    public List<GroupAuthor> groupAuthorList(GroupAuthor groupAuthor, Page page) {
        return signGroupReadDao.groupAuthorList(groupAuthor, page);
    }

    @Override
    public List<SignGroup> batchList(@NotNull Set<String> ids, SignGroup signGroup, Page page) {
        return null;
    }

    /**
     * 小组排行
     * @param projectId 项目名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 分页参数
     * @return 小组排行
     */
    @Override
    public List<GroupAuthor> rankList(String projectId, String startTime, String endTime, Page page) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("projectId", projectId);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("gradeStatus", SignGradeStatus.EFFECTIVE.getCode());
        List<GroupAuthor> list = signGroupReadDao.rankList(param, page);
        list = LangUtils.transform(list, new Function<GroupAuthor, GroupAuthor>() {
            @Override
            public GroupAuthor apply(GroupAuthor input) {
                if (null == input.getStepNum()){
                    input.setStepNum(0l);
                }
                return input;
            }
        });


        return list;
    }
}
