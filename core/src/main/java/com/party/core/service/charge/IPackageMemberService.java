package com.party.core.service.charge;

import com.party.core.model.charge.PackageMember;
import com.party.core.service.IBaseService;

/**
 * 等级与会员中间表
 */
public interface IPackageMemberService extends IBaseService<PackageMember> {

    PackageMember findByMemberId(PackageMember packageMember);
}
