package com.party.core.service.activity.impl;

import com.party.basetest.BaseTest;
import com.party.common.paging.Page;
import com.party.core.dao.write.activity.ActivityWriteDao;
import com.party.core.dao.write.user.UserWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.activity.Activity;
import com.party.core.model.activity.ActivityDetail;
import com.party.core.model.user.User;
import com.party.core.service.user.IUserService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * ActivityServiceTest
 *
 * @author Wesley
 * @data 16/9/6 16:03 .
 */

public class ActivityServiceTest extends BaseTest{
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityDetailService activityDetailService;

    @Autowired
    ActivityWriteDao activityWriteDao;

    @Autowired
    UserWriteDao userWriteDao;


    @Autowired
    IUserService userService;

    Logger logger = Logger.getLogger(ActivityServiceTest.class);

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void update() throws Exception {
        String id = "00f9e7cf9c5a4597b739cf8028eeafff";
        Activity activity = activityService.get(id);

        activity.setDelFlag(BaseModel.DEL_FLAG_NORMAL);

        boolean bResult = activityService.update(activity);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void deleteLogic() throws Exception {
        String id = "00f9e7cf9c5a4597b739cf8028eeafff";
        boolean bResult = activityService.deleteLogic(id);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void delete() throws Exception {
        String id = "00f9e7cf9c5a4597b739cf8028eeaff1";
        boolean bResult = activityService.delete(id);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void batchDeleteLogic() throws Exception {
        Set<String> ids = new HashSet<String>(Arrays.asList("00f9e7cf9c5a4597b739cf8028eeafff", "2716c401d2934ccda3af4623f7a6d31c"));
        boolean bResult = activityService.batchDeleteLogic(ids);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void batchDelete() throws Exception {

    }

    @Test
    public void get() throws Exception {
        String id = "00f9e7cf9c5a4597b739cf8028eeafff";
        Activity activity = activityService.get(id);
        ActivityDetail activityDetail = activityDetailService.getByRefId(id);
        logger.info(activity.toString());
        logger.info(activityDetail.toString());
    }

    @Test
    public void listPage() throws Exception {

        List<Activity> list = activityService.listPage(new Activity(), new Page(1,100));
        logger.info(list.toString());
    }

    @Test
    public void list() throws Exception {
        List<Activity> list = activityService.list(new Activity());
        logger.info(list.toString());
    }

    @Test
    public void batchList() throws Exception {
        Set<String> ids = new HashSet<String>(Arrays.asList("00f9e7cf9c5a4597b739cf8028eeafff", "2716c401d2934ccda3af4623f7a6d31c"));

        List<Activity> list = activityService.batchList(ids, new Activity(), new Page(1,10));
        logger.info(list.toString());
    }

    @Test
    public void idTest() {
        //userBizService.test();
        test();
    }

    @Transactional
    public void test(){
        User user = new User();
        userService.insert(user);
        throw new RuntimeException("aaaa");
    }

}