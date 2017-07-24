package com.party.mobile.biz.verifyCode;

import com.party.common.redis.StringJedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证码业务接口
 * Created by wei.li
 *
 * @date 2017/2/25 0025
 * @time 10:47
 */

@Service
public class VerifyCodeBizService {

    @Autowired
    private StringJedis stringJedis;

    /**
     * 验证码验证
     * @param key 验证的key值
     * @param code 待验证码
     * @return 验证结果（true/false）
     */
    public boolean verify(String key, String code){
        //验证验证码
        String verifyCode = stringJedis.getValue(key);
        if (null == verifyCode || !code.equals(verifyCode)) {
            return false;
        }
        return true;
    }
}
