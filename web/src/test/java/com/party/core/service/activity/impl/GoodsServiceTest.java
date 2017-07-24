package com.party.core.service.activity.impl;

import com.party.basetest.BaseTest;
import com.party.common.paging.Page;
import com.party.core.model.BaseModel;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsDetail;
import com.party.core.service.goods.IGoodsDetailService;
import com.party.core.service.goods.IGoodsService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * ActivityServiceTest
 *
 * @author Wesley
 * @data 16/9/6 16:03 .
 */

public class GoodsServiceTest extends BaseTest{
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsDetailService goodsDetailService;

    Logger logger = Logger.getLogger(GoodsServiceTest.class);

    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void insert() throws Exception {


    }

    @Test
    public void update() throws Exception {
        String id = "c5c8c97068854de8b4da6a567c394a07";
        Goods goods = goodsService.get(id);

        goods.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        goods.setJoinNum(101);

        boolean bResult = goodsService.update(goods);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void deleteLogic() throws Exception {
        String id = "282485b09f1d4f6d8ed3cc0e0ad68133";
        boolean bResult = goodsService.deleteLogic(id);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void delete() throws Exception {
        String id = "282485b09f1d4f6d8ed3cc0e0ad68133";
        boolean bResult = goodsService.delete(id);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void batchDeleteLogic() throws Exception {
        Set<String> ids = new HashSet<String>(Arrays.asList("c5c8c97068854de8b4da6a567c394a07", "4c0f629763594c3da08b529d36aaf880", "4a7d5ef181ed430f86b0b795f1461b71"));
        boolean bResult = goodsService.batchDeleteLogic(ids);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void batchDelete() throws Exception {
        Set<String> ids = new HashSet<String>(Arrays.asList("a3e8800cd91a45afa0f1179bd1f598fd", "a41a0e072e544fb186c144eaf97042f4", "5db82cb0d158438a9c1e4201939361f9"));
        boolean bResult = goodsService.batchDelete(ids);

        logger.info("bResult = " + bResult);
    }

    @Test
    public void get() throws Exception {
        String id = "2eb2e1d58ee640bf88b61acb61ad12cd";
        Goods goods = goodsService.get(id);
        GoodsDetail goodsDetail = goodsDetailService.getByRefId(id);
        logger.info(goods.toString());
        logger.info(goodsDetail.toString());
    }

    @Test
    public void listPage() throws Exception {

        List<Goods> list = goodsService.listPage(new Goods(), new Page(1,5));
        logger.info(list.toString());
    }

    @Test
    public void list() throws Exception {
        List<Goods> list = goodsService.list(new Goods());
        logger.info(list.toString());
    }

    @Test
    public void batchList() throws Exception {
        Set<String> ids = new HashSet<String>(Arrays.asList("13b707373ffd4180b5e1aa0ac113deab", "1487bd95d12c4e9b8b54050b4cf86273"));

        Goods  goods = new Goods();
        goods.setTitle("马");
        List<Goods> list = goodsService.batchList(ids, goods, new Page(1,10));
        logger.info(list.toString());
    }

}