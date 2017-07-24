package com.party.web.web.controller.distribution;

import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.redis.StringJedis;
import com.party.core.model.activity.Activity;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.service.activity.IActivityService;
import com.party.web.biz.asynexport.IAsynExportService;
import com.party.web.biz.distribution.DistributionBizService;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.distribution.ChildListInput;
import com.party.web.web.dto.input.distribution.DistributionListInput;
import com.party.web.web.dto.output.distribution.ChildListOutput;
import com.party.web.web.dto.output.distribution.DistributionListOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分销商控制器
 * Created by wei.li
 *
 * @date 2017/3/13 0013
 * @time 10:52
 */

@Controller
@RequestMapping(value = "/distribution/relation")
public class DistributionController {

    @Autowired
    private DistributionBizService distributionBizService;
    
    @Autowired
    private IActivityService activityService;

    @Resource(name = "distributionAsynExportService")
    private IAsynExportService distributionAsynExportService;

    @Autowired
    private StringJedis stringJedis;

    /**
     * 分销列表
     * @param input 输入视图
     * @param page 分页参数
     * @return 分销列表
     */
    @RequestMapping(value = "list")
    public ModelAndView list(DistributionListInput input, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("/distribution/distributionList");
        List<DistributionListOutput> listOutputs = distributionBizService.list(input, page);
        modelAndView.addObject("page", page);
        modelAndView.addObject("input", input);
        modelAndView.addObject("list", listOutputs);
        return modelAndView;
    }


    /**
     * 分销列表
     * @param targetId 目标编号
     * @param input 输入视图
     * @param page 分页参数
     * @return 分销列表
     */
    @RequestMapping(value = "targetList/{targetId}")
    public ModelAndView targetList(@PathVariable(value = "targetId")String targetId, DistributionListInput input, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("/distribution/distributionList");
        List<DistributionListOutput> listOutputs = distributionBizService.targetList(targetId, input, page);
        Activity activity = activityService.get(targetId);
        String zipUrl = stringJedis.getValue(Constant.PRE_ZIP_URL + "distribution" + targetId);
        modelAndView.addObject("activity", activity);
        modelAndView.addObject("targetId", targetId);
        modelAndView.addObject("page", page);
        modelAndView.addObject("input", input);
        modelAndView.addObject("list", listOutputs);
        modelAndView.addObject("zipUrl", zipUrl);
        return modelAndView;
    }


    /**
     * 分销导出
     * @param targetId 目标编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "targetListExport")
    public AjaxResult targetListExport(String targetId){
        distributionAsynExportService.export(targetId);
        return AjaxResult.success();
    }

    /**
     * 子级分销商列表
     * @param distributionId 分销编号
     * @param page 分页参数
     * @return 分销列表
     */
    @RequestMapping(value = "childList/{distributionId}")
    public ModelAndView childList(@PathVariable(value = "distributionId") String distributionId,String targetId, ChildListInput input,Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("/distribution/childList");
        List<ChildListOutput> childList = distributionBizService.childList(distributionId, input, page);
        modelAndView.addObject("list", childList);
        modelAndView.addObject("input", input);
        modelAndView.addObject("targetId", targetId);
        modelAndView.addObject("distributionId", distributionId);
        modelAndView.addObject("page", page);
        return modelAndView;
    }
}
