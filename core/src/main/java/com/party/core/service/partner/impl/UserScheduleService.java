package com.party.core.service.partner.impl;

import com.google.common.base.Strings;
import com.party.common.paging.Page;
import com.party.core.dao.read.partner.UserScheduleReadDao;
import com.party.core.dao.write.partner.UserScheduleWriteDao;
import com.party.core.model.BaseModel;
import com.party.core.model.partner.UserSchedule;
import com.party.core.service.partner.IUserScheduleService;
import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户服务接口实现
 * party
 * Created by wei.li
 * on 2016/10/21 0021.
 */
@Service
public class UserScheduleService implements IUserScheduleService {

    @Autowired
    UserScheduleReadDao userScheduleReadDao;

    @Autowired
    UserScheduleWriteDao userScheduleWriteDao;

    /**
     * 用户附加信息插入
     * @param userSchedule 用户附加信息
     * @return 主键
     */
    @Override
    public String insert(UserSchedule userSchedule) {
        BaseModel.preInsert(userSchedule);
        boolean result = userScheduleWriteDao.insert(userSchedule);
        return result ? userSchedule.getId() : null;
    }

    /**
     * 更新用户附加信息
     * @param userSchedule 用户附加信息
     * @return 更新结果（true/false）
     */
    @Override
    public boolean update(UserSchedule userSchedule) {
        return userScheduleWriteDao.update(userSchedule);
    }

    /**
     * 逻辑删除用户附加信息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean deleteLogic(@NotNull String id) {
       if (Strings.isNullOrEmpty(id)){
           return false;
       }
       return userScheduleWriteDao.deleteLogic(id);
    }

    /**
     * 物理删除用户附加信息
     * @param id 实体主键
     * @return 删除结果（true/false）
     */
    @Override
    public boolean delete(@NotNull String id) {
        if (Strings.isNullOrEmpty(id)){
            return false;
        }
        return userScheduleWriteDao.delete(id);
    }

    /**
     * 批量逻辑删除用户附加信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDeleteLogic(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userScheduleWriteDao.batchDeleteLogic(ids);
    }

    /**
     * 批量物理删除用户附加信息
     * @param ids 主键集合
     * @return 删除结果（true/false）
     */
    @Override
    public boolean batchDelete(@NotNull Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            return false;
        }
        return userScheduleWriteDao.batchDelete(ids);
    }

    /**
     * 根据主键获取用户附加信息
     * @param id 主键
     * @return 用户附加信息
     */
    @Override
    public UserSchedule get(String id) {
        return userScheduleReadDao.get(id);
    }

    /**
     * 根据用户编号查询附加信息
     * @param userId 用户编号
     * @return 用户附加信息
     */
    @Override
    public UserSchedule findByUserId(String userId) {
        if (!Strings.isNullOrEmpty(userId)){
            UserSchedule userSchedule = new UserSchedule();
            userSchedule.setUserId(userId);
            List<UserSchedule> userScheduleList = this.list(userSchedule);
            if (!CollectionUtils.isEmpty(userScheduleList)){
                return userScheduleList.get(0);
            }
        }
        return null;
    }

    /**
     * 分页查询用户附加信息
     * @param userSchedule 用户附加信息
     * @param page 分页信息
     * @return 附加信息列表
     */
    @Override
    public List<UserSchedule> listPage(UserSchedule userSchedule, Page page) {
        return userScheduleReadDao.listPage(userSchedule, page);
    }

    /**
     * 查询所有用户附加信息
     * @param userSchedule 用户附加信息
     * @return 附加信息列表
     */
    @Override
    public List<UserSchedule> list(UserSchedule userSchedule) {
        return userScheduleReadDao.listPage(userSchedule, null);
    }

    /**
     * 批量查询用户附加信息
     * @param ids 主键集合
     * @param userSchedule 用户附加信息
     * @param page 分页信息
     * @return 附加信息列表
     */
    @Override
    public List<UserSchedule> batchList(@NotNull Set<String> ids, UserSchedule userSchedule, Page page) {
        return userScheduleReadDao.batchList(ids, new HashedMap(), page);
    }
}
