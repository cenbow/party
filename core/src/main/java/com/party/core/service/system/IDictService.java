package com.party.core.service.system;

import com.party.core.model.system.Dict;
import com.party.core.service.IBaseService;

/**
 * 系统字典服务接口
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
public interface IDictService extends IBaseService<Dict>{
	Dict getByProperty(Dict dict);
}
