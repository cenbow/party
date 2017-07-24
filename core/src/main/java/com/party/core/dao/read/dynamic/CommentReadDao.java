package com.party.core.dao.read.dynamic;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.dynamic.Comment;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**动态评论数据读取
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Repository
public interface CommentReadDao extends BaseReadDao<Comment> {
    /**
     * 统计评论数
     * @param enttity 筛选调条件
     * @return
     */
    public Integer countComment(Comment enttity);

    /**
     * web端查询
     * @param comment
     * @param params
     * @param page
     * @return
     */
	public List<Comment> webListPage(@Param("comment") Comment comment, @Param("params") Map<String, Object> params, Page page);
}
