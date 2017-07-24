package com.party.core.service.dynamic;

import com.party.common.paging.Page;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.circle.TopicMap;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.service.IBaseService;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 动态接口
 * party
 * Created by wei.li
 * on 2016/8/19 0019.
 */
public interface IDynamicService extends IBaseService<Dynamic> {

    /**
     * 根据会员ID查询动态
     * @param memberIds 会员ID
     * @param dynamic 会员信息
     * @param page 分页信息
     * @return 会员列表
     */
    List<Dynamic> batchListByMemberId(@NotNull Set<String> memberIds, Dynamic dynamic, Page page);

    /**
     * app 查询动态
     * @param dynamic
     * @param params
     * @return
     */
    List<Dynamic> listDynamicMapPage(Dynamic dynamic, Map<String, Object> params);

    /**
     * app 查询话题
     * @param dynamic
     * @param params
     * @return
     */
    List<TopicMap> listTopicMapPage(Dynamic dynamic, Map<String, Object> params);
    /**
     * web端查询
     * @param dynamic
     * @param params
     * @param page
     * @return
     */
	List<Dynamic> webListPage(Dynamic dynamic, Map<String, Object> params, Page page);

    /**
     * web根据圈子话题查询动态
     * @param circleTopic
     * @param page
     * @return
     */
    List<TopicMap> listCircleTopicPage(CircleTopic circleTopic, Dynamic dynamic, Map<String, Object> params, Page page);


}
