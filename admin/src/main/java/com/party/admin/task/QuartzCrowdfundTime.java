package com.party.admin.task;

import com.google.common.collect.Maps;
import com.party.common.utils.DateUtils;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.member.Member;
import com.party.core.model.notify.Event;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.notify.IEventService;
import com.party.notify.notifyPush.servce.INotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 众筹剩余
 * Created by wei.li
 *
 * @date 2017/4/12 0012
 * @time 19:48
 */

@Component(value = "quartzCrowdfundTime")
public class QuartzCrowdfundTime {

    @Autowired
    private INotifyService notifyService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IEventService eventService;


    private static final String EVENT_CODE = "fundTimeTip";

    protected static Logger logger = LoggerFactory.getLogger(QuartzCrowdfundTime.class);

    public void run(){
        logger.info("众筹时间提示定时任务启动");
        List<ProjectWithAuthor> projectList =  projectService.underwayList();
        for (Project project : projectList){
            try {
                this.send(project);
            } catch (Exception e) {
                logger.error("众筹时间提示定时任务异常", e);
                continue;
            }
        }
    }


    /**
     * 短信发送
     * @param project 项目编号
     */
    public void send(Project project){
        Member author = memberService.get(project.getAuthorId());
        Integer dataNum = DateUtils.diffDate(project.getEndDate(), new Date());
        //如果活动已经截止
        if (dataNum < 0){
            return;
        }
        HashMap<String, Object> constant = Maps.newHashMap();
        constant.put("{user}", author.getRealname());
        constant.put("{project}", project.getTitle());
        constant.put("{favorerNum}", project.getFavorerNum());
        constant.put("{actualAmount}", project.getActualAmount());
        constant.put("{dataNum}", dataNum);
        notifyService.push(EVENT_CODE, author.getMobile(), author.getId(), constant);
    }
}
