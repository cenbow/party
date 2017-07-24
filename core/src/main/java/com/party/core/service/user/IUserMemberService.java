package com.party.core.service.user;

import com.party.core.model.user.UserMember;
import com.party.core.service.IBaseService;

/**
 * 系统用户-会员接口
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public interface IUserMemberService extends IBaseService<UserMember>{

    /**
     * 根据会员主键查找 系统用户-会员关系
     * @param memberId 会员ID
     * @return 系统会员关系
     */
    UserMember findByMemberId(String memberId);
}
