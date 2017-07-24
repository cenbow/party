package com.party.core.dao.read.sign;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.sign.GroupAuthor;
import com.party.core.model.sign.GroupMember;
import com.party.core.model.sign.SignGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 签到分组数据读取
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:51
 */

@Repository
public interface SignGroupReadDao extends BaseReadDao<SignGroup> {

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
     * @param param 请求参参数
     * @param page 分页参数
     * @return 小组列表
     */
    List<GroupAuthor> rankList(@Param("param")Map<String , Object> param, Page page);
}
