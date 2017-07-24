package com.party.web.web.dto.input.sign;

import com.party.core.model.sign.GroupAuthor;
import org.springframework.beans.BeanUtils;

/**
 * Created by wei.li
 *
 * @date 2017/6/7 0007
 * @time 11:30
 */
public class SignGroupInput extends GroupAuthor {

    //时间类型
    private Integer timeType;

    //开始时间
    private String startDate;

    //结束时间
    private String endDate;

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static GroupAuthor transform(SignGroupInput signGroupInput){
        GroupAuthor groupAuthor = new GroupAuthor();
        BeanUtils.copyProperties(signGroupInput, groupAuthor);
        return groupAuthor;
    }
}
