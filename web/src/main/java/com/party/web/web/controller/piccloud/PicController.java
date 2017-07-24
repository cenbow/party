package com.party.web.web.controller.piccloud;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.party.core.service.picCloud.PicCloudBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.AjaxResult;

/**
 * 万象优图前端签名接口
 * Created by Wesley on 16/8/4.
 */
@Controller
@RequestMapping(value = "/piccloud")
public class PicController {
    @Autowired
    private PicCloudBizService picCloudService;
    /**
     * 
     * @param type
     * @param fileid
     * @param expired
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSign")
    @ResponseBody
    public AjaxResult getSign(String type, String fileid, Long expired, String userId, HttpServletRequest request) {        
        Map<String, Object> mapRet = picCloudService.getPicCloudSign(type, fileid, userId, RealmUtils.getCurrentUser().getId());
        return AjaxResult.success(mapRet);
    }
}
