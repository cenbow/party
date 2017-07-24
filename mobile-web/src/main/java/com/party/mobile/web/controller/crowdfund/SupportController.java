package com.party.mobile.web.controller.crowdfund;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.mobile.biz.crowdfund.ProjectBizService;
import com.party.mobile.biz.crowdfund.SupportBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.crowdfund.input.SupportStatisticsInput;
import com.party.mobile.web.dto.crowdfund.output.SupportOutput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 众筹支持控制器
 * Created by wei.li
 *
 * @date 2017/2/22 0022
 * @time 14:38
 */

@Controller
@RequestMapping(value = "crowdfund/support")
public class SupportController {

    @Autowired
    private ProjectBizService projectBizService;

    @Autowired
    private SupportBizService supportBizService;

    /**
     * 众筹支持列表
     * @param page 分页参数
     * @param id 项目编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public AjaxResult list(Page page, String id){
        AjaxResult ajaxResult = new AjaxResult();
        //参数验证
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription("项目编号不能为空");
            return ajaxResult;
        }

        List<SupportOutput> supportOutputList = null;
        try {
            supportOutputList = projectBizService.listSupport(page, id);
        } catch (Exception e) {
            ajaxResult.setDescription("系统异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(supportOutputList);
        return ajaxResult;
    }


    /**
     * 统计
     * @param input 输入视图
     * @param result 验证参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "statistics")
    public AjaxResult statistics(@Validated SupportStatisticsInput input, BindingResult result){
        AjaxResult ajaxResult = new AjaxResult();

        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
            ajaxResult.setDescription(description);
            return ajaxResult;
        }

        supportBizService.statistics(input);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }
}
