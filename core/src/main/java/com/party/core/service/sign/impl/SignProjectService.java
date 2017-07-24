package com.party.core.service.sign.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.sign.SignApplyReadDao;
import com.party.core.dao.read.sign.SignGroupReadDao;
import com.party.core.dao.read.sign.SignProjectReadDao;
import com.party.core.dao.write.sign.SignProjectWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.sign.SignApply;
import com.party.core.model.sign.SignGroup;
import com.party.core.model.sign.SignProject;
import com.party.core.model.sign.SignProjectAuthor;
import com.party.core.service.sign.ISignProjectService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 签到项目接口实现
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 17:26
 */

@Service
public class SignProjectService implements ISignProjectService {

    @Autowired
    private SignProjectReadDao signProjectReadDao;

    @Autowired
    private SignProjectWriteDao signProjectWriteDao;

    @Autowired
    private SignApplyReadDao signApplyReadDao;

    @Autowired
    private SignGroupReadDao signGroupReadDao;

    /**
     * 插入签到项目
     * @param signProject 签到项目
     * @return 项目编号
     */
    @Override
    public String insert(SignProject signProject) {
        BaseModel.preInsert(signProject);
        boolean result = signProjectWriteDao.insert(signProject);
        if (result){
            return signProject.getId();
        }
        return null;
    }

    /**
     * 更新签到项目
     * @param signProject 签到项目
     * @return 是否更新成功（true/false）
     */
    @Override
    public boolean update(SignProject signProject) {
        return signProjectWriteDao.update(signProject);
    }

    /**
     * 逻辑删除签到项目
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signProjectWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除签到项目
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {

        //是否有人报名
        SignApply signApply = new SignApply();
        signApply.setProjectId(id);
        List<SignApply> list = signApplyReadDao.listPage(signApply, null);
        if (!CollectionUtils.isEmpty(list)){
            throw new BusinessException("项目下有人报名，不能删除");
        }

        //是否有小组
        SignGroup signGroup = new SignGroup();
        signGroup.setProjectId(id);
        List<SignGroup> signGroupList = signGroupReadDao.listPage(signGroup, null);
        if (!CollectionUtils.isEmpty(signGroupList)){
            throw new BusinessException("项目小拥有小组,不能删除");
        }
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return signProjectWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除签到项目
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signProjectWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除签到项目
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return signProjectWriteDao.batchDelete(ids);
    }

    /**
     * 获取签到项目
     * @param id 主键
     * @return 签到项目
     */
    @Override
    public SignProject get(String id) {
        return signProjectReadDao.get(id);
    }


    /**
     * 获取签到项目
     * @param id 主键
     * @return 签到项目
     */
    @Override
    public SignProjectAuthor getProjectAuthor(String id) {
        return signProjectReadDao.getProjectAuthor(id);
    }

    /**
     * 分页查询签到项目
     * @param signProject 签到项目
     * @param page 分页信息
     * @return 签到项目列表
     */
    @Override
    public List<SignProject> listPage(SignProject signProject, Page page) {
        return signProjectReadDao.listPage(signProject, page);
    }

    /**
     * 查询所有签到项目
     * @param signProject 签到项目
     * @return 签到项目列表
     */
    @Override
    public List<SignProject> list(SignProject signProject) {
        return signProjectReadDao.listPage(signProject, null);
    }

    /**
     * 签到项目列表
     * @param signProjectAuthor 签到项目
     * @param page 分页参数
     * @return 列表数据
     */
    @Override
    public List<SignProjectAuthor> projectAuthorList(SignProjectAuthor signProjectAuthor, Page page) {
        return signProjectReadDao.projectAuthorList(signProjectAuthor, page);
    }

    @Override
    public List<SignProject> batchList(@NotNull Set<String> ids, SignProject signProject, Page page) {
        return null;
    }
}
