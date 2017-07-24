package com.party.core.dao.write.crowdfund;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.crowdfund.ProjectComment;

/**
 * 众筹评论数据写入
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 17:07
 */
@Repository
public interface ProjectCommentWriteDao extends BaseWriteDao<ProjectComment> {

}
