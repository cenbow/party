package com.party.core.dao.write.user;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.user.User;
import org.springframework.stereotype.Repository;

/**
 * 后台用户数据写入
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
@Repository
public interface UserWriteDao extends BaseWriteDao<User> {
}
