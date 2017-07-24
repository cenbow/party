package com.party.mobile.web.dto.member.output;

import com.party.core.model.member.Industry;
import org.springframework.beans.BeanUtils;

/**
 * 行业信息输出视图
 * party
 * Created by wei.li
 * on 2016/9/28 0028.
 */
public class IndustryOutput {
    //行业主键id
    private String id;

    //行业父节点id
    private String parentId;

    //行业名字
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
     * 行业实体转行业输出视图
     * @param industry 行业实体
     * @return 交互数据
     */
    public static IndustryOutput transform(Industry industry){
        IndustryOutput industryOutput = new IndustryOutput();
        BeanUtils.copyProperties(industry, industryOutput);
        return industryOutput;
    }
}
