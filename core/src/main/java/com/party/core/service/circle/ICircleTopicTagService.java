package com.party.core.service.circle;

import com.party.core.model.circle.CircleTopicTag;
import com.party.core.model.circle.CircleType;
import com.party.core.service.IBaseService;


/**
 * 圈子话题类型
 * @author Administrator
 *
 */
public interface ICircleTopicTagService extends IBaseService<CircleTopicTag> {

    void delByCircle(String id);
}
