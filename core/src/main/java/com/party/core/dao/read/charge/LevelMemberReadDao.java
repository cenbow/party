package com.party.core.dao.read.charge;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.charge.LevelMember;
import org.springframework.stereotype.Repository;

/**
 * 等级和用户的中间表
 */
@Repository
public interface LevelMemberReadDao extends BaseReadDao<LevelMember> {

}
