package com.party.core.service.message;

import com.party.core.model.member.Member;
import com.party.core.model.message.Message;
import com.party.core.service.IBaseService;

/**
 * 会员消息服务接口
 * party
 * Created by wei.li
 * on 2016/9/18 0018.
 */
public interface IMessageService extends IBaseService<Message> {

    /**
     * 存储消息
     * @param title，消息title
     * @param logo，	 消息图标
     * @param picUrl，封面图片
     * @param content，内容
     * @param messageType，消息类型
     * @param relId，消息关联id
     * @param author, 消息作者
     * @param member，推送用户
     * @param tag，标签
     */
    void saveAppMessage(String title, String logo, String picUrl, String content, String messageType,
                        String relId, Member author, Member member, String tag);

    /**
     * 存储商品或者活动消息
     * @param title，消息title
     * @param logo，	 消息图标
     * @param picUrl，封面图片
     * @param content，内容
     * @param messageType，消息类型
     * @param relId，消息关联id
     * @param author, 消息作者
     * @param member，推送用户
     * @param tag，标签
     * @param orderId，商品或者活动的订单id
     * @param orderStatus ，商品或者活动的订单状态
     */
    void saveAppMessage(String title, String logo, String picUrl, String content, String messageType,
                        String relId, Member author, Member member, String tag, String orderId, Integer orderStatus);

    /**
     * 选择性查询
     * @param message 消息
     * @return 消息
     */
    Message find(Message message);

    /**
     * 更新用户消息状态为已读
     * @param message
     * @return
     */
    boolean updateRead(Message message);
    
    /**
     * 获取消息数
     * @param message
     * @return
     */
	Integer getCount(Message message);
}
