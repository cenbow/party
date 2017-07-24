package com.party.core.service.picCloud;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.base.Strings;
import com.party.common.qcloud.picapi.UploadResult;
import com.party.common.utils.ImgUtil;
import com.party.core.dao.write.qcloud.PicCloudWriteDao;
import com.party.core.model.BaseModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.party.common.qcloud.picapi.PicCloud;
import com.party.core.dao.read.qcloud.PicCloudReadDao;
import com.party.core.model.member.Member;
import com.party.core.model.qcloud.PicCloudSign;

/**
 * @author 作者 E-mail: Juliana
 * @version 创建时间：2017年1月4日 下午5:58:44 万象优图
 */
@Service
public class PicCloudBizService {
    @Autowired
    private PicCloudReadDao picCloudReadDao;

    @Autowired
    private PicCloudWriteDao picCloudWriteDao;

    private static int APP_ID = 10052192;
    private static String SECRET_ID = "";
    private static String SECRET_KEY = "";
    private static String BUCKET = "";
    private static Integer EXPIRED = 7200;
    private static PicCloud pc = null;

    @PostConstruct
    public void initPic() {
        APP_ID = 10052192;
        SECRET_ID = "AKID0zLb467fRifqb0rsyo5tVJKZqKiebe5B";
        SECRET_KEY = "UcfDJpkyGi90IDw0YsnxxUcLkWopbrC6";
        BUCKET = "txzapp"; // 空间名
        if (pc == null) {
            pc = new PicCloud(APP_ID, SECRET_ID, SECRET_KEY, BUCKET);
        }
        System.out.println("初始化万象优图 ：" + APP_ID + " " + SECRET_ID + " "
                + SECRET_KEY + " " + BUCKET + " ");
    }

    /**
     * fileId命名规则
     *
     * @return
     */
    private String getFileId() {
        return new Date().getTime() + 1 + "";
    }

    /**
     * 上传图片
     *
     * @param inputStream
     * @return
     */
    public UploadResult upload(InputStream inputStream) {
        return pc.upload(inputStream, getFileId());
    }

    /**
     * 上传图片
     *
     * @param remoteUrl 远程路径
     * @return
     */
    public UploadResult upload(String remoteUrl) {
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(ImgUtil.getImageFromNetByUrl(remoteUrl));
            UploadResult upload = pc.upload(input, getFileId());
            if (null != upload) {
                return upload;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 获取万象优图签名
     *
     * @param type      类型
     * @param fileid    文件编号
     * @param userId    用户编号
     * @param curUserId 当前用户编号
     * @return 签名map
     */
    public Map<String, Object> getPicCloudSign(String type, String fileid, String userId, String curUserId) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        String sign = "";
        String url = "";
        boolean flag = true;

        if (fileid == null)
            fileid = "";
        if (userId == null)
            userId = curUserId;

        try {
            if (type != null || !"".equals(type)) {
                if ("upload".equals(type)) {
                    // 换成数据库读取
                    PicCloudSign picCloudSign = picCloudReadDao.getCloudSign();
                    if (null == picCloudSign) {
                        picCloudSign = refreshPicCloudSign();
                    }
                    sign = picCloudSign.getSign();
                } else if ("copy".equals(type) || "del".equals(type) || "download".equals(type)) {
                    sign = pc.getSignOnce(fileid);
                }
                url = pc.getUrl(userId, fileid);
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }

        retMap.put("scuss", flag);
        retMap.put("ver", "V2");
        retMap.put("sign", sign);
        retMap.put("url", url);
        return retMap;
    }

    /**
     * 刷新万象优图签名
     *
     * @return 万象优图签名
     */
    public PicCloudSign refreshPicCloudSign() {
        pc = new PicCloud(APP_ID, SECRET_ID, SECRET_KEY, BUCKET);
        Long expiredTime = System.currentTimeMillis() / 1000 + EXPIRED;
        String sign = "";
        sign = pc.getSign(expiredTime);
        PicCloudSign picCloudSign = picCloudReadDao.getCloudSign();
        if (null == picCloudSign) {
            picCloudSign = new PicCloudSign();
        }
        picCloudSign.setSign(sign);
        if (Strings.isNullOrEmpty(picCloudSign.getId())) {
            BaseModel.preInsert(picCloudSign);
            picCloudWriteDao.insert(picCloudSign);
        } else {
            picCloudSign.setUpdateDate(new Date());
            picCloudWriteDao.update(picCloudSign);
        }
        return picCloudSign;
    }

    /**
     * Delete 删除单张图片
     *
     * @param url 图片的路径
     * @return 错误码，0为成功
     */
    public int delByUrl(String url) {
        url = url.trim();
        String temp[] = url.split("\\/");
        String fileId = temp[temp.length - 1];
        return pc.delete(fileId);
    }

}
