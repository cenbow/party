package com.party.core.dao.read.wechat;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.wechat.WechatAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**微信账户数据读取
 * party
 * Created by wei.li
 * on 2016/9/12 0012.
 */
@Repository
public interface WechatAccountReadDao extends BaseReadDao<WechatAccount>{

    /**
     * 获取系统配置
     * @return 系统配置
     */
    WechatAccount getSystem();

    /**
     * 根据会员编号查询
     * @param memberId 会员编号
     * @return 微信账户
     */
    WechatAccount findByMemberId(@Param(value = "memberId") String memberId);


}
