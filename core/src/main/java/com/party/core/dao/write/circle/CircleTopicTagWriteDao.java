package com.party.core.dao.write.circle;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.circle.CircleTopic;
import com.party.core.model.circle.CircleTopicTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * CircleTopicTagWriteDao
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleTopicTagWriteDao extends BaseWriteDao<CircleTopicTag> {
    void delByCircle(@Param(value = "id") String id);
}
