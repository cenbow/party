package com.party.admin.biz.system;

import com.google.common.base.Strings;
import com.party.admin.biz.wechat.WechatBizService;
import com.party.admin.biz.wechat.WechatTokenBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.input.wechat.GetTicketResponse;
import com.party.common.constant.Constant;
import com.party.core.exception.BusinessException;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.wechat.IWechatAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *用户商户信息实体接口服务
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 11:42
 */

@Service
public class MemberMerchantBizService {

    @Autowired
    private IMemberMerchantService memberMerchantService;

    @Autowired
    private IWechatAccountService wechatAccountService;

    @Autowired
    private WechatBizService wechatBizService;

    @Autowired
    private WechatTokenBizService wechatTokenBizService;

    @Autowired
    private IMemberService memberService;

    @Transactional
    public void save(MemberMerchant memberMerchant) throws Exception{
        //验证商户唯一性
/*        MemberMerchant hasMerchant = memberMerchantService.findByAccountId(memberMerchant.getOfficialAccountId());
        if (null != hasMerchant){
            Member member = memberService.get(hasMerchant.getMemberId());
            throw new BusinessException("公众已经绑定合作商"+member.getRealname());
        }*/
        if (StringUtils.isNotEmpty(memberMerchant.getId())) {
            MemberMerchant t = memberMerchantService.get(memberMerchant.getId());
            MyBeanUtils.copyBeanNotNull2Bean(memberMerchant, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值
            memberMerchantService.update(t);

            // 初始化商户信息
            WechatAccount wechatAccount = wechatAccountService.findByMemberId(memberMerchant.getMemberId());
            if (null == wechatAccount){
                this.inserWechatAccount(memberMerchant);
            }
            else {
                this.updateWechatAccount(wechatAccount, memberMerchant);
            }
        } else {
            String memberId = RealmUtils.getCurrentUser().getId();
            memberMerchant.setCreateBy(memberId);
            memberMerchant.setUpdateBy(memberId);
            memberMerchantService.insert(memberMerchant);
            // 初始化商户信息
            this.inserWechatAccount(memberMerchant);
        }
    }

    /**
     * 更新微信账户
     * @param wechatAccount 微信账户
     * @param memberMerchant 商户信息
     */
    public void updateWechatAccount(WechatAccount wechatAccount, MemberMerchant memberMerchant){
        //验证商户唯一性
      /*  MemberMerchant hasMerchant = memberMerchantService.findByAccountId(memberMerchant.getOfficialAccountId());
        if (null != hasMerchant && memberMerchant.getId().equals(hasMerchant.getId()) ){
            Member member = memberService.get(hasMerchant.getMemberId());
            throw new BusinessException("公众已经绑定合作商"+member.getRealname());
        }*/
        String token
                = wechatTokenBizService.getToken(memberMerchant.getOfficialAccountId(), memberMerchant.getOfficialAccountSecret());
        if (!Strings.isNullOrEmpty(token)){
            GetTicketResponse response = wechatBizService.getTicket(token);
            if (null != response && !Strings.isNullOrEmpty(response.getTicket())){
                wechatAccount.setTicket(response.getTicket());
            }
            wechatAccount.setToken(token);
            wechatAccountService.update(wechatAccount);
        }
        else {
            throw new BusinessException("微信公众号配置信息错误");
        }
    }

    /**
     * 新增微信账户
     * @param memberMerchant 商户信息
     */
    public void inserWechatAccount(MemberMerchant memberMerchant){
        String token
                = wechatTokenBizService.getToken(memberMerchant.getOfficialAccountId(), memberMerchant.getOfficialAccountSecret());
        if (!Strings.isNullOrEmpty(token)){
            WechatAccount wechatAccount = new WechatAccount();
            GetTicketResponse response = wechatBizService.getTicket(token);
            if (null != response && !Strings.isNullOrEmpty(response.getTicket())){
                wechatAccount.setTicket(response.getTicket());
            }
            wechatAccount.setToken(token);
            wechatAccount.setMemberId(memberMerchant.getMemberId());
            wechatAccount.setType(Constant.WECHAT_ACCOUNT_TYPE_PARTNER);
            wechatAccountService.insert(wechatAccount);
        }
        else {
            throw new BusinessException("微信公众号配置信息错误");
        }
    }
}
