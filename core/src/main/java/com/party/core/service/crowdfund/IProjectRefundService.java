package com.party.core.service.crowdfund;

import com.party.core.model.crowdfund.Project;

/**
 * 众筹退款接口
 * Created by wei.li
 *
 * @date 2017/4/28 0028
 * @time 11:46
 */
public interface IProjectRefundService {

    /**
     * 众筹退款
     * @param project 众筹信息
     */
    void refund(Project project);
}
