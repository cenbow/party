package com.party.core.dao.read.crowdfund;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.crowdfund.ProjectComment;

/**
 * 众筹评论数据读取
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 17:04
 */
@Repository
public interface ProjectCommentReadDao extends BaseReadDao<ProjectComment> {

}
