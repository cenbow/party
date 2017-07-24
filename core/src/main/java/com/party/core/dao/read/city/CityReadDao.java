package com.party.core.dao.read.city;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.city.City;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 城市数据读取
 * party
 * Created by wei.li
 * on 2016/8/13 0013.
 */
@Repository
public interface CityReadDao extends BaseReadDao<City>{
    List<City> validateName(City city);
}
