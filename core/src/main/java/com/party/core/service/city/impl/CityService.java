package com.party.core.service.city.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.city.CityReadDao;
import com.party.core.dao.write.city.CityWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.city.City;
import com.party.core.redisDao.city.CityRedisDao;
import com.party.core.service.city.ICityService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 城市服务实现
 * party
 * Created by wei.li
 * on 2016/8/13 0013.
 */

@Service
public class CityService implements ICityService {

    @Autowired
    private CityReadDao cityReadDao;

    @Autowired
    private CityWriteDao cityWriteDao;

    @Autowired
    private CityRedisDao cityRedisDao;

    private static final String  REDIS_CITY_LIST_KEY = "ListCityKey";

    /**
     * 城市插入
     * @param city 城市信息
     * @return 插入结果（true/false）
     */
    public String insert(City city) {
        BaseModel.preInsert(city);
        boolean result = cityWriteDao.insert(city);
        if (result){
            return city.getId();
        }
        return null;
    }


    /**
     * 城市更新
     * @param city 城市信息
     * @return 更新结果（true/false）
     */
    public boolean update(City city) {
        city.setUpdateDate(new Date());
        return cityWriteDao.update(city);
    }


    /**
     * 城市逻辑删除
     * @param id 城市主键
     * @return 删除结果（true/false）
     */
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return cityWriteDao.deleteLogic(id);
    }


    /**
     * 城市物理删除
     * @param id 城市主键
     * @return 删除结果（true/false）
     */
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return cityWriteDao.delete(id);
    }

    /**
     * 城市批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return cityWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 城市批量物理删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return cityWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取城市
     * @param id 主键
     * @return 城市信息
     */
    public City get(String id) {
        return cityReadDao.get(id);
    }

    /**
     * 分页查询城市信息
     * @param city 城市信息
     * @param page 分页信息
     * @return 城市列表
     */
    public List<City> listPage(City city, Page page) {
        return cityReadDao.listPage(city, page);
    }

    /**
     * 查询所有信息
     * @param city 城市信息
     * @return 城市列表
     */
    public List<City> list(City city) {
        return cityReadDao.listPage(city, null);
    }


    /**
     * 查询所有城市列表
     * @return 城市列表
     */
    @Override
    public List<City> listAll() {
        return this.list(null);
    }

    @Override
    public List<City> listOpen() {
        //是否存在缓存
        List<City> cityList = cityRedisDao.listAll(REDIS_CITY_LIST_KEY);
        if (!CollectionUtils.isEmpty(cityList)){
            return cityList;
        }

        City city = new City();
        city.setIsOpen(YesNoStatus.YES.getCode());//已经打开的城市
        cityList = list(city);

        if (!CollectionUtils.isEmpty(cityList))
            cityRedisDao.listPushAll(REDIS_CITY_LIST_KEY, cityList);

        return cityList;
    }

    @Override
    public void updateCityRedis(){
        City city = new City();
        city.setIsOpen(YesNoStatus.YES.getCode());//已经打开的城市
        List<City> cityList = list(city);
        if (!CollectionUtils.isEmpty(cityList))
            cityRedisDao.listPushAll(REDIS_CITY_LIST_KEY, cityList);
    }

    @Override
    public List<City> validateName(City city) {
        return cityReadDao.validateName(city);
    }

    /**
     * 批量查询城市信息
     * @param ids 主键集合
     * @param city 城市信息
     * @param page 分页信息
     * @return 城市列表
     */
    public List<City> batchList(@NotNull Set<String> ids, City city, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return cityReadDao.batchList(ids, new HashedMap(),page);
    }
}
