package com.party.core.dao.read.channel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.channel.Channel;

@Repository
public interface ChannelReadDao extends BaseReadDao<Channel> {

	/**
	 * web端
	 * 
	 * @param channel
	 * @param params
	 * @param page
	 * @return
	 */
	List<Channel> webListPage(@Param(value = "channel") Channel channel, @Param(value = "params") Map<String, Object> params, Page page);

}
