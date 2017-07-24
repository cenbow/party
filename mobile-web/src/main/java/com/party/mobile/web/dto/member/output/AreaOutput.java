package com.party.mobile.web.dto.member.output;

import com.party.core.model.city.Area;
import org.springframework.beans.BeanUtils;


/**
 * 地区输出视图（省市两级，用于输入用户信息）
 * party
 * Created by wei.li
 * on 2016/9/28 0028.
 */
public class AreaOutput {

    //城市主键id
    private String id;

    //城市父节点
    private String parentId;

    //城市名字
    private String name;

    //是否有子节点
    private boolean haschildren;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getHaschildren() {
        return haschildren;
    }

    public void setHaschildren(boolean haschildren) {
        this.haschildren = haschildren;
    }

    /**
     * 地区实体转地区输出视图
     * @param area 地区实体
     * @return 交互数据
     */
    public static AreaOutput transform(Area area){
        AreaOutput areaOutput = new AreaOutput();
        BeanUtils.copyProperties(area, areaOutput);
        return areaOutput;
    }
}
