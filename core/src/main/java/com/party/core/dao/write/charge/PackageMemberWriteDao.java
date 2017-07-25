package com.party.core.dao.write.charge;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.charge.PackageMember;
import org.springframework.stereotype.Repository;

/**
 * 等级和用户的中间表
 */
@Repository
public interface PackageMemberWriteDao extends BaseWriteDao<PackageMember> {

}
