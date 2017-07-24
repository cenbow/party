package com.party.mobile.web.controller.contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.party.authorization.annotation.Authorization;
import com.party.core.exception.BusinessException;
import com.party.core.model.contact.Contact;
import com.party.core.service.contact.impl.ContactService;
import com.party.mobile.biz.currentUser.CurrentUserBizService;
import com.party.mobile.web.dto.AjaxResult;
import com.party.mobile.web.dto.login.output.CurrentUser;
import com.party.common.utils.PartyCode;

/**
 * 通讯录
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/contact")
public class ContactController {

	@Autowired
	CurrentUserBizService currentUserBizService;
	@Autowired
	ContactService contactService;

	@Authorization
	@ResponseBody
	@RequestMapping(value = "/addContacts")
	public AjaxResult memberInfo(String contacts, HttpServletRequest request) {
		try {
			CurrentUser curuser = currentUserBizService.getCurrentUser(request);
			if (null == curuser) {
				return AjaxResult.error(PartyCode.CURRENT_USER_DATA_ERROR,
						"用户未登录");
			} else {
				List<Contact> contactsList = JSONObject.parseArray(contacts
						+ "", Contact.class);
				for (Contact contact : contactsList) {
					contact.setMemberId(curuser.getId());
					String phonesStr = contact.getPhones();
					List<Map> phonesList = JSONArray.parseArray(phonesStr,
							Map.class);
					String retPhones = "";
					for (Map phones : phonesList) {
						Set<String> keys = phones.keySet();
						for (String key : keys) {
							try {
								String phone = getPhoneMessage((String) phones.get(key));
								retPhones += phone + " ";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					contact.setPhones(retPhones);
					Contact searchContact = new Contact();
					searchContact.setMemberId(curuser.getId());
					searchContact.setFullName(contact.getFullName());

					List<Contact> searchList = contactService
							.list(searchContact);
					if (null == searchList || searchList.size() == 0) {
						contactService.insert(contact);
					} else {
						for (Contact dbContact : searchList) {
							String id = dbContact.getId();
							BeanUtils.copyProperties(contact, dbContact);
							dbContact.setId(id);
							contactService.update(dbContact);
						}
					}
				}
				return AjaxResult.success();
			}
		} catch (BusinessException be) {
			return AjaxResult.error(be.getCode(), be.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/fixContact")
	public void fixContact( HttpServletRequest request) {
		Contact scontact = new Contact();
		scontact.setPhones("[{");
		List<Contact> contacts = contactService.list(scontact);
		for(Contact contact:contacts){
			String phonesStr = contact.getPhones();
			List<Map> phonesList = JSONArray.parseArray(phonesStr,
					Map.class);
			String retPhones = "";
			for (Map phones : phonesList) {
				Set<String> keys = phones.keySet();
				for (String key : keys) {
					try {
						String phone = getPhoneMessage((String) phones.get(key));
						retPhones += phone + " ";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
//			System.out.println(retPhones);
			contact.setPhones(retPhones);
			contactService.update(contact);
		}
		
	}
	
	/**
	 * 淘宝手机归属地查询api
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	private String getPhoneMessage(String phone) throws Exception, IOException {
		// 手机归属地查询api
		// 淘宝，返回json
		// https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13430878244

		// 拍拍，返回json
		// http://virtual.paipai.com/extinfo/GetMobileProductInfo?mobile=13430878244&amount=10000&callname=getPhoneNumInfoExtCallback

		// 百度钱包，返回json的unicode
		// https://www.baifubao.com/callback?cmd=1059&callback=phone&phone=13430878244
		phone = phone.replaceAll(" ", "").replaceAll("-", "");
		if(phone.indexOf("+") != -1 && phone.length() >= 11){
			phone = phone.substring(phone.length()-11);
		}
		URI uri = new URIBuilder().setScheme("https").setHost("tcc.taobao.com")
				.setPath("/cc/json/mobile_tel_segment.htm")
				.setParameter("tel", phone).build();
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse closeableHttpResponse = closeableHttpClient
				.execute(httpGet);
		HttpEntity httpEntity = closeableHttpResponse.getEntity();
		InputStream inputStream = httpEntity.getContent();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream, "GBK"));
		StringBuffer stringBuffer = new StringBuffer();
		String text = null;
		while ((text = bufferedReader.readLine()) != null) {
			stringBuffer.append(text);
		}
		inputStream.close();
		closeableHttpResponse.close();
		String jsonString = stringBuffer.toString().split("=")[1].trim();// 处理=号前的非json字符串
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);// 设置可用单引号
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);// 设置字段可以不用双引号包括
		JsonNode root = objectMapper.readTree(jsonString);
		String place = "";
		if (!Strings.isNullOrEmpty(root.path("carrier").asText())) {
			place = "(" + root.path("carrier").asText() + ")";
		}
		return phone + place;
	}
}
