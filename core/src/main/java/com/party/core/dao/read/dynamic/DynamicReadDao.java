package com.party.core.dao.read.dynamic;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.circle.TopicMap;
import com.party.core.model.dynamic.Dynamic;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 动态数据读取
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
@Repository
public interface DynamicReadDao extends BaseReadDao<Dynamic>{

    /**
     * 查询 评论列表也会查出
     * @param dynamic
     * @param params
     * @return
     */
    List<Dynamic> listDynamicMapPage(@Param("dynamic") Dynamic dynamic, @Param("params") Map<String, Object> params);
    /**
     * 查询 评论列表也会查出
     * @param dynamic
     * @param params
     * @return
     */
    List<TopicMap> listTopicMapPage( @Param("dynamic")Dynamic dynamic, @Param("params") Map<String, Object> params);
     /**
     * 根据会员ID查询动态
     * @param memberIds 会员ID
     * @param dynamic 会员信息
     * @param page 分页信息
     * @return 会员列表
     */
    List<Dynamic> batchListByMemberId(@Param("ids") Set<String> memberIds, @Param("param") Dynamic dynamic, Page page);

    /**
     * web端列表查询
     * @param dynamic
     * @param params
     * @param page
     * @return
     */
	List<Dynamic> webListPage(@Param("dynamic") Dynamic dynamic, @Param("params") Map<String, Object> params, Page page);

    /**
     *话题列表
     * @param circleTopic
     * @param page
     * @return
     */
    List<TopicMap> listCircleTopicPage(@Param("topic") CircleTopic circleTopic, @Param("dynamic") Dynamic dynamic, @Param("params") Map<String, Object> params, Page page);


}
