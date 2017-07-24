package com.party.core.service.fans;

import com.party.core.model.fans.Fans;
import com.party.core.service.IBaseService;

/**
 * 粉丝服务接口
 * party
 * Created by wei.li
 * on 2016/9/19 0019.
 */
public interface IFansService extends IBaseService<Fans> {
    public Integer countFans(Fans entity);
    public Integer countFocus(Fans entity);
}
