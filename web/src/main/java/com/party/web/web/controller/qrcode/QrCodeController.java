package com.party.web.web.controller.qrcode;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.party.core.service.file.IFileEntityService;
import com.party.web.biz.file.FileBizService;
import com.party.web.utils.RealmUtils;

/**
 * 二维码
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/qrcode/qrcode")
public class QrCodeController {
	
	@Autowired
	FileBizService fileBizService;

	@RequestMapping("toForm")
	@RequiresPermissions("qrcode:qrcode:toForm")
	public ModelAndView toForm() {
		ModelAndView mv = new ModelAndView("qrcode/qrcodeForm");
		return mv;
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping("create")
	public String save(String content) {
		try {
			try {
				content = URLDecoder.decode(content, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}

			String path = "/" + RealmUtils.getCurrentUser().getId() + "/qrcode/";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			return fileBizService.getFileEntity(sdf.format(new Date()), path, content, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
