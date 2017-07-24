package com.party.web.biz.sign;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.DateUtils;
import com.party.core.model.sign.GroupAuthor;
import com.party.core.service.sign.ISignGroupService;
import com.party.web.web.dto.input.sign.SignGroupInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 签到小组业务接口
 * Created by wei.li
 *
 * @date 2017/6/7 0007
 * @time 11:42
 */
@Service
public class SignGroupBizService {

    @Autowired
    private ISignGroupService signGroupService;

    /**
     * 获取签到分组
     * @param signGroupInput 签到分组
     * @param page 分页
     * @return 分组列表
     */
    public List<GroupAuthor> list(SignGroupInput signGroupInput, Page page){
        String startTime = null;
        String endTime = null;

        if (null != signGroupInput.getTimeType() && !signGroupInput.getTimeType().equals(0)){
            String now = DateUtils.getTodayStr();
            endTime = now;
            if (signGroupInput.getTimeType().equals(1)){
                startTime = now;
            }
            else if (signGroupInput.getTimeType().equals(2)){
                startTime = DateUtils.formatDate(DateUtils.addDay(now, -7));
            }
            else if (signGroupInput.getTimeType().equals(3)){
                startTime = DateUtils.formatDate(DateUtils.addMonth(DateUtils.getTodayDate(), -1));
            }
        }

        startTime = Strings.isNullOrEmpty(signGroupInput.getStartDate()) ? startTime : signGroupInput.getStartDate();
        endTime = Strings.isNullOrEmpty(signGroupInput.getEndDate()) ? endTime : signGroupInput.getEndDate();
        GroupAuthor groupAuthor = SignGroupInput.transform(signGroupInput);
        groupAuthor.setStartDate(startTime);
        groupAuthor.setEndDate(endTime);
        List<GroupAuthor> list = signGroupService.groupAuthorList(groupAuthor, page);
        return list;
    }
}
