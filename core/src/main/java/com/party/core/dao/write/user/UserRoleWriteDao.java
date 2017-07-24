package com.party.core.dao.write.user;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.user.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色数据写入
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */

@Repository
public interface UserRoleWriteDao extends BaseWriteDao<UserRole> {

    /**
     * 根据用户ID删除 用户角色关系
     * @param userId 用户主键
     * @return
     */
    boolean deleteByUserId(String userId);

    /**
     * 根据角色ID删除 用户角色关系
     * @param roleId 角色主键
     * @return 删除结果（true/false）
     */
    boolean deleteByRoleId(String roleId);
}
