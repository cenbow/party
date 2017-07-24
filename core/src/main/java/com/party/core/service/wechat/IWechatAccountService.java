package com.party.core.service.wechat;

import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.IBaseService;

/**
 * 微信账户服务接口
 * party
 * Created by wei.li
 * on 2016/9/13 0013.
 */
public interface IWechatAccountService extends IBaseService<WechatAccount> {

    /**
     * 获取微信账户信息
     * @return 微信账户信息
     */
    WechatAccount getSystem();

    /**
     * 获取合作商配置
     * @param memberId 会员编号
     * @return 微信账户
     */
    WechatAccount getPartner(String memberId);

    /**
     * 根据会员编号查询
     * @param memberId 会员编号
     * @return 微信账户
     */
    WechatAccount findByMemberId(String memberId);



}
