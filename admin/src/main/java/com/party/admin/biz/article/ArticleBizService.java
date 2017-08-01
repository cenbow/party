package com.party.admin.biz.article;

import com.party.admin.biz.file.FileBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文章业务接口
 * Created by wei.li
 *
 * @date 2017/3/22 0022
 * @time 19:44
 */
@Service
public class ArticleBizService {

    @Autowired
    private FileBizService fileBizService;

    /**
     * 拼接分销连接
     * @param id 活动编号
     * @param memberId 创建者
     * @return 分销连接
     */
    public String getDistributionUrl(String id, String memberId){
        StringBuilder stringBuilder = new StringBuilder("article/article_distribution_detail.html?");
        stringBuilder.append("id=").append(id)
                .append("&parentId=").append("0")
                .append("&distributorId=").append(memberId);
        return stringBuilder.toString();
    }

    /**
     * 获取二维码连接
     * @param id 活动编号
     * @param memberId 创建者
     * @return 二维码连接
     */
    public String getQrCode(String id, String memberId){
        String url = this.getDistributionUrl(id, memberId);
        String path = memberId + "/distribution/";
        String qrCodeUrl = fileBizService.getFileEntity(id, path, url);
        return qrCodeUrl;
    }
}
