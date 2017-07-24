package com.party.core.service.crowdfund;

import com.party.core.model.crowdfund.TargetProject;
import com.party.core.service.IBaseService;

/**
 * 众筹对象关系服务接口
 * Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 14:04
 */
public interface ITargetProjectService extends IBaseService<TargetProject> {

    /**
     * 根据项目名称查找众筹关系
     * @param projectId 项目编号
     * @return 众筹关系
     */
    TargetProject findByProjectId(String projectId);



    /**
     * 根据订单号查询众筹关系
     * @param orderId 订单号
     * @return 众筹关系
     */
    TargetProject findByOrderId(String orderId);
}
