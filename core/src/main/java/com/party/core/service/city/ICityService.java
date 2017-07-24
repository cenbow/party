package com.party.core.service.city;

import com.party.core.model.city.City;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 城市服务接口
 * party
 * Created by wei.li
 * on 2016/8/13 0013.
 */
public interface ICityService extends IBaseService<City> {

    /**
     * 查询所有城市列表
     * @return 城市列表
     */
    List<City> listAll();

    List<City> listOpen();

    /**
     * 更新redis里的city
     */
    void updateCityRedis();

    List<City> validateName(City city);
}
