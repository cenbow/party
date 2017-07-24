package com.party.core.dao.read.circle;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.party.core.model.circle.ListAllMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.CircleMember;

/**
 * CircleReadDao活动数据读取
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleMemberReadDao extends BaseReadDao<CircleMember> {

    List<ListAllMember> listAll(@Param("cMember") CircleMember cMember, @Param("params") Map<String,Object>params, @Param("tags") Set<String> tags);

    List<Map<String, Object>> webListPage(@Param("cMember") CircleMember circleMember, @Param("params") Map<String, Object> params, Page page);

    List<CircleMember> listPageSearch(@Param("cMember")CircleMember circleMember,@Param("params")  Map<String, Object> params, Page page);

    CircleMember getByMobile(@Param("cMember")CircleMember circleMember,@Param("params")  Map<String, Object> params);

    List<ListAllMember> listAllByType(@Param("cMember") CircleMember cMember, @Param("params") Map<String,Object>params, @Param("tags") Set<String> tags);
}
