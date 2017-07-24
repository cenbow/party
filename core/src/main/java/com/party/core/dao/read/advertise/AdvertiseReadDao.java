package com.party.core.dao.read.advertise;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.advertise.Advertise;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 广告数据读取
 * party
 * Created by wei.li
 * on 2016/9/20 0020.
 */
@Repository
public interface AdvertiseReadDao extends BaseReadDao<Advertise>{
    List<Advertise> webListPage(Advertise advertise, Map<String, Object> params, Page page);
}
