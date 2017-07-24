package com.party.core.service;

import com.party.common.paging.Page;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Set;

/**
 * 服务基类
 * party
 * Created by wei.li
 * on 2016/8/10 0010.
 */
public interface IBaseService<T> {

    /**
     * 新增实体
     * @param t 实体信息
     * @return 新增结果（true/false）
     */
    String insert(T t);


    /**
     * 更新实体
     * @param t 实体信息
     * @return 更新结果（true/false）
     */
    boolean update(T t);

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    boolean deleteLogic(@NotNull String id);


    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    boolean delete(@NotNull String id);

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    boolean batchDeleteLogic(@NotNull Set<String> ids);


    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    boolean batchDelete(@NotNull Set<String> ids);

    /**
     * 根据ID查询实体
     * @param id 主键
     * @return 实体信息
     */
    T get(String id);

    /**
     * 分页查询实体列表
     * @param t 实体信息
     * @param page 分页信息
     * @return 实体列表
     */
    List<T> listPage(T t, Page page);

    /**
     * 查询所以实体列表
     * @param t 实体信息
     * @return 实体列表
     */
    List<T> list(T t);
    

    /**
     * 根据主键集合批量查询列表
     * @param ids 主键集合
     * @param t 实体信息
     * @param page 分页信息
     * @return 实体列表
     */
    List<T> batchList(@NotNull Set<String> ids, T t, Page page);
}
