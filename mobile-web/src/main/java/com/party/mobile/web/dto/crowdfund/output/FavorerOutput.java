package com.party.mobile.web.dto.crowdfund.output;

import com.party.core.model.crowdfund.SupportWithMember;
import org.springframework.beans.BeanUtils;

/**
 * 支持者输出视图
 * Created by wei.li
 *
 * @date 2017/2/22 0022
 * @time 11:05
 */
public class FavorerOutput {

    //支持者编号
    private String id;

    //支持者图像
    private String favorerLogo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavorerLogo() {
        return favorerLogo;
    }

    public void setFavorerLogo(String favorerLogo) {
        this.favorerLogo = favorerLogo;
    }

    /**
     * 转换视图
     * @param supportWithMember 支持信息
     * @return 支持者
     */
    public static  FavorerOutput transform(SupportWithMember supportWithMember){
        FavorerOutput favorerOutput = new FavorerOutput();
        BeanUtils.copyProperties(supportWithMember, favorerOutput);
        return favorerOutput;
    }
}
