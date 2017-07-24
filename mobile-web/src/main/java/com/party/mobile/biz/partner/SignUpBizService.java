package com.party.mobile.biz.partner;

import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.core.exception.BusinessException;
import com.party.core.model.member.Member;
import com.party.core.model.member.MemberAct;
import com.party.core.model.store.StoreGoods;
import com.party.core.service.member.IMemberActService;
import com.party.core.service.member.IMemberService;
import com.party.core.service.store.IStoreGoodsService;
import com.party.mobile.web.dto.partner.output.SignUpListOutput;
import com.party.common.utils.PartyCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 合作商活动报名服务接口
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */

@Service
public class SignUpBizService {

    @Autowired
    IStoreGoodsService storeGoodsService;

    @Autowired
    IMemberActService memberActService;

    @Autowired
    IMemberService memberService;



    /**
     * 合作商商品报名列表
     * @param userId 用户编号
     * @param page 分页参数
     * @return 报名列表
     */
    public List<SignUpListOutput> list(String userId, Page page){
        StoreGoods storeGoods = storeGoodsService.findByUserId(userId);
        if (null == storeGoods){
            throw new BusinessException(PartyCode.STORE_GOODS_UNEXIST, "商铺商品不存在");
        }

        List<MemberAct> memberActList = memberActService.listByStoreGoodsId(storeGoods.getId(), page);
        if (!CollectionUtils.isEmpty(memberActList)){
            return LangUtils.transform(memberActList, m -> {
                SignUpListOutput signUpListOutput = SignUpListOutput.transform(m);
                Member member = memberService.get(m.getMemberId());
                if (null != member){
                    signUpListOutput.setLogo(member.getLogo());
                }
                return signUpListOutput;
            });
        }
        return Collections.EMPTY_LIST;
    }
}
