package com.party.core.model.article;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 文章类型
 *
 * @author Wesley
 * @data 16/11/22 9:22 .
 */
public enum ArticleType {
    ARTICLE_TYPE_OUTING("1","攻略文章"),

    ARTICLE_TYPE_INDOOR("2","达人文章"),

    ARTICLE_TYPE_LECTURES("3","动态文字");

    //类型
    private String code;

    //类型名
    private String value;

    ArticleType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据状态码获取状态值
     * @param code 状态码
     * @return 状态值
     */
    public static String getValue(String code){
        for (ArticleType articleType : ArticleType.values()){
            if (articleType.getCode().equals(code)){
                return articleType.getValue();
            }
        }
        return null;
    }


    /**
     * 枚举类型转换为map
     * @return 转换后的map
     */
    public static Map<String,String> convertMap(){
        Map<String,String> map = Maps.newHashMap();
        for (ArticleType actStatus : ArticleType.values()){
            map.put(actStatus.getCode(),actStatus.getValue());
        }
        return map;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
