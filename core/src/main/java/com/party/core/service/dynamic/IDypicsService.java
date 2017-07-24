package com.party.core.service.dynamic;

import com.party.common.paging.Page;
import com.party.core.model.dynamic.Dypics;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 动态图片服务接口
 * party
 * Created by Wesley
 * on 2016/11/17
 */
public interface IDypicsService extends IBaseService<Dypics> {
    /**
     * 统计图片数
     * @param enttity 筛选调条件
     * @return
     */
    public Integer countPics(Dypics enttity);

    /**
     * 根据动态id查找统统图片
     * @param dynamicId
     * @param page
     * @return 动态图片列表
     */
    public List<Dypics> findByDynamicId(String dynamicId, Page page);

    /**
     * 根据会员id主键（图片图片创建者id）查找图片
     * @param memberId 会员主键
     * @param page
     * @return 返回图片列表
     */
    public List<Dypics> findByMemberId(String memberId, Page page);

    /**
     * 根据动态id和类弄删除图片
     * @param id
     * @return
     */
	public boolean deleteByRefId(String id);
}
