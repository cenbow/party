package com.party.mobile.web.dto.user.output;

import com.party.core.model.partner.UserSchedule;
import org.springframework.beans.BeanUtils;

/**
 * 商户预登陆输出视图
 * party
 * Created by wei.li
 * on 2016/10/24 0024.
 */
public class LoginPreOutput {

    //标题
    private String title;

    //封面图片
    private String coverPic;

    //分享图片
    private String sharePic;

    //备注
    private String remarks;

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    /**
     * 用户附加表转预登陆信息
     * @param userSchedule 附加信息
     * @return 预登陆输出视图
     */
    public static LoginPreOutput transform(UserSchedule userSchedule){
        LoginPreOutput loginPreOutput = new LoginPreOutput();
        BeanUtils.copyProperties(userSchedule, loginPreOutput);
        return loginPreOutput;
    }
}
