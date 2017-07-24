package com.party.core.dao.write.love;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.love.Love;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 点赞数据写入
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
@Repository
public interface LoveWriteDao extends BaseWriteDao<Love> {
    /**
     * 通过动态id删除
     * @param id
     */
    public void delByDynamicId(@Param(value = "id") String id);
}
