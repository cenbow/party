package com.party.notify.jms.publisher.service;

/**
 * Created by wei.li
 *
 * @date 2017/4/7 0007
 * @time 10:37
 */
public interface INotifyPublisherService {


    void send(final Object object, final String type, final boolean isWrite);
}
