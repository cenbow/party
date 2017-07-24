package com.party.web.web.controller.user;

import java.util.List;

import com.party.notify.notifyPush.servce.INotifySendService;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
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
import org.springframework.web.servlet.ModelAndView;

import com.party.common.redis.StringJedis;
import com.party.common.utils.StringUtils;
import com.party.core.model.member.Member;
import com.party.core.model.resource.Resource;
import com.party.core.service.member.impl.MemberService;
import com.party.web.biz.index.IndexBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;
import com.party.web.web.dto.input.user.LoginInput;
import com.party.web.web.dto.input.user.ResetPwdInput;
import com.party.web.web.dto.output.index.IndexOutput;

/**
 * 登陆控制器
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */

@Controller
@RequestMapping(value = "")
public class LoginController {

    protected static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IndexBizService indexBizService;
    
    @Autowired
    private INotifySendService notifySendService;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
	StringJedis stringJedis;

    /**
     * 用户登陆
     * @param loginInput 登陆输入视图
     * @return 登陆返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/user/login/login")
    public AjaxResult login(@Validated LoginInput loginInput, BindingResult result){

        AjaxResult ajaxResult = new AjaxResult();
        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErros = result.getAllErrors();
            ajaxResult.setDescription(allErros.get(0).getDefaultMessage());
            return ajaxResult;
        }

        UsernamePasswordToken token
                = new UsernamePasswordToken(loginInput.getLoginName(), loginInput.getPassword());
        logger.info("为了验证登录用户而封装的token为{}",
                ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();

        try {
            currentUser.login(token);
        } catch (UnknownAccountException ue) {
            logger.info("账号不存在", ue);
            token.clear();
            currentUser.getSession().removeAttribute("currentUser");
            ajaxResult.setDescription("账号不存在");
            return ajaxResult;
        }catch (IncorrectCredentialsException ie){
            logger.info("密码错误", ie);
            token.clear();
            currentUser.getSession().removeAttribute("currentUser");
            ajaxResult.setDescription("密码错误");
            return ajaxResult;
        }

        ajaxResult.setDescription("/crowdfund/project/list.do");
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }


    /**
     * 退出登陆
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/login/logout")
    public AjaxResult logout(){
        SecurityUtils.getSubject().logout();
        return AjaxResult.success();
    }


    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = "/user/login/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("index");
        IndexOutput  indexOutput = indexBizService.getIndex();
        modelAndView.addObject("index", indexOutput);
        return modelAndView;
    }


    /**
     * 初始化页面
     * @return 初始化页面
     */
    @RequestMapping(value = "/user/login/defalt")
    public String defalt(){
        return "defalt";
    }


    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = "/user/login/loginView")
    public String loginView(){
        return "login";
    }
    
    /**
     * 发送手机验证码
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/user/login/getCode")
    public AjaxResult getCode(String mobile){

        if (StringUtils.isEmpty(mobile)) {
			return new AjaxResult(false);
		}

        notifySendService.sendVerifyCode(mobile);
        return AjaxResult.success();
    }
	
	/**
	 * 找回密码
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/user/login/resetPassword")
	public AjaxResult resetPassword(@Validated ResetPwdInput resetPwdInput, BindingResult result) {
		// 数据验证
		if (result.hasErrors()) {
			List<ObjectError> allErros = result.getAllErrors();
			return AjaxResult.error(allErros.get(0).getDefaultMessage());
		}

		// 验证验证码
		String verifyCode = stringJedis.getValue(resetPwdInput.getLoginName());
		if (null == verifyCode || !verifyCode.equals(resetPwdInput.getVerifyCode())) {
			return AjaxResult.error("手机验证码不一致");
		}
		Member member = memberService.findByLoginName(resetPwdInput.getLoginName());
		if (member == null) {
			return AjaxResult.error("用户不存在");
		}

		String encryptPassword = RealmUtils.encryptPassword(resetPwdInput.getPassword());
		member.setPassword(encryptPassword);
		memberService.update(member);
		return AjaxResult.success();
	}
	
	/**
     * 合作商登录
     * @return 合作商登录
     */
    @RequestMapping(value = "/pLogin")
    public ModelAndView partnerLogin(){	
    	ModelAndView mv = new ModelAndView("partner_login");
    	List<Resource> partners = indexBizService.getPartner();
    	IndexOutput indexOutput = new IndexOutput();
    	indexOutput.setResources(partners);
    	mv.addObject("index", indexOutput);
        return mv;
    }
}
