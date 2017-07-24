package com.party.admin.biz.asynexport;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.party.admin.biz.distribution.DistributionBizService;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.input.distribution.DistributionListInput;
import com.party.admin.web.dto.output.activity.MemberActOutput;
import com.party.admin.web.dto.output.crowdfund.ListForTargetOutput;
import com.party.admin.web.dto.output.crowdfund.ProjectForActivityOutput;
import com.party.admin.web.dto.output.distribution.DistributionListOutput;
import com.party.common.constant.Constant;
import com.party.common.redis.StringJedis;
import com.party.common.utils.DateUtils;
import com.party.common.utils.FileUtils;
import com.party.common.utils.LangUtils;
import com.party.core.model.activity.Activity;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 分销异步导出导出
 * Created by wei.li
 *
 * @date 2017/7/20 0020
 * @time 15:21
 */

@Service
@Component(value = "distributionAsynExportService")
public class DistributionAsynExportService implements IAsynExportService {


    @Value("#{party['userfiles.basedir']}")
    private String baseDir;

    private Integer target = 0;

    private String targetId = null;

    @Autowired
    private DistributionBizService distributionBizService;

    @Autowired
    private IMemberActService memberActService;

    @Autowired
    private AsynExportBizService asynExportBizService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private StringJedis stringJedis;

    /**
     * 回调
     * @param result 是否成功(true/false)
     */
    @Override
    public void callBack(Boolean result) {

        target = target -1;
        if (target.equals(0)){

            String targetUrl =  baseDir + Constant.BATCH+ Constant.EXCEL_URL + targetId;
            String resourceUrl =  baseDir + Constant.BATCH + Constant.ZIP_URL;
            String fileName = targetId + ".zip";
            FileUtils.deleteFile(Constant.BATCH + Constant.ZIP_URL + fileName);
            FileUtils.createDirectory(resourceUrl);
            FileUtils.zipFiles(targetUrl, "*", resourceUrl + fileName);

            //设置24小时过期
            stringJedis.setValue(Constant.PRE_ZIP_URL + "distribution" + targetId,
                    Constant.BATCH + Constant.ZIP_URL + fileName, 86400);
            FileUtils.deleteDirectory(baseDir + Constant.BATCH + Constant.EXCEL_URL + targetId);
        }
    }


    /**
     * 导出文件
     * @param id 目标编号
     */
    @Override
    public void export(String id) {
        targetId = id;
        List<DistributionListOutput> listOutputs = distributionBizService.targetList(id, new DistributionListInput(), null);
        target = listOutputs.size() +1;

        for (DistributionListOutput output : listOutputs){
            Map<String, Object> params = Maps.newHashMap();
            params.put("distributionId", output.getId());
            List<MemberAct> list = memberActService.listPage(new MemberAct(), params, null);

            List<MemberActOutput> memberActOutputs = LangUtils.transform(list, new Function<MemberAct, MemberActOutput>() {
                @Override
                public MemberActOutput apply(MemberAct memberAct) {
                    MemberActOutput output = MemberActOutput.transform(memberAct);
                    output.setCreateTime(memberAct.getCreateDate());
                    Activity activity = activityService.get(memberAct.getActId());
                    output.setActivity(activity);
                    Member member = memberService.get(memberAct.getMemberId());
                    output.setMember(member);
                    return output;
                }
            });

            String realPath = baseDir + Constant.BATCH + Constant.EXCEL_URL + id + "/" + output.getDistributorName()
                    + DateUtils.formatNowToYMDHMS().replace(" ", "").replace("|", "") +"/";
            String fileName = DateUtils.formatNowToYMDHMS() + ".xlsx";
            asynExportBizService.export(fileName, MemberActOutput.class, memberActOutputs, realPath, this);
        }

        Activity activity = activityService.get(id);
        asynExportBizService.export(activity.getTitle() + "的分销.xlsx", DistributionListOutput.class,
                listOutputs, baseDir + Constant.BATCH + Constant.EXCEL_URL + id + "/", this);
    }
}
