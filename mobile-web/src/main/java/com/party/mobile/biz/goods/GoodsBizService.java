package com.party.mobile.biz.goods;

import com.party.common.constant.Constant;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.AuditStatus;
import com.party.core.model.BaseModel;
import com.party.core.model.YesNoStatus;
import com.party.core.model.city.City;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.DistributorTargetAttache;
import com.party.core.model.goods.Category;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsDetail;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.member.Member;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.service.city.ICityService;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.distributor.IDistributorTargetAttacheService;
import com.party.core.service.goods.ICategoryService;
import com.party.core.service.goods.IGoodsDetailService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.thirdParty.IThirdPartyService;
import com.party.core.service.user.IUserService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.biz.member.CreateByBizService;
import com.party.mobile.web.dto.activity.ActProgressStatus;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.goods.CustomizeOutput;
import com.party.mobile.web.dto.goods.GoodsOutput;
import com.party.mobile.web.dto.goods.input.ListInput;
import com.party.mobile.web.dto.goods.output.CustomizeListOutput;
import com.party.mobile.web.dto.goods.output.ListOutput;
import com.party.mobile.web.dto.goods.output.ThirdPartyOutput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 商品业务逻辑接口
 * party
 * Created by wei.li
 * on 2016/9/27 0027.
 */

@Service
public class GoodsBizService {

    @Autowired
    ICityService cityService;

    @Autowired
    IUserService userService;

    @Autowired
    IMemberService memberService;

    @Autowired
    CreateByBizService createByBizService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    IGoodsDetailService goodsDetailService;

    @Autowired
    IThirdPartyService thirdPartyService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    DistributorBizService distributorBizService;

    @Autowired
    IDistributorRelationService distributorRelationService;

    @Autowired
    IDistributorTargetAttacheService distributorTargetAttacheService;
    /**
     * 根据商品获取标准商品输出视图
     * @param goods 商品
     * @return 标准商品输出视图
     */
    public GoodsOutput get(Goods goods, HttpServletRequest request){

        GoodsOutput goodsOutput = GoodsOutput.transform(goods);
        City city = cityService.get(goods.getCityId());
        goodsOutput.setCityName(city.getName());

        //获取创建者信息
        goodsOutput = createByBizService.getCreateBy(goodsOutput, goods.getCreateBy());

        //回去第三方公司
        ThirdParty thirdParty = thirdPartyService.get(goods.getThirdPartyId());
        ThirdPartyOutput thirdPartyOutput = ThirdPartyOutput.transform(thirdParty);
        goodsOutput.setThirdParty(thirdPartyOutput);

        //获取商品详情
        GoodsDetail goodsDetail = goodsDetailService.getByRefId(goods.getId());
        if (null != goodsDetail && null != goodsDetail.getContent()) {
            goodsDetail.setContent(StringUtils.stringtohtml(goodsDetail.getContent()));
        }
        if (null != goods && null != goods.getRecommend()) {
            goodsOutput.setRecommend(StringUtils.stringtohtml(goods.getRecommend()));
        }
        if (null != goods && null != goods.getNotice()) {
            goodsOutput.setNotice(StringUtils.stringtohtml(goods.getNotice()));
        }
        
        goodsOutput.setContent(goodsDetail.getContent());

        //是否登陆
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser){
            Member member = memberService.get(currentUser.getId());
            //如果不是自己的商品
            if (!currentUser.getId().equals(goods.getMemberId())){
                goodsOutput.setIsDistributor(member.getIsDistributor());
            }
            else {
                goodsOutput.setIsDistributor(YesNoStatus.NO.getCode());
            }
        }
        else
        {
            goodsOutput.setIsDistributor(YesNoStatus.NO.getCode());//默认没有开分销权限
        }

        //设置商品状态，是否进行中
        goodsOutput.setIsInProgress(setIsInProgress(goodsOutput.getEndTime()));

