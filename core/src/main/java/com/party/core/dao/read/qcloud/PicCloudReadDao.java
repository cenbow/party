package com.party.core.dao.read.qcloud;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.qcloud.PicCloudSign;

/**
 * PicCloudReadDao万象优图
 *
 * @author Juliana
 * @data 2017-1-4
 */
@Repository
public interface PicCloudReadDao extends BaseReadDao<PicCloudSign> {
	public PicCloudSign getCloudSign();
}
