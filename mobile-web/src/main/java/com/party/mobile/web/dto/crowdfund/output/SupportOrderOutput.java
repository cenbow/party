package com.party.mobile.web.dto.crowdfund.output;

/**
 * 众筹支持订单编号
 * Created by wei.li
 *
 * @date 2017/2/17 0017
 * @time 14:27
 */
public class SupportOrderOutput {

    //订单编号
    private String id;

    public SupportOrderOutput() {
    }

    public SupportOrderOutput(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
