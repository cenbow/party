package com.party.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wenqiang.luo date:16/4/12
 */
public final class HttpServletRequestUtils {

    /**
     * 私有构造方法
     */
    private HttpServletRequestUtils() {}

    public static String getRemoteIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return ip;
    }

    public static String getRequestUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        String urlPath = request.getRequestURI();

        url.append (scheme);		// http, https
        url.append ("://");
        url.append (request.getServerName());
        if ((scheme.equals ("http") && port != 80)
                || (scheme.equals ("https") && port != 443)) {
            url.append (':').append(request.getServerPort());
        }
        url.append(urlPath);

        return url.toString();
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return  "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
