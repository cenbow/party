package com.party.core.service.verifyCode.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.common.utils.UUIDUtils;
import com.party.core.dao.read.verifyCode.VerifyCodeReadDao;
import com.party.core.dao.write.verifyCode.VerifyCodeWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.verifyCode.VerifyCode;
import com.party.core.service.verifyCode.IVerifyCodeService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 手机验证码服务实现
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */

@Service
public class VerifyCodeService implements IVerifyCodeService{

    @Autowired
    VerifyCodeReadDao verifyCodeReadDao;

    @Autowired
    VerifyCodeWriteDao verifyCodeWriteDao;

    /**
     * 手机验证码插入
     * @param verifyCode 手机验证码
     * @return 插入结果（true/false）
     */
    @Override
    public String insert(VerifyCode verifyCode) {
        BaseModel.preInsert(verifyCode);
        boolean result = verifyCodeWriteDao.insert(verifyCode);
        if (result){
            return verifyCode.getId();
        }
        return null;
    }

    /**
     * 手机验证码更新
     * @param verifyCode 手机1验证码
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(VerifyCode verifyCode) {
        verifyCode.setUpdateDate(new Date());
        return verifyCodeWriteDao.update(verifyCode);
    }

    /**
     * 手机验证码逻辑删除
     * @param id 实体主键
     * @return 删除结果(true/false)
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return verifyCodeWriteDao.deleteLogic(id);
    }

    /**
     * 手机验证码物理删除
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return verifyCodeWriteDao.delete(id);
    }


    /**
     * 手机验证码批量逻辑删除
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return verifyCodeWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 手机验证码批量物理删除
     * @param ids 主键集合
     * @return 删除结果(true/false)
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        return verifyCodeWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取手机验证码
     * @param id 主键
     * @return 手机验证码
     */
    @Override
    public VerifyCode get(String id) {
        return verifyCodeReadDao.get(id);
    }

    /**
     * 分页查询手机验证码
     * @param verifyCode 手机验证码
     * @param page 分页信息
     * @return 手机验证码列表
     */
    @Override
    public List<VerifyCode> listPage(VerifyCode verifyCode, Page page) {
        return verifyCodeReadDao.listPage(verifyCode, page);
    }

    /**
     * 查询所有手机验证码
     * @param verifyCode 手机验证码
     * @return 手机验证码列表
     */
    @Override
    public List<VerifyCode> list(VerifyCode verifyCode) {
        return verifyCodeReadDao.listPage(verifyCode, null);
    }

    /**
     * 手机验证码批量查询
     * @param ids 主键集合
     * @param verifyCode 手机验证码
     * @param page 分页信息
     * @return 手机验证码列表
     */
    @Override
    public List<VerifyCode> batchList(@NotNull Set<String> ids, VerifyCode verifyCode, Page page) {
        if (CollectionUtils.isEmpty(ids)){
            return Collections.EMPTY_LIST;
        }
        return verifyCodeReadDao.batchList(ids, new HashedMap(), page);
    }
}
