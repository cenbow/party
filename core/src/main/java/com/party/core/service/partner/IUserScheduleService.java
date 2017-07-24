package com.party.core.service.partner;

import com.party.core.model.partner.UserSchedule;
import com.party.core.service.IBaseService;

/**
 * 用户附加信息服务接口
 * party
 * Created by wei.li
 * on 2016/10/20 0020.
 */
public interface IUserScheduleService extends IBaseService<UserSchedule> {

    /**
     * 根据用户编号查询附加信息
     * @param userId 用户编号
     * @return 用户附加信息
     */
    UserSchedule findByUserId(String userId);
}
