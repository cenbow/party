package com.party.admin.biz.goods;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.party.admin.biz.file.FileBizService;
import com.party.admin.utils.MyBeanUtils;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.input.common.CommonInput;
import com.party.admin.web.dto.output.goods.GoodsOutput;
import com.party.common.paging.Page;
import com.party.common.utils.LangUtils;
import com.party.common.utils.StringUtils;
import com.party.core.model.BaseModel;
import com.party.core.model.city.City;
import com.party.core.model.goods.Category;
import com.party.core.model.goods.Goods;
import com.party.core.model.goods.GoodsDetail;
import com.party.core.model.goods.GoodsType;
import com.party.core.model.order.OrderStatus;
import com.party.core.model.order.OrderType;
import com.party.core.model.thirdParty.ThirdParty;
import com.party.core.model.user.User;
import com.party.core.service.city.ICityService;
import com.party.core.service.goods.ICategoryService;
import com.party.core.service.goods.IGoodsDetailService;
import com.party.core.service.goods.IGoodsService;
import com.party.core.service.order.IOrderFormService;
import com.party.core.service.thirdParty.IThirdPartyService;
import com.party.core.service.user.IUserService;

/**
 * 商品业务接口 Created by wei.li
 *
 * @date 2017/3/22 0022
 * @time 19:29
 */
@Service
public class GoodsBizService {

	@Autowired
	private FileBizService fileBizService;

	@Autowired
	private ICityService cityService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IThirdPartyService thirdPartyService;

	@Autowired
	private IGoodsDetailService goodsDetailService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private IUserService userService;

	/**
	 * 拼接分销连接
	 * 
	 * @param id
	 *            活动编号
	 * @param memberId
	 *            创建者
	 * @return 分销连接
	 */
	public String getDistributionUrl(String id, String memberId, String url) {
		StringBuilder stringBuilder = new StringBuilder(url);
		stringBuilder.append("id=").append(id).append("&parentId=").append("0").append("&distributorId=").append(memberId);
		return stringBuilder.toString();
	}

	/**
	 * 获取二维码连接
	 * 
	 * @param id
	 *            活动编号
	 * @param memberId
	 *            创建者
	 * @return 二维码连接
	 */
	public String getQrCode(String id, String memberId, Integer type) {
		String url = "";
		if (GoodsType.GOODS_NOMAL.getCode().equals(type)) {
			url = this.getDistributionUrl(id, memberId, "order/custom_made_distribution_detail.html?");
		} else if (GoodsType.GOODS_CUSTOMIZED.getCode().equals(type)) {
			url = this.getDistributionUrl(id, memberId, "order/order_dz_distribution_detail.html?");
		}
		String path = RealmUtils.getCurrentUser().getId() + "/distribution/";
		String qrCodeUrl = fileBizService.getFileEntity(id, path, url);
		return qrCodeUrl;
	}

	/**
	 * 新增/编辑
	 * 
	 * @param id
	 * @param mv
	 */
	public void goodsForm(String id, ModelAndView mv) {
		City city = new City();
		city.setIsOpen(1);
		mv.addObject("citys", cityService.list(city));

		Category t = new Category();
		t.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Category> categories = categoryService.list(t);
		mv.addObject("categories", categories);

		if (StringUtils.isNotEmpty(id)) {
			Goods goods = goodsService.get(id);
			if (StringUtils.isNotEmpty(goods.getNotice())) {
				goods.setNotice(com.party.common.utils.StringUtils.stringtohtml(goods.getNotice()));
			}
			if (StringUtils.isNotEmpty(goods.getRecommend())) {
				goods.setRecommend(com.party.common.utils.StringUtils.stringtohtml(goods.getRecommend()));
			}
			GoodsOutput goodsOutput = GoodsOutput.transform(goods);
			if (StringUtils.isNotEmpty(goods.getThirdPartyId())) {
				ThirdParty thirdParty = thirdPartyService.get(goods.getThirdPartyId());
				goodsOutput.setThirdPartyName(thirdParty.getComName());
			}
			GoodsDetail goodsDetail = goodsDetailService.getByRefId(id);
			if (StringUtils.isNotEmpty(goodsDetail.getContent())) {
				goodsDetail.setContent(com.party.common.utils.StringUtils.stringtohtml(goodsDetail.getContent()));
			}
			mv.addObject("goodsDetail", goodsDetail);
			mv.addObject("goods", goodsOutput);
			
			mv.addObject("cityName", cityService.get(goods.getCityId()).getName());
		}
		
		mv.addObject("isGoods", true);
	}

