package com.party.admin.projectest;

import com.party.admin.basetest.BaseTest;
import com.party.common.constant.Constant;
import com.party.core.model.crowdfund.Project;
import com.party.core.model.crowdfund.ProjectWithAuthor;
import com.party.core.model.crowdfund.SupportWithMember;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.crowdfund.ISupportService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wei.li
 *
 * @date 2017/3/30 0030
 * @time 17:18
 */
public class ProjectTest extends BaseTest {

    @Autowired
    private ISupportService supportService;

    @Autowired
    private IProjectService projectService;

    @Test
    public void test(){
        float sum = supportService.sumByProjectId("cd1d72527be743c9abc8ce064a4bc74b");
        System.out.println(sum);
    }
}
