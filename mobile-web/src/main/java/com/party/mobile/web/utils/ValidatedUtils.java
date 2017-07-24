package com.party.mobile.web.utils;

import com.party.common.utils.PartyCode;
import com.party.mobile.web.dto.AjaxResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by wei.li
 *
 * @date 2017/1/3 0003
 * @time 17:49
 */
public class ValidatedUtils {

    /**
     * 前端参数验证
     * @param ajaxResult 交互数据
     * @param result 验证参数
     * @return 交互数据
     */
    public static AjaxResult validated(AjaxResult ajaxResult, BindingResult result ){
        List<ObjectError> allErrors = result.getAllErrors();
        String description = allErrors.get(0).getDefaultMessage();
        ajaxResult.setErrorCode(PartyCode.PARAMETER_ILLEGAL);
        ajaxResult.setDescription(description);
        return ajaxResult;
    }
}
