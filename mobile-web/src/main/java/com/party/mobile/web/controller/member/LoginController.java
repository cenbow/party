package com.party.mobile.web.controller.member;

import com.party.authorization.annotation.Authorization;
import com.party.authorization.manager.impl.RedisTokenManager;
import com.party.authorization.utils.Constant;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.common.redis.StringJedis;
import com.party.core.exception.BusinessException;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.member.Member;
import com.party.core.model.member.ThirdPartyUser;
import com.party.core.model.version.VersionManager;
import com.party.core.service.member.IMemberService;
import com.party.core.service.member.IThirdPartyUserService;
import com.party.core.service.version.IVersionManagerService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.utils.RealmUtils;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.input.BindInput;
import com.party.mobile.web.dto.login.input.LoginInfoInput;
import com.party.mobile.web.dto.login.input.LoginInput;
import com.party.mobile.web.dto.login.input.ThirdPartyUserInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.LoginInfoOutput;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.common.utils.PartyCode;
import com.party.mobile.web.utils.ValidatedUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员登陆控制层
 * party
 * Created by wei.li
 * on 2016/9/22 0022.
 */

@Controller
@RequestMapping(value = "member/login")
public class LoginController {

    protected static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    IMemberService memberService;

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    RedisTokenManager redisTokenManager;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    IVersionManagerService versionManagerService;

    @Autowired
    IThirdPartyUserService thirdPartyUserService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    /**
     * 获取第三方登录开关信息
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getLoginInfo")
    public AjaxResult getLoginInfo(LoginInfoInput infoInput, BindingResult result)
    {
        VersionManager versionManager = new VersionManager();
        versionManager = LoginInfoInput.transform(versionManager, infoInput);
        versionManager.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        List<VersionManager> list = versionManagerService.list(versionManager);
        LoginInfoOutput info = new LoginInfoOutput();
        if (list.size() > 0){
            versionManager = list.get(0);
            info = LoginInfoOutput.transform(versionManager);
        }else{
            //如果没有，默认给开放第三方登录
            info.setLoginQQ(YesNoStatus.YES.getCode());
            info.setLoginWX(YesNoStatus.YES.getCode());
        }
        return AjaxResult.success(info);
    }

    /**
     * 获取版本信息
     */
    @ResponseBody
    @RequestMapping(value = "/getNewVersion")
    public AjaxResult getVersion(String type)
    {
        VersionManager versionManager = new VersionManager();
        List<VersionManager> list = versionManagerService.list(versionManager);
        VersionManager version = null;
        if(list != null && list.size()>0){
        	version = list.get(0);
        }
        return AjaxResult.success(version);
    }

