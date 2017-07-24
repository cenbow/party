package com.party.notify.template.service;

import java.util.HashMap;

/**
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 10:29
 */
public interface ITemplateService {

    /**
     * 替换模板
     * @param template 模板
     * @param map 内容
     * @return 内容
     */
    String replace(String template, HashMap<String, Object> map);

    /**
     * 替换模板
     * @param url 连接
     * @param map 内容
     * @return 内容
     */
    String replaceUrl(String url, HashMap<String, Object> map);
}
