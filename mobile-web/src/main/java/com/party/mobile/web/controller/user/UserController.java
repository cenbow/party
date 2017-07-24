package com.party.mobile.web.controller.user;

import com.google.common.base.Strings;
import com.party.authorization.manager.impl.RedisTokenManager;
import com.party.authorization.utils.Constant;
import com.party.core.model.partner.UserSchedule;
import com.party.core.model.user.User;
import com.party.core.service.partner.IUserScheduleService;
import com.party.core.service.user.IUserService;
import com.party.mobile.biz.user.UserBizService;
import com.party.mobile.utils.RealmUtils;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.user.input.LoginInput;
import com.party.mobile.web.dto.user.output.LoginOutput;
import com.party.mobile.web.dto.user.output.LoginPreOutput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户控制层
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */

@Controller
@RequestMapping(value = "/user/login")
public class UserController {

    @Autowired
    IUserScheduleService userScheduleService;

    @Autowired
    IUserService userService;

    @Autowired
    UserBizService userBizService;

    @Autowired
    RedisTokenManager redisTokenManager;


    /**
     * 预登陆信息
     * @param userId 用户主键
     * @return 预登陆输出视图
     */
    @ResponseBody
    @RequestMapping(value = "/preInfo/{userId}")
    public AjaxResult preInfo(@PathVariable(value = "userId") String userId){
        //数据验证
        if (Strings.isNullOrEmpty(userId)){
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数验证不通过");
        }
        UserSchedule userSchedule = userScheduleService.findByUserId(userId);
        if (null == userSchedule){
            return AjaxResult.success();
        }
        return AjaxResult.success(LoginPreOutput.transform(userSchedule));
    }


    /**
     * 用戶登陸
     * @param input 輸入視圖
     * @param result 验证参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public AjaxResult login(@Validated LoginInput input, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        //查找用户
        User user = userService.findByLoginName(input.getUsername());
        if (null == user){
            return AjaxResult.error(PartyCode.USER_UNEXIST, "用户不存在");
        }

        //凭证验证
        String encryptPassword = RealmUtils.encryptPassword(input.getPassword());
        if (user.getPassword().equals(encryptPassword)){
            return AjaxResult.error(PartyCode.USER_PASSWORD_UNMATCH, "用户密码不匹配");
        }

        LoginOutput loginOutput = userBizService.login(user);
        return AjaxResult.success(loginOutput);
    }

    /**
     * 用户登出
     * @param request 请求参数
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/logout")
    public AjaxResult logout(HttpServletRequest request){
        //删除token
        //String key = (String) request.getAttribute(Constant.REQUEST_CURRENT_KEY);
        String token = request.getHeader(Constant.HTTP_HEADER_NAME);
        redisTokenManager.delRelationshipByToken(token);
        return AjaxResult.success();
    }
}
