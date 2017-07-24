package com.party.mobile.web.dto.crowdfund.output;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.party.core.model.crowdfund.ProjectComment;

/**
 * 众筹评论回复输出视图
 * Created by Juliana
 *
 * @date 2017/2/17 0017
 * @time 10:07
 */
public class ProjectCommentOutput {

    //支持编号
    private String id;

    //支持者名称
    private String comment;

    //支持者图像
    private String commentLogo;

    //支持评论
    private String commentName;

    //创建时间
    private Date createDate;


    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getCommentLogo() {
		return commentLogo;
	}


	public void setCommentLogo(String commentLogo) {
		this.commentLogo = commentLogo;
	}


	public String getCommentName() {
		return commentName;
	}


	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	/**
     * 众筹回复评论转输出视图
     * @param supportWithMember 众筹支持
     * @return 支持输出视图
     */
    public static ProjectCommentOutput transform(ProjectComment projectComment){
        ProjectCommentOutput projectCommentOutPut = new ProjectCommentOutput();
        BeanUtils.copyProperties(projectComment, projectCommentOutPut);
        return projectCommentOutPut;
    }
}
