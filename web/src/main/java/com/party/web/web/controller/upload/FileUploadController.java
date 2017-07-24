package com.party.web.web.controller.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/fileupload")
public class FileUploadController {
	
	@Value("#{party['userfiles.basedir']}")
	private String baseDir;

	@RequestMapping("uploadCIImage")
	public ModelAndView uploadCIImage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("upload/uploadCIImage");
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			if (name.indexOf('.') != -1) {
				name = name.replaceAll("\\.", "_");
			}
			mv.addObject(name, values.length > 1 ? values : values[0]);
		}
		return mv;
	}
	
	/**
	 * 下载二维码
	 * 
	 * @param filePath
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("download")
	public String download(String filePath, HttpServletResponse response) {
		try {
			filePath = baseDir + filePath;
			File file = new File(filePath);
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[1024];
			int len = 0;
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			OutputStream outputStream = response.getOutputStream();
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
