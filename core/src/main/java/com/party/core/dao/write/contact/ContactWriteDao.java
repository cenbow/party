package com.party.core.dao.write.contact;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.contact.Contact;

/**
 * 通讯录
 * @author Administrator
 *
 */

@Repository
public interface ContactWriteDao extends BaseWriteDao<Contact> {
}
