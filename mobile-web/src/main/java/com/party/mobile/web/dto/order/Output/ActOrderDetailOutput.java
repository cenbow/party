package com.party.mobile.web.dto.order.Output;

import com.party.core.model.order.OrderForm;
import org.springframework.beans.BeanUtils;

/**
 * Created by Wesley on 16/11/11.
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 9:08 16/11/11
 * @Modified by:
 */
public class ActOrderDetailOutput extends OrderDetailOutput{
    //活动报名编号
    private String meberActId;

    //活动报名状态
    private Integer actStatus;

    public String getMeberActId() {
        return meberActId;
    }

    public void setMeberActId(String meberActId) {
        this.meberActId = meberActId;
    }

    public Integer getActStatus() {
        return actStatus;
    }

    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }

    /**
     * 商品实体转输出视图
     * @param orderForm 商品实体
     * @return 输出视图
     */
    public static ActOrderDetailOutput transform(OrderForm orderForm){
        ActOrderDetailOutput actOrderDetailOutput = new ActOrderDetailOutput();
        BeanUtils.copyProperties(orderForm, actOrderDetailOutput);
        return actOrderDetailOutput;
    }
}
