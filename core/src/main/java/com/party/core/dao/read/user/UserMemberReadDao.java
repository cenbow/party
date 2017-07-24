package com.party.core.dao.read.user;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.user.UserMember;
import org.springframework.stereotype.Repository;

/**
 * 后台用户-会员关联数据读取
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
@Repository
public interface UserMemberReadDao extends BaseReadDao<UserMember> {
}
