package com.party.core.service.count;

import com.party.core.model.count.DataCount;
import com.party.core.service.IBaseService;

/**
 * 统计实体
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 19:41
 */
public interface IDataCountService extends IBaseService<DataCount>{

    /**
     * 根据目标编号查询统计数据
     * @param targetId
     * @return 目标编号
     */
    DataCount findByTargetId(String targetId);

    /**
     * 统计销售信息
     * @param targetId 目标编号
     * @param payment 支付金额
     */
    void countSales(String targetId, Float payment);
}
