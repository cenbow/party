package com.party.mobile.web.dto.member.output;

import com.party.core.model.city.City;
import org.springframework.beans.BeanUtils;


/**
 * 城市输出视图（用于根据城市筛选活动，商品列表）
 * party
 * Created by Wesley
 * on 2016/11/8
 */
public class CityOutput {
    //城市主键id
    private String id;

    //城市名字
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 城市实体转城市输出视图
     * @param city 城市实体
     * @return 交互数据
     */
    public static CityOutput transform(City city){
        CityOutput cityOutput = new CityOutput();
        BeanUtils.copyProperties(city, cityOutput);
        return cityOutput;
    }
}
