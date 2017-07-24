package com.party.core.service.city;

import com.party.core.model.city.Area;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 地区服务接口
 * party
 * Created by wei.li
 * on 2016/8/15 0015.
 */
public interface IAreaService extends IBaseService<Area> {


    /**
     * 查询所有地区
     * @return 地区列表
     */
    List<Area> listAll();


    /**
     * 查找所有市级
     * @return
     */
    List<Area> cityLevelAll();

    /**
     * 根据城市获取区域
     * @param cityName
     * @return
     */
	List<Area> getAreaByCityName(String cityName);

    /**
     * 用于下拉 根据名称查询
     * @param params
     * @return
     */
    List<Map<String,Object>> thFindByName(Map<String,Object> params);
}
