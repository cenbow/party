package com.party.mobile.web.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.party.common.qcloud.picapi.UploadResult;
import com.party.core.service.member.biz.MemberBaseBizService;
import com.party.mobile.biz.verifyCode.VerifyCodeBizService;
import com.party.mobile.utils.RealmUtils;
import com.party.mobile.web.dto.member.input.BindPhoneInput;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.common.redis.StringJedis;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.city.Area;
import com.party.core.model.city.City;
import com.party.core.model.member.Industry;
import com.party.core.model.member.Member;
import com.party.core.service.city.IAreaService;
import com.party.core.service.city.ICityService;
import com.party.core.service.member.IIndustryService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.login.output.MemberOutput;
import com.party.mobile.web.dto.member.input.FillInfoInput;
import com.party.mobile.web.dto.member.input.PasswordSetInput;
import com.party.mobile.web.dto.member.input.RegisterInput;
import com.party.mobile.web.dto.member.output.AreaOutput;
import com.party.mobile.web.dto.member.output.CityOutput;
import com.party.mobile.web.dto.member.output.IndustryOutput;
import com.party.mobile.web.dto.member.output.MessageSetOutput;
import com.party.common.utils.PartyCode;

/**
 * 会员服务控制层
 * party
 * Created by wei.li
 * on 2016/9/22 0022.
 */

@Controller
@RequestMapping(value = "member/member")
public class MemberController {

    @Autowired
    IMemberService memberService;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    IAreaService areaService;

    @Autowired
    ICityService cityService;

    @Autowired
    IIndustryService industryService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    VerifyCodeBizService verifyCodeBizService;

    @Autowired
    MemberBaseBizService memberBaseBizService;