        //设置分享链接
        goodsOutput.setShareLink(setShareLink(goods));

        return goodsOutput;
    }

    /**
     * 获取分销详情
     * @param goods 商品
     * @param request 请求参数
     * @param input 输入视图
     * @return 输出视图
     */
    public GoodsOutput getDistribution(Goods goods, HttpServletRequest request, GetDistributorInput input){

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        GoodsOutput goodsOutput = this.get(goods, request);
        //如果是活动发布者
        if (input.getParentId().equals(Constant.BUILDER_PARENT)){
            Member member = memberService.get(goods.getMemberId());
            goodsOutput.setDistributorTime(goods.getCreateDate());
            goodsOutput.setDistributorName(member.getRealname());
            goodsOutput.setDistributorLogo(member.getLogo());
            goodsOutput.setDeclaration(Constant.BUILDER_DECLARATION);

            if (null != currentUser){
                //如果不是发布者
                if (!currentUser.getId().equals(goods.getMemberId())){
                    goodsOutput.setIsDistributor(Constant.ALLOW_DISTRIBUTION);
                }
                else {
                    goodsOutput.setMyDistribution(true);
                    goodsOutput.setIsDistributor(Constant.BAN_DISTRIBUTION);
                }
            }
            else {
                goodsOutput.setIsDistributor(Constant.ALLOW_DISTRIBUTION);
            }
        }
        else {
            if (null != currentUser){
                boolean isMyDistribution
                        = distributorBizService.isMyDistribution(currentUser, goods.getType(), goods.getId(), input.getDistributorId(), input.getParentId());
                goodsOutput.setMyDistribution(isMyDistribution);

            }
            DistributorRelation distributorRelation
                    = distributorRelationService.get(input.getType(), input.getTargetId(),input.getDistributorId());

            if (null == distributorRelation){
                throw new BusinessException("分销关系不存在");
            }
            goodsOutput.setIsDistributor(Constant.BAN_DISTRIBUTION);
            //分销者
            Member distributor = memberService.get(distributorRelation.getDistributorId());
            goodsOutput.setDistributorName(distributor.getRealname());
            goodsOutput.setDistributorLogo(distributor.getLogo());
            goodsOutput.setDistributorTime(distributorRelation.getCreateDate());

            //分销附属信息
            DistributorTargetAttache distributorTargetAttache
                    = distributorTargetAttacheService.findByRelationId(distributorRelation.getId());
            if (null != distributorTargetAttache){
                goodsOutput.setStyle(distributorTargetAttache.getStyle());
                goodsOutput.setDeclaration(distributorTargetAttache.getContent());
            }
        }
        return goodsOutput;
    }

    /**
     * 分页查询标准商品列表
     * @param listInput 查询参数
     * @param page 分页参数
     * @return 商品输出视图列表
     */
    public List<ListOutput> list(ListInput listInput, Page page){
        Goods goods = new Goods();
        goods.setCityId(listInput.getCityId());
        goods.setType(listInput.getType());
        goods.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        goods.setCheckStatus(AuditStatus.PASS.getCode());//审核通过
        List<Goods> goodsList = goodsService.listPage(goods, page);

        if (!CollectionUtils.isEmpty(goodsList)){
            List<ListOutput> listOutputList = LangUtils.transform(goodsList, input -> {
                ListOutput listOutput = ListOutput.transform(input);
                City city = cityService.get(input.getCityId());
                if (null != city){
                    listOutput.setCityName(city.getName());
                }

                //设置商品的分类名
                Category category = categoryService.get(input.getCategoryId());
                if (null != category)
                {
                    listOutput.setCategory(category.getName());
                }

                //设置商品状态，是否进行中
                listOutput.setIsInProgress(setIsInProgress(input.getEndTime()));
                //设置分享链接
                listOutput.setShareLink(setShareLink(input));
                return listOutput;
            });
            return listOutputList;
        }
        return Collections.EMPTY_LIST;
    }


    /**
     * 根据商品获取定制商品输出视图
     * @param goods 商品
     * @return 定制商品输出视图
     */
    public CustomizeOutput getCustomized(Goods goods, HttpServletRequest request){

        CustomizeOutput customizeOutput = CustomizeOutput.transform(goods);
        City city = cityService.get(goods.getCityId());
        customizeOutput.setCityName(city.getName());

        //获取创建者信息
        customizeOutput = createByBizService.getCreateBy(customizeOutput, goods.getCreateBy());

        //回去第三方公司
        ThirdParty thirdParty = thirdPartyService.get(goods.getThirdPartyId());
        ThirdPartyOutput thirdPartyOutput = ThirdPartyOutput.transform(thirdParty);
        customizeOutput.setThirdParty(thirdPartyOutput);

        //获取商品详情
        GoodsDetail goodsDetail = goodsDetailService.getByRefId(goods.getId());
        //转为正常html
        if (null != goodsDetail && null != goodsDetail.getContent()) {
            goodsDetail.setContent(StringUtils.stringtohtml(goodsDetail.getContent()));
        }
        customizeOutput.setContent(goodsDetail.getContent());

        //是否登陆
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        if (null != currentUser){
            Member member = memberService.get(currentUser.getId());
            customizeOutput.setIsDistributor(member.getIsDistributor());
        }
        else
        {
            customizeOutput.setIsDistributor(YesNoStatus.NO.getCode());//默认没有开分销权限
        }

        //设置商品状态，是否进行中
        customizeOutput.setIsInProgress(setIsInProgress(customizeOutput.getEndTime()));

        //设置分享链接
        customizeOutput.setShareLink(setShareLink(goods));


        return customizeOutput;
    }

    /**
     * 分页查询定制商品列表
     * @param listInput 查询参数
     * @param page 分页参数
     * @return 商品输出视图列表
     */
    public List<CustomizeListOutput> customizeList(ListInput listInput, Page page){
        Goods goods = new Goods();
        goods.setCityId(listInput.getCityId());
        goods.setType(listInput.getType());
        goods.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
        goods.setCheckStatus(AuditStatus.PASS.getCode());//审核通过
        List<Goods> goodsList = goodsService.listPage(goods, page);

        if (!CollectionUtils.isEmpty(goodsList)){
            List<CustomizeListOutput> listOutputList = LangUtils.transform(goodsList, input -> {
                CustomizeListOutput listOutput = CustomizeListOutput.transform(input);
                City city = cityService.get(input.getCityId());
                if (null != city){
                    listOutput.setCityName(city.getName());
                }


                //设置商品的分类名
                Category category = categoryService.get(input.getCategoryId());
                if (null != category)
                {
                    listOutput.setCategory(category.getName());
                }

                //设置商品状态，是否进行中
                listOutput.setIsInProgress(setIsInProgress(input.getEndTime()));


                //设置分享链接
                listOutput.setShareLink(setShareLink(input));

                return listOutput;
            });
            return listOutputList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 设置商品状态，是否进行中
     * @param endtime
     * @return
     */
    public Integer setIsInProgress(Date endtime)
    {
        //设置商品状态，是否进行中
        if (endtime.getTime() > new Date().getTime())
        {
            return ActProgressStatus.PROGRESS_STATUS_AGOING.getCode();
        }

        return ActProgressStatus.PROGRESS_STATUS_OVERDUE.getCode();
    }

    /**
     * 设置商品分享链接
     * @param goods 商品id
     * @return
     */
    public String setShareLink(Goods goods)
    {	
    	if(goods.getType() == 0){
    		return "http://3g.tongxingzhe.cn/micWeb/html/order/custom_made_detail.html?id=" + goods.getId();
    	}else if(goods.getType() == 1){
    		return "http://3g.tongxingzhe.cn/micWeb/html/order/order_dz_detail.html?id=" + goods.getId();
    	}else{
    		return "";
    	}
    }
}
