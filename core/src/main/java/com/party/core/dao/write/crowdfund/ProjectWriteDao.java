package com.party.core.dao.write.crowdfund;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.crowdfund.Project;
import org.springframework.stereotype.Repository;

/**
 * 众筹项目数据写入
 * Created by wei.li
 *
 * @date 2017/2/16 0016
 * @time 17:07
 */
@Repository
public interface ProjectWriteDao extends BaseWriteDao<Project> {

}
