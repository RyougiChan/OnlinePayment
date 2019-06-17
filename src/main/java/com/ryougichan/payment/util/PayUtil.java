package com.ryougichan.payment.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.github.wxpay.sdk.WXPay;
import com.google.gson.Gson;
import com.ryougichan.payment.entity.AlipayConfig;
import com.ryougichan.payment.entity.WeChatPayConfig;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

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
