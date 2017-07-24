package com.party.notify.jpush;

import com.party.notify.appPush.service.IPushService;
import com.party.notify.appPush.service.impl.JpushService;
import org.junit.Test;

/**
 * Created by wei.li
 *
 * @date 2017/7/14 0014
 * @time 18:12
 */
public class JpushTest {

    private JpushService jpushService = new JpushService();

    @Test
    public void send(){
        jpushService.setAppKey("693c97292b51be19d250b2df");
        jpushService.setMasterSecret("935a9c00d4d963ba3f9e5ec2");
        jpushService.send("71080f2503d44867ad1ec27f7487915a", "测试消息", "1");
    }
}
