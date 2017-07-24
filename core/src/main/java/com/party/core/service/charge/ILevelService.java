package com.party.core.service.charge;

import com.party.common.paging.Page;
import com.party.core.model.charge.Level;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 等级接口
 */
public interface ILevelService extends IBaseService<Level> {

    List<Level> webListPage(Level level, Map<String, Object> params, Page page);
}
