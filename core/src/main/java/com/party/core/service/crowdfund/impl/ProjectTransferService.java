package com.party.core.service.crowdfund.impl;

import com.party.core.model.crowdfund.Support;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.model.crowdfund.TargetProject;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.IProjectTransferService;
import com.party.core.service.crowdfund.ISupportService;
import com.party.core.service.crowdfund.ITargetProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 众筹数据转换接口实现
 * Created by wei.li
 *
 * @date 2017/7/13 0013
 * @time 16:24
 */

@Service
public class ProjectTransferService implements IProjectTransferService {

    @Autowired
    private ISupportService supportService;

    @Autowired
    private ProjectReviseService projectReviseService;

    @Autowired
    private ITargetProjectService targetProjectService;


    /**
     * 转换支持
     * @param sourceId 资源编号
     * @param targetId 目标编号
     */
    @Override
    @Transactional
    public void transferSupport(String sourceId, String targetId) {
        List<SupportWithMember> list = supportService.findByProjectId(sourceId);
        for (SupportWithMember supportWithMember : list){
            Support support = supportWithMember;
            support.setProjectId(targetId);
            supportService.update(support);
        }

        //校正数据
        projectReviseService.reviseFavorer(sourceId);
        projectReviseService.reviseFavorer(targetId);

        //校正众筹目标数据
        TargetProject source = targetProjectService.findByProjectId(sourceId);
        TargetProject target = targetProjectService.findByProjectId(targetId);
        projectReviseService.reviseTarget(source.getTargetId());
        projectReviseService.reviseTarget(target.getTargetId());
    }
}
