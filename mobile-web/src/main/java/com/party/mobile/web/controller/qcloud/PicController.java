package com.party.mobile.web.controller.qcloud;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.party.authorization.annotation.Authorization;
import com.party.core.service.picCloud.PicCloudBizService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;

/**
 * 万象优图前端签名接口
 * Created by Wesley on 16/8/4.
 */
@Controller
@RequestMapping(value = "/member/piccloud")
public class PicController {
    @Autowired
    private PicCloudBizService picCloudService;
	@Autowired
	private CurrentUserBizService currentUserBizService;
    /**
     * 
     * @param type
     * @param fileid
     * @param expired
     * @param userId
     * @param request
     * @return
     */
    @Authorization
    @RequestMapping(value = "/getSign")
    @ResponseBody
    public AjaxResult getSign(String type, String fileid, Long expired, String userId, HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        
        Map<String, Object> mapRet = picCloudService.getPicCloudSign(type, fileid, userId, currentUser.getId());
        return AjaxResult.success(mapRet);
    }
}
