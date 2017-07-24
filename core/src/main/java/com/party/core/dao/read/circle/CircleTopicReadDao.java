package com.party.core.dao.read.circle;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.CircleTag;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.dynamic.Dynamic;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CircleTopicReadDao
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleTopicReadDao extends BaseReadDao<CircleTopic> {
}
