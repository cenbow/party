package com.party.core.service.circle;

import com.party.common.paging.Page;
import com.party.core.model.circle.Circle;
import com.party.core.model.circle.CircleInvite;
import com.party.core.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Juliana
 *
 */
public interface ICircleInviteService extends IBaseService<CircleInvite> {

    CircleInvite getTheFirstEmptyInvite(CircleInvite search);
}
