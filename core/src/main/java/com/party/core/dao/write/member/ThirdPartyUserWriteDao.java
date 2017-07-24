package com.party.core.dao.write.member;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.member.ThirdPartyUser;
import org.springframework.stereotype.Repository;

/**
 * 第三方用户信息数据读取
 * party
 * Created by wei.li
 * on 2016/8/11 0011.
 */
@Repository
public interface ThirdPartyUserWriteDao extends BaseWriteDao<ThirdPartyUser> {
}
