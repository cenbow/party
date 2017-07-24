package com.party.core.service.message;

import com.party.core.model.member.Member;
import com.party.core.model.message.MessageSet;
import com.party.core.service.IBaseService;
import com.sun.istack.NotNull;

/**
 * 消息设置服务接口
 * party
 * Created by wei.li
 * on 2016/8/12 0012.
 */
public interface IMessageSetService extends IBaseService<MessageSet>{
    /**
     * 根据会员id获取消息设置信息
     * @param memberId
     * @return
     */
    public MessageSet findByMemberId(@NotNull String memberId);

	public void initMessageSet(Member member);
}
