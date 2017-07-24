import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.party.common.paging.Page;
import com.party.common.redis.StringJedis;
import com.party.common.utils.BigDecimalUtils;
import com.party.core.dao.read.article.ArticleReadDao;
import com.party.core.dao.read.city.AreaReadDao;
import com.party.core.dao.read.store.StoreGoodsReadDao;
import com.party.core.dao.write.dynamic.DynamicWriteDao;
import com.party.core.dao.write.goods.GoodsCouponsWriteDao;
import com.party.core.exception.BusinessException;
import com.party.core.model.activity.Activity;
import com.party.core.model.city.Area;
import com.party.core.model.distributor.DistributorCount;
import com.party.core.model.distributor.DistributorDetail;
import com.party.core.model.dynamic.Dynamic;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsCoupons;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.order.OrderForm;
import com.party.core.model.order.PaymentWay;
import com.party.core.model.store.StoreCount;
import com.party.core.model.store.StoreGoods;
import com.party.core.model.store.TimeType;
import com.party.core.model.wechat.WechatAccount;
import com.party.core.service.activity.IActivityService;
import com.party.core.service.crowdfund.IProjectService;
import com.party.core.service.distributor.IDistributorCountService;
import com.party.core.service.distributor.IDistributorDetailService;
import com.party.core.service.goods.impl.GoodsCouponsService;
import com.party.core.service.goods.impl.GoodsService;
import com.party.core.service.order.impl.OrderFormService;
import com.party.core.service.store.IStoreCountService;
import com.party.core.service.store.IStoreGoodsService;
import com.party.core.service.wechat.IWechatAccountService;
import com.party.mobile.biz.activity.MemberActBizService;
import com.party.mobile.biz.crowdfund.CrowdfundActivityBizService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.biz.order.OrderBizService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.math.BigDecimal;


/**
 * party
 * Created by wei.li
 * on 2016/10/8 0008.
 */
public class OrderTest extends BaseTest {

    @Autowired
    OrderBizService orderBizService;

    @Autowired
    OrderFormService orderFormService;

    @Autowired
    GoodsCouponsService goodsCouponsService;

    @Autowired
    GoodsCouponsWriteDao goodsCouponsWriteDao;

    @Autowired
    GoodsService goodsService;

    @Autowired
    DynamicWriteDao dynamicWriteDao;

    @Autowired
    MemberActBizService memberActBizService;

    @Autowired
    AreaReadDao areaReadDao;

    @Autowired
    StringJedis stringJedis;

    @Autowired
    IStoreGoodsService storeGoodsService;

    @Autowired
    IStoreCountService storeCountService;

    @Autowired
    IProjectService projectService;

    @Autowired
    IActivityService activityService;

    @Autowired
    CrowdfundActivityBizService crowdfundActivityBizService;

    @Autowired
    DistributorBizService distributorBizService;

    @Autowired
    IDistributorCountService distributorCountService;

    @Autowired
    IWechatAccountService wechatAccountService;


    @Test
    public void test(){
        double percent = BigDecimalUtils.div(0.01f, 0.04f, 2);
        int intPercent = BigDecimalUtils.amplify2int(percent, 1);
        System.out.println(intPercent);
    }

    @Test
    public void get() throws IOException {
        WechatAccount wechatAccount = wechatAccountService.getSystem();
        System.out.println(wechatAccount);
    }


    public OrderForm createOrder(){
        return orderFormService.get("9fb385f0d0ec43d38ec47da847f8bfc5");
    }


    @Test
    public void test2(){
        float a = 0.04f + 0.01f;
        float b = BigDecimalUtils.add(0.03f, 0.02f);
        boolean result = projectService.isSuccess(BigDecimalUtils.round(0.05f, 2), BigDecimalUtils.round(b, 2));
        System.out.println(result);
    }

    @Test
    public void testVerify(){
        Activity activity = activityService.get("8dbc7a0519f947e2afc4d6923fec0769");
        BusinessException businessException = crowdfundActivityBizService.verifyApply(activity);
        System.out.println(businessException);
    }
}
