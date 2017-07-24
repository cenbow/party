package com.party.mobile.web.controller.activity;

import com.party.authorization.annotation.Authorization;
import com.party.mobile.biz.activity.ManageBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.activity.output.ManageDetailOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 活动管理控制器
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 17:48
 */

@Controller
@RequestMapping(value = "activity/manage")
public class ManageController {

    @Autowired
    private ManageBizService manageBizService;


    /**
     * 活动管理详情
     * @param id 活动编号
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "getDetail")
    public AjaxResult getDetail(String id, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        String serverName = request.getServerName();
        Integer serverPort = request.getServerPort();
        StringBuilder contentPath = new StringBuilder("http://")
                .append(serverName).append(":").append(serverPort);
        ManageDetailOutput manageDetailOutput = manageBizService.getDetail(id, contentPath.toString());
        ajaxResult.setSuccess(true);
        ajaxResult.setData(manageDetailOutput);
        return ajaxResult;
    }
}
