package com.party.core.service.contact;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.contact.Contact;
import com.party.core.model.member.Member;
import com.party.core.service.IBaseService;

/**
 * 通讯录
 * @author Administrator
 *
 */
public interface IContactService extends IBaseService<Contact> {

	List<Map<String, Object>> memberPage(Member member, Map<String, Object> params, Page page);

}
