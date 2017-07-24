package com.party.core.service.city.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.city.AreaReadDao;
import com.party.core.dao.write.city.AreaWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.city.Area;
import com.party.core.redisDao.city.AreaRedisDao;
import com.party.core.service.city.IAreaService;
import com.sun.istack.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 地区服务实现
 * party
 * Created by wei.li
 * on 2016/8/15 0015.
 */
@Service
public class AreaService implements IAreaService {

    @Autowired
    private AreaReadDao areaReadDao;

    @Autowired
    private AreaWriteDao areaWriteDao;

    @Autowired
    private AreaRedisDao areaRedisDao;

    private static final String  REDIS_LIST_KEY = "areaListKey";

    private static final String  REDIS_AREA_LIST_KEY = "areaCityListKey";
    /**
     * 地区插入
     * @param area 地区信息
     * @return 插入结果（true/false）
     */
    public String insert(Area area) {
        //删除缓存
        areaRedisDao.delete(REDIS_LIST_KEY);
        BaseModel.preInsert(area);
        boolean result = areaWriteDao.insert(area);
        if (result){
            return area.getId();
        }
        return null;
    }

    /**
     * 地区更新
     * @param area 地区信息
     * @return 更新结果（true/false）
     */
    public boolean update(Area area) {
        //删除缓存
        area.setUpdateDate(new Date());
        areaRedisDao.delete(REDIS_LIST_KEY);
        return areaWriteDao.update(area);
    }

    /**
     * 地区逻辑删除
     * @param id 地区主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(String id) {
        return areaWriteDao.deleteLogic(id);
    }


    /**
     * 地区物理删除
     * @param id 地区主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        //删除缓存
        areaRedisDao.delete(REDIS_LIST_KEY);
        return areaWriteDao.delete(id);
    }


    /**
     * 地区批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return areaWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 地区批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return areaWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取地区信息
     * @param id 主键
     * @return 地区信息
     */
    public Area get(String id) {
        return areaReadDao.get(id);
    }

    /**
     * 分页查询地区信息
     * @param area 地区信息
     * @param page 分页信息
     * @return 地区列表
     */
    public List<Area> listPage(Area area, Page page) {
        return areaReadDao.listPage(area, page);
    }

    /**
     * 查询全部地区信息
     * @param area 地区信息
     * @return 地区列表
     */
    public List<Area> list(Area area) {
        return areaReadDao.listPage(area, null);
    }


    /**
     * 查询所有地区
     * @return 地区列表
     */
    public List<Area> listAll() {
        //是否存在缓存
        List<Area> areaRedisList = areaRedisDao.listAll(REDIS_LIST_KEY);
        if (!CollectionUtils.isEmpty(areaRedisList)){
            return areaRedisList;
        }
        List<Area> areaList = areaReadDao.listPage(null,null);
        areaRedisDao.listPushAll(REDIS_LIST_KEY, areaList);
        return areaList;
    }


    /**
     * 查询所有市级城市
     * @return 城市列表
     */
    public List<Area> cityLevelAll() {
//        return areaReadDao.cityLevelAll();
        //是否存在缓存
        List<Area> cityList = areaRedisDao.listAll(REDIS_AREA_LIST_KEY);
        if (!CollectionUtils.isEmpty(cityList)){
            return cityList;
        }
        cityList = new ArrayList<Area>();

        List<Area> list = areaReadDao.listPage(null,null);

        for (int i = 0; i < list.size(); i++) {
            Area e = list.get(i);
            Area parent = getArea(e.getParentId(), list);

            if (null != parent &&("0".equals(parent.getParentId()) || "1".equals(parent.getParentId()))){
                cityList.add(e);
            }

        }


        areaRedisDao.listPushAll(REDIS_AREA_LIST_KEY, cityList);
        return cityList;

    }



    /**
     * 批量查询地区信息
     * @param ids 主键集合
     * @param area 地区信息
     * @param page 分页信息
     * @return 地区列表
     */
    public List<Area> batchList(@NotNull Set<String> ids, Area area, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return areaReadDao.batchList(ids, new HashedMap(), page);
    }


    /**
     *
     * @param parentId
     * @param list
     * @return
     */
    private static Area getArea(String parentId, List<Area> list)
    {
        if(StringUtils.isNotBlank(parentId) && null != list && list.size() > 0)
        {
            for(int i = 0; i < list.size(); i ++)
            {
                if(parentId.equals(list.get(i).getId()))
                    return list.get(i);
            }
        }

        return null;
    }

	@Override
	public List<Area> getAreaByCityName(String cityName) {
		return areaReadDao.areaLevel(cityName);
	}

    @Override
    public List<Map<String, Object>> thFindByName(Map<String,Object> params) {
        return areaReadDao.thFindByName(params);
    }
}
