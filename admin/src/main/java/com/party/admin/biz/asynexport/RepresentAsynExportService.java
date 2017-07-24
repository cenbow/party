package com.party.admin.biz.asynexport;

import com.party.admin.biz.crowdfund.ProjectBizService;
import com.party.admin.biz.crowdfund.RepresentBizService;
import com.party.admin.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.common.constant.Constant;
import com.party.common.redis.StringJedis;
import com.party.common.utils.DateUtils;
import com.party.common.utils.FileUtils;
import com.party.core.model.activity.Activity;
import com.party.core.service.activity.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 代言数据异步导出
 * Created by wei.li
 *
 * @date 2017/7/19 0019
 * @time 14:33
 */

@Service
@Component(value = "representAsynExportService")
public class RepresentAsynExportService implements  IAsynExportService{

    @Value("#{party['userfiles.basedir']}")
    private String baseDir;

    private Integer target = 0;

    private String targetId = null;

    @Autowired
    private ProjectBizService projectBizService;

    @Autowired
    private AsynExportBizService asynExportBizService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private StringJedis stringJedis;

    @Autowired
    private RepresentBizService representBizService;

    /**
     * 批量导出
     * @param id 代言编号
     */
    public void export(String id){
        targetId = id;
        List<ListForTargetOutput> listForTargetOutputList = representBizService.listForTarget(null, null, id);
        target = listForTargetOutputList.size() + 1;

        for (ListForTargetOutput output : listForTargetOutputList){
            //导出
            List<ProjectForActivityOutput> list = projectBizService.listForDistributorId(output.getId(), null);
            String realPath = baseDir + Constant.BATCH + Constant.EXCEL_URL + id + "/" + output.getAuthorName()
                    + DateUtils.formatNowToYMDHMS().replace(" ", "").replace("|", "") +"/";
            String fileName = DateUtils.formatNowToYMDHMS() + ".xlsx";
            asynExportBizService.export(fileName, ProjectForActivityOutput.class, list, realPath, this);
        }
        Activity activity = activityService.get(id);
        asynExportBizService.export(activity.getTitle() + "的代言.xlsx", ListForTargetOutput.class,
                listForTargetOutputList, baseDir + Constant.BATCH + Constant.EXCEL_URL + id + "/", this);
    }


    /**
     * 导出回调
     * @param result 导出结果
     */
    @Override
    public synchronized void callBack(Boolean result){
        target = target -1;
        if (target.equals(0)){

            String targetUrl =  baseDir + Constant.BATCH+ Constant.EXCEL_URL + targetId;
            String resourceUrl =  baseDir + Constant.BATCH + Constant.ZIP_URL;
            String fileName = targetId + ".zip";
            FileUtils.deleteFile(Constant.BATCH + Constant.ZIP_URL + fileName);
            FileUtils.createDirectory(resourceUrl);
            FileUtils.zipFiles(targetUrl, "*", resourceUrl + fileName);

            //设置24小时过期
            stringJedis.setValue(Constant.PRE_ZIP_URL + "represent"  + targetId, Constant.BATCH + Constant.ZIP_URL + fileName, 86400);
            FileUtils.deleteDirectory(baseDir + Constant.BATCH + Constant.EXCEL_URL + targetId);
        }
    }
}
