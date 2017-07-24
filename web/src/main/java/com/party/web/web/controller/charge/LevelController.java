package com.party.web.web.controller.charge;

import com.google.common.collect.Maps;
import com.party.common.utils.DateUtils;
import com.party.core.model.charge.Level;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.service.charge.ILevelService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.system.IMemberSysRoleService;
import com.party.core.service.system.ISysRoleService;
import com.party.web.utils.RealmUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 等级
 */
@Controller
@RequestMapping("/charge/level")
public class LevelController {
    @Autowired
    private ILevelService levelService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private IMemberSysRoleService memberSysRoleService;
    @Autowired
    private IOrderFormService orderFormService;
    @Autowired
    private IMemberService memberService;

    @RequestMapping("levelList")
    public ModelAndView levelList() {
        ModelAndView modelAndView = new ModelAndView("charge/levelList");
        List<Level> levels = levelService.list(new Level());
        modelAndView.addObject("levels", levels);
        return modelAndView;
    }

    @RequestMapping("{levelId}/buyOrder")
    public ModelAndView buyOrder(@PathVariable("levelId") String levelId) {
        ModelAndView mv = new ModelAndView("charge/buyOrder");
        Level level = levelService.get(levelId);
        mv.addObject("level", level);

        // 查看这个套餐当天是否产生未支付的订单，如有则返回；否则生成新订单
        String memberId = RealmUtils.getCurrentUser().getId();
        OrderForm t = new OrderForm();
        t.setMemberId(memberId);
        t.setGoodsId(levelId);
        t.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
        Map<String, Object> params = Maps.newHashMap();
        Date sd = new Date();
        String std = DateUtils.formatDate(sd, "yyyy-MM-dd") + " 00:00:00";
        params.put("startDate", std);
        String end = DateUtils.formatDate(sd, "yyyy-MM-dd") + " 23:59:59";
        params.put("endDate", end);
        OrderForm orderForm = orderFormService.getOrderByCondition(t, params);
        if (orderForm == null) {
            Member adminMember = memberService.findByLoginName("admin");
            Member member = memberService.get(memberId);
            orderForm = new OrderForm();
            orderForm.setCreateBy(memberId);
            orderForm.setUpdateBy(memberId);
            orderForm.setMemberId(memberId);
            orderForm.setGoodsId(levelId);
            orderForm.setInitiatorId(adminMember.getId());
            orderForm.setPayment(level.getPrice());
            orderForm.setIsPay("0");
            orderForm.setUnit(1);
            orderForm.setLinkman(member.getRealname());
            String title = level.getName() + "功能";
            if (level.getUnit().equals(Level.UNIT_HALF_YEAR)) {
                title += level.getUnit();
            } else {
                title += ("一" + level.getUnit());
            }
            orderForm.setTitle(title);
            orderForm.setPhone(member.getMobile());
            orderForm.setType(OrderType.ORDER_LEVEL.getCode());
            orderForm.setStatus(OrderStatus.ORDER_STATUS_TO_BE_PAID.getCode());
            orderFormService.insert(orderForm);
        }
        mv.addObject("orderForm", orderForm);
        return mv;
    }
}
