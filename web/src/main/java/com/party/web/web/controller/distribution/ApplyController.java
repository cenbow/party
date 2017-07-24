package com.party.web.web.controller.distribution;

import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.web.biz.distribution.DistributionApplyBizService;
import com.party.web.web.dto.output.activity.MemberActOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 分销报名控制器
 * Created by wei.li
 *
 * @date 2017/3/14 0014
 * @time 10:35
 */

@Controller
@RequestMapping(value = "/distribution/apply")
public class ApplyController {

    @Autowired
    private DistributionApplyBizService distributionApplyBizService;

    /**
     * 列表查询
     * @param distributionId 分销编号
     * @return 列表视图
     */
    @RequestMapping(value = "listView")
    public ModelAndView listView(String distributionId, String hrefId, String targetId, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("/distribution/applyList");
        List<MemberActOutput> list = distributionApplyBizService.list(distributionId, page);
        modelAndView.addObject("distributionId", distributionId);
        modelAndView.addObject("hrefId", hrefId);
        modelAndView.addObject("targetId", targetId);
        modelAndView.addObject("page", page);
        modelAndView.addObject("list", list);
        return modelAndView;
    }


}
