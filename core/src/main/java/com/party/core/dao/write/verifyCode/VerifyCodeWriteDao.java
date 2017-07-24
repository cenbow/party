package com.party.core.dao.write.verifyCode;

import com.party.core.dao.write.BaseWriteDao;
import com.party.core.model.verifyCode.VerifyCode;
import org.springframework.stereotype.Repository;

/**
 * 手机验证码数据写入
 * party
 * Created by wei.li
 * on 2016/9/14 0014.
 */
@Repository
public interface VerifyCodeWriteDao extends BaseWriteDao<VerifyCode>{
}
