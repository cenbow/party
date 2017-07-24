package com.party.admin.web.controller.user;
import com.party.admin.biz.index.IndexBizService;
import com.party.admin.web.dto.AjaxResult;
import com.party.admin.web.dto.input.user.LoginInput;
import com.party.admin.web.dto.output.index.IndexOutput;
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

import java.util.List;

/**
 * 登陆控制器
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */

@Controller
@RequestMapping(value = "/user/login")
public class LoginController {

    protected static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IndexBizService indexBizService;

    /**
     * 用户登陆
     * @param loginInput 登陆输入视图
     * @return 登陆返回结果
     */
    @ResponseBody
    @RequestMapping(value = "login")
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
    @RequestMapping(value = "logout")
    public AjaxResult logout(){
        SecurityUtils.getSubject().logout();
        return AjaxResult.success();
    }


    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = "home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("index");
        IndexOutput indexOutput = indexBizService.getIndex();
        modelAndView.addObject("index", indexOutput);
        return modelAndView;
    }


    /**
     * 初始化页面
     * @return 初始化页面
     */
    @RequestMapping(value = "/defalt")
    public String defalt(){
        return "defalt";
    }


    /**
     * 登陆页面
     * @return
     */
    @RequestMapping(value = "loginView")
    public String loginView(){
        return "login";
    }
}
