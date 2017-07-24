package com.party.mobile.web.controller.store;
import com.party.authorization.annotation.Authorization;
import com.party.common.paging.Page;
import com.party.core.service.store.IStoreGoodsService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.biz.member.MemberBizService;
import com.party.mobile.biz.store.StoreGoodsBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.mobile.web.dto.store.input.AddToStoreInput;
import com.party.mobile.web.dto.store.input.GetShareUrlInput;
import com.party.mobile.web.dto.store.input.ListInput;
import com.party.mobile.web.dto.store.output.CountOutput;
import com.party.mobile.web.dto.store.output.GetShareUrlOutput;
import com.party.mobile.web.dto.store.output.ListOutput;
import com.party.common.utils.PartyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 店铺商品控制层
 * party
 * Created by wei.li
 * on 2016/10/10 0010.
 */

@Controller
@RequestMapping(value = "/store/goods")
public class StoreGoodsController {

    @Autowired
    MemberBizService memberBizService;

    @Autowired
    IStoreGoodsService storeGoodsService;

    @Autowired
    StoreGoodsBizService storeGoodsBizService;

    @Autowired
    CurrentUserBizService currentUserBizService;

    /**
     * 添加商品到店铺
     * @param input 输入视图
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/addToStore")
    public AjaxResult addToStore(@Validated AddToStoreInput input, HttpServletRequest request, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        storeGoodsBizService.addToStore(input, currentUser.getId());
        return AjaxResult.success();
    }


    /**
     * 销售中商品列表
     * @param input 输入视图
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/inSalesList")
    public AjaxResult inSalesList(ListInput input, Page page, HttpServletRequest request){
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        List<ListOutput> listOutputList = storeGoodsBizService.inSalesList(input, currentUser.getId(), page);
        return AjaxResult.success(listOutputList, page);
    }

    /**
     * 已下架商品列表
     * @param input 输入视图
     * @param page 分页参数
     * @return 交互数据
     */
    @ResponseBody
    @Authorization
    @RequestMapping(value = "/soldOutList")
    public AjaxResult soldOutList(ListInput input, Page page, HttpServletRequest request){
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        List<ListOutput> listOutputList = storeGoodsBizService.soldOutList(input,currentUser.getId(), page);
        return AjaxResult.success(listOutputList, page);
    }


    /**
     * 获取分享连接
     * @param input 输入视图
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/getShareUrl")
    public AjaxResult getShareUrl(@Validated GetShareUrlInput input, BindingResult result){

        //数据验证
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            String description = allErrors.get(0).getDefaultMessage();
            return AjaxResult.error(PartyCode.PARAMETER_ILLEGAL, description);
        }

        GetShareUrlOutput getShareUrlOutput = new GetShareUrlOutput();
        String url = storeGoodsBizService.getShareUrl(input);
        getShareUrlOutput.setUrl(url);
        return AjaxResult.success(getShareUrlOutput);
    }


    /**
     * 统计
     * @return 交互数据
     */
    @ResponseBody
    @RequestMapping(value = "/count")
    public AjaxResult count(HttpServletRequest request){
        CurrentUser currentUser = currentUserBizService.getCurrentUser(request);
        Map<String, CountOutput> result = storeGoodsBizService.count(currentUser.getId());
        return AjaxResult.success(result);
    }
}
