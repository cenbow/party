package com.party.core.service.contact.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.contact.ContactReadDao;
import com.party.core.dao.write.contact.ContactWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.contact.Contact;
import com.party.core.model.member.Member;
import com.party.core.service.contact.IContactService;
import com.sun.istack.NotNull;

/**
 * 动态服务实现
 * party
 * Created by wei.li
 * on 2016/8/19 0019.
 */

@Service
public class ContactService implements IContactService {

    @Autowired
    private ContactReadDao contactReadDao;

    @Autowired
    private ContactWriteDao contactWriteDao;


    /**
     * 动态插入
     * @param contact 动态信息
     * @return 插入结果（true/false）
     */
    public String insert(Contact contact) {
        BaseModel.preInsert(contact);
        boolean result = contactWriteDao.insert(contact);
        if (result){
            return contact.getId();
        }
        return null;
    }


    /**
     * 动态更新
     * @param contact 动态信息
     * @return 更新结果（true/false）
     */
    public boolean update(Contact contact) {
        contact.setUpdateDate(new Date());
        return contactWriteDao.update(contact);
    }


    /**
     * 动态逻辑删除
     * @param id 动态主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(String id) {
        return contactWriteDao.deleteLogic(id);
    }

    /**
     * 动态物理删除
     * @param id 动态主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return contactWriteDao.delete(id);
    }


    /**
     * 动态批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return contactWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 动态批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return contactWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取动态信息
     * @param id 主键
     * @return 动态信息
     */
    public Contact get(String id) {
        return contactReadDao.get(id);
    }

    /**
     * 分页查询动态信息
     * @param contact 动态信息
     * @param page 分页信息
     * @return 动态列表
     */
    public List<Contact> listPage(Contact contact, Page page) {
        return contactReadDao.listPage(contact, page);
    }

    /**
     * 查询所有动态信息
     * @param contact 动态信息
     * @return 动态列表
     */
    public List<Contact> list(Contact contact) {
        return contactReadDao.list(contact);
    }


    /**
     * 批量查询动态信息
     * @param ids 主键集合
     * @param contact 动态信息
     * @param page 分页信息
     * @return 动态列表
     */
    public List<Contact> batchList(@NotNull Set<String> ids, Contact contact, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return contactReadDao.batchList(ids, new HashedMap(), page);
    }

    /**
     * 通讯录用户列表
     */
	@Override
	public List<Map<String, Object>> memberPage(Member member, Map<String, Object> params,
			Page page) {
		 return contactReadDao.memberPage(member, params,page);
	}

}
