package com.party.core.service.circle;

import com.party.common.paging.Page;
import com.party.core.model.circle.CircleApply;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Juliana
 *
 */
public interface ICircleApplyService extends IBaseService<CircleApply> {
    public Long count(CircleApply circleApply);

    CircleApply getUnique(CircleApply applySearch);

    List<Map<String,Object>> webListPage(CircleApply apply,Map<String,Object> params, Page page);
    public void delBySearch(CircleApply circleApply);
}
