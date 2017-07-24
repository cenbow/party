package com.party.core.dao.read.verifyCode;

import com.party.core.dao.read.BaseReadDao;
import com.party.core.model.verifyCode.VerifyCode;
import org.springframework.stereotype.Repository;

/**
 * 手机验证码数据读取
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
@Repository
public interface VerifyCodeReadDao extends BaseReadDao<VerifyCode> {
}
