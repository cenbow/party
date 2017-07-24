package com.party.core.dao.read;

import com.party.common.paging.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据读取交互层基类
 * party
 * Created by wei.li
 * on 2016/8/10 0010.
 */
public interface BaseReadDao<T> {

    /**
     * 根据ID查询实体
     * @param id 主键
     * @return 实体信息
     */
    T get(String id);
    
    /**
     * 根据参数查询实体
     * @param t 实体信息
     * @return 实体信息
     */
    T getUnique(T t);
    
    /**
     * 根据参数查询数量
     * @param t 实体信息
     * @return 实体信息
     */
    Long count(T t);
    
    /**
     * 查询实体列表
     * @param t 实体信息
     * @param page 分页信息
     * @return 实体列表
     */
    List<T> listPage(T t, Page page);


    /**
     * 根据主键集合批量查询列表
     * @param ids 主键集合
     * @param param 查询参数
     * @param page 分页信息
     * @return 实体列表
     */
    List<T> batchList(@Param("ids") Set<String> ids, @Param("param")Map<String , Object> param, Page page);
}
