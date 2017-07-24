package com.party.core.service.crowdfund;

import com.party.core.model.crowdfund.Support;
import com.party.pay.model.refund.WechatPayRefundResponse;

/**
 * 支持退款接口
 * Created by wei.li
 *
 * @date 2017/4/28 0028
 * @time 11:46
 */
public interface ISupportRefundService {


    /**
     * 众筹支持退款
     * @param support 支持信息
     * @param refundResponse 退款报文
     */
    void refund(Support support, WechatPayRefundResponse refundResponse);
}
