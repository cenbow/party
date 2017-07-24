package com.party.core.service.sign;

import com.party.common.paging.Page;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignApply;
import com.party.core.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 签到报名接口
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 17:56
 */

public interface ISignApplyService extends IBaseService<SignApply> {

    /**
     * 小组成员列表
     * @param groupMember 小组成员
     * @param page 分页参数
     * @return 小组成员列表
     */
    List<GroupMember> groupMemberList(GroupMember groupMember, Page page);

    /**
     * 获取报名信息
     * @param authorId 会员编号
     * @param projectId 项目编号
     * @return 报名信息
     */
    GroupMember get(String authorId, String projectId);

    /**
     * 获取报名信息
     * @param id 编号
     * @return 报名信息
     */
    GroupMember getGroupMember(String id);

    /**
     * 根据创建者和小组查询报名
     * @param authorId 创建者
     * @param groupId 小组
     * @return 报名信息
     */
    GroupMember getByAuthorAndGroup(String authorId, String groupId);

    /**
     * 用户是否报名
     * @param authorId 用户编号
     * @param projectId 项目编号
     * @return 是否报名（true/false）
     */
    boolean isApply(String authorId, String projectId);


    /**
     * 报名在项目中排行
     * @param projectId 项目编号
     * @param id 报名编号
     * @return 排名
     */
    Integer projectRank(String projectId, String id);


    /**
     * 报名在小组排行
     * @param groupId 小组编号
     * @param id 报名编号
     * @return 排名
     */
    Integer groupRank(String groupId, String id);




    /**
     * 排行列表
     * @param projectId 项目编号
     * @param startTime 开始时间
     * @param endTime 接受时间
     * @param page 分页参数
     * @return 列表数据
     */
    List<GroupMember> rankList(String projectId, String startTime, String endTime, Page page);
}
