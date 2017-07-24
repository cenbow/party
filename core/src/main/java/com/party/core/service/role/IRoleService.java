package com.party.core.service.role;

import com.party.core.model.role.Role;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 角色服务接口
 * party
 * Created by wei.li
 * on 2016/8/27 0027.
 */
public interface IRoleService extends IBaseService<Role>{

    /**
     * 根据用户id查找角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> findByUserId(String userId);
}
