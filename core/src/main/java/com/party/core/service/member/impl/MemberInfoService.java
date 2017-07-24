package com.party.core.service.member.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.member.MemberInfoReadDao;
import com.party.core.dao.write.member.MemberInfoWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.member.MemberInfo;
import com.party.core.service.member.IMemberInfoService;

/**
 * 用户附属信息
 * 
 * @author Administrator
 *
 */
@Service
public class MemberInfoService implements IMemberInfoService {

	@Autowired
	private MemberInfoReadDao memberInfoReadDao;

	@Autowired
	private MemberInfoWriteDao memberInfoWriteDao;

	@Override
	public String insert(MemberInfo t) {
		BaseModel.preInsert(t);
		boolean result = memberInfoWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(MemberInfo t) {
		t.setUpdateDate(new Date());
		return memberInfoWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return memberInfoWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (Strings.isNullOrEmpty(id)) {
			return false;
		}
		return memberInfoWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return memberInfoWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return false;
		}
		return memberInfoWriteDao.batchDelete(ids);
	}

	@Override
	public MemberInfo get(String id) {
		return memberInfoReadDao.get(id);
	}

	@Override
	public List<MemberInfo> listPage(MemberInfo t, Page page) {
		return memberInfoReadDao.listPage(t, page);
	}

	@Override
	public List<MemberInfo> list(MemberInfo t) {
		return memberInfoReadDao.listPage(t, null);
	}

	@Override
	public List<MemberInfo> batchList(Set<String> ids, MemberInfo t, Page page) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.EMPTY_LIST;
		}
		return memberInfoReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public MemberInfo findByMemberId(String memberId) {
		MemberInfo info = new MemberInfo();
		info.setMemberId(memberId);
		return memberInfoReadDao.getUnique(info);
	}

}
