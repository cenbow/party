package com.party.core.dao.read.circle;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.CircleMemberTag;

/**
 * CircleMemberTagReadDao
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleMemberTagReadDao extends BaseReadDao<CircleMemberTag> {

	List<String> getByTagsId(String tags);
}
