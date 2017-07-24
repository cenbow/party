package com.party.mobile.web.controller.verifyCode;

import com.party.authorization.annotation.Authorization;
import com.party.authorization.utils.Constant;
import com.party.common.redis.StringJedis;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.utils.RealmUtils;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.verifyCode.input.PhoneInput;
import com.party.mobile.web.dto.verifyCode.input.VerifyCodeInput;
import com.party.common.utils.PartyCode;
import com.party.mobile.web.security.VerificationCodeController;
import com.party.notify.notifyPush.servce.INotifySendService;
import com.party.notify.sms.service.ISmsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 验证码控制层
 * party
 * Created by wei.li
 * on 2016/9/22 0022.
 */
@Controller
@RequestMapping(value = "verifyCode/verifyCode")
public class VerifyCodeController {

    @Autowired
    INotifySendService notifySendService;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    CurrentUserBizService currentUserBizService;

    protected static Logger logger = LoggerFactory.getLogger(VerifyCodeController.class);

    /**
     * 发送手机验证码
     * @param phoneInput 验证码输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    public AjaxResult getCode(@Validated PhoneInput phoneInput, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErros = result.getAllErrors();
            String description = allErros.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        String code = (String)session.getAttribute("verificationCode");

        logger.debug("获取图片验证码,session{}, code{}", session.getId(), code);
        if (null == code || !code.equalsIgnoreCase(phoneInput.getImgCode())){
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setDescription("图片验证码不正确");
            return ajaxResult;
        }
        notifySendService.sendVerifyCode(phoneInput.getPhone());
        //验证后清掉 防止重复验证
        session.removeAttribute("verificationCode");
        return AjaxResult.success();
    }


    @ResponseBody
    @Authorization
    @RequestMapping(value = "/miniProgram/getCode", method = RequestMethod.POST)
    public AjaxResult getMiniProgramCode(@Validated PhoneInput phoneInput,
                                         BindingResult result, HttpServletRequest request){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErros = result.getAllErrors();
            String description = allErros.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        String code = stringJedis.getValue(currentUser.getOpenId());
        if (null == code || !code.equalsIgnoreCase(phoneInput.getImgCode())){
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setDescription("图片验证码不正确");
            return ajaxResult;
        }
        notifySendService.sendVerifyCode(phoneInput.getPhone());
        //验证后清掉 防止重复验证
        stringJedis.delete(currentUser.getOpenId());
        return AjaxResult.success();
    }

    /**
     * 获取手机验证码
     * @param phoneInput 输入参数
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/mobile/getCode", method = RequestMethod.POST)
    public AjaxResult getMobileCode(@Validated PhoneInput phoneInput, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErros = result.getAllErrors();
            String description = allErros.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        String code = stringJedis.getValue(phoneInput.getPhone() + "VERIFY_CODE");
        logger.debug("验证图片验证码, code{},input{}", code, phoneInput.getImgCode());
        if (null == code || !code.equalsIgnoreCase(phoneInput.getImgCode())){
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setDescription("图片验证码不正确");
            return ajaxResult;
        }
        notifySendService.sendVerifyCode(phoneInput.getPhone());
        //验证后清掉 防止重复验证
        stringJedis.delete(phoneInput.getPhone() + "VERIFY_CODE");
        return AjaxResult.success();
    }

    /**
     * 验证手机号验证码
     * @param verifyCodeInput 验证码输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/verify")
    public AjaxResult verify(@Validated VerifyCodeInput verifyCodeInput, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        //验证验证码是否一致
        String code = stringJedis.getValue(verifyCodeInput.getPhone());
        if (!code.equals(verifyCodeInput.getVerifyCode())){
            return AjaxResult.error(PartyCode.VERIFY_CODE_ERROR, "手机号验证码不一致");
        }

        return AjaxResult.success();
    }
}