	/**
	 * 标准/定制列表查询
	 * 
	 * @param goods
	 * @param page
	 * @param timeType
	 * @param c_start
	 * @param c_end
	 * @param memberName
	 * @param mv
	 * @param path
	 * @param content
	 * @param startDate
	 * @param endDate
	 */
	public void commonList(Goods goods, Page page, CommonInput commonInput, ModelAndView mv, String path, String content, String startDate,
			String endDate) {
		Map<String, Object> params = CommonInput.appendParams(commonInput);
		mv.addObject("input", commonInput);
		params.put("startTime", startDate);
		params.put("endTime", endDate);

		goods.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Goods> activities = goodsService.webListPage(goods, params, page);
		Set<Integer> status = new HashSet<Integer>();
		status.add(OrderStatus.ORDER_STATUS_OTHER.getCode()); // 其他
		List<GoodsOutput> goodsOutputs = LangUtils.transform(activities, input -> {
			GoodsOutput output = GoodsOutput.transform(input);
			// 购买二维码
				String content1 = content + input.getId();
				String fileEntity = fileBizService.getFileEntity(input.getId(), path, content1);
				output.setQrCodeUrl(fileEntity);

				// 分销二维码
				String disQr = getQrCode(input.getId(), input.getMemberId(), input.getType());
				output.setDisQrCode(disQr);
				int joinNum = orderFormService.calculateBuyNum(input.getId(), null, false, OrderType.ORDER_NOMAL.getCode(), status);
				output.setJoinNum(joinNum);
				return output;

			});

		mv.addObject("page", page);
		mv.addObject("goodsOutputs", goodsOutputs);

		Category t = new Category();
		t.setDelFlag(BaseModel.DEL_FLAG_NORMAL);
		List<Category> categories = categoryService.list(t);
		mv.addObject("categories", categories);
	}

	public void saveGoodsBiz(Goods goods, GoodsDetail goodsDetail, HttpServletRequest request) throws Exception {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (StringUtils.isNotEmpty(startDate)) {
			goods.setStartTime(sdf.parse(startDate));
		}
		if (StringUtils.isNotEmpty(endDate)) {
			goods.setEndTime(sdf.parse(endDate));
		}

		/****************** html编码 *****************************************************/
		// 推荐理由
		String recommend = "";
		if (StringUtils.isNotEmpty(goods.getRecommend().trim())) {
			recommend = StringEscapeUtils.escapeHtml4(goods.getRecommend().trim());
			goods.setRecommend(recommend);
		}

		// 注意事项
		String notice = "";
		if (StringUtils.isNotEmpty(goods.getNotice().trim())) {
			notice = StringEscapeUtils.escapeHtml4(goods.getNotice().trim());
			goods.setNotice(notice);
		}

		// 商品内容
		String content = "";
		if (StringUtils.isNotEmpty(goodsDetail.getContent().trim())) {
			content = StringEscapeUtils.escapeHtml4(goodsDetail.getContent().trim());
			goodsDetail.setContent(content);
		}

		User user = userService.findByLoginName("admin");
		goods.setCreateBy(user.getId());
		goods.setUpdateBy(user.getId());
		goods.setMemberId(RealmUtils.getCurrentUser().getId()); // 当前用户

		/****************** html编码 *****************************************************/

		if (StringUtils.isEmpty(goods.getId())) {
			goods.setCheckStatus("0");
			String goodsId = goodsService.insert(goods);
			// 新增
			if (StringUtils.isNotEmpty(goodsDetail.getContent())) {
				goodsDetail.setContent(goodsDetail.getContent());
				goodsDetail.setRefId(goodsId);
				goodsDetailService.insert(goodsDetail);
			}
		} else {
			// 编辑
			Goods t = goodsService.get(goods.getId());// 从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(goods, t);// 将编辑表单中的非NULL值覆盖数据库记录中的值

			if (StringUtils.isNotEmpty(goodsDetail.getContent())) {
				GoodsDetail ad = goodsDetailService.getByRefId(t.getId());
				if (ad == null) {
					ad = new GoodsDetail();
					ad.setContent(goodsDetail.getContent());
					ad.setRefId(t.getId());
					goodsDetailService.insert(ad);
				} else {
					ad.setContent(goodsDetail.getContent());
					goodsDetailService.update(ad);
				}
			}
			goodsService.update(t);
		}
	}
}
