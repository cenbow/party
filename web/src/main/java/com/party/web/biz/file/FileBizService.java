package com.party.web.biz.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.party.core.model.file.FileEntity;
import com.party.core.service.file.IFileEntityService;
import com.party.common.utils.QRCodeUtil;

@Service
public class FileBizService {

	@Value("#{party['mic.url']}")
	private String micUrl;

	@Value("#{party['userfiles.basedir']}")
	private String baseDir;

	@Autowired
	private IFileEntityService fileEntityService;

	public FileEntity getByOptionId(String optionId) {
		return fileEntityService.getByOptionId(optionId);
	}

	/**
	 * 插入数据库
	 * 
	 * @param file
	 * @param path
	 * @param targetId
	 */
	public void insertFileEntity(File file, String path, String targetId) {
		FileEntity fileEntity = new FileEntity();
		fileEntity.setExtension(".jpg");
		fileEntity.setFileName(file.getName());
		fileEntity.setMimetype("image/jpeg");
		fileEntity.setPath(path);
		fileEntity.setSize(file.length());
		fileEntity.setOptionId(targetId);
		fileEntityService.insert(fileEntity);
	}

	/**
	 * 生成二维码
	 * 
	 * @param content
	 *            内容
	 * @param path
	 *            路径
	 * @param fileName
	 *            文件名
	 * @param flag 
	 * @return
	 */
	public File saveFile(String content, String path, String fileName, String imgPath, boolean flag) {
		try {
			String basePath = baseDir + path;
			File dir = new File(basePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String realPath = basePath + fileName;
			if (flag) {
				content = micUrl + content;
			}
			File file = new File(realPath);
			if (!file.exists()) {
				file.createNewFile();
				
				OutputStream output = new FileOutputStream(realPath);
				
				String logoPath = null;
				if (StringUtils.isNotEmpty(imgPath)) {
					logoPath = download(imgPath, realPath);
					QRCodeUtil.encode(content, logoPath, output, true);
				} else {
					QRCodeUtil.encode(content, output);
				}
				
				output.close();
			}
			return file;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String download(String imgPath, String realPath) {
		try {
			URL imgUrl = new URL(imgPath);
			HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream is = connection.getInputStream();
			byte[] data = readInputStream(is);
			
			String newPath = realPath.substring(0, realPath.lastIndexOf(".") - 1);
			newPath = newPath + "-1" + realPath.substring(realPath.lastIndexOf("."), realPath.length());
			File file = new File(newPath);
			if (!file.exists()) {
				file.createNewFile();
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(data);
				outputStream.close();
			}
			return newPath;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] readInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		while ((len = is.read(data)) != -1) {
			outStream.write(data, 0, len);
		}
		is.close();
		return outStream.toByteArray();
	}
	
	public String getFileEntity(String targetId, String path, String content) {
		String fileName = targetId + ".jpg";

		saveFile(content, path, fileName, null, true);
		
		return path + fileName;
	}
	
	public String getFileEntity(String targetId, String path, String content, boolean flag) {
		String fileName = targetId + ".jpg";

		saveFile(content, path, fileName, null, flag);
		
		return path + fileName;
	}

	public FileEntity getFileEntity(String targetId, String path, String content, String imgPath) {
		String fileName = targetId + ".jpg";

		File file = saveFile(content, path, fileName, imgPath, true);

		FileEntity fileEntity = getByOptionId(targetId);
		if (fileEntity == null) {
			insertFileEntity(file, path + fileName, targetId);

			fileEntity = getByOptionId(targetId);
		}
		return fileEntity;
	}
}
