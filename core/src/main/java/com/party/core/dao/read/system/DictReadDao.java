package com.party.core.dao.read.system;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.system.Dict;
import org.springframework.stereotype.Repository;

/**
 * 系统字典数据读取
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */

@Repository
public interface DictReadDao extends BaseReadDao<Dict> {
	
	Dict getByProperty(Dict dict);
}
