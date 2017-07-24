package com.party.core.model.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志实体
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
public class Log implements Serializable{


    private static final long serialVersionUID = -6697734160730897977L;

    //主键
    private String id;

    //日志类型
    private Integer type;

    //日志标题
    private String title;

    //创建者
    private String createBy;

    //创建时间
    private Date createDate;

    //操作IP地址
    private String remoteAddr;

    //用户代理
    private String userAgent;

    //请求URI
    private String requestUri;

    //操作方式
    private String method;

    //交互数据
    private String params;

    //异常信息
    private String exception;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (id != null ? !id.equals(log.id) : log.id != null) return false;
        if (type != null ? !type.equals(log.type) : log.type != null) return false;
        if (title != null ? !title.equals(log.title) : log.title != null) return false;
        if (createBy != null ? !createBy.equals(log.createBy) : log.createBy != null) return false;
        if (createDate != null ? !createDate.equals(log.createDate) : log.createDate != null) return false;
        if (remoteAddr != null ? !remoteAddr.equals(log.remoteAddr) : log.remoteAddr != null) return false;
        if (userAgent != null ? !userAgent.equals(log.userAgent) : log.userAgent != null) return false;
        if (requestUri != null ? !requestUri.equals(log.requestUri) : log.requestUri != null) return false;
        if (method != null ? !method.equals(log.method) : log.method != null) return false;
        if (params != null ? !params.equals(log.params) : log.params != null) return false;
        return exception != null ? exception.equals(log.exception) : log.exception == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (remoteAddr != null ? remoteAddr.hashCode() : 0);
        result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
        result = 31 * result + (requestUri != null ? requestUri.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", requestUri='" + requestUri + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", exception='" + exception + '\'' +
                '}';
    }
}
