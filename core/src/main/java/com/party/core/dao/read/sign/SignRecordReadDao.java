package com.party.core.dao.read.sign;

import com.party.common.paging.Page;
import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.sign.GroupAuthor;
import com.party.core.model.sign.SignRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 签到记录数据读取
 * Created by wei.li
 *
 * @date 2017/6/5 0005
 * @time 15:53
 */

@Repository
public interface SignRecordReadDao extends BaseReadDao<SignRecord> {

    /**
     * 获取步数
     * @param param 参数
     * @return 步数
     */
    Long getStepNum(@Param("param")Map<String , Object> param);

    /**
     * 获取某天的签到
     * @param applyId 报名编号
     * @param date 时间
     * @return 某天的签到
     */
    SignRecord getByDate(@Param(value = "applyId") String applyId,@Param(value = "date") String date);


    /**
     * 获取打卡记录
     * @param param 参数
     * @return 记录数
     */
    Integer getCount(@Param("param")Map<String , Object> param);



}