    /**
     * 会员登录
     * @param loginInput 登陆输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public AjaxResult login(@Validated LoginInput loginInput, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        //查找用户
        Member member = memberService.findByLoginName(loginInput.getUsername());
        if (null == member){
            return AjaxResult.error(PartyCode.MEMBER_UNEXIST, "用户不存在");
        }

        boolean validate = RealmUtils.validatePassword(loginInput.getPassword(), member.getPassword());
        //验证凭证
        if (!validate){
            return AjaxResult.error(PartyCode.PASSWORD_UNMATCH, "用户名密码不匹配");
        }

        AjaxResult ajaxResult = AjaxResult.success();
        MemberOutput memberOutput = memberBizService.getLoginMember(member);
        ajaxResult.setData(memberOutput);
        return ajaxResult;
    }


    /**
     * 获取登陆用户信息
     * @param request 请求参数
     * @return 加护数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "getLoginMember")
    public AjaxResult getLoginMember(HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        MemberOutput memberOutput;
        try {
             memberOutput = memberBizService.getLoginMember(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setDescription("服务器异常");
            return ajaxResult;
        }
        ajaxResult.setSuccess(true);
        ajaxResult.setData(memberOutput);
        return ajaxResult;
    }


    /**
     * 登陆接口2.0
     * @param loginInput 输入参数
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "login2")
    public AjaxResult login2(@Validated LoginInput loginInput, BindingResult result){

        AjaxResult ajaxResult = new AjaxResult();
        //数据验证
        if (result.hasErrors()){
            return ValidatedUtils.validated(ajaxResult, result);
        }

        //登陆验证
        Member member = memberService.findByLoginName(loginInput.getUsername());

        if (null == member){
            ajaxResult.setErrorCode(PartyCode.MEMBER_UNEXIST);
            ajaxResult.setDescription("用户不存在");
            return ajaxResult;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(member.getId(), loginInput.getPassword());
        token.setRememberMe(true);

        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();

        try {
            currentUser.login(token);
        } catch (IncorrectCredentialsException ex) {
            logger.debug("用户登陆验证不通过", ex);
            ajaxResult.setErrorCode(PartyCode.PASSWORD_UNMATCH);
            ajaxResult.setDescription("用户名密码不匹配");
            return ajaxResult;
        }

        MemberOutput memberOutput = memberBizService.getLoginMember(member);
        ajaxResult.setSuccess(true);
        ajaxResult.setData(memberOutput);
        return ajaxResult;
    }


    /**
     * 登出接口2.0
     * @return 登出
     */
    @ResponseBody
    @RequestMapping(value = "logout2")
    public AjaxResult logout2(){
        AjaxResult ajaxResult = new AjaxResult();
        SecurityUtils.getSubject().logout();
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 登出
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/logout")
    public AjaxResult logout(HttpServletRequest request){
        //删除token
        //String key = stringJedis.getValue(Constant.REQUEST_CURRENT_KEY);
        String token = request.getHeader(Constant.HTTP_HEADER_NAME);
        redisTokenManager.delRelationshipByToken(token);
        return AjaxResult.success();
    }

    /**
     * 使用微信登录
     * @param thirdPartyUserInput 第三方登陆输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/tp_login")
    public AjaxResult tp_login(@Validated ThirdPartyUserInput thirdPartyUserInput, BindingResult result, HttpServletRequest request){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        AjaxResult ajaxResult = AjaxResult.success();
        MemberOutput memberOutput = memberBizService.thirdPartyUserLogin(thirdPartyUserInput, request);
        ajaxResult.setData(memberOutput);
        return ajaxResult;
    }


    /**
     * 第三方账户登录2.0
     * @param thirdPartyUserInput 输入参数
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "tp_login2")
    public AjaxResult tp_login2(@Validated ThirdPartyUserInput thirdPartyUserInput, BindingResult result, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        //数据验证
        if (result.hasErrors()){
            return ValidatedUtils.validated(ajaxResult, result);
        }
        MemberOutput memberOutput = memberBizService.thirdPartyUserLogin(thirdPartyUserInput, request);

        //查找用户
        ThirdPartyUser thirdPartyUser = thirdPartyUserService.getByOpenId(thirdPartyUserInput.getOpenId());
        UsernamePasswordToken token = new UsernamePasswordToken(thirdPartyUser.getMemberId(), RealmUtils.DEFALT_PASSWORD);
        token.setRememberMe(true);

        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();

        try {
            currentUser.login(token);
        } catch (IncorrectCredentialsException ex) {
            logger.debug("第三方用户登陆验证不通过", ex);
            ajaxResult.setErrorCode(PartyCode.PASSWORD_UNMATCH);
            ajaxResult.setDescription("用户名密码不匹配");
            return ajaxResult;
        }
        ajaxResult.setData(memberOutput);
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 手机用户绑定第三方授权
     * @param thirdPartyUserInput 第三方登陆输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/bindThird")
    public AjaxResult bindThird(@Validated ThirdPartyUserInput thirdPartyUserInput, HttpServletRequest request, BindingResult result){
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        AjaxResult ajaxResult = AjaxResult.success();
        MemberOutput memberOutput = memberBizService.bindThird(thirdPartyUserInput,request);
        ajaxResult.setData(memberOutput);
        return ajaxResult;
    }

    /**
     * 第三方授权账号绑定手机号
     * @param bindInput 手机号及验证码视图
     * @param result 验证结果
     * @return 交互数据
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/bindPhone")
    public AjaxResult bindPhone(@Validated BindInput bindInput,  HttpServletRequest request, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        //验证验证码
        String verifyCode = stringJedis.getValue(bindInput.getPhone());
        if (null == verifyCode || !bindInput.getVerifyCode().equals(verifyCode)){
            return AjaxResult.error(PartyCode.VERIFY_CODE_ERROR, "手机验证码不一致");
        }

        MemberOutput memberOutput;
        try {
            memberOutput = memberBizService.bindPhone(bindInput.getPhone(), null, null, null, null, request);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.setData(memberOutput);
        return ajaxResult;
    }
}
