package com.party.mobile.web.controller.sign;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.sign.SignRecord;
import com.party.core.service.sign.ISignRecordService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.sign.SignRecordBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.sign.output.SignRecodListOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 签到记录控制器
 * Created by wei.li
 *
 * @date 2017/6/12 0012
 * @time 10:18
 */

@Controller
@RequestMapping(value = "sign/record")
public class SignRecordController {

    @Autowired
    private SignRecordBizService signRecordBizService;

    @Autowired
    private CurrentUserBizService currentUserBizService;

    @Autowired
    private ISignRecordService signRecordService;

    /**
     * 签到记录集合
     * @param applyId 报名信息
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public AjaxResult list(String applyId){
        AjaxResult ajaxResult = new AjaxResult();
        List<SignRecodListOutput> listOutputList = Lists.newArrayList();
        try {
            listOutputList = signRecordBizService.list(applyId);
        } catch (BusinessException be) {
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ajaxResult.setData(listOutputList);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 签到记录详情
     * @param id 签到记录编号
     * @return 详情
     */
    @ResponseBody
    @RequestMapping(value = "detail")
    public AjaxResult detail(String id){
        AjaxResult ajaxResult = new AjaxResult();
        if (Strings.isNullOrEmpty(id)){
            ajaxResult.setSuccess(false);
            return ajaxResult;
        }
        SignRecord signRecord = signRecordService.get(id);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(signRecord);
        return ajaxResult;
    }


    /**
     * 签到
     * @param signRecord 签到记录
     * @param result 验证结果
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "sign")
    public AjaxResult sign(@Validated SignRecord signRecord, BindingResult result, HttpServletRequest request){

        //数据验证
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        if (null != currentUser){
            signRecord.setCreateBy(currentUser.getId());
            signRecord.setUpdateBy(currentUser.getId());
        }

        try {
            signRecordBizService.sign(signRecord);
        } catch (BusinessException be) {
            ajaxResult.setSuccess(false);
            ajaxResult.setDescription(be.getMessage());
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

}
