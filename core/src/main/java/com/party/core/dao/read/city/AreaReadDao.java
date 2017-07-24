package com.party.core.dao.read.city;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.city.Area;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 地区数据读取
 * party
 * Created by wei.li
 * on 2016/8/15 0015.
 */

@Repository
public interface AreaReadDao extends BaseReadDao<Area> {

    /**
     * 查询所有市级城市
     * @return 城市列表
     */
    List<Area> cityLevelAll();
    
	List<Area> areaLevel(@Param(value = "cityName") String cityName);

    /**
     * 用于下拉 根据名称查询
     * @param value
     * @return
     */
    List<Map<String,Object>> thFindByName(@Param(value = "params")Map<String,Object> params);
}
