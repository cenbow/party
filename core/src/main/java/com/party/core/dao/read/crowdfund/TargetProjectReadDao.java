package com.party.core.dao.read.crowdfund;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.crowdfund.TargetProject;
import org.springframework.stereotype.Repository;

/**目标众筹关联读取
 * Created by wei.li
 *
 * @date 2017/2/24 0024
 * @time 13:44
 */
@Repository
public interface TargetProjectReadDao extends BaseReadDao<TargetProject>{

    /**
     * 根据项目名称查找众筹关系
     * @param projectId 项目编号
     * @return 众筹关系
     */
    TargetProject findByProjectId(String projectId);

    /**
     * 根据目标名称查找关系
     * @param orderId 订单号
     * @return 众筹关系
     */
    TargetProject findByOrderId(String orderId);

}
