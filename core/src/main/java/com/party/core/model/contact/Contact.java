package com.party.core.model.contact;

import java.io.Serializable;

import com.party.core.model.BaseModel;

/**
 * 通讯录
 */
public class Contact extends BaseModel  implements Serializable {

	private static final long serialVersionUID = 1L;

	public Contact(){
	}

    //firstName
    private String firstName;

    //全名
    private String fullName;
    
    //lastName
    private String lastName;
    
    //middleName
    private String middleName;

    //prefix
    private String prefix;
    
    //suffix
    private String suffix;
    
    //手机
    private String phones;
    
    //email
    private String email;
    
    //company
    private String company;
    
    //company
    private String title;
    
    //company
    private String address;
    
    //company
    private String note;
    
    //用户
    private String memberId;

    //排序
    private Integer sort;
    
    //状态 1有效 0 无效
    private Integer status;
    
	//组
    private String groupName;

    //组id
    private String groupId;
    


    public String getMiddleName() {
		return middleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Contact contact = (Contact) o;

        if (groupName != null ? !groupName.equals(contact.groupName) : contact.groupName != null) return false;
        if (groupId != null ? !groupId.equals(contact.groupId) : contact.groupId != null) return false;
        if (fullName != null ? !fullName.equals(contact.fullName) : contact.fullName != null) return false;
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;
        if (phones != null ? !phones.equals(contact.phones) : contact.phones != null) return false;
        if (memberId != null ? !memberId.equals(contact.memberId) : contact.memberId != null) return false;
        return sort != null ? sort.equals(contact.sort) : contact.sort == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phones != null ? phones.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phones=" + phones + '\'' +
                ", memberId=" + memberId + '\'' +
                ", sort=" + sort + '\'' +
                '}';
    }
}
