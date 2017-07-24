package com.party.core.service.crowdfund;


import com.party.core.model.crowdfund.ProjectContent;

/**
 * 众筹项目详情服务接口
 * Created by wei.li
 *
 * @date 2017/2/23 0023
 * @time 11:19
 */
public interface IProjectContentService {

    /**
     * 插入详情
     * @param projectContent
     */
    String insert(ProjectContent projectContent);

    /**
     * 更新详情
     * @param projectContent
     */
    void update(ProjectContent projectContent);

    /**
     * 根据编号获取详情
     * @param id
     * @return
     */
    ProjectContent get(String id);

    /**
     * 删除详情
     * @param id
     */
    void delete(String id);
}
