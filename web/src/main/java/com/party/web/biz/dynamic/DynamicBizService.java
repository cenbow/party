package com.party.web.biz.dynamic;

import com.party.core.model.circle.CircleTopic;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.dynamic.DynamicType;
import com.party.core.model.dynamic.Dypics;
import com.party.core.model.member.Member;
import com.party.core.service.circle.biz.CircleTopicBizService;
import com.party.core.service.dynamic.ICommentService;
import com.party.core.service.dynamic.IDynamicService;
import com.party.core.service.dynamic.IDypicsService;
import com.party.core.service.dynamic.biz.DynamicBaseBizService;
import com.party.core.service.love.ILoveService;
import com.party.core.service.member.IMemberService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.RealmUtils;
import com.party.web.web.dto.output.dynamic.DynamicOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DynamicBizService {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ILoveService loveService;

    @Autowired
    private IDypicsService dypicsService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IDynamicService dynamicService;

    @Autowired
    private FileBizService fileBizService;

    @Autowired
    private DynamicBaseBizService dynamicBaseBizService;

    @Autowired
    private CircleTopicBizService circleTopicBizService;

    public DynamicOutput transformCommon(Dynamic input){
        String path = RealmUtils.getCurrentUser().getId() + "/dynamic/";
        DynamicOutput output = DynamicOutput.transform(input);
        // 动态图片列表
        List<Dypics> dypics = dypicsService.findByDynamicId(input.getId(), null);
        output.setPicList(dypics);
        // 点赞数
        output.setCommentNum(input.getCommentNum());
        // 评论数
        output.setLoveNum(input.getLoveNum());
        // 详情二维码
        String content = "community/dynamic_detail.html?id=" + input.getId();
        String qrCodeUrl = fileBizService.getFileEntity(input.getId(), path, content);
        output.setQrCodeUrl(qrCodeUrl);
        // 动态发布者
        Member authorMember = memberService.get(input.getCreateBy());
        output.setAuthorMember(authorMember);
        return output;
    }

    /**
     * 编辑或保存话题
     *
     * @param entity
     * @throws Exception
     */
    @Transactional
    public void saveBizTopic(Dynamic entity, CircleTopic topic) throws Exception {
        entity.setDynamicType(DynamicType.CIRCLE.getCode());
        String dynamicId = dynamicBaseBizService.saveBizDynamic(entity, RealmUtils.getCurrentUser().getId());
        topic.setDynamic(dynamicId);
        circleTopicBizService.saveTopic(topic,RealmUtils.getCurrentUser().getId());
    }

}
