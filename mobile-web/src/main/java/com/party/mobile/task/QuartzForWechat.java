package com.party.mobile.task;

import com.party.common.constant.Constant;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.wechat.IWechatAccountService;
import com.party.core.service.wechat.impl.WechatAccountService;
import com.party.mobile.biz.wechat.WechatBizService;
import com.party.mobile.biz.wechat.WechatTokenBizService;
import com.party.mobile.web.dto.wechat.input.GetTicketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 刷新微信方面数据
 * Created by wei.li
 *
 * @date 2016/12/16 0016
 * @time 15:54
 */

@Component(value = "quartzForWechat")
public class QuartzForWechat {

    @Autowired
    WechatTokenBizService wechatTokenBizService;

    @Autowired
    WechatBizService wechatBizService;

    @Autowired
    IWechatAccountService wechatAccountService;

    @Autowired
    IMemberMerchantService memberMerchantService;

    private static final Logger logger = LoggerFactory.getLogger(QuartzForWechat.class);

    @Scheduled( fixedRate = 1000*60*90)
    public void refresh(){
        List<WechatAccount> wechatAccountList = wechatAccountService.list(new WechatAccount());
        for (WechatAccount wechatAccount : wechatAccountList){
            try {
                String token = null;
                if (Constant.WECHAT_ACCOUNT_TYPE_SYSTEM.equals(wechatAccount.getType())){
                     token = wechatTokenBizService.getToken();
                }
                else {
                     MemberMerchant memberMerchant = memberMerchantService.findByMemberId(wechatAccount.getMemberId());
                    if (null != memberMerchant){
                        token = wechatTokenBizService.getToken(memberMerchant.getOfficialAccountId(), memberMerchant.getOfficialAccountSecret());
                    }

                }
                //刷新微信 ticket
                GetTicketResponse getTicketResponse = wechatBizService.getTicket(token);
                wechatAccount.setToken(token);
                wechatAccount.setTicket(getTicketResponse.getTicket());
                wechatAccount.setUpdateDate(new Date());
                wechatAccountService.update(wechatAccount);
            } catch (Exception e) {
                logger.error("更新商户信息异常", e);
                continue;
            }
        }
    }
}
