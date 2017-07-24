package com.party.core.dao.read.notify;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.notify.Instance;
import com.party.core.model.notify.InstanceWithMember;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 消息实例数据读取
 * Created by wei.li
 *
 * @date 2017/4/5 0005
 * @time 11:47
 */
@Repository
public interface InstanceReadDao extends BaseReadDao<Instance>{

    /**
     * 获取历史
     * @param param 查询参数
     * @return 实例列表
     */
    List<Instance> getHistory(@Param("param")Map<String , Object> param);

    /**
     * 消息列表
     * @param instanceWithMember 消息
     * @param page 分页参数
     * @return 列表数据
     */
    List<InstanceWithMember> listWithMemberPage(InstanceWithMember instanceWithMember, Page page);
}
