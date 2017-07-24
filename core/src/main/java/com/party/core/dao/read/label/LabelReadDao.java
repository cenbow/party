package com.party.core.dao.read.label;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.label.Label;
import org.springframework.stereotype.Repository;

/**
 * 标签数据读取
 * Created by wei.li
 *
 * @date 2017/7/7 0007
 * @time 15:51
 */

@Repository
public interface LabelReadDao extends BaseReadDao<Label> {
}
