package com.party.mobile.web.dto.login.output;

import com.google.common.base.Strings;
import com.party.core.model.member.Member;
import com.party.core.model.user.User;

/**
 * 登陆用户
 * party
 * Created by wei.li
 * on 2016/9/26 0026.
 */
public class CurrentUser {

    //主键
    private String id;

    //第三方登录id
    private String openId;

    //登陆名
    private String loginName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 会员转登陆实体
     * @param member 会员
     * @return 登陆实体
     */
    public static CurrentUser transform(Member member){
        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(member.getId());
        currentUser.setLoginName(member.getMobile());
        return currentUser;
    }

    /**
     * 用户转登陆实体
     * @param user 用户
     * @return 登陆实体
     */
    public static CurrentUser transform(User user){
        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(user.getId());
        currentUser.setLoginName(user.getLoginName());
        return currentUser;
    }

    /**
     * 是否是第三方用户
     * @param currentUser 登陆用户
     * @return 验证结果（true/false）
     */
    public static boolean isThirdparty(CurrentUser currentUser){
        if (!Strings.isNullOrEmpty(currentUser.getOpenId()) && Strings.isNullOrEmpty(currentUser.getLoginName())){
            return true;
        }
        return false;
    }
}
