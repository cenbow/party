package com.party.admin.web.controller.qrcode;

import com.party.admin.biz.file.FileBizService;
import com.party.admin.biz.wechat.WechatBizService;
import com.party.admin.utils.RealmUtils;
import com.party.admin.web.dto.AjaxResult;
import com.party.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 二维码
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/qrcode/qrcode")
public class QrCodeController {

    @Autowired
    FileBizService fileBizService;
    @Autowired
    private WechatBizService wechatBizService;

    @Value("#{party['url.maxLength']}")
    private int maxLength;

    @RequestMapping("toForm")
    public ModelAndView toForm() {
        ModelAndView mv = new ModelAndView("qrcode/qrcodeForm");
        return mv;
    }

    /**
     * 生成二维码
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping("create")
    public String save(String content) {
        try {
            if ((content.indexOf("http://") != -1 || content.indexOf("https://") != -1) && content.length() >= maxLength) {
                String shortUrl = wechatBizService.longToShort(content);
                if (StringUtils.isNotEmpty(shortUrl)) {
                    content = shortUrl;
                }
            }

            String path = "/" + RealmUtils.getCurrentUser().getId() + "/qrcode/";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return fileBizService.getFileEntity(sdf.format(new Date()), path, content, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换短链接
     *
     * @param longUrl
     * @return
     */
    @ResponseBody
    @RequestMapping("convertLink")
    public AjaxResult convertLink(String longUrl) {
        if (StringUtils.isEmpty(longUrl)) {
            return AjaxResult.error("内容不能为空");
        }
        try {
            String shortUrl = wechatBizService.longToShort(longUrl);
            if (StringUtils.isEmpty(shortUrl)) {
                return AjaxResult.error("转换失败");
            } else {
                return AjaxResult.success((Object) shortUrl);
            }
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
