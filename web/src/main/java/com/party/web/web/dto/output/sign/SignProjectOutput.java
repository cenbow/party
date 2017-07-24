package com.party.web.web.dto.output.sign;

import com.party.core.model.sign.SignProject;
import com.party.core.model.sign.SignProjectAuthor;
import org.springframework.beans.BeanUtils;

/**
 * 签到项目输出视图
 * Created by wei.li
 *
 * @date 2017/6/7 0007
 * @time 9:39
 */
public class SignProjectOutput extends SignProjectAuthor {

    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public static SignProjectOutput transform(SignProject signProject){
        SignProjectOutput signProjectOutput = new SignProjectOutput();
        BeanUtils.copyProperties(signProject, signProjectOutput);
        return signProjectOutput;
    }
}
