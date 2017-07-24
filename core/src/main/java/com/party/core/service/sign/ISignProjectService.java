package com.party.core.service.sign;

import com.party.common.paging.Page;
import com.party.core.model.sign.SignProject;
import com.party.core.model.sign.SignProjectAuthor;
import com.party.core.service.IBaseService;

import java.util.List;

/**
 * 签到项目接口
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 17:25
 */
public interface ISignProjectService extends IBaseService<SignProject> {

    /**
     * 签到项目列表
     * @param signProjectAuthor 签到项目
     * @param page 分页参数
     * @return 列表数据
     */
    List<SignProjectAuthor> projectAuthorList(SignProjectAuthor signProjectAuthor, Page page);

    /**
     * 获取签到项目
     * @param id 编号
     * @return 签到项目
     */
    SignProjectAuthor getProjectAuthor(String id);
}
