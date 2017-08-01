package com.party.web.web.controller.charge;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.party.common.utils.DateUtils;
import com.party.core.model.charge.PackageMember;
import com.party.core.model.charge.ProductPackage;
import com.party.core.model.member.Member;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.system.SysRole;
import com.party.core.service.charge.IPackageService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.order.IOrderFormService;
import com.party.web.biz.charge.PackageBizService;
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
@RequestMapping("/charge/package")
public class PackageController {
    @Autowired
    private IPackageService packageService;
    @Autowired
    private IOrderFormService orderFormService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private PackageBizService packageBizService;

    @RequestMapping("packageList")
    public ModelAndView levelList() {
        ModelAndView mv = new ModelAndView("charge/packageList");
        List<ProductPackage> productPackages = packageService.list(new ProductPackage());
        mv.addObject("packages", productPackages);

        String memberId = RealmUtils.getCurrentUser().getId();
        List<SysRole> sysRoles = Lists.newArrayList();
        PackageMember packageMember = packageBizService.getPackageMember(memberId, sysRoles);
        mv.addObject("packageMember", packageMember);
        mv.addObject("sysRoles", sysRoles);

        List<Map<String, Object>> privileges = packageBizService.getPackagePrivilegeList(productPackages);
        mv.addObject("privileges", privileges);
        return mv;
    }

    @RequestMapping("{levelId}/buyOrder")
    public ModelAndView buyOrder(@PathVariable("levelId") String levelId) {
        ModelAndView mv = new ModelAndView("charge/buyOrder");
        ProductPackage productPackage = packageService.get(levelId);
        mv.addObject("level", productPackage);

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
            orderForm.setPayment(productPackage.getPrice());
            orderForm.setIsPay("0");
            orderForm.setUnit(1);
            orderForm.setLinkman(member.getRealname());
            String title = productPackage.getName() + "功能";
            if (productPackage.getUnit().equals(ProductPackage.UNIT_HALF_YEAR)) {
                title += productPackage.getUnit();
            } else {
                title += ("一" + productPackage.getUnit());
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
