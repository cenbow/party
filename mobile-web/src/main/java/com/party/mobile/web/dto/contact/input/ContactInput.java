package com.party.mobile.web.dto.contact.input;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.beans.BeanUtils;

import com.party.core.model.contact.Contact;

/**
 * 通讯录输入
 * @author Administrator
 *
 */
public class ContactInput {

    //动态图
    private List<Contact> contacts;


    public List<Contact> getContacts() {
		return contacts;
	}


	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}


	/**
     * 转换为动态实体
     * @param contactInput 动态视图
     * @return 动态实体
     */
    public static Contact transform(ContactInput contactInput){
    	Contact contact = new Contact();
        BeanUtils.copyProperties(contactInput, contact);
        return contact;
    }
}
