package com.party.admin.web.controller.distribution;

import java.util.List;

import com.party.admin.biz.asynexport.IAsynExportService;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.constant.Constant;
import com.party.common.redis.StringJedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.distribution.DistributionBizService;
import com.party.admin.web.dto.input.distribution.ChildListInput;
import com.party.admin.web.dto.input.distribution.DistributionListInput;
import com.party.admin.web.dto.output.distribution.ChildListOutput;
import com.party.admin.web.dto.output.distribution.DistributionListOutput;
import com.party.common.paging.Page;

import javax.annotation.Resource;

/**
 * 分销控制器
 * Created by wei.li
 *
 * @date 2017/3/9 0009
 * @time 11:37
 */

@Controller
@RequestMapping(value = "distribution/relation")
public class DistributionController {

    @Autowired
    private DistributionBizService distributionBizService;

    @Autowired
    private StringJedis stringJedis;

    @Resource(name = "distributionAsynExportService")
    private IAsynExportService distributionAsynExportService;


    /**
     * 分销关系列表
     * @param input 输入视图
     * @param page 分页参数
     * @return 分销关系列表
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public ModelAndView list(DistributionListInput input, Page page){
        page.setLimit(20);
        ModelAndView modelAndView = new ModelAndView("/distribution/distributionList");
        List<DistributionListOutput> list = distributionBizService.list(input, page);
        modelAndView.addObject("list", list);
        modelAndView.addObject("input", input);
        modelAndView.addObject("page", page);
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
        String zipUrl = stringJedis.getValue(Constant.PRE_ZIP_URL + "distribution" + targetId);
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
    @RequestMapping(value = "childList/{distributionId}/{targetId}")
    public ModelAndView childList(@PathVariable(value = "distributionId") String distributionId, @PathVariable(value = "targetId") String targetId, ChildListInput input, Page page){
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
