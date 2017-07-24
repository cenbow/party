package com.party.admin.task;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.party.admin.biz.activity.OrderBizService;
import com.party.common.utils.LangUtils;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.service.order.IOrderFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 订单作废处理定时任务
 * Created by wei.li
 *
 * @date 2017/3/30 0030
 * @time 17:30
 */
@Component(value = "quartzOrder")
public class QuartzOrder {

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private IOrderFormService orderFormService;

    protected static Logger logger = LoggerFactory.getLogger(QuartzOrder.class);


    /**
     * 作废订单
     */
    public void refresh(){
        List<OrderForm> orderFormList = orderFormService.invalidList();
        for (OrderForm orderForm : orderFormList){
            try {
                orderBizService.invalid(orderForm);
                logger.info("订单作废成功");
            } catch (Exception e) {
                logger.error("订单作废异常", e);
                continue;
            }
        }
    }

    /**
     * 删除作废订单
     * (每周六 23:59 执行)
     */
    public void clean(){
        Set<Integer> status = Sets.newHashSet();
        status.add(OrderStatus.ORDER_STATUS_OTHER.getCode());
        List<OrderForm> orderFormList = orderFormService.listPage(new OrderForm(), null, status);
        List<String> ids = LangUtils.transform(orderFormList, new Function<OrderForm, String>() {
            @Override
            public String apply(OrderForm orderForm) {
                return orderForm.getId();
            }
        });
        orderFormService.batchDelete(Sets.newHashSet(ids));
        logger.info("删除作废订单成功");
    }




}
