package com.party.core.service.dynamic;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.dynamic.Comment;
import com.party.core.service.IBaseService;

/**
 * 动态评论服务接口
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public interface ICommentService extends IBaseService<Comment> {
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
	public List<Comment> webListPage(Comment comment, Map<String, Object> params, Page page);

    void delByDynamicId(String id);
}
