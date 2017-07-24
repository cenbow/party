package com.party.core.dao.read.fans;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.fans.Fans;
import org.springframework.stereotype.Repository;

/**
 * 粉丝数据读取
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */

@Repository
public interface FansReadDao extends BaseReadDao<Fans> {
    public Integer countFans(Fans entity);
    public Integer countFocus(Fans entity);
}
