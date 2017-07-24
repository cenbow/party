package com.party.core.dao.write;

import com.sun.istack.NotNull;

import java.util.Set;

/**
 * 数据写入交互层基类
 * party
 * Created by wei.li
 * on 2016/8/10 0010.
 */
public interface BaseWriteDao<T> {

    /**
     * 新增实体
     * @param t 实体信息
     * @return 新增结果（true/false）
     */
    boolean insert(T t);


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
}
