package com.party.core.service.crowdfund.impl;

import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.crowdfund.ProjectContentReadDao;
import com.party.core.dao.write.crowdfund.ProjectContentWriteDao;
import com.party.core.dao.write.crowdfund.ProjectWriteDao;
import com.party.core.model.crowdfund.ProjectContent;
import com.party.core.service.crowdfund.IProjectContentService;
import com.party.core.service.crowdfund.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 众筹详情服务实现
 * Created by wei.li
 *
 * @date 2017/2/23 0023
 * @time 11:18
 */

@Service
public class ProjectContentService implements IProjectContentService {

    @Autowired
    private ProjectContentReadDao projectContentReadDao;

    @Autowired
    private ProjectContentWriteDao projectContentWriteDao;

    /**
     * 插入详情
     * @param projectContent
     */
    @Override
    public String insert(ProjectContent projectContent) {
        projectContent.setId(UUIDUtils.generateRandomUUID());
        boolean result = projectContentWriteDao.insert(projectContent);
        if (result){
            return projectContent.getId();
        }
        return null;
    }

    /**
     * 更新详情
     * @param projectContent
     */
    @Override
    public void update(ProjectContent projectContent) {
        projectContentWriteDao.update(projectContent);
    }

    /**
     * 根据编号获取详情
     * @param id 编号
     * @return
     */
    @Override
    public ProjectContent get(String id) {
        return projectContentReadDao.get(id);
    }

    /**
     * 删除详情
     * @param id
     */
    @Override
    public void delete(String id) {

    }
}
