package com.party.admin.biz.asynexport;

/**
 * 异步导出接口
 * Created by wei.li
 *
 * @date 2017/7/19 0019
 * @time 14:31
 */
public interface IAsynExportService {

    /**
     * 导出成功回调
     * @param result 是否成功(true/false)
     */
    void callBack(Boolean result);


    /**
     * 导出
     * @param id 目标编号
     */
    void export(String id);
}
