package com.party.core.service.circle.biz;

import com.party.common.utils.PartyCode;
import com.party.core.exception.BusinessException;
import com.party.core.model.YesNoStatus;
import com.party.core.model.circle.Circle;
import com.party.core.model.circle.CircleCode;
import com.party.core.model.circle.CircleInvite;
import com.party.core.model.circle.CircleMember;
import com.party.core.service.circle.ICircleInviteService;
import com.party.core.service.circle.ICircleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 圈子邀请业务逻辑接口 party Created by Juliana on 2016-12-14
 */

@Service
public class CircleInviteBizService {

    @Autowired
    ICircleInviteService circleInviteService;
    @Autowired
    ICircleMemberService circleMemberService;
    @Autowired
    CircleMemberBizService circleMemberBizService;

    /**
     * 获取是否需要审核信息
     * @return
     */
    @Transactional
    public Boolean getIsVerify(CircleInvite circleInvite, String curUserId){
        Boolean isVerify = true;
        if(null != circleInvite){
            CircleMember search = new CircleMember();
            search.setMember(curUserId);
            search.setCircle(circleInvite.getCircle());
            CircleMember circleMember = circleMemberService.getUnique(search);
            if(null != circleMember){
                throw new BusinessException(PartyCode.CIRCLE_IS_CIRCLE_MEMBER,"当前用户是圈子成员");
            }
            if(circleInvite.getIsVerify() == YesNoStatus.NO.getCode()){
                Date now = new Date();
                if(now.getTime() < circleInvite.getEndTime().getTime()){
                    isVerify = false;
                }else{
                    circleInvite.setIsVerify(YesNoStatus.YES.getCode());
                    isVerify = true;
                }
            }
        }else{
            throw new BusinessException(PartyCode.DATA_IS_BE_DELETE,"数据被删除");
        }
        return isVerify;
    }

    /**
     * 加入圈子
     * @param iId
     * @param curuserId
     */
    public CircleInvite join(String iId, String curuserId) {
        CircleInvite circleInvite = circleInviteService.get(iId);
        if(null == circleInvite){
            throw new BusinessException(PartyCode.DATA_IS_BE_DELETE,"邀请id找不到关系");
        }else{
            circleInvite.setUpdateBy(curuserId);
            circleInviteService.update(circleInvite);
            CircleMember sCircleMember = new CircleMember();// 检索对象
            sCircleMember.setCircle(circleInvite.getCircle());
            sCircleMember.setMember(curuserId);
            CircleMember dbCircleMember = circleMemberService.getUnique(sCircleMember);
            if(dbCircleMember == null){
                circleMemberBizService.saveBiz(circleInvite.getCircle(),curuserId,curuserId, 4); // 前端邀请生成圈子成员
            }
            return circleInvite;
        }

    }
}
