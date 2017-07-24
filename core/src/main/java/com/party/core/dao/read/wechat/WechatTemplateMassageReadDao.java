package com.party.core.dao.read.wechat;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.wechat.WechatTemplateMassage;
import com.party.core.model.wechat.WechatTemplateMessageWithEvent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 微信模板消息数据读取
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 15:24
 */
@Repository
public interface WechatTemplateMassageReadDao extends BaseReadDao<WechatTemplateMassage> {

    List<WechatTemplateMessageWithEvent> withEventList(WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent, Page page);

    /**
     * 获取系统微信模板配置
     * @param eventChannelId 消息通道事件
     * @return 微信呢模板配置
     */
    WechatTemplateMassage getSystem(String eventChannelId);

    /**
     * 获取合作商
     * @param eventChannelId 消息通道事件
     * @param memberId 会员编号
     * @return 微信呢模板配置
     */
    WechatTemplateMassage getPartner(@Param(value = "eventChannelId") String eventChannelId,@Param(value = "memberId") String memberId);
}
