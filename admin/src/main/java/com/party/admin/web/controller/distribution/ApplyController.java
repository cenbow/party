package com.party.admin.web.controller.distribution;

import com.party.admin.biz.distribution.DistributionApplyBizService;
import com.party.admin.web.dto.output.distribution.ApplyListOutput;
import com.party.common.paging.Page;
import com.party.core.model.activity.ActStatus;
import com.party.core.model.member.ApplyWithActivity;
import com.party.core.model.member.MemberAct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 分销报名控制器
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 17:15
 */

@Controller
@RequestMapping(value = "distribution/apply")
public class ApplyController {

    @Autowired
    private DistributionApplyBizService distributionApplyBizService;


    @RequestMapping(value = "list/{distributionId}")
    public ModelAndView list(@PathVariable(value = "distributionId") String distributionId, ApplyWithActivity applyWithActivity, Page page){
        ModelAndView modelAndView = new ModelAndView("distribution/applyList");
        List<ApplyListOutput> listOutputs = distributionApplyBizService.list(distributionId, applyWithActivity, page);
        Map<Integer, String> actStatus = ActStatus.convertMap();
        modelAndView.addObject("list", listOutputs);
        modelAndView.addObject("actStatus", actStatus);
        modelAndView.addObject("distributionId", distributionId);
        modelAndView.addObject("memberAct", applyWithActivity);
        modelAndView.addObject("page", page);
        return modelAndView;
    }
}
