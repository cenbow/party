package com.party.core.dao.write.charge;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.charge.Level;
import com.party.core.model.charge.LevelMember;
import org.springframework.stereotype.Repository;

/**
 * 等级和用户的中间表
 */
@Repository
public interface LevelMemberWriteDao extends BaseWriteDao<LevelMember> {

}
