package com.party.core.service.wechat.impl;

import com.google.common.base.Strings;
import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.core.dao.read.wechat.WechatTemplateMassageReadDao;
import com.party.core.dao.write.wechat.WechatTemplateMassageWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.notify.Event;
import com.party.core.model.notify.EventChannel;
import com.party.core.model.wechat.WechatTemplateMassage;
import com.party.core.model.wechat.WechatTemplateMessageWithEvent;
import com.party.core.service.notify.IEventChannelService;
import com.party.core.service.notify.IEventService;
import com.party.core.service.wechat.IWechatTemplateMassageService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 微信消息服务实现
 * Created by wei.li
 *
 * @date 2017/5/19 0019
 * @time 15:48
 */

@Service
public class WechatTemplateMassageService implements IWechatTemplateMassageService {

    @Autowired
    private WechatTemplateMassageReadDao wechatTemplateMassageReadDao;

    @Autowired
    private WechatTemplateMassageWriteDao wechatTemplateMassageWriteDao;

    @Autowired
    private IEventService eventService;

    @Autowired
    private IEventChannelService eventChannelService;

    /**
     * 微信消息插入
     * @param wechatTemplateMassage 微信模板消息
     * @return 编号
     */
    @Override
    public String insert(WechatTemplateMassage wechatTemplateMassage) {
        BaseModel.preInsert(wechatTemplateMassage);
        boolean result = wechatTemplateMassageWriteDao.insert(wechatTemplateMassage);
        if (result){
            return wechatTemplateMassage.getId();
        }
        return null;
    }

    /**
     * 更新微信消息
     * @param wechatTemplateMassage 微信模板消息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(WechatTemplateMassage wechatTemplateMassage) {
        return wechatTemplateMassageWriteDao.update(wechatTemplateMassage);
    }

    /**
     * 逻辑删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return wechatTemplateMassageWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return wechatTemplateMassageWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatTemplateMassageWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除
     * @param ids 主键集合
     * @return 删除结果
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ids)){
            return false;
        }
        return wechatTemplateMassageWriteDao.batchDelete(ids);
    }

    /**
     * 根据编号获取
     * @param id 主键
     * @return 微信模板消息
     */
    @Override
    public WechatTemplateMassage get(String id) {
        return wechatTemplateMassageReadDao.get(id);
    }

    /**
     * 获取系统的模板配置
     * @param eventCode 消息事件编号
     * @return 微信模板
     */
    @Override
    public WechatTemplateMassage getSystem(String eventCode) {
        Event event = eventService.findByCode(eventCode);
        if (null == event){
            return null;
        }
        EventChannel wEventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_WECHAT, event.getId());
        if (null == wEventChannel){
            return null;
        }
        return wechatTemplateMassageReadDao.getSystem(wEventChannel.getId());
    }

    /**
     * 获取合作商的模板配置
     * @param memberId 会员编号
     * @param eventCode 消息事件编号
     * @return 微信模板
     */
    @Override
    public WechatTemplateMassage getPartner(String memberId, String eventCode) {
        Event event = eventService.findByCode(eventCode);
        WechatTemplateMassage wechatTemplateMassage = new WechatTemplateMassage();
        if (null == event){
            return wechatTemplateMassage;
        }
        EventChannel wEventChannel = eventChannelService.findByChannelCodeAndEventId(Constant.MESSAGE_CHANNEL_WECHAT, event.getId());
        if (null == wEventChannel){
            return wechatTemplateMassage;
        }
        return wechatTemplateMassageReadDao.getPartner(wEventChannel.getId(), memberId);
    }

    /**
     * 分页查询
     * @param wechatTemplateMassage 微信模板消息
     * @param page 分页信息
     * @return 微信模板消息列表
     */
    @Override
    public List<WechatTemplateMassage> listPage(WechatTemplateMassage wechatTemplateMassage, Page page) {
        return wechatTemplateMassageReadDao.listPage(wechatTemplateMassage, page);
    }

    /**
     * 微信模板消息列表
     * @param wechatTemplateMessageWithEvent 微信模板消息
     * @param page 分页参数
     * @return 模板消息列表
     */
    @Override
    public List<WechatTemplateMessageWithEvent> withEventList(WechatTemplateMessageWithEvent wechatTemplateMessageWithEvent, Page page) {
        return wechatTemplateMassageReadDao.withEventList(wechatTemplateMessageWithEvent, page);
    }

    /**
     * 查询所有微信模板
     * @param wechatTemplateMassage 微信模板消息
     * @return 列表
     */
    @Override
    public List<WechatTemplateMassage> list(WechatTemplateMassage wechatTemplateMassage) {
        return wechatTemplateMassageReadDao.listPage(wechatTemplateMassage, null);
    }

    @Override
    public List<WechatTemplateMassage> batchList(@NotNull Set<String> ids, WechatTemplateMassage wechatTemplateMassage, Page page) {
        return null;
    }
}
