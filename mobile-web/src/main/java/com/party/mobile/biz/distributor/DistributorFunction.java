package com.party.mobile.biz.distributor;

import javax.annotation.Nullable;

/**
 * 分销接口
 * Created by wei.li
 *
 * @date 2017/3/2 0002
 * @time 13:54
 */
public interface DistributorFunction {

    @Nullable
    void success( @Nullable String distributorId);
}
