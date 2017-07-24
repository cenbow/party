package com.party.mobile.biz.article;

import com.party.common.constant.Constant;
import com.party.core.exception.BusinessException;
import com.party.core.model.distributor.DistributorRelation;
import com.party.core.model.distributor.DistributorTargetAttache;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.member.Member;
import com.party.core.service.distributor.IDistributorRelationService;
import com.party.core.service.distributor.IDistributorTargetAttacheService;
import com.party.core.service.member.IMemberService;
import com.party.mobile.biz.distributor.DistributorBizService;
import com.party.mobile.web.dto.article.output.ArticleOutput;
import com.party.mobile.web.dto.distributor.input.GetDistributorInput;
import com.party.mobile.web.dto.login.output.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章业务接口
 * Created by wei.li
 *
 * @date 2017/3/22 0022
 * @time 15:17
 */
@Service
public class ArticleBizService {

    @Autowired
    private DistributorBizService distributorBizService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IDistributorRelationService distributorRelationService;

    @Autowired
    private IDistributorTargetAttacheService distributorTargetAttacheService;

    /**
     * 获取分销详情
     * @param output 输出视图
     * @param input 输入视图
     * @param currentUser 当前用户
     * @return
     */
    public ArticleOutput getDistributionDetails(ArticleOutput output, GetDistributorInput input, CurrentUser currentUser){

        //如果是活动发布者
        if (input.getParentId().equals(Constant.BUILDER_PARENT)){
            Member member = memberService.get(output.getMember());
            output.setDistributorName(member.getRealname());
            output.setDistributorLogo(member.getLogo());
            output.setDeclaration(Constant.BUILDER_DECLARATION);

            if (null != currentUser){
                //如果不是自己发布的
                if (!currentUser.getId().equals(output.getMember())){
                    output.setIsDistributor(Constant.ALLOW_DISTRIBUTION);
                }
                else {
                    output.setMyDistribution(true);
                    output.setIsDistributor(Constant.BAN_DISTRIBUTION);
                }
            }
            else {
                output.setIsDistributor(Constant.ALLOW_DISTRIBUTION);
            }
        }
        else {
            DistributorRelation distributorRelation
                    = distributorRelationService.get(input.getType(), input.getTargetId(),input.getDistributorId());

            if (null == distributorRelation){
                throw new BusinessException("分销关系不存在");
            }

            if (null != currentUser){
                //是否是我的分销
                boolean isMyDistribution
                        =  distributorBizService.isMyDistribution(currentUser, GoodsType.GOODS_ARTICLE.getCode(),
                        input.getTargetId(), input.getDistributorId(), input.getParentId());
                output.setMyDistribution(isMyDistribution);
            }
            output.setIsDistributor(Constant.BAN_DISTRIBUTION);

            //分销者
            Member distributor = memberService.get(distributorRelation.getDistributorId());
            output.setDistributorName(distributor.getRealname());
            output.setDistributorLogo(distributor.getLogo());
            output.setDistributorTime(distributorRelation.getCreateDate());

            //分销附属信息
            DistributorTargetAttache distributorTargetAttache
                    = distributorTargetAttacheService.findByRelationId(distributorRelation.getId());
            if (null != distributorTargetAttache){
                output.setStyle(distributorTargetAttache.getStyle());
                output.setDeclaration(distributorTargetAttache.getContent());
            }
        }

        return output;
    }
}
