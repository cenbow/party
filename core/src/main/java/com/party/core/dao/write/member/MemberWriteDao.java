package com.party.core.dao.write.member;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.member.Member;
import org.springframework.stereotype.Repository;

/**
 * 会员数据读取
 * party
 * Created by wei.li
 * on 2016/8/11 0011.
 */
@Repository
public interface MemberWriteDao extends BaseWriteDao<Member> {
}
