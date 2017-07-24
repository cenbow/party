package com.party.core.dao.write.system;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.system.MemberSysRole;

/**
 * 用户角色数据写入
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */

@Repository
public interface MemberSysRoleWriteDao extends BaseWriteDao<MemberSysRole> {

    /**
     * 根据用户ID删除 用户角色关系
     * @param userId 用户主键
     * @return
     */
    boolean deleteByMemberId(String memberId);

    /**
     * 根据角色ID删除 用户角色关系
     * @param roleId 角色主键
     * @return 删除结果（true/false）
     */
    boolean deleteByRoleId(String roleId);

    /**
	 * 根据用户ID和角色ID删除用户角色关系
	 * 
	 * @param roleId
	 *            角色编号
	 * @param memberId
	 *            角色编号
	 * @return 删除结果（true/false）
	 */
	boolean deleteByRoleIdAndMemberId(@Param(value = "roleId") String roleId, @Param(value = "memberId") String memberId);
}
