package com.party.admin.web.controller.map;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.party.admin.web.dto.AjaxResult;

@Controller
@RequestMapping(value = "/map/map")
public class MapController {
	String ak = "rov0CrEf1g7FMNIsZKtwZD9jAs31BqGz";

	@ResponseBody
	@RequestMapping(value = "/nearByQuery")
	public AjaxResult nearByQuery(String query, String location) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			query = URLEncoder.encode(query, "UTF-8");
			// 39.915,116.404
			String pageSize = "20";
			String pageNum = "0";
			String radius = "2000";
			String requestUrl = "http://api.map.baidu.com/place/v2/search?query=" + query + "&page_size=" + pageSize + "&page_num=" + pageNum
					+ "&scope=1&location=" + location + "&radius=" + radius + "&output=json&ak=" + ak;
			String result = sendGet(requestUrl);
			JSONObject jsonObject = JSONObject.parseObject(result);
			ajaxResult.setData(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResult;
	}

	/**
	 * 
	 * @param query
	 *            关键字
	 * @param region
	 *            区域
	 * @param pageNum
	 *            页码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search")
	public AjaxResult placeSearch(String query, String region, Integer pageNum) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			Integer pageSize = 10;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ak", ak);
			params.put("scope", 2);
			params.put("output", "json");
			params.put("page_size", pageSize);
			pageNum = pageNum == null ? 0 : pageNum - 1;
			params.put("page_num", pageNum);
			params.put("query", URLEncoder.encode(query, "UTF-8")); // 关键字
			params.put("region", URLEncoder.encode(region, "UTF-8")); // 区域
			String requestParams = returnLinkString(params);
			System.out.println(requestParams);

			String requestUrl = "http://api.map.baidu.com/place/v2/search?" + requestParams;
			String result = sendGet(requestUrl);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (jsonObject != null) {
				int total = jsonObject.get("total") != null ? (Integer)jsonObject.get("total") : 0;
				int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
				jsonObject.put("totalPage", totalPage);
				jsonObject.put("pageNum", pageNum);
				ajaxResult.setData(jsonObject);
			}
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
			String result = sendGet(requestUrl);
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

	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 防止乱码
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String returnLinkString(Map<String, Object> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				stringBuilder = stringBuilder.append(key).append("=").append(params.get(key));
			} else {
				stringBuilder = stringBuilder.append(key).append("=").append(params.get(key)).append("&");
			}
		}

		return stringBuilder.toString();
	}
}
