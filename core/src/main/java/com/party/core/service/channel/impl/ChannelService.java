package com.party.core.service.channel.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.party.common.paging.Page;
import com.party.common.utils.StringUtils;
import com.party.core.dao.read.channel.ChannelReadDao;
import com.party.core.dao.write.channel.ChannelWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.channel.Channel;
import com.party.core.service.channel.IChannelService;

/**
 * 专题实现
 * 
 * @author Administrator
 *
 */
@Service
public class ChannelService implements IChannelService {

	@Autowired
	private ChannelReadDao channelReadDao;
	@Autowired
	private ChannelWriteDao channelWriteDao;

	@Override
	public String insert(Channel t) {
		BaseModel.preInsert(t);
		boolean result = channelWriteDao.insert(t);
		if (result) {
			return t.getId();
		}
		return null;
	}

	@Override
	public boolean update(Channel t) {
		if (null == t)
			return false;
		t.setUpdateDate(new Date());
		return channelWriteDao.update(t);
	}

	@Override
	public boolean deleteLogic(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return channelWriteDao.deleteLogic(id);
	}

	@Override
	public boolean delete(String id) {
		if (StringUtils.isBlank(id))
			return false;

		return channelWriteDao.delete(id);
	}

	@Override
	public boolean batchDeleteLogic(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return channelWriteDao.batchDeleteLogic(ids);
	}

	@Override
	public boolean batchDelete(Set<String> ids) {
		if (CollectionUtils.isEmpty(ids))
			return false;

		return channelWriteDao.batchDelete(ids);
	}

	@Override
	public Channel get(String id) {
		return channelReadDao.get(id);
	}

	@Override
	public List<Channel> listPage(Channel t, Page page) {
		return channelReadDao.listPage(t, page);
	}

	@Override
	public List<Channel> list(Channel t) {
		return channelReadDao.listPage(t, null);
	}

	@Override
	public List<Channel> batchList(Set<String> ids, Channel t, Page page) {
		if (CollectionUtils.isEmpty(ids))
			return Collections.EMPTY_LIST;

		return channelReadDao.batchList(ids, new HashedMap(), page);
	}

	@Override
	public List<Channel> webListPage(Channel channel, Map<String, Object> params, Page page) {
		return channelReadDao.webListPage(channel, params, page);
	}

}
