package com.party.core.dao.read.records;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.records.UpRecordWithProject;
import com.party.core.model.records.UpRecords;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 跟进记录数据读取
 * Created by wei.li
 *
 * @date 2017/7/12 0012
 * @time 9:52
 */

@Repository
public interface UpRecordsReadDao extends BaseReadDao<UpRecords> {


    List<UpRecordWithProject> listWithProject(@Param("param")Map<String , Object> param, Page page);
}
