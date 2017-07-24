package com.party.core.dao.write.count;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.count.DataCount;
import org.springframework.stereotype.Repository;

/**
 * 数据统计写入
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 19:19
 */
@Repository
public interface DataCountWriteDao extends BaseWriteDao<DataCount> {
}
