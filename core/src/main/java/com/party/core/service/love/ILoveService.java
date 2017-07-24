package com.party.core.service.love;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.love.Love;
import com.party.core.service.IBaseService;

/**
 * 点赞服务接口
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public interface ILoveService extends IBaseService<Love> {
    /**
     * 统计点赞数
     * @param entity
     * @return
     */
    public Integer countLove(Love entity);

    /**
     * 根据关联的动态id及创建者会员id，查找一条点赞记录
     * @param dyId 关联的动态id
     * @param memberId 点赞的创建者会员id
     * @return
     */
    public Love findByRefId(String dyId, String memberId);

    /**
     * web端查询
     * @param love
     * @param params
     * @param page
     * @return
     */
	public List<Love> webListPage(Love love, Map<String, Object> params, Page page);

    void delByDynamicId(String id);
}
