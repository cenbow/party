package com.party.admin.task;

import com.party.admin.biz.system.MemberBizService;
import com.party.common.qcloud.picapi.UploadResult;
import com.party.core.model.member.Member;
import com.party.core.service.member.impl.MemberService;
import com.party.core.service.picCloud.PicCloudBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

/**
 * 用户
 * Created by Juliana
 *
 * @date 2017/7/7
 * @time 15:25
 */

@Component(value = "quartzRemotePicToCloud")
public class QuartzRemotePicToCloud {


    @Autowired
    private MemberBizService memberBizService;

    protected static Logger logger = LoggerFactory.getLogger(QuartzRemotePicToCloud.class);
    // 获取ip地址
    public String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
    /**
     * 更新远程路径为万象优图路径
     */
    public void refresh(){
        logger.info("更新远程路径为万象优图路径定时任务启动");
        try {
            if(getIpAddress().equals("139.199.196.46")) {
                memberBizService.updateMemberLogoToCloud(null);
            }
        } catch (Exception e) {
            logger.debug("更新远程路径为万象优图路径定时任务启动异常", e);
        }
    }
}
