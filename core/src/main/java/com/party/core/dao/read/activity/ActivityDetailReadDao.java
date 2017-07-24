package com.party.core.dao.read.activity;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.activity.ActivityDetail;
import org.springframework.stereotype.Repository;

/**
 * ActivityDetailReadDao
 *
 * @author Wesley
 * @data 16/9/6 14:45 .
 */
@Repository
public interface ActivityDetailReadDao extends BaseReadDao<ActivityDetail> {
    public ActivityDetail getByRefId(String refId);
}
