package com.party.mobile.web.dto;

import com.party.common.paging.Page;

import java.io.Serializable;

/**
 * 异步请求实体
 * party
 * Created by wei.li
 * on 2016/8/26 0026.
 */
public class AjaxResult implements Serializable {

    private static final String SUCCESS_DESCRIPTION = "success";

    public static final Integer SUCCESS_CODE = 200;

    public static final Integer ERROR_CODE = 99999;

    public static final String ERROR_DESCRIPTION = "error";

    /** ajax 交互结果 */
    private boolean success;

    /** 交互描述信息 */
    private String description;

    /** 错误编码 */
    private Integer errorCode;

    /** 交互数据 */
    private Object data;

    /** 分页模型 */
    private Page page;

    public AjaxResult() {
        this(false);
    }

    public AjaxResult(boolean success) {
        this(success, SUCCESS_DESCRIPTION, SUCCESS_CODE);
    }

    public AjaxResult(boolean success, String description) {
        this(success, description, SUCCESS_CODE, null, null);
    }

    public AjaxResult(boolean success, String description, Integer errorCode) {
        this.success = success;
        this.description = description;
        this.errorCode = errorCode;
    }

    public AjaxResult(boolean success, String description, Integer errorCode, Object data, Page page) {
        this.success = success;
        this.description = description;
        this.errorCode = errorCode;
        this.data = data;
        this.page = page;
    }

    public static AjaxResult error(){
        return new AjaxResult(false, ERROR_DESCRIPTION, ERROR_CODE);
    }
    public static AjaxResult error(Integer errorCode, String description) {
        return new AjaxResult(false, description, errorCode);
    }
    public static AjaxResult error(Object data, Integer errorCode, String description) {
        return new AjaxResult(false, description, errorCode, data, null);
    }
    public static AjaxResult success() {
        return new AjaxResult(true, SUCCESS_DESCRIPTION, SUCCESS_CODE);
    }

    public static AjaxResult success(String description) {
        return new AjaxResult(true, description);
    }

    public static AjaxResult success(Object data) {
        return new AjaxResult(true, SUCCESS_DESCRIPTION, SUCCESS_CODE, data, null);
    }

    public static AjaxResult success(Object data, Page page) {
        return new AjaxResult(true, SUCCESS_DESCRIPTION, SUCCESS_CODE, data, page);
    }

    public static AjaxResult success(String description, Object data) {
        return new AjaxResult(true, description, null, data, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
