package com.party.core.service.member.biz;

import com.party.common.qcloud.picapi.UploadResult;
import com.party.core.service.picCloud.PicCloudBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Juliana on 2017/7/7 0007.
 */
@Service
public class MemberBaseBizService {

    @Autowired
    PicCloudBizService picCloudBizService;

    /**
     * 把其他服务器的头像上传到万象优图并返回万象优图路径
     * @param remoteUrl
     * @return
     */
    public UploadResult changeLogoToPicCloud(String remoteUrl) {
        return picCloudBizService.upload(remoteUrl);
    }

}
