package com.party.mobile.biz.member;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.authorization.manager.impl.RedisTokenManager;
import com.party.authorization.utils.Constant;
import com.party.common.redis.StringJedis;
import com.party.common.utils.*;
import com.party.core.exception.BusinessException;
import com.party.core.model.AuditStatus;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.city.Area;
import com.party.core.model.fans.Fans;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberMerchant;
import com.party.core.model.member.ThirdPartyUser;
import com.party.core.model.message.MessageSet;
import com.party.core.service.city.IAreaService;
import com.party.core.service.fans.IFansService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberMerchantService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.member.IThirdPartyUserService;
import com.party.core.service.message.impl.MessageSetService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.utils.RealmUtils;
import com.party.mobile.web.dto.login.input.ThirdPartyUserInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.mobile.web.dto.login.output.ThirdPartyUserOutput;
import com.party.mobile.web.dto.member.input.FillInfoInput;
import com.party.mobile.web.dto.member.input.RegisterInput;
import com.party.mobile.web.dto.member.output.MessageSetOutput;
import com.party.common.utils.PartyCode;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 会员业务服务
 * party
 * Created by wei.li
 * on 2016/9/22 0022.
 */

@Service
public class MemberBizService {

    @Autowired
    StringJedis stringJedis;

    @Autowired
    IAreaService areaService;

    @Autowired
    IMemberService memberService;

    @Autowired
    RedisTokenManager redisTokenManager;

    @Autowired
    IThirdPartyUserService thirdPartyUserService;

    @Autowired
    MessageSetService messageSetService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    IIndustryService industryService;

    @Autowired
    IFansService fansService;

    @Autowired
    IMemberMerchantService memberMerchantService;

    //公众号编号
    @Value("#{wechat['wechat.wwz.appid']}")
    private String appid;

    /**
     * 获取登陆会员输出视图
     *
     * @param member 会员信息
     * @return 会员输出视图
     */
    @Transactional
    public MemberOutput getLoginMember(Member member) {

        return getLoginMember(member, null);
    }

    /**
     * 获取登陆会员输出视图
     *
     * @param member 会员信息
     * @param openId 第三方授权登陆id
     * @return 会员输出视图
     */
    @Transactional
    public MemberOutput getLoginMember(Member member, String openId) {
        MemberOutput memberOutput = MemberOutput.transform(member);
        //设置城市名字
        if (!Strings.isNullOrEmpty(member.getCity())) {
            Area area = areaService.get(member.getCity());
            if (null != area) {
                memberOutput.setCityId(area.getId());
                memberOutput.setCityName(area.getName());
            }
        }
        //设置行业名字
        if (!Strings.isNullOrEmpty(member.getIndustry())) {
            Industry industry = industryService.get(member.getIndustry());
            if (null != industry) {
                memberOutput.setIndustryId(industry.getId());
                memberOutput.setIndustry(industry.getName());
            }
        }
        //设置粉丝数和关注数
        Fans fans = new Fans();
        fans.setMemberId(member.getId());
        fans.setFollowerId(member.getId());
        memberOutput.setFansNum(fansService.countFans(fans));
        memberOutput.setFocusNum(fansService.countFocus(fans));
        //设置token
        String token = this.getToken(member, openId);
        memberOutput.setToken(token);

        //设置第三方登录id列表
//        memberOutput.setThirdPartyUserList(this.getThirdPartyUser(member.getId()));

        return memberOutput;
    }

    /**
     * 获取登陆凭证
     *
     * @param member 会员
     * @return 登陆凭证
     */
    public String getToken(Member member) {
        return getToken(member, null);
    }

    /**
     * 获取登陆凭证
     *
     * @param member 会员
     * @return 登陆凭证
     */
    public String getToken(Member member, String openId) {
        //String token = redisTokenManager.getToken(member.getId());
        //获取登陆交互凭证
        CurrentUser currentUser = CurrentUser.transform(member);
        currentUser.setOpenId(openId);
        String token = RealmUtils.getToken(currentUser);
        redisTokenManager.createRelationship(member.getId(), token);
        return token;
    }

