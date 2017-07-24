package com.party.core.dao.read.circle;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.circle.CircleInvite;
import org.springframework.stereotype.Repository;

/**
 * 圈子邀请
 *
 * @author Juliana
 * @data 2016-12-14
 */
@Repository
public interface CircleInviteReadDao extends BaseReadDao<CircleInvite> {

    CircleInvite getTheFirstEmptyInvite(CircleInvite search);
}
