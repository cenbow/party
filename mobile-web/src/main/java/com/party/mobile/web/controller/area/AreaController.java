package com.party.mobile.web.controller.area;

import com.party.core.exception.BusinessException;
import com.party.core.model.city.Area;
import com.party.core.service.city.IAreaService;
import com.party.mobile.web.dto.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 圈子
 *
 * @author Juliana 2016-12-14
 */

@Controller
@RequestMapping(value = "/area")
public class AreaController {
    @Autowired
    IAreaService areaService;
    protected static Logger logger = LoggerFactory.getLogger(AreaController.class);
    /**
     * 根据区域名称获取id
     */
    @ResponseBody
    @RequestMapping(value = "getAreaIdByName")
    public AjaxResult getAreaIdByName(Area area) {
        try {
            AjaxResult ajaxResult = new AjaxResult();
            String id = "";
            List<Area> list = areaService.list(area);
            if(null != list && list.size() > 0){
                id=list.get(0).getId();
            }
            ajaxResult.setData(id);
            ajaxResult.setSuccess(true);
            return ajaxResult;
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }

    }

}