    /**
     * 会员注册
     * @param registerInput 会员注册输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/register")
    public AjaxResult register(@Validated RegisterInput registerInput,HttpServletRequest request, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        //验证验证码
        String verifyCode = stringJedis.getValue(registerInput.getPhone());
        if (null == verifyCode || !registerInput.getVerifyCode().equals(verifyCode)){
            return AjaxResult.error(PartyCode.VERIFY_CODE_ERROR, "手机验证码不一致");
        }

        MemberOutput memberOutput;
        try {
            memberOutput = memberBizService.register(registerInput);
        } catch (BusinessException be) {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.setData(memberOutput);
        return ajaxResult;
    }


    /**
     * 修改用户密码
     * @param passwordSetInput 密码输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/resetPwd")
    public AjaxResult resetPassword(@Validated PasswordSetInput passwordSetInput, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        //验证验证码
        String verifyCode = stringJedis.getValue(passwordSetInput.getPhone());
        if (null == verifyCode || !verifyCode.equals(passwordSetInput.getVerifyCode())){
            return AjaxResult.error(PartyCode.VERIFY_CODE_ERROR, "手机验证码不一致");
        }

        //验证手机号是否存在
        Member dbMember = memberService.findByPhone(passwordSetInput.getPhone());
        if (null == dbMember){
            return AjaxResult.error(PartyCode.MEMBER_UNEXIST, "会员未注册");
        }

        //修改密码
        Member member = memberService.findByPhone(passwordSetInput.getPhone());
        member.setPassword(RealmUtils.encryptPassword(passwordSetInput.getPassword()));
        memberService.update(member);
        return AjaxResult.success();
    }

    /**
     * 重置手机号
     * @param phone 手机号
     * @param verifyCode 验证码
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/resetMobile")
    public AjaxResult resetMobile(String phone, String verifyCode, HttpServletRequest request)
    {
        //参数验证
        if (Strings.isNullOrEmpty(phone) || Strings.isNullOrEmpty(verifyCode))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        try {
            memberBizService.resetMobile(phone, verifyCode, request);
            return AjaxResult.success();
        } catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 重置头像
     * @param logo 用户头像url
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/resetAvata")
    public AjaxResult resetAvata(String logo, HttpServletRequest request)
    {
        //参数验证
        if (Strings.isNullOrEmpty(logo))
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        try {
            memberBizService.resetAvata(logo, request);
            return AjaxResult.success();
        } catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 设置用户资料是否公开
     * @param isOpen 用户资料是否公开，0：否，1：是
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setOpenInfo")
    public AjaxResult setOpenInfo(Integer isOpen, HttpServletRequest request)
    {
        //参数验证
        if (null == isOpen || isOpen < 0 || isOpen > 1)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        try {
            memberBizService.setOpenInfo(isOpen ,request);
            return AjaxResult.success();
        } catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 获取push消息开关接口
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMsgTips")
    public AjaxResult getMsgTips(HttpServletRequest request)
    {
        try {
            MessageSetOutput messageSetOutput = memberBizService.getMessageSetInfo(request);
            return AjaxResult.success(messageSetOutput);
        } catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 设置点赞push消息开关
     * @param loveTip 点赞开关（0：关，1：开）
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setLoveTip")
    public AjaxResult setLoveTip(Integer loveTip, HttpServletRequest request)
    {
        //参数验证
        if (null == loveTip || 0 > loveTip || 1 < loveTip)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        MessageSetOutput input = new MessageSetOutput();
        input.setLoveTip(loveTip);

        try {
            memberBizService.updateMessageSet(input, request);
            return AjaxResult.success();
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 设置评论push消息开关
     * @param commentTip 评论开关（0：关，1：开）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setCommentTip")
    public AjaxResult setCommentTip(Integer commentTip, HttpServletRequest request)
    {
        //参数验证
        if (null == commentTip || 0 > commentTip || 1 < commentTip)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        MessageSetOutput input = new MessageSetOutput();
        input.setCommentTip(commentTip);

        try {
            memberBizService.updateMessageSet(input, request);
            return AjaxResult.success();
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 设置关注push消息开关
     * @param focusTip 关注开关（0：关，1：开）
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setFocusTip")
    public AjaxResult setFocusTip(Integer focusTip, HttpServletRequest request)
    {
        //参数验证
        if (null == focusTip || 0 > focusTip || 1 < focusTip)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        MessageSetOutput input = new MessageSetOutput();
        input.setFocusTip(focusTip);

        try {
            memberBizService.updateMessageSet(input, request);
            return AjaxResult.success();
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }


    /**
     * 设置系统push消息开关
     * @param sysTip 系统开关（0：关，1：开）
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setSysTip")
    public AjaxResult setSysTip(Integer sysTip, HttpServletRequest request)
    {
        //参数验证
        if (null == sysTip || 0 > sysTip || 1 < sysTip)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        MessageSetOutput input = new MessageSetOutput();
        input.setSysTip(sysTip);

        try {
            memberBizService.updateMessageSet(input, request);
            return AjaxResult.success();
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }

    /**
     * 设置活动push消息开关
     * @param actTip 活动开关（0：关，1：开）
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setActTip")
    public AjaxResult setActTip(Integer actTip, HttpServletRequest request)
    {
        //参数验证
        if (null == actTip || 0 > actTip || 1 < actTip)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        MessageSetOutput input = new MessageSetOutput();
        input.setActTip(actTip);

        try {
            memberBizService.updateMessageSet(input, request);
            return AjaxResult.success();
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }


    /**
     * 设置商品push消息开关
     * @param goodsTip 商品开关（0：关，1：开）
     * @param request
     * @return
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/setGoodsTip")
    public AjaxResult setGoodsTip(Integer goodsTip, HttpServletRequest request)
    {
        //参数验证
        if (null == goodsTip || 0 > goodsTip || 1 < goodsTip)
        {
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, "参数错误");
        }

        MessageSetOutput input = new MessageSetOutput();
        input.setGoodsTip(goodsTip);

        try {
            memberBizService.updateMessageSet(input, request);
            return AjaxResult.success();
        }catch (BusinessException be)
        {
            return AjaxResult.error(be.getCode(), be.getMessage());
        }
    }


    /**
     * 完善用户信息
     * @param bindInput 绑定手机输入视图
     * @param fillInfoInput 输入视图
     * @param result 验证结果
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/fillInfo")
    public AjaxResult fillInfo(BindPhoneInput bindInput,@Validated FillInfoInput fillInfoInput, BindingResult result, HttpServletRequest request) throws Exception {

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        try {
            if(!Strings.isNullOrEmpty(bindInput.getMobile()) && !Strings.isNullOrEmpty(bindInput.getVerifyCode())) {
                //验证码验证
                boolean verifyResult = verifyCodeBizService.verify(bindInput.getMobile(), bindInput.getVerifyCode());
                if (verifyResult) {
                    //绑定手机号
                    Map<String, Object> map = memberBizService.bindPhoneNew(bindInput.getMobile(), request);
                    //如果当前用户有合并操作需重新赋值
                    if(null != map.get("memberId") && !map.get("memberId").equals(currentUser.getId())){
                        currentUser = currentUserBizService.getCurrentUserByToken((String) map.get("token"));
                    }
                }
            }
        }catch (BusinessException be){
            return  AjaxResult.error(be.getCode(),be.getMessage());
        }
        MemberOutput output = memberBizService.fillInfo(fillInfoInput, currentUser.getId());

        return AjaxResult.success(output);
    }


    /**
     * 用户信息初始化数据
     * @return 用户信息初始化数据
     */
    @ResponseBody
    @RequestMapping("/InfoInit")
    public AjaxResult InfoInit(){
        List<Area> dbAreaList = areaService.cityLevelAll();
        List<AreaOutput> areaOutputList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(dbAreaList)){
            areaOutputList = LangUtils.transform(dbAreaList, input -> {
                AreaOutput areaOutput = AreaOutput.transform(input);
                return areaOutput;
            });
        }

