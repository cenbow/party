package com.party.core.dao.read.file;

import org.springframework.stereotype.Repository;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.file.FileEntity;

@Repository
public interface FileEntityReadDao extends BaseReadDao<FileEntity> {

	FileEntity getByOptionId(String optionId);

}
