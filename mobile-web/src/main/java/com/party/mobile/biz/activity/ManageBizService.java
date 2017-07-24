package com.party.mobile.biz.activity;

import com.party.core.model.activity.Activity;
import com.party.core.model.city.City;
import com.party.core.model.count.DataCount;
import com.party.core.model.member.Member;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.city.ICityService;
import com.party.core.service.count.IDataCountService;
import com.party.mobile.biz.file.FileBizService;
import com.party.mobile.web.dto.activity.output.ManageDetailOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 活动管理业务接口
 * Created by wei.li
 *
 * @date 2017/3/28 0028
 * @time 18:02
 */

@Service
public class ManageBizService {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private ICityService cityService;

    @Autowired
    private FileBizService fileBizService;

    @Autowired
    private IDataCountService dataCountService;

    private static String ACTIvITY_PATH = "/activity/";

    private static String SIGN_PATH = "/activity/memberAct/";

    private static String DIS_PATH = "/distribution/";

    private static String ACTIVITY_CONTENT = "hd/hd_detail.html?hdId=";

    private static String SIGN_CONTENT = "hd/hdbm_sign.html?hdId=";

    private static String UPLOAD_PATH = "/upload/";
    /**
     * 获取详情
     * @param id 活动编号
     * @return 活动详情
     */
    public ManageDetailOutput getDetail(String id, String contentPath){
        Activity activity = activityService.get(id);
        ManageDetailOutput manageDetailOutput = ManageDetailOutput.transform(activity);
        City city = cityService.get(activity.getCityId());
        if (null != city){
            manageDetailOutput.setCityName(city.getName());
        }


        String path = activity.getMember() + ACTIvITY_PATH;
        String content = ACTIVITY_CONTENT + activity.getId();
        String qrCodeUrl = fileBizService.getFileEntity(activity.getId(), path, content);

        String signPath = activity.getMember() + SIGN_PATH;
        String signContent = SIGN_CONTENT + activity.getId();
        String signCodeUrl = fileBizService.getFileEntity(activity.getId(), signPath, signContent);

        String disPath = activity.getMember() + DIS_PATH;
        String disContent = getDistributionUrl(activity.getId(), activity.getMember());
        String disCodeUrl = fileBizService.getFileEntity(activity.getId(), disPath, disContent);

        manageDetailOutput.setQrCodeUrl(contentPath + UPLOAD_PATH + qrCodeUrl);
        manageDetailOutput.setSignCodeUrl(contentPath + UPLOAD_PATH + signCodeUrl);
        manageDetailOutput.setDisQrCode(contentPath + UPLOAD_PATH + disCodeUrl);

        //统计数据
        DataCount dataCount = dataCountService.findByTargetId(id);
        manageDetailOutput.setViewNum(dataCount.getViewNum());
        manageDetailOutput.setShareNum(dataCount.getShareNum());
        manageDetailOutput.setApplyNum(dataCount.getApplyNum());
        manageDetailOutput.setSalesNum(dataCount.getSalesNum());
        manageDetailOutput.setSalesAmount(dataCount.getSalesAmount());
        return manageDetailOutput;
    }


    /**
     * 拼接分销连接
     * @param id 活动编号
     * @param memberId 创建者
     * @return 分销连接
     */
    public String getDistributionUrl(String id, String memberId){
        StringBuilder stringBuilder = new StringBuilder("hd/hd_distribution.html?");
        stringBuilder.append("id=").append(id)
                .append("&parentId=").append("0")
                .append("&distributorId=").append(memberId);
        return stringBuilder.toString();
    }
}
