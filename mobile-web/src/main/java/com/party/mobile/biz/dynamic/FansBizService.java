package com.party.mobile.biz.dynamic;

import com.google.common.base.Strings;
import com.party.core.model.fans.Fans;
import com.party.core.model.fans.FocusStatus;
import com.party.core.service.fans.IFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 粉丝业务
 *
 * @Author: Wesley
 * @Description:
 * @Date: Created in 17:05 16/11/9
 * @Modified by:
 */
@Service
public class FansBizService {
    @Autowired
    private IFansService fansService;

    /**
     * 获取当前要查看的用户与其它会员的关注关系
     * @param memberId 当前用户
     * @param followerId 其它会员
     * @return -1:同一用户，0：未关注，1：已关注，2：互相关注
     */
    public Integer getFocusStatus(String memberId, String followerId)
    {
        if (!Strings.isNullOrEmpty(memberId) && !Strings.isNullOrEmpty(followerId))
        {
            //当前用户不能自己关注自己
            if (memberId.equals(followerId)) {
                return FocusStatus.FOCUS_STATUS_SAME_MEMBER.getCode();
            }

            Fans fans = new Fans();

            //查找当前会员的粉丝列表
            fans.setMemberId(memberId);
            fans.setFollowerId(followerId);
            List<Fans> fansList = fansService.list(fans);

            //查找当前会员的关注列表
            fans.setMemberId(followerId);
            fans.setFollowerId(memberId);
            List<Fans> focusList = fansService.list(fans);
            if (fansList.size() > 0 && focusList.size() > 0) {
                return FocusStatus.FOCUS_STATUS_MUTUAL_FOCUS_ON.getCode();//互相关注
            } else if (fansList.size() <= 0) {
                return FocusStatus.FOCUS_STATUS_NOT_FOCUS_ON.getCode();//未关注
            } else if (fansList.size() > 0) {
                return FocusStatus.FOCUS_STATUS_HAS_FOCUS_ON.getCode();//已关注
            }
        }

        //默认未关注
        return FocusStatus.FOCUS_STATUS_NOT_FOCUS_ON.getCode();
    }
}
