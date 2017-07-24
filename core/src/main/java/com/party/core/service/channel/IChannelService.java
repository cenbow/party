package com.party.core.service.channel;

import java.util.List;
import java.util.Map;

import com.party.common.paging.Page;
import com.party.core.model.channel.Channel;
import com.party.core.service.IBaseService;

/**
 * 专题接口
 * 
 * @author Administrator
 *
 */
public interface IChannelService extends IBaseService<Channel> {

	/**
	 * web端
	 * 
	 * @param channel
	 * @param params
	 * @param page
	 * @return
	 */
	List<Channel> webListPage(Channel channel, Map<String, Object> params, Page page);
}
