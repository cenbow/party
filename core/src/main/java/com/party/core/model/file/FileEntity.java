package com.party.core.model.file;

import com.party.core.model.BaseModel;

public class FileEntity extends BaseModel {

	private static final long serialVersionUID = -4307502614090387831L;

	private String mimetype; // 文件类型

	private long size; // 文件大小

	private String fileName; // 文件名不带扩展名

	private String extension; // 扩展名

	private String path; // 文件路径

	private String optionId; // 目标id

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

}
