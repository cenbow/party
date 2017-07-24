package com.party.notify.sms;

import com.party.notify.sms.service.ISmsService;
import com.party.notify.sms.service.impl.SmsService;
import org.junit.Test;

/**
 * Created by wei.li
 *
 * @date 2017/7/14 0014
 * @time 17:35
 */
public class SmsTest {

    private ISmsService smsService = new SmsService();

    private  final String uid = "whhwxxkj";

    private final String pwd = "Whhw888888";

    private final String url = "http://222.73.117.158/msg/HttpBatchSendSM";

    @Test
    public void send(){
        String result = smsService.send("13249845944", "【】李威", uid, pwd, url);
        System.out.println(result);
    }
}
