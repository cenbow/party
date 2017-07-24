package com.party.core.dao.write.file;

import org.springframework.stereotype.Repository;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.file.FileEntity;

/**
 * 文件数据写入
 * 
 * @author Administrator
 *
 */
@Repository
public interface FileEntityWriteDao extends BaseWriteDao<FileEntity> {
}
