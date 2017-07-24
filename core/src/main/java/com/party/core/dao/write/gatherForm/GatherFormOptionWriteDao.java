package com.party.core.dao.write.gatherForm;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.gatherForm.GatherFormOption;

@Repository
public interface GatherFormOptionWriteDao extends BaseWriteDao<GatherFormOption> {

	boolean deleteByField(@Param(value = "fieldId") String fieldId);

}
