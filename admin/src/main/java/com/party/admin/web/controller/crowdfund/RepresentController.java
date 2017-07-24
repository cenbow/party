package com.party.admin.web.controller.crowdfund;

import com.party.admin.biz.crowdfund.RepresentBizService;
import com.party.admin.utils.excel.ExportExcel;
import com.party.admin.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.CrowdfundEvent;
import com.party.core.model.distributor.WithCount;
import com.party.core.service.crowdfund.ICrowdfundEventService;
import com.party.core.service.crowdfund.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * 代言控制器
 * Created by wei.li
 *
 * @date 2017/6/29 0029
 * @time 11:07
 */

@Controller
@RequestMapping(value = "crowdfund/represen")
public class RepresentController {

    @Autowired
    private RepresentBizService representBizService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ICrowdfundEventService crowdfundEventService;

    /**
     * 根据事件查询代言数据
     * @param withCount 查询参数
     * @param page 分页参数
     * @return 交互数据
     */
    @RequestMapping(value = "listForEvent")
    public ModelAndView listForEvent(WithCount withCount, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("/crowdfund/representForEventList");
        List<ListForTargetOutput> listForTargetOutputList = representBizService.listForEvent(page, withCount);
        Integer projectNum = projectService.countForEvent(withCount.getEventId());
        modelAndView.addObject("list", listForTargetOutputList);
        modelAndView.addObject("projectNum", projectNum);
        modelAndView.addObject("representNun", page.getTotalCount());
        modelAndView.addObject("page", page);
        return modelAndView;
    }
    /**
     * 代言列表导出
     * @param response 响应参数
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "representListForEventExport")
    public String representListExport(WithCount withCount, HttpServletResponse response){
        CrowdfundEvent crowdfundEvent = crowdfundEventService.get(withCount.getEventId());
        String fileName = crowdfundEvent.getName() + "众筹项目的代言" + DateUtils.todayDate() + ".xlsx";
        List<ListForTargetOutput> listForTargetOutputList = representBizService.listForEvent(null, withCount);
        try {
            new ExportExcel(fileName, ListForTargetOutput.class).setDataList(listForTargetOutputList).write(response, fileName).dispose();
            return null;
        } catch (IOException e) {
        }
        return null;
    }

}