    /**
     * 注册会员
     *
     * @param registerInput 注册输入视图
     */
    @Transactional
    public MemberOutput register(RegisterInput registerInput) {

        //验证手机号是否存在
        Member dbMember = memberService.findByPhone(registerInput.getPhone());
        if (null != dbMember) {
            throw new BusinessException(PartyCode.PHONE_EXIST, "手机号码已经注册");
        }
        Member member = createMember();

        member.setMobile(registerInput.getPhone());
        member.setPassword(RealmUtils.encryptPassword(registerInput.getPassword()));
        memberService.update(member);

        MemberOutput memberOutput = MemberOutput.transform(member);

        //设置第三方登录id列表
//        memberOutput.setThirdPartyUserList(this.getThirdPartyUser(member.getId()));

        String token = this.getToken(member);
        memberOutput.setToken(token);
        return memberOutput;
    }

    /**
     * 通过第三方账户登录注册
     *
     * @param thirdPartyUserInput 第三方登陆输入视图
     * @param request
     */
    @Transactional
    public MemberOutput thirdPartyUserLogin(ThirdPartyUserInput thirdPartyUserInput, HttpServletRequest request) {

        //查找用户
        ThirdPartyUser tUser = null;
        tUser = thirdPartyUserService.getByOpenId(thirdPartyUserInput.getOpenId());
        if (tUser == null) {
            if (!Strings.isNullOrEmpty(thirdPartyUserInput.getUnionId())) {
                tUser = thirdPartyUserService.getByUnionId(thirdPartyUserInput.getUnionId());
            }
        }

        Member member;
        //第三方账号信息不为空
        if (null != tUser) {
            member = memberService.get(tUser.getMemberId());

            boolean bResult = false;
            //如果之前用户没有头像，则补充头像信息
            if (Strings.isNullOrEmpty(member.getLogo()) && !Strings.isNullOrEmpty(thirdPartyUserInput.getLogo())) {
                member.setLogo(thirdPartyUserInput.getLogo());

                bResult = true;
            }
            //如果之前用户没有名字，则补充用户名字
            if (Strings.isNullOrEmpty(member.getRealname()) && !Strings.isNullOrEmpty(thirdPartyUserInput.getRealname())) {
                //过滤Emoji表情
                member.setRealname(PinyinUtil.filterEmoji(thirdPartyUserInput.getRealname(), ""));
                member.setPinyin(PinyinUtil.hanziToPinyin(member.getRealname(), ""));
                bResult = true;
            }
            //更新unionId
            if (Strings.isNullOrEmpty(tUser.getUnionId()) && !Strings.isNullOrEmpty(thirdPartyUserInput.getUnionId())) {
                tUser.setUnionId(thirdPartyUserInput.getUnionId());
                thirdPartyUserService.update(tUser);
            }
            //更新会员信息
            if (bResult) {
                memberService.update(member);
            }
            if (!Strings.isNullOrEmpty(thirdPartyUserInput.getUnionId())) {
                ThirdPartyUser unionIdTUser = thirdPartyUserService.getByUnionIdType(thirdPartyUserInput.getUnionId(), thirdPartyUserInput.getType());
                if (unionIdTUser == null) {
                    createTpUser(member, thirdPartyUserInput);
                } else {
                    tUser.setThirdPartyId(thirdPartyUserInput.getOpenId());
                    thirdPartyUserService.update(tUser);
                }
            }
        } else {
            member = registerMember(thirdPartyUserInput, request);
        }

        MemberOutput memberOutput = getLoginMember(member, thirdPartyUserInput.getOpenId());
        return memberOutput;
    }


