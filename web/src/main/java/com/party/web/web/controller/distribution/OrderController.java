package com.party.web.web.controller.distribution;

import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.web.biz.distribution.DistributionOrderBizService;
import com.party.web.web.dto.output.distribution.OrderListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 分销订单控制器
 * Created by wei.li
 *
 * @date 2017/3/14 0014
 * @time 11:20
 */

@Controller
@RequestMapping(value = "distribution/order")
public class OrderController {


    @Autowired
    private DistributionOrderBizService distributionOrderBizService;


    /**
     * 列表视图
     * @param distributionId 分销编号
     * @return
     */
    @RequestMapping(value = "listView")
    public ModelAndView listView(String distributionId, String hrefId, String targetId, Page page){
        ModelAndView modelAndView = new ModelAndView("distribution/orderList");
        List<OrderListOutput> listOutputs = distributionOrderBizService.list(distributionId, page);
        modelAndView.addObject("distributionId", distributionId);
        modelAndView.addObject("hrefId", hrefId);
        modelAndView.addObject("targetId", targetId);
        modelAndView.addObject("list", listOutputs);
        modelAndView.addObject("page", page);
        return modelAndView;
    }


}
