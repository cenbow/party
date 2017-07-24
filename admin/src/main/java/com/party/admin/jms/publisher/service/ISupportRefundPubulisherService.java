package com.party.admin.jms.publisher.service;

/**
 * Created by wei.li
 *
 * @date 2017/5/3 0003
 * @time 11:23
 */
public interface ISupportRefundPubulisherService {

    void send(final String supportId);
}
