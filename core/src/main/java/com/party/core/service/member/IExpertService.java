package com.party.core.service.member;

import com.party.core.model.member.Expert;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 达人接口
 * party
 * Created by wei.li
 * on 2016/8/18 0018.
 */
public interface IExpertService extends IBaseService<Expert>{

    /**
     * 查询所有达人信息
     * @return 达人列表
     */
    List<Expert> listAll();


    /**
     * 根据会员id查询达人
     * @param memberId 会员ID
     * @return 达人信息
     */
    Expert findByMemberId(String memberId);
}