        List<Industry> dbIndustryList = industryService.listAll();
        List<IndustryOutput> industryOutputList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dbIndustryList)){
            industryOutputList = LangUtils.transform(dbIndustryList, input -> {
                IndustryOutput industryOutput = IndustryOutput.transform(input);
                return industryOutput;
            });
        }

        Map map = new HashMap();
        map.put("areaList", areaOutputList);
        map.put("industryList", industryOutputList);

        return AjaxResult.success(map);
    }


    /**
     * 获取城市信息（用于填写用户信息）
     * @return 信息信息
     */
    @ResponseBody
    @RequestMapping("/areaInfo")
    public AjaxResult areaInfo(){
        List<Area> dbAreaList = areaService.cityLevelAll();

        List<AreaOutput> areaOutputList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dbAreaList)){
            areaOutputList = LangUtils.transform(dbAreaList, input -> {
                AreaOutput areaOutput = AreaOutput.transform(input);
                return areaOutput;
            });
        }

        return AjaxResult.success(areaOutputList);
    }

    /**
     * 根据parentId获取省市信息（一级目录, 用于填写用户信息）
     * @param parentId 父节点id
     * @return 信息信息
     */
    @ResponseBody
    @RequestMapping("/getCityInfo")
        public AjaxResult getCityInfo(String parentId){
        List<Area> dbAreaList = areaService.cityLevelAll();

        List<AreaOutput> areaOutputList = new ArrayList<>();
        for (int i = 0; i < dbAreaList.size(); i++) {
            AreaOutput areaOutput = AreaOutput.transform(dbAreaList.get(i));
            //如果parentId为空，则给出省份列表，否则给出该省份下的所有城市列表
            if (Strings.isNullOrEmpty(parentId)) {
                if (null != areaOutput && "1".equals(areaOutput.getParentId())) {
                    areaOutput.setHaschildren(true);
                    areaOutputList.add(areaOutput);
                }
            } else {
                if (null != areaOutput && parentId.equals(areaOutput.getParentId())) {
                    areaOutput.setHaschildren(false);
                    areaOutputList.add(areaOutput);
                }
            }
        }
        return AjaxResult.success(areaOutputList);
    }


    /**
     * 获取行业信息
     * @return 行业信息列表
     */
    @ResponseBody
    @RequestMapping("/industryInfo")
    public AjaxResult industryInfo(){
        List<Industry> dbIndustryList = industryService.listAll();

        List<IndustryOutput> industryOutputList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dbIndustryList)){
            industryOutputList = LangUtils.transform(dbIndustryList, input -> {
                IndustryOutput industryOutput = IndustryOutput.transform(input);
                return industryOutput;
            });
        }

        return AjaxResult.success(industryOutputList);
    }

    /**
     * 根据父节点获取行业信息（一级目录, 用于填写用户信息）
     * @param parentId 父节点id
     * @return 行业信息列表
     */
    @ResponseBody
    @RequestMapping("/getIndustryInfo")
    public AjaxResult getIndustryInfo(String parentId){
        List<Industry> dbIndustryList = industryService.listAll();

        List<IndustryOutput> industryOutputList = new ArrayList<>();

        for (int i = 0; i < dbIndustryList.size(); i++) {
            IndustryOutput industryOutput = IndustryOutput.transform(dbIndustryList.get(i));
            //如果parentId为空，则给出一级行业列表，否则给出该一级行业下的所有二级行业列表
            if (Strings.isNullOrEmpty(parentId)) {
                if (null != industryOutput && "0".equals(industryOutput.getParentId())) {
                    industryOutput.setHaschildren(true);
                    industryOutputList.add(industryOutput);
                }
            } else {
                if (null != industryOutput && parentId.equals(industryOutput.getParentId())) {
                    industryOutput.setHaschildren(false);
                    industryOutputList.add(industryOutput);
                }
            }
        }

        return AjaxResult.success(industryOutputList);
    }

    /**
     * 获取城市信息(用于筛选活动，商品列表)
     * @return 行业信息列表
     */
    @ResponseBody
    @RequestMapping("/cityInfo")
    public AjaxResult cityInfo(){
        List<City> dbCityList = cityService.listOpen();

        List<CityOutput> cityOutputList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dbCityList)){
            cityOutputList = LangUtils.transform(dbCityList, input -> {
                CityOutput cityOutput = CityOutput.transform(input);
                return cityOutput;
            });
        }

        return AjaxResult.success(cityOutputList);
    }
    
	@ResponseBody
	@Authorization
	@RequestMapping("/memberInfo")
	public AjaxResult memberInfo(String memberId, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(memberId)) {
				CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
				memberId = currentUser.getId();
			}
			Member member = memberService.get(memberId);
			
			MemberOutput output = memberBizService.getLoginMember(member);
			
			//MemberOutput output = MemberOutput.transform(member);
			return AjaxResult.success(output);
		} catch (BusinessException be) {
			return AjaxResult.error(be.getCode(), be.getMessage());
		}
	}

    @ResponseBody
    @RequestMapping("/updateRemoteLogo")
    public AjaxResult updateRemoteLogo(String remoteUrl, HttpServletRequest request) {
        UploadResult uploadResult = memberBaseBizService.changeLogoToPicCloud(remoteUrl);
        return AjaxResult.success(uploadResult);
    }
}
