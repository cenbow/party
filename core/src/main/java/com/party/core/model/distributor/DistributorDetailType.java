package com.party.core.model.distributor;

/**
 * 分销详情类型枚举
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 14:03
 */
public enum DistributorDetailType {

    DISTRIBUTOR_TYPE_ORDER(0, "分销订单"),

    DISTRIBUTOR_TYPE_APPLY(1, "分销报名"),

    DISTRIBUTOR_TYPE_CROWDFUN(3, "分销众筹"),

    DISTRIBUTOR_TYPE_SUPPORT(4, "分销支持");

    //状态码
    private Integer code;

    //状态值
    private String value;

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (DistributorDetailType distributorDetailType : DistributorDetailType.values()){
            if (distributorDetailType.getCode().equals(code)){
                return distributorDetailType.getValue();
            }
        }
        return null;
    }


    DistributorDetailType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
