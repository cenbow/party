package com.party.core.service.advertise;

import com.party.common.paging.Page;
import com.party.core.model.advertise.Advertise;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 广告服务接口
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */
public interface IAdvertiseService extends IBaseService<Advertise> {
    /**
     * web端查询
     *
     * @param advertise
     * @param params
     * @param page
     * @return
     */
    List<Advertise> webListPage(Advertise advertise, Map<String, Object> params, Page page);
}