    /**
     * 通过第三方账号授权注册会员
     *
     * @param thirdPartyUserInput
     * @param request
     * @return
     */
    @Transactional
    public Member registerMember(ThirdPartyUserInput thirdPartyUserInput, HttpServletRequest request) {
        Member member = createMember();

        //如果之前用户没有头像，则补充头像信息
        if (Strings.isNullOrEmpty(member.getLogo()) && !Strings.isNullOrEmpty(thirdPartyUserInput.getLogo())) {
            member.setLogo(thirdPartyUserInput.getLogo());
        }
        //如果之前用户没有名字，则补充用户名字
        if (Strings.isNullOrEmpty(member.getRealname()) && !Strings.isNullOrEmpty(thirdPartyUserInput.getRealname())) {
            //过滤Emoji表情
            member.setRealname(PinyinUtil.filterEmoji(thirdPartyUserInput.getRealname(), ""));
            member.setPinyin(PinyinUtil.hanziToPinyin(member.getRealname(), ""));
        }
        memberService.update(member);

        createTpUser(member, thirdPartyUserInput);
        return member;
    }

    private ThirdPartyUser createTpUser(Member member, ThirdPartyUserInput thirdPartyUserInput) {
        ThirdPartyUser tUser = new ThirdPartyUser();
        tUser.setType(thirdPartyUserInput.getType());//第三方账号类型
        tUser.setMemberId(member.getId());
        tUser.setThirdPartyId(thirdPartyUserInput.getOpenId());
        tUser.setType(thirdPartyUserInput.getType());
        tUser.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        tUser.setUnionId(thirdPartyUserInput.getUnionId());
        //保存商户信息
        if (!Strings.isNullOrEmpty(thirdPartyUserInput.getMerchantId())) {
            MemberMerchant memberMerchant = memberMerchantService.findByMemberId(thirdPartyUserInput.getMerchantId());
            if (null == memberMerchant) {
                tUser.setAppId(appid);
            } else {
                tUser.setAppId(memberMerchant.getOfficialAccountId());
            }
        }
        //默认为平台
        else {
            tUser.setAppId(appid);
        }
        thirdPartyUserService.insert(tUser);//保存微信登陆openID
        return tUser;
    }

    /**
     * 创建会员，并初始化
     *
     * @return 会员实体
     */
    @Transactional
    public Member createMember() {
        Member member = new Member();
        member.setUserStatus(Integer.parseInt(AuditStatus.AUDITING.getCode()));//默认未审核
        member.setIsAdmin(YesNoStatus.NO.getCode());
        member.setIsExpert(YesNoStatus.NO.getCode());
        member.setIsDistributor(YesNoStatus.NO.getCode());
        member.setPicNum(0);
        member.setIsOpen(YesNoStatus.NO.getCode());
        member.setPassword(RealmUtils.encryptPassword(RealmUtils.DEFALT_PASSWORD));
        memberService.insert(member);//保存会员

        MessageSet messageSet = new MessageSet();
        messageSet.setLoveTip(YesNoStatus.YES.getCode());
        messageSet.setCommentTip(YesNoStatus.YES.getCode());
        messageSet.setFocusTip(YesNoStatus.YES.getCode());
        messageSet.setSysTip(YesNoStatus.YES.getCode());
        messageSet.setActTip(YesNoStatus.YES.getCode());
        messageSet.setGoodsTip(YesNoStatus.YES.getCode());
        messageSet.setMemberId(member.getId());
        messageSetService.insert(messageSet);//保存push消息提示

        return member;
    }

    /**
     * 绑定手机号
     *
     * @param mobile     手机号
     * @param realname   姓名
     * @param company    公司名
     * @param jobTitle   职位
     * @param industryID 行业主键id
     * @param request
     * @return
     */
    @Transactional
    public MemberOutput bindPhone(String mobile, String realname, String company, String jobTitle, String industryID, HttpServletRequest request) {

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);//根据token获取当前登录用户
        ThirdPartyUser tUser = thirdPartyUserService.getByOpenId(currentUser.getOpenId());//获取第三方授权用户实体
        Member memberNew = memberService.get(currentUser.getId());//根据当前登录用户获取会员实体
        Member dbMember = memberService.findByPhone(mobile);//根据用户输入手机号获取的会员实体

        //根据当前登录用户不能获取第三方授权登陆用户实体，判定为没有使用第三方授权账号登陆
        if (null == tUser) {
            throw new BusinessException(PartyCode.BIND_PHONE_UNUSE_OPEN_ID, "未使用第三方授权账号登陆");
        }

