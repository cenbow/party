package com.party.core.dao.write.user;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.user.UserMember;
import org.springframework.stereotype.Repository;

/**
 * 后台用户-会员关联数据写入
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */

@Repository
public interface UserMemberWriteDao extends BaseWriteDao<UserMember> {
}
