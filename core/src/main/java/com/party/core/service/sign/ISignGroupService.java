package com.party.core.service.sign;

import com.party.common.paging.Page;
import com.party.core.model.sign.GroupAuthor;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignGroup;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 签到分组接口
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 17:36
 */

public interface ISignGroupService extends IBaseService<SignGroup>{

    /**
     * 小组列表
     * @param groupAuthor 签到小组
     * @param page 分页参数
     * @return 小组列表
     */
    List<GroupAuthor> groupAuthorList(GroupAuthor groupAuthor, Page page);

    /**
     * 获取签到小组
     * @param id 编号
     * @return 签到小组
     */
    GroupAuthor getGroupAuthor(String id);

    /**
     * 小组排行
     * @param projectId 项目名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 分页参数
     * @return 小组列表
     */
    List<GroupAuthor> rankList(String projectId, String startTime, String endTime, Page page);
}
