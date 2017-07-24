package com.party.admin.web.controller.distribution;

import com.party.admin.biz.distribution.DistributionOrderBizService;
import com.party.admin.web.dto.output.distribution.OrderListOutput;
import com.party.common.paging.Page;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 分销订单
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 14:46
 */

@Controller
@RequestMapping(value = "distribution/order")
public class OrderController {

    @Autowired
    private DistributionOrderBizService distributionOrderBizService;


    /**
     * 分销订单控制器
     * @param distributionId 分销
     * @param orderForm
     * @param page
     * @return
     */
    @RequestMapping(value = "list/{distributionId}")
    public ModelAndView list(@PathVariable(value = "distributionId") String distributionId, OrderForm orderForm, Page page){
        ModelAndView modelAndView = new ModelAndView("distribution/orderList");
        List<OrderListOutput> list = distributionOrderBizService.list(distributionId, orderForm, page);
        Map<Integer, String> statusMap = OrderStatus.convertMap();
        modelAndView.addObject("list", list);
        modelAndView.addObject("statusMap", statusMap);
        modelAndView.addObject("page", page);
        modelAndView.addObject("distributionId", distributionId);
        modelAndView.addObject("orderForm", orderForm);
        return modelAndView;
    }
}
