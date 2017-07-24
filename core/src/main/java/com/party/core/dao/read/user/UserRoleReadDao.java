package com.party.core.dao.read.user;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.user.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色关联数据读取
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */
@Repository
public interface UserRoleReadDao extends BaseReadDao<UserRole> {
}