        //已经绑定了手机号，就不能再绑定新手机号
        if (!Strings.isNullOrEmpty(memberNew.getMobile())) {
            throw new BusinessException(PartyCode.BIND_PHONE_HAS_BINDED, "已经绑定手机号");
        }

        MemberOutput memberOutput = null;
        if (null != dbMember)//当前手机号已经注册
        {
            //更新第三方授权信息 修改用户id字段
            tUser.setMemberId(dbMember.getId());
            thirdPartyUserService.update(tUser);
            //逻辑删除原用户信息
            memberService.deleteLogic(memberNew.getId());

            //删除token
            String oldToken = request.getHeader(Constant.HTTP_HEADER_NAME);
            redisTokenManager.delRelationshipByToken(oldToken);

            //重新绑定token
            memberOutput = MemberOutput.transform(dbMember);
            String token = this.getToken(dbMember, currentUser.getOpenId());
            memberOutput.setToken(token);
        } else {//当前手机号未注册，填入相关用户信息
            memberNew.setMobile(mobile);
            //所有用户自主填写的内容都需过滤Emoji表情
            if (!Strings.isNullOrEmpty(realname)) {
                memberNew.setRealname(PinyinUtil.filterEmoji(realname, ""));
                memberNew.setPinyin(PinyinUtil.hanziToPinyin(memberNew.getRealname(), ""));
            }
            if (!Strings.isNullOrEmpty(company)) {
                memberNew.setCompany(PinyinUtil.filterEmoji(company, ""));
            }
            if (!Strings.isNullOrEmpty(industryID)) {
                memberNew.setIndustry(industryID);
            }
            if (!Strings.isNullOrEmpty(jobTitle)) {
                memberNew.setJobTitle(PinyinUtil.filterEmoji(jobTitle, ""));
            }
            memberService.update(memberNew);

            //删除token
            String oldToken = request.getHeader(Constant.HTTP_HEADER_NAME);
            redisTokenManager.delRelationshipByToken(oldToken);

            //重新绑定token
            memberOutput = MemberOutput.transform(memberNew);
            String token = this.getToken(memberNew, currentUser.getOpenId());
            memberOutput.setToken(token);
        }

        //设置第三方登录id列表
//        memberOutput.setThirdPartyUserList(this.getThirdPartyUser(currentUser.getId()));

