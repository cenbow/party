package com.party.notify.template.service.impl;

import com.party.common.utils.StringUtils;
import com.party.notify.template.service.ITemplateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 10:30
 */

@Service
public class TemplateService implements ITemplateService {


    @Value("${mobile.domain}")
    private String domain;

    /**
     * 替换模板
     * @param template 模板
     * @param map 内容
     * @return 内容
     */
    public String replace(String template, HashMap<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            template = StringUtils.replace(template, entry.getKey(), entry.getValue().toString());
        }
        return template;
    }


    /**
     * 替换url
     * @param url
     * @param map
     * @return
     */
    public String replaceUrl(String url, HashMap<String, Object> map){
        url = this.replace(url, map);
        return domain + url;
    }
}
