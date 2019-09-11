package com.ryougichan.payment.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.Gson;
import com.ryougichan.payment.entity.AlipayConfig;
import com.ryougichan.payment.entity.WeChatPayConfig;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class PayUtil {

    public static Gson gson;
    public static AlipayConfig alipayConfig;
    public static WeChatPayConfig weChatPayConfig;


    /**
     * Get real IP address of access client
     *
     * @param request A javax.servlet.http.HttpServletRequest object
     * @return Return real IP address of access client
     */
    public static String getRealIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // multi-reverse proxy
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * Access
     *
     * @param request A javax.servlet.http.HttpServletRequest object
     * @return
     * @throws Exception
     */
    public static Map<String, String> getWeChatPayNotifyData(HttpServletRequest request) throws Exception {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readLen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readLen == -1) {
                break;
            }
            i += readLen;
        }

        String charEncoding = request.getCharacterEncoding();
        String notifyData;
        Map<String, String> notifyMap;
        notifyData = new String(buffer, null == charEncoding ? "UTF-8" : charEncoding);
        notifyMap = WXPayUtil.xmlToMap(notifyData);
        return notifyMap;
    }

    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";
    /**
     * AES decryption
     *
     * @param base64Data Encrypted data
     * @param key        WechatPay secret key
     * @return
     * @throws Exception
     */
    public static String aesDecryptData(String base64Data, String key) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(WXPayUtil.MD5(key).toLowerCase().getBytes(), "AES"));
        return new String(cipher.doFinal(base64Decode8859(base64Data).getBytes(StandardCharsets.ISO_8859_1)), StandardCharsets.UTF_8);
    }

    /**
     * Base64 decoder
     *
     * @param source Base64 string characters
     * @return Base64 decoded result
     */
    public static String base64Decode8859(final String source) {
        String result;
        final Base64.Decoder decoder = Base64.getDecoder();
        result = new String(decoder.decode(source), StandardCharsets.ISO_8859_1);
        return result;
    }

    /**
     * Initial and return the Google Gson instance
     * @return Return the initialized Google Gson instance
     */
    public static Gson getGson() {
        if(null == gson) gson = new Gson();
        return gson;
    }

    /**
     * Initial the AlipayConfig instance
     * @return Return the initialized AlipayConfig instance
     */
    public static AlipayConfig getAlipayConfig() {
        if(null == alipayConfig) {
            alipayConfig = new AlipayConfig();
        }
        return alipayConfig;
    }

    /**
     * Initial a new AlipayClient object
     * @return Return a new initialized AlipayClient instance
     */
    public static AlipayClient getAlipayClient() {
        getAlipayConfig();
        AlipayClient client = new DefaultAlipayClient(alipayConfig.getURL(), alipayConfig.getAppID(), alipayConfig.getPrivateKey(), alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(),alipayConfig.getSignType());
        return client;
    }

    /**
     * Initial the WeChatPayConfig instance
     * @return Return the initialized WeChatPayConfig instance
     */
    public static WeChatPayConfig getWeChatPayConfig() throws Exception {
        if(null == weChatPayConfig) {
            weChatPayConfig = new WeChatPayConfig();
        }
        return weChatPayConfig;
    }

    /**
     * Initial a new WXPay object
     * @return Return a new initialized WXPay instance
     */
    public static WXPay getWXPay() throws Exception {
        getWeChatPayConfig();
        WXPay wxpay = new WXPay(weChatPayConfig);
        return  wxpay;
    }

    /**
     * Convert java.util.Date object to specific format
     * @param pattern The specific format
     * @param date The java.util.Date object
     * @return Return the formatted String
     */
    public static String dateToStringFormat(String pattern, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
