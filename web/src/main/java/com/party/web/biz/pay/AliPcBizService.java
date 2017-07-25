package com.party.web.biz.pay;

import com.party.common.utils.StringUtils;
import com.party.pay.model.pay.ali.pc.TradePagePayRequest;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * 支付宝Pc扫码支付业务
 */
@Service
public class AliPcBizService {

    /**
     * sha256WithRsa 算法请求类型
     */
    public static final String SIGN_TYPE_RSA = "RSA";

    public static final String SIGN_TYPE_RSA2 = "RSA2";

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    protected static Logger logger = LoggerFactory.getLogger(AliPcBizService.class);

    /**
     * rsa内容签名
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     */
    public String rsaSign(String content, String privateKey, String charset, String signType) {
        if (SIGN_TYPE_RSA.equals(signType)) {
            return rsaSign(content, privateKey, charset);
        } else if (SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256Sign(content, privateKey, charset);
        }
        return null;
    }

    /**
     * sha1WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @param inputCharset
     * @return
     */
    public String rsaSign(String content, String privateKey, String inputCharset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(inputCharset));

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            logger.error("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * sha256WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @param inputCharset
     * @return
     */
    public String rsa256Sign(String content, String privateKey, String inputCharset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(inputCharset));

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            logger.error("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * rsa内容验签
     *
     * @param content
     * @param publicKey
     * @param charset
     * @return
     */
    public boolean rsaCheck(String content, String sign, String publicKey, String charset, String signType) {
        if (SIGN_TYPE_RSA.equals(signType)) {
            return rsaCheckContent(content, sign, publicKey, charset);
        } else if (SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256CheckContent(content, sign, publicKey, charset);
        }
        return false;
    }

    /**
     * sha256WithRsa 验签
     *
     * @param content
     * @param sign
     * @param publicKey
     * @param charset
     * @return
     */
    public boolean rsa256CheckContent(String content, String sign, String publicKey,
                                      String charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(charset));

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            logger.error("验签失败", e);
        }
        return false;
    }

    /**
     * sha1WithRsa 验签
     *
     * @param content
     * @param sign
     * @param publicKey
     * @param charset
     * @return
     */
    public boolean rsaCheckContent(String content, String sign, String publicKey, String charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
            byte[] encodedKey = Base64.decodeBase64(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(charset));
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            logger.error("验签失败", e);
        }
        return false;
    }

    /**
     * 加签/验签字符串
     *
     * @param sortedParams
     * @return
     */
    public String getSignContent(Map<String, String> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = sortedParams.get(key);
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }


    /**
     * 构建表单
     *
     * @param baseUrl
     * @param parameters
     * @return
     */
    public String buildForm(String baseUrl, Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<form name=\"punchout_form\" method=\"post\" action=\"");
        sb.append(baseUrl);
        sb.append("\">\n");
        sb.append(buildHiddenFields(parameters));
        sb.append("<input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n");
        sb.append("</form>\n");
        sb.append("<script>document.forms[0].submit();</script>");
        return sb.toString();
    }

    /**
     * 表单隐藏字段
     *
     * @param parameters
     * @return
     */
    private String buildHiddenFields(Map<String, String> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Set<String> keys = parameters.keySet();
            Iterator var3 = keys.iterator();

            while (var3.hasNext()) {
                String key = (String) var3.next();
                String value = (String) parameters.get(key);
                if (key != null && value != null) {
                    sb.append(buildHiddenField(key, value));
                }
            }

            String result = sb.toString();
            return result;
        } else {
            return "";
        }
    }

    /**
     * 表单隐藏字段
     *
     * @param key
     * @param value
     * @return
     */
    private String buildHiddenField(String key, String value) {
        StringBuffer sb = new StringBuffer();
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(key);
        sb.append("\" value=\"");
        String a = value.replace("\"", "&quot;");
        sb.append(a).append("\">\n");
        return sb.toString();
    }

    /**
     * 构建请求的URL
     *
     * @param request
     * @param charset
     * @return
     */
    public String getRequestUrl(TradePagePayRequest request, String charset, String gateway) {
        StringBuffer urlSb = new StringBuffer(gateway);
        try {
            String sysMustQuery = buildQuery(request.getProtocalMustParams(), charset);
            String sysOptQuery = buildQuery(request.getProtocalOptParams(), charset);
            urlSb.append("?");
            urlSb.append(sysMustQuery);
            if (sysOptQuery != null & sysOptQuery.length() > 0) {
                urlSb.append("&");
                urlSb.append(sysOptQuery);
            }
        } catch (IOException e) {
            logger.error("拼接请求链接异常", e);
        }
        return urlSb.toString();
    }

    public String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params != null && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            boolean hasParam = false;
            Iterator var5 = entries.iterator();

            while (var5.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var5.next();
                String name = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
                    if (hasParam) {
                        query.append("&");
                    } else {
                        hasParam = true;
                    }
                    query.append(name).append("=").append(URLEncoder.encode(value, charset));
                }
            }
            return query.toString();
        } else {
            return null;
        }
    }
}
