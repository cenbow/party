package com.party.core.service.label.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.crowdfund.ProjectLabelReadDao;
import com.party.core.dao.read.label.LabelReadDao;
import com.party.core.dao.write.label.LabelWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.crowdfund.ProjectLabel;
import com.party.core.model.label.Label;
import com.party.core.service.label.ILabelService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/** 标签列表
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 17:00
 */
@Service
public class LabelService implements ILabelService {

    @Autowired
    private LabelReadDao labelReadDao;

    @Autowired
    private LabelWriteDao labelWriteDao;

    @Autowired
    private ProjectLabelReadDao projectLabelReadDao;

    /**
     * 插入标签
     * @param label 标签
     * @return 编号
     */
    @Override
    public String insert(Label label) {
        BaseModel.preInsert(label);
        boolean result = labelWriteDao.insert(label);
        if (result){
            return label.getId();
        }
        return null;
    }

    /**
     * 更新标签
     * @param label 标签
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Label label) {
        return labelWriteDao.update(label);
    }

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return labelWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        List<ProjectLabel> labels = projectLabelReadDao.findByLabelId(id);
        if (!CollectionUtils.isEmpty(labels)){
            throw new BusinessException("标签被分配,删除失败");
        }
        return labelWriteDao.delete(id);
    }

    /**
     * 批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return labelWriteDao.batchDelete(ids);
    }

    /**
     * 批量删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return labelWriteDao.batchDelete(ids);
    }

    /**
     * 获取标签
     * @param id 主键
     * @return 标签
     */
    @Override
    public Label get(String id) {
        return labelReadDao.get(id);
    }

    /**
     * 分页查询
     * @param label 标签
     * @param page 分页信息
     * @return 标签列表
     */
    @Override
    public List<Label> listPage(Label label, Page page) {
        return labelReadDao.listPage(label, page);
    }

    /**
     * 列表查询
     * @param label 标签
     * @return 标签列表
     */
    @Override
    public List<Label> list(Label label) {
        return labelReadDao.listPage(label, null);
    }

    @Override
    public List<Label> batchList(@NotNull Set<String> ids, Label label, Page page) {
        return null;
    }
}
