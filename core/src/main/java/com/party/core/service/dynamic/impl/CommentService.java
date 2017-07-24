package com.party.core.service.dynamic.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.dynamic.CommentReadDao;
import com.party.core.dao.write.dynamic.CommentWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.dynamic.Comment;
import com.party.core.service.dynamic.ICommentService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 动态评论服务实现
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
@Service
public class CommentService implements ICommentService{

    @Autowired
    CommentReadDao commentReadDao;

    @Autowired
    CommentWriteDao commentWriteDao;

    /**
     * 动态评论插入
     * @param comment 动态评论
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(Comment comment) {
        BaseModel.preInsert(comment);
        boolean result = commentWriteDao.insert(comment);
        if (result){
            return comment.getId();
        }
        return null;
    }

    /**
     * 动态评论更新
     * @param comment 动态评论
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(Comment comment) {
        comment.setUpdateDate(new Date());
        return commentWriteDao.update(comment);
    }

    /**
     * 逻辑删除动态评论
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return commentWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除动态评论
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return commentWriteDao.delete(id);
    }


    /**
     * 批量逻辑删除动态评论
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return commentWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除动态评论
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return commentWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取动态评论
     * @param id 主键
     * @return 动态评论
     */
    @Override
    public Comment get(String id) {
        return commentReadDao.get(id);
    }

    /**
     * 分页查询动态评论
     * @param comment 动态评论
     * @param page 分页信息
     * @return 动态评论列表
     */
    @Override
    public List<Comment> listPage(Comment comment, Page page) {
        return commentReadDao.listPage(comment, page);
    }

    /**
     * 查询所有动态评论
     * @param comment 动态评论
     * @return 动态评论列表
     */
    @Override
    public List<Comment> list(Comment comment) {
        return commentReadDao.listPage(comment, null);
    }

    /**
     * 批量查询动态评论
     * @param ids 主键集合
     * @param comment 动态评论
     * @param page 分页信息
     * @return 动态评论列表
     */
    @Override
    public List<Comment> batchList(@NotNull Set<String> ids, Comment comment, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return commentReadDao.batchList(ids, new HashedMap(), page);
    }

    @Override
    public Integer countComment(Comment enttity) {
        return commentReadDao.countComment(enttity);
    }

	@Override
	public List<Comment> webListPage(Comment comment, Map<String, Object> params, Page page) {
		return commentReadDao.webListPage(comment, params, page);
	}

    @Override
    public void delByDynamicId(String id) {
        commentWriteDao.delByDynamicId(id);
    }
}
