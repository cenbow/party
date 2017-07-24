package com.party.core.service.records;

import com.party.common.paging.Page;
import com.party.core.model.records.UpRecordWithProject;
import com.party.core.model.records.UpRecords;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 跟进记录接口
 * Created by wei.li
 *
 * @date 2017/7/12 0012
 * @time 10:09
 */
public interface IUpRecordsService extends IBaseService<UpRecords>{

    /**
     * 跟进记录列表
     * @param upRecordWithProject 查询参数
     * @param page 分页参数
     * @return 记录列表
     */
    List<UpRecordWithProject> listWithProject(UpRecordWithProject upRecordWithProject, Page page);
}
