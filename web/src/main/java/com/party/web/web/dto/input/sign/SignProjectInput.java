package com.party.web.web.dto.input.sign;

import com.party.common.utils.DateUtils;
import com.party.core.model.sign.SignProject;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;

/**
 * Created by wei.li
 *
 * @date 2017/6/6 0006
 * @time 16:41
 */
public class SignProjectInput extends SignProject{

    private String startStrTime;

    private String endStrTime;


    public String getStartStrTime() {
        return startStrTime;
    }

    public void setStartStrTime(String startStrTime) {
        this.startStrTime = startStrTime;
    }

    public String getEndStrTime() {
        return endStrTime;
    }

    public void setEndStrTime(String endStrTime) {
        this.endStrTime = endStrTime;
    }

    public static SignProject transform(SignProjectInput signProjectInput){
        SignProject signProject = new SignProject();
        BeanUtils.copyProperties(signProjectInput, signProject);
        try {
            signProject.setStartTime(DateUtils.parse(signProjectInput.getStartStrTime(), "yyyy-MM-dd HH:mm"));
            signProject.setEndTime(DateUtils.parse(signProjectInput.getEndStrTime(), "yyyy-MM-dd HH:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return signProject;
    }
}
