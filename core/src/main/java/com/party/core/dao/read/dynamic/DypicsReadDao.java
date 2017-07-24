package com.party.core.dao.read.dynamic;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.dynamic.Dypics;
import org.springframework.stereotype.Repository;

/**动态图片数据读取
 * party
 * Created by Wesley
 * on 2016/11/17
 */
@Repository
public interface DypicsReadDao extends BaseReadDao<Dypics> {
    /**
     * 统计评图片
     * @param enttity 筛选调条件
     * @return
     */
    public Integer countPics(Dypics enttity);
}
