package com.party.mobile.biz.member;

import com.party.core.model.member.Member;
import com.party.core.model.user.User;
import com.party.core.service.member.IMemberService;
import com.party.core.service.user.IUserService;
import com.party.mobile.web.dto.activity.output.ActivityOutput;
import com.party.mobile.web.dto.member.output.CreateByOutput;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 创建者业务逻辑接口
 * party
 * Created by wei.li
 * on 2016/9/28 0028.
 */

@Service
public class CreateByBizService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMemberService memberService;

    /**
     * 获取创建者信息
     * @param t 创建者子类
     * @param createBy 创建者ID
     * @param <T> 被转换实体
     * @return 有创建者信息的实体
     */
    public <T extends CreateByOutput> T getCreateBy(T t, String createBy){
        User user = userService.get(createBy);
        if (null != user){
            t.setRealname(user.getName());
            t.setLogo(user.getPhoto());
        }
        else {
            Member member = memberService.get(createBy);
            t.setLogo(member.getLogo());
            t.setRealname(member.getRealname());
        }
        return t;
    }

    /**
     * 
     * @param activityOutput
     * @param userId 创建者ID
     * @param memberId 不为空表示是前端发布
     * @return
     */
	public ActivityOutput getCreateBy(ActivityOutput activityOutput, String userId, String memberId) {
		if (StringUtils.isEmpty(memberId)) {
			User user = userService.get(userId);
			activityOutput.setRealname(user.getName());
			activityOutput.setLogo(user.getPhoto());
		} else {
			Member member = memberService.get(memberId);
			activityOutput.setRealname(member.getRealname());
			activityOutput.setLogo(member.getLogo());
		}
		return activityOutput;
	}
}
