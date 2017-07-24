package com.party.core.dao.read.sign;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 签到报名数据读取
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:52
 */

@Repository
public interface SignApplyReadDao extends BaseReadDao<SignApply> {

    /**
     * 获取当前用户
     * @param id 主键
     * @return 报名信息
     */
    GroupMember getGroupMember(String id);

    /**
     * 小组成员列表
     * @param groupMember 小组成员
     * @param page 分页参数
     * @return 小组成员列表
     */
    List<GroupMember> groupMemberList(GroupMember groupMember, Page page);

    /**
     * 获取报名信息
     * @param memberId 会员编号
     * @param projectId 项目编号
     * @return 报名信息
     */
    GroupMember getUnique( @Param(value = "memberId") String memberId,@Param(value = "projectId") String projectId);


    /**
     * 根据创建者和小组查询报名
     * @param authorId 创建者
     * @param groupId 小组
     * @return 报名信息
     */
    GroupMember getByAuthorAndGroup(@Param(value = "authorId") String authorId,@Param(value = "groupId") String groupId);


    /**
     * 当前报名者排名
     * @param id 编号
     * @return 排名
     */
    Integer rank(@Param("id") String id, @Param("param")Map<String , Object> param);


    /**
     * 当前排名详情
     * @param param 参数
     * @return 排名
     */
    Integer rankRecord(@Param("param")Map<String , Object> param);

    /**
     * 成绩排行列表
     * @param param 参数
     * @return 成绩列表
     */
    List<GroupMember> rankList(@Param("param")Map<String , Object> param, Page page);
}
