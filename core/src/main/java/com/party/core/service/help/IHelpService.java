package com.party.core.service.help;

import com.party.common.paging.Page;
import com.party.core.model.help.Help;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 帮助
 */
public interface IHelpService extends IBaseService<Help> {
    List<Help> webListPage(Help help, Map<String, Object> params, Page page);

    List<Help> webListPage2(Help help, Map<String, Object> params, Page page);

    Integer getMaxSort(Help help);
}
