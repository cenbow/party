package com.party.core.model.distributor;

/**
 * 分销关系枚举
 * Created by wei.li
 *
 * @date 2017/3/1 0001
 * @time 13:55
 */
public enum DistributorRelationType {

    DISTRIBUTOR_TYPE_ACTIVITY(0, "分销活动"),

    DISTRIBUTOR_TYPE_GOODS(1, "商品"),

    DISTRIBUTOR_TYPE_ARTICLE(2, "文章");

    //状态码
    private Integer code;

    //状态值
    private String value;

    /**
     * 获取值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(Integer code){
        for (DistributorRelationType distributorRelationType : DistributorRelationType.values()){
            if (distributorRelationType.getCode().equals(code)){
                return distributorRelationType.getValue();
            }
        }
        return null;
    }


    DistributorRelationType(Integer code, String value) {
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
