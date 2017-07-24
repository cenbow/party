package com.party.core.model.crowdfund;

import com.party.core.model.BaseModel;
import com.party.core.model.member.Member;

/**
 * 众筹评论
 * Created by Juliana
 *
 * @date 2017/2/23 0023
 * @time 10:57
 */
public class ProjectComment extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	//评论关联的支持id
	private String refId;

    //评论内容
    private String comment;
    

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}


	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProjectComment comment = (ProjectComment) o;

        if (comment != null ? !comment.equals(comment.comment) : comment.comment != null) return false;
        if (refId != null ? !refId.equals(comment.refId) : comment.refId != null) return false;
		return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (refId != null ? refId.hashCode() : 0);
        return result;
    }
}
