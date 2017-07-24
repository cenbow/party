package com.party.core.service.member.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.dao.read.member.MemberReadDao;
import com.party.core.dao.write.member.MemberWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;
import com.party.core.model.role.Role;
import com.party.core.model.system.SysRole;
import com.party.core.service.member.IMemberService;
import com.party.core.service.system.ISysRoleService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 会员服务实现
 * party
 * Created by wei.li
 * on 2016/8/12 0012.
 */
@Service
public class MemberService implements IMemberService {

    @Autowired
    private MemberReadDao memberReadDao;

    @Autowired
    private MemberWriteDao memberWriteDao;

    @Autowired
    private ISysRoleService sysRoleService;
    /**
     * 插入会员信息
     * @param member 会员信息
     * @return 插入结果（true/false）
     */
    public String insert(Member member) {
        BaseModel.preInsert(member);
        boolean result = memberWriteDao.insert(member);
        if (result){
            return member.getId();
        }
        return null;
    }

    /**
     * 更新会员信息
     * @param member 会员信息
     * @return 更新结果（true/false）
     */
    public boolean update(Member member) {
        member.setUpdateDate(new Date());
        return memberWriteDao.update(member);
    }

    /**
     * 逻辑删除会员信息
     * @param id  会员主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return memberWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除会员信息
     * @param id 会员主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return memberWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除会员信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return memberWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除会员信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return memberWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取会员信息
     * @param id 主键
     * @return 会员信息
     */
    public Member get(String id) {
        return memberReadDao.get(id);
    }


    /**
     * 根据手机号码查找会员
     * @param phone 电话号码
     * @return 会员信息
     */
    @Override
    public Member findByPhone(@NotNull String phone) {
        Member member = new Member();
        member.setMobile(phone);
        List<Member> memberList = memberReadDao.listPage(member, null);
        if (CollectionUtils.isEmpty(memberList)){
            return null;
        }
        return memberList.get(0);
    }

    /**
     * 根据登录名查询会员信息
     * @param loginName 登陆名
     * @return 会员信息
     */
    @Override
    public Member findByLoginName(@NotNull String loginName) {
        return memberReadDao.findByLoginName(loginName);
    }

    /**
     * 分页查询会员列表
     * @param member 会员信息
     * @param page 分页信息
     * @return 会员列表
     */
    public List<Member> listPage(Member member, Page page) {
        return memberReadDao.listPage(member, page);
    }

    /**
     * 查询所有会员信息
     * @param member 会员信息
     * @return 会员列表
     */
    public List<Member> list(Member member) {
        return memberReadDao.listPage(member, null);
    }



    /**
     * 分页查询会员信息
     * @param ids 主键集合
     * @param member 会员信息
     * @param page 分页信息
     * @return 会员列表
     */
    public List<Member> batchList(@NotNull Set<String> ids, Member member, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return memberReadDao.batchList(ids, new HashedMap(), page);
    }

	@Override
	public List<Member> listPage(Member member, Map<String, Object> params, Page page) {
		return memberReadDao.webListPage(params, member, page);
	}

	
	@Override
	public List<Member> checkUserName(String property, String userId, String type) {
		return memberReadDao.checkUserName(property, userId,type);
	}

    /**
     * 判断是否拥有角色
     * @param memberId 会员编号
     * @param roleCode 角色代码
     * @return 是否拥有角色（true/false）
     */
    @Override
    public boolean hasRole(String memberId, String roleCode) {
        List<SysRole> roleList = sysRoleService.findByMemberId(memberId);
        for (SysRole role : roleList){
            if (role.getCode().equals(roleCode)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> findMemberByPhoneOrName(String property, String value) {
        return memberReadDao.findMemberByPhoneOrName(property, value);
    }

    /**
     * 统计每天的会员数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 统计数据
     */
    @Override
    public List<HashMap<String, Integer>> countByDate(String startDate, String endDate) {
        HashMap<String, Object> parameter = Maps.newHashMap();
        parameter.put("delFlag", BaseModel.DEL_FLAG_NORMAL);
        parameter.put("startDate", startDate);
        parameter.put("endDate", endDate);
        return memberReadDao.countByDate(parameter);
    }

    /**
     * 所有会员
     * @return 统计数
     */
    @Override
    public Integer allCount() {
        HashMap<String, Object> parameter = Maps.newHashMap();
        //parameter.put("delFlag", BaseModel.DEL_FLAG_NORMAL);
        return memberReadDao.count(parameter);
    }

    /**
     * 未审核用户统计
     * @return 统计数
     */
    @Override
    public Integer unauditedCount() {
        HashMap<String, Object> parameter = Maps.newHashMap();
        //parameter.put("delFlag", BaseModel.DEL_FLAG_NORMAL);
        parameter.put("userStatus", Constant.USER_STATUS_UNAUDITED);
        return memberReadDao.count(parameter);
    }

    /**
     * 隐藏用户重要信息
     * @param member
     * @return
     */
    public Member hideImportantInfo(Member member){
        //隐藏手机
        String mobile = member.getMobile();
        if (null != mobile) {
            if (mobile.length() >= 11) {
                mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            }
        }
        member.setMobile(mobile);
        //隐藏真实姓名
        member.setFullname("未公开");
        //隐藏微信号
        member.setWechatNum("未公开");
        return member;
    }

    @Override
    public List<Member> getRemoteLogoList(Integer limit) {
        return memberReadDao.getRemoteLogoList(limit);
    }
}
