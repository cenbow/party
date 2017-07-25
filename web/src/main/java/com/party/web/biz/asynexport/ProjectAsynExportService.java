package com.party.web.biz.asynexport;

import com.party.common.constant.Constant;
import com.party.common.redis.StringJedis;
import com.party.common.utils.DateUtils;
import com.party.common.utils.FileUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.service.activity.IActivityService;
import com.party.web.biz.crowdfund.ProjectBizService;
import com.party.web.biz.crowdfund.SupportBizService;
import com.party.web.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.web.web.dto.output.crowdfund.ProjectForActivityOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 众筹批量导入
 * Created by wei.li
 *
 * @date 2017/7/19 0019
 * @time 14:52
 */

@Service
@Component(value = "projectAsynExportService")
public class ProjectAsynExportService implements IAsynExportService {

    @Value("#{party['userfiles.basedir']}")
    private String baseDir;

    private Integer target = 0;

    private String targetId = null;


    @Autowired
    private ProjectBizService projectBizService;

    @Autowired
    private SupportBizService supportBizService;

    @Autowired
    private AsynExportBizService asynExportBizService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private StringJedis stringJedis;

    /**
     * 异步导出回调业务
     * @param result 是否成功(true/false)
     */
    @Override
    public synchronized void callBack(Boolean result) {
        target = target -1;
        if (target.equals(0)){

            String targetUrl =  baseDir + Constant.BATCH+ Constant.EXCEL_URL + targetId;
            String resourceUrl =  baseDir + Constant.BATCH + Constant.ZIP_URL;
            String fileName = targetId + ".zip";
            FileUtils.deleteFile(Constant.BATCH + Constant.ZIP_URL + fileName);
            FileUtils.createDirectory(resourceUrl);
            FileUtils.zipFiles(targetUrl, "*", resourceUrl + fileName);

            //设置24小时过期
            stringJedis.setValue(Constant.PRE_ZIP_URL + "crowdfund" + targetId, Constant.BATCH + Constant.ZIP_URL + fileName, 86400);
            FileUtils.deleteDirectory(baseDir + Constant.BATCH + Constant.EXCEL_URL + targetId);
        }
    }


    /**
     * 导出
     * @param id 目标编号
     */
    @Override
    public void export(String id) {
        targetId = id;
        ProjectWithAuthor projectWithAuthor = new ProjectWithAuthor();
        projectWithAuthor.setTargetId(id);
        List<ProjectForActivityOutput> projectForActivityOutputList = projectBizService.projectForActivityList(projectWithAuthor, null);
        target = projectForActivityOutputList.size() + 1;

        for (ProjectForActivityOutput output : projectForActivityOutputList){
            List<SupportWithMember> list = supportBizService.listForProjectId(output.getId(), null, null);
            String realPath = baseDir + Constant.BATCH + Constant.EXCEL_URL + id + "/" + output.getAuthorName()
                    + DateUtils.formatNowToYMDHMS().replace(" ", "").replace("|", "") +"/";
            String fileName = DateUtils.formatNowToYMDHMS() + ".xlsx";
            asynExportBizService.export(fileName, SupportWithMember.class, list, realPath, this);
        }

        Activity activity = activityService.get(id);
        asynExportBizService.export(activity.getTitle() + "的众筹.xlsx", ProjectForActivityOutput.class,
                projectForActivityOutputList, baseDir + Constant.BATCH + Constant.EXCEL_URL + id + "/", this);
    }
}