        return memberOutput;
    }

    /**
     * 绑定手机 新
     *  2017-06-26
     * @param mobile  手机号
     * @param request
     * @return 绑定手机
     */
    @Transactional
    public Map<String, Object> bindPhoneNew(String mobile, HttpServletRequest request) {
        CurrentUser curUser = currentUserBizService.getCurrentUser(request);
        Member curMember = memberService.get(curUser.getId());
        //根据手机号查找用户
        Member mobileMember = memberService.findByPhone(mobile);
        Map<String, Object> output = Maps.newHashMap();
        //若用户存在
        if (null != mobileMember) {
            //当前用户不等于手机查到的用户
            if (!mobileMember.getId().equals(curMember.getId())) {
                //如果当前用户是第三方授权账户
                if (CurrentUser.isThirdparty(curUser)) {
                    //查找第三方表中当前用户的记录
                    List<ThirdPartyUser> tUsers = thirdPartyUserService.getByMemberId(curUser.getId());
                    if (null != tUsers && tUsers.size() > 0) {
                        //更新第三方表中当前用户的记录为手机号查询到的用户
                        for (ThirdPartyUser tUser : tUsers) {
                            tUser.setMemberId(mobileMember.getId());
                            thirdPartyUserService.update(tUser);
                        }
                    }

                    //更新登录用户信息
                    //删除token
                    String oldToken = request.getHeader(Constant.HTTP_HEADER_NAME);
                    redisTokenManager.delRelationshipByToken(oldToken);

                    //重新绑定token
                    String token = this.getToken(mobileMember);
                    output.put("token", token);
                    output.put("memberId", mobileMember.getId());
                }else{
                    /**
                     * 不是第三方用户,此处不处理合并操作，
                     * 因为现机构账号是使用用户名密码登录的，若合并其他账号的手机，会使当前用户混乱
                     */
                    throw new BusinessException(PartyCode.PHONE_EXIST,"该手机已被绑定，请确认");
                }
            }
        }else{//若用户不存在
            //更新当前用户的手机号为传入的手机
            curMember.setMobile(mobile);
            memberService.update(curMember);
            output.put("memberId",curMember.getId());
        }
        return output;
    }

    /**
     * 如果当前用户信息为空 更新当前用户的信息
     *
     * @param member
     * @param curMember
     */
    public Member updateImportantInfo(Member member, Member curMember) {
        //所有用户自主填写的内容都需过滤Emoji表情
        if (!Strings.isNullOrEmpty(member.getRealname()) && Strings.isNullOrEmpty(curMember.getRealname())) {
            curMember.setRealname(PinyinUtil.filterEmoji(member.getRealname(), ""));
            curMember.setPinyin(PinyinUtil.hanziToPinyin(member.getRealname(), ""));
        }
        if (!Strings.isNullOrEmpty(member.getCompany()) && Strings.isNullOrEmpty(curMember.getCompany())) {
            curMember.setCompany(PinyinUtil.filterEmoji(member.getCompany(), ""));
        }
        if (!Strings.isNullOrEmpty(member.getJobTitle()) && Strings.isNullOrEmpty(curMember.getJobTitle())) {
            curMember.setJobTitle(PinyinUtil.filterEmoji(member.getJobTitle(), ""));
        }
        if (!Strings.isNullOrEmpty(member.getWechatNum()) && Strings.isNullOrEmpty(curMember.getWechatNum())) {
            curMember.setWechatNum(PinyinUtil.filterEmoji(member.getWechatNum(), ""));
        }
        if (!Strings.isNullOrEmpty(member.getIndustry()) && Strings.isNullOrEmpty(curMember.getIndustry())) {
            curMember.setIndustry(member.getIndustry());
        }
        if (!Strings.isNullOrEmpty(member.getCity()) && Strings.isNullOrEmpty(curMember.getCity())) {
            curMember.setCity(member.getCity());
        }
        memberService.update(curMember);
        return curMember;
    }

    /**
     * 根据当前用户主键id，获取openId列表
     *
     * @param currentUserId 当前用户主键id
     * @return
     */
    public List<ThirdPartyUserOutput> getThirdPartyUser(String currentUserId) {
        ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setMemberId(currentUserId);
//        thirdPartyUser.setDelFlag("0");

        List<ThirdPartyUser> dbList = thirdPartyUserService.list(thirdPartyUser);

        if (!CollectionUtils.isEmpty(dbList)) {
            List<ThirdPartyUserOutput> listOutputList = LangUtils.transform(dbList, input -> {
                ThirdPartyUserOutput listOutput = ThirdPartyUserOutput.transform(input);
                return listOutput;
            });
            return listOutputList;
        }
        return Collections.EMPTY_LIST;

    }


    public MemberOutput fillInfo(FillInfoInput fillInfoInput, String curId) throws Exception {
        //验证当前登录用户数据
        Member dbMember = memberService.get(curId);
        if (null == dbMember) {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        //验证地区Area数据
        Area area = areaService.get(fillInfoInput.getCity());
        if (null == area) {
            throw new BusinessException(PartyCode.AREA_DATA_ERROR, "当前输入的地区id有误，没有找到相应的地区信息");
        }
        //验证行业industry数据
        Industry industry = industryService.get(fillInfoInput.getIndustry());
        if (null == industry) {
            throw new BusinessException(PartyCode.INDUSTRY_DATA_ERROR, "当前输入的行业id有误，没有找到相应的地区信息");
        }

        FillInfoInput.transform(dbMember, fillInfoInput);
        //所有用户自主填写的内容都需过滤Emoji表情
        dbMember.setRealname(PinyinUtil.filterEmoji(dbMember.getRealname(), ""));
        dbMember.setPinyin(PinyinUtil.hanziToPinyin(dbMember.getRealname(), ""));
        dbMember.setFullname(PinyinUtil.filterEmoji(dbMember.getFullname(), ""));
        dbMember.setCompany(PinyinUtil.filterEmoji(dbMember.getCompany(), ""));
        dbMember.setJobTitle(PinyinUtil.filterEmoji(dbMember.getJobTitle(), ""));
        dbMember.setWechatNum(PinyinUtil.filterEmoji(dbMember.getWechatNum(), ""));
        dbMember.setWantRes(PinyinUtil.filterEmoji(dbMember.getWantRes(), ""));

        memberService.update(dbMember);

        MemberOutput output = this.getLoginMember(dbMember);

        return output;
    }

    /**
     * 重置用户手机号
     *
     * @param mobile     手机号
     * @param verifyCode 验证码
     * @param request
     * @return
     * @throws BusinessException
     */
    public boolean resetMobile(String mobile, String verifyCode, HttpServletRequest request) throws BusinessException {
        //验证码验证
        String code = stringJedis.getValue(mobile);
        if (null == code || !verifyCode.equals(code)) {
            throw new BusinessException(PartyCode.VERIFY_CODE_ERROR, "验证码验证失败");
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        //验证手机号是否跟当前用户一致
        if (mobile.equals(currentUser.getLoginName())) {
            throw new BusinessException(PartyCode.RESET_MOBILE_SAME_PHONE_CODE, "该手机号与当前登录用户手机号一致，不用修改");
        }

        Member memberNew = memberService.findByPhone(mobile);
        //手机号已经注册了
        if (null != memberNew) {
            throw new BusinessException(PartyCode.PHONE_EXIST, "该手机号已存在，请用该手机号登陆");
        }
        //验证当前登录用户数据
        Member dbMember = memberService.get(currentUser.getId());
        if (null == dbMember) {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }

        dbMember.setMobile(mobile);

        memberService.update(dbMember);

        return true;
    }

    /**
     * 重置用户头像
     *
     * @param logo    用户头像url
     * @param request
     * @return
     * @throws BusinessException
     */
    public boolean resetAvata(String logo, HttpServletRequest request) throws BusinessException {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        Member dbMember = memberService.get(currentUser.getId());

        //验证当前登录用户数据
        if (null == dbMember) {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }

        dbMember.setLogo(logo);

        memberService.update(dbMember);

        return true;
    }

    /**
     * 设置用户资料是否公开
     *
     * @param isOpen  用户资料是否公开，0：否，1：是
     * @param request
     * @return
     * @throws BusinessException
     */
    public boolean setOpenInfo(Integer isOpen, HttpServletRequest request) throws BusinessException {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        Member dbMember = memberService.get(currentUser.getId());

        //验证当前登录用户数据
        if (null == dbMember) {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }

        dbMember.setIsOpen(isOpen);

        memberService.update(dbMember);

        return true;
    }

    /**
     * 获取push消息通知设置信息
     *
     * @param request
     * @return
     */
    public MessageSetOutput getMessageSetInfo(HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);

        Member dbMember = memberService.get(currentUser.getId());
        //验证当前登录用户数据
        if (null == dbMember) {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }

        MessageSet messageSet = messageSetService.findByMemberId(dbMember.getId());
        //如果之前没有设置，则给加上
        if (null == messageSet) {
            messageSet = new MessageSet();
            messageSet.setLoveTip(YesNoStatus.YES.getCode());
            messageSet.setCommentTip(YesNoStatus.YES.getCode());
            messageSet.setFocusTip(YesNoStatus.YES.getCode());
            messageSet.setSysTip(YesNoStatus.YES.getCode());
            messageSet.setActTip(YesNoStatus.YES.getCode());
            messageSet.setGoodsTip(YesNoStatus.YES.getCode());
            messageSet.setMemberId(dbMember.getId());
            messageSetService.insert(messageSet);//保存push消息提示
        }

        MessageSetOutput output = MessageSetOutput.transform(messageSet);

        return output;

    }

    public boolean updateMessageSet(MessageSetOutput input, HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        Member dbMember = memberService.get(currentUser.getId());
        //验证当前登录用户数据
        if (null == dbMember) {
            throw new BusinessException(PartyCode.CURRENT_USER_DATA_ERROR, "当前用户数据错误,请重新登录");
        }
        MessageSet messageSet = messageSetService.findByMemberId(dbMember.getId());

        if (null == messageSet) {
            messageSet = new MessageSet();
            messageSet.setLoveTip(YesNoStatus.YES.getCode());
            messageSet.setCommentTip(YesNoStatus.YES.getCode());
            messageSet.setFocusTip(YesNoStatus.YES.getCode());
            messageSet.setSysTip(YesNoStatus.YES.getCode());
            messageSet.setActTip(YesNoStatus.YES.getCode());
            messageSet.setGoodsTip(YesNoStatus.YES.getCode());
            messageSet.setMemberId(dbMember.getId());
            messageSetService.insert(messageSet);//保存push消息提示
        }

        //将输入的非空字段copy到MessageSet实体中
        BeanUtils.copyProperties(input, messageSet);
        return messageSetService.update(messageSet);
    }

    /**
     * 手机注册用户绑定第三方授权用户
     *
     * @param thirdPartyUserInput
     * @param request
     * @return
     */
    public MemberOutput bindThird(ThirdPartyUserInput thirdPartyUserInput,
                                  HttpServletRequest request) {
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);//根据token获取当前登录用户
        Member curMember = memberService.get(currentUser.getId());
        ThirdPartyUser tUser = thirdPartyUserService.getByOpenId(thirdPartyUserInput.getOpenId());
        if (null != tUser) {//授权用户存在
            thirdPartyUserService.delete(tUser.getId());
        }
        List<ThirdPartyUser> tpUsers = thirdPartyUserService.getTypeUser(thirdPartyUserInput.getType(), currentUser.getId());
        if (null == tpUsers || tpUsers.size() == 0) {
            ThirdPartyUser dbtUser = new ThirdPartyUser();
            dbtUser.setType(thirdPartyUserInput.getType());//第三方账号类型
            dbtUser.setMemberId(currentUser.getId());
            dbtUser.setThirdPartyId(thirdPartyUserInput.getOpenId());
            dbtUser.setType(thirdPartyUserInput.getType());
            dbtUser.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
            dbtUser.setUnionId(thirdPartyUserInput.getUnionId());
            dbtUser.setAppId(appid);
            thirdPartyUserService.insert(dbtUser);//保存微信登陆openID
        }
        boolean bResult = false;
        if (Strings.isNullOrEmpty(curMember.getLogo()) && !Strings.isNullOrEmpty(thirdPartyUserInput.getLogo())) {
            curMember.setLogo(thirdPartyUserInput.getLogo());
            bResult = true;
        }
        //如果之前用户没有名字，则补充用户名字
        if (Strings.isNullOrEmpty(curMember.getRealname()) && !Strings.isNullOrEmpty(thirdPartyUserInput.getRealname())) {
            //过滤Emoji表情
            curMember.setRealname(PinyinUtil.filterEmoji(thirdPartyUserInput.getRealname(), ""));
            curMember.setPinyin(PinyinUtil.hanziToPinyin(curMember.getRealname(), ""));
            bResult = true;
        }
        //更新会员信息
        if (bResult) {
            memberService.update(curMember);
        }
        MemberOutput memberOutput = new MemberOutput();
        memberOutput = MemberOutput.transform(curMember);
        String token = this.getToken(curMember, currentUser.getOpenId());
        memberOutput.setToken(token);
        //设置第三方登录id列表
//        memberOutput.setThirdPartyUserList(this.getThirdPartyUser(currentUser.getId()));
        return memberOutput;
    }


    /**
     * 获取登陆用户详情
     *
     * @param currentUser 当前用户
     * @return 登陆用户详情
     */
    public MemberOutput getLoginMember(CurrentUser currentUser) {

        Member member = memberService.get(currentUser.getId());
        //获取用户信息
        return this.getLoginMember(member);
    }
}
