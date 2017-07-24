package com.party.core.dao.read.contact;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.contact.Contact;
import com.party.core.model.member.Member;

/**
 * 通讯录
 * @author Administrator
 *
 */
@Repository
public interface ContactReadDao extends BaseReadDao<Contact> {
	public List<Contact> list(Contact contact);

	public List<Map<String, Object>> memberPage(@Param(value="member") Member member, @Param(value="params") Map<String, Object> params,Page page);
}
