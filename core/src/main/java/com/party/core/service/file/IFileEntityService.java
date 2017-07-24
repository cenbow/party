package com.party.core.service.file;

import com.party.core.model.file.FileEntity;
import com.party.core.service.IBaseService;

public interface IFileEntityService extends IBaseService<FileEntity> {

	FileEntity getByOptionId(String optionId);

}
