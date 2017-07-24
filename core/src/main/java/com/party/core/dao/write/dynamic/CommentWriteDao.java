package com.party.core.dao.write.dynamic;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.dynamic.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 动态评论数据写入
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */

@Repository
public interface CommentWriteDao extends BaseWriteDao<Comment> {
    void delByDynamicId(@Param(value = "id") String id);
}
