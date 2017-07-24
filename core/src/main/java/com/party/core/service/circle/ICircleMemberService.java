package com.party.core.service.circle;

import com.party.common.paging.Page;
import com.party.core.model.circle.Circle;
import com.party.core.model.circle.CircleMember;
import com.party.core.model.circle.ListAllMember;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Juliana
 *
 */
public interface ICircleMemberService extends IBaseService<CircleMember> {

    /**
     * 获取所有关系
     * @param circleMember
     * @param params
     * @param tags
     * @return
     */
    List<ListAllMember>listAll(CircleMember circleMember, Map<String,Object> params, Set<String> tags);

    /**
     * 获取唯一一个
     * @param search
     * @return
     */
    CircleMember getUnique(CircleMember search);

    /**
     * web端查询
     * @param circleMember
     * @param params
     * @param page
     * @return
     */
    List<Map<String,Object>> webListPage(CircleMember circleMember, Map<String,Object> params, Page page);

    CircleMember getByMobile(CircleMember circleMember, Map<String, Object> params);
    /**
     * 根据类型排序获取所有关系
     * @param circleMember
     * @param params
     * @param tags
     * @return
     */
    List<ListAllMember> listAllByType(CircleMember circleMember, Map<String,Object> params, Set<String> tags);
    public List<CircleMember> listPageSearch(CircleMember circleMember, Map<String, Object> params, Page page);
}
