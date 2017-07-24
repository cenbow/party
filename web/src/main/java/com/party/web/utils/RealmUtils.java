package com.party.web.utils;

import com.party.common.utils.Digests;
import com.party.common.utils.Encodes;
import com.party.core.model.member.Member;
import com.party.core.model.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * 登陆加密工具类
 * party
 * Created by wei.li
 * on 2016/8/29 0029.
 */
public class RealmUtils {

    public static final String SALT = "tongxing";

    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;


    /**
     * 登陆加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param password 密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0,16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
    }

    /**
     * 获取session
     * @return
     */
    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){

        }
        return null;
    }


    /**
     * 获取当前登陆用户
     * @return 当前登陆用户
     */
    public static Member getCurrentUser(){
        Session session = getSession();
        if (null != session){
            return (Member) session.getAttribute("currentUser");
        }
        return null;
    }

    public static void main(String[] args) {

        String result = encryptPassword("admin8");
        System.out.println(result);
    }

}
