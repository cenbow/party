package com.party.admin.web.controller.area;

import com.google.common.collect.Maps;
import com.party.admin.web.dto.AjaxResult;
import com.party.core.service.city.IAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController {

    @Autowired
    private IAreaService areaService;

    protected static Logger logger = LoggerFactory.getLogger(AreaController.class);

    /**
     * 下拉搜索 根据名称查询
     *
     * @param value
     * @return
     */
    @ResponseBody
    @RequestMapping("th_findByName")
    public AjaxResult th_findByName(String value, String type) {
        try {
            Map<String, Object> params = Maps.newHashMap();
            params.put("value", value);
            params.put("type", type);
            List<Map<String, Object>> list = areaService.thFindByName(params);
            return new AjaxResult().success(list);
        } catch (Exception e) {
            logger.info("根据用户手机或用户名查询失败", e);
            return new AjaxResult(false);
        }

    }

}
