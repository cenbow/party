package com.party.mobile.web.controller.baiduMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.party.common.utils.HttpUtils;
import com.party.mobile.web.dto.AjaxResult;

@Controller
@RequestMapping(value = "/map/map")
public class MapController {
	// 百度key
	String ak = "rov0CrEf1g7FMNIsZKtwZD9jAs31BqGz";
	// 腾讯key
	String txKey = "UAJBZ-WYVC3-FDW3T-3EEQS-IOAM6-RJBHT";

	/**
	 * 如果是微信，则需要将通过微信获取的坐标转换成百度的坐标
	 * 
	 * @param x
	 *            经度
	 * @param y
	 *            维度
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/convertLocation")
	public AjaxResult convertLocation(float x, float y) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String requestUrl = "http://api.map.baidu.com/geoconv/v1/?coords=" + x + "," + y + "&from=1&to=5&ak=" + ak;
			String result = HttpUtils.httpGet(requestUrl, "UTF-8", "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(result);
			ajaxResult.setData(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}

	/**
	 * 百度坐标转腾讯
	 * 
	 * @param lat
	 *            纬度
	 * @param lng
	 *            经度
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bdConvertTxLocation")
	public AjaxResult bdConvertTxLocation(float lat, float lng) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String locations = lat + "," + lng;
			/**
			 * 1 GPS坐标 2 sogou经纬度 3 baidu经纬度 4 mapbar经纬度 5 [默认]腾讯、google、高德坐标 6
			 * sogou墨卡托
			 */
			String type = "3";
			String requestUrl = "http://apis.map.qq.com/ws/coord/v1/translate?locations=" + locations + "&type=" + type + "&key=" + txKey;
			String result = HttpUtils.httpGet(requestUrl, "UTF-8", "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(result);
			ajaxResult.setData(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}

	/**
	 * 通过坐标位置逆解析获取地址信息
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAddress")
	public AjaxResult getAddress(float x, float y) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String location = y + "," + x;
			String requestUrl = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak + "&callback=renderReverse&location=" + location
					+ "&output=json&pois=0";
			String result = HttpUtils.httpGet(requestUrl, "UTF-8", "UTF-8");
			if (StringUtils.isNotEmpty(result)) {
				result = result.replace("renderReverse&&renderReverse(", "").replace(")", "");
			}
			JSONObject jsonObject = JSONObject.parseObject(result);
			ajaxResult.setData(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}

	/**
	 * 如果不是微信，则通过ip去获取位置信息
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLocation")
	public AjaxResult getLocation() {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String requestUrl = "http://api.map.baidu.com/location/ip?ak=" + ak + "&coor=bd09ll";
			String result = HttpUtils.httpGet(requestUrl, "UTF-8", "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(result);
			ajaxResult.setData(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}
}
