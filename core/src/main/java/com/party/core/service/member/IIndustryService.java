package com.party.core.service.member;

import com.party.core.model.member.Industry;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 行业服务接口
 * party
 * Created by wei.li
 * on 2016/8/13 0013.
 */
public interface IIndustryService extends IBaseService<Industry> {

    /**
     * 查询所有行业
     * @return 行业列表
     */
    List<Industry> listAll();
}
