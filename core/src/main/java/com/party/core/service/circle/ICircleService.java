package com.party.core.service.circle;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.circle.Circle;
import com.party.core.service.IBaseService;

/**
 * 
 * @author Juliana
 *
 */
public interface ICircleService extends IBaseService<Circle> {

    /**
     * 查询所有圈子
     * @return 活动列表
     */
    List<Circle> listAll();

    /**
     * web分页
     * @param circle
     * @param page
     * @return
     */
    List<Map<String, Object>> webListPage(Circle circle, Map<String, Object> params, Page page);
}
