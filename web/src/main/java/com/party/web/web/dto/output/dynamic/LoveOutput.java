package com.party.web.web.dto.output.dynamic;

import com.party.core.model.love.Love;
import com.party.core.model.member.Member;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 点赞实体 party Created by wei.li on 2016/9/18 0018.
 */
public class LoveOutput {

	// 是否点赞(0：否 1：是 )
	private Integer isLove;

	// 关联id
	private String refId;

	// 点赞类型（1：社区动态；2：圈子动态；）
	private String loveType;

	// 排序
	private Integer sort;

	// 点赞者
	private Member author;

	// 主键
	private String id;

	// 创建人
	private String createBy;

	// 创建时间
	private Date createDate;

	// 更新人
	private String updateBy;

	// 更新时间
	private Date updateDate;

	// 备注
	private String remarks;

	// 删除标记
	private String delFlag;

	public Integer getIsLove() {
		return isLove;
	}

	public void setIsLove(Integer isLove) {
		this.isLove = isLove;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getLoveType() {
		return loveType;
	}

	public void setLoveType(String loveType) {
		this.loveType = loveType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Member getAuthor() {
		return author;
	}

	public void setAuthor(Member author) {
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public static LoveOutput transform(Love input) {
		LoveOutput output = new LoveOutput();
		try {
			BeanUtils.copyProperties(output, input);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return output;
	}

}
