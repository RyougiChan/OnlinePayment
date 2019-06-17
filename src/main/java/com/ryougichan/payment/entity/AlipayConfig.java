package com.ryougichan.payment.entity;

public class AlipayConfig {
    /**
     * Merchant APP ID
     */
    private String appID = "";
    /**
     * Merchant private key format in pkcs8
     */
    private String privateKey = "";
    /**
     * Alipay public key(Be careful, not merchant RSA_PUBLIC_KEY)
     */
    private String alipayPublicKey = "";
    /**
     * Merchant sever notify address for asynchronous callback
     */
    private String notifyUrl = "https://xxx.com/pay/alipay/notify";
    /**
     * Merchant sever return address for success payment dispatch
     */
    private String returnUrl = "https://xxx.com/pay/alipay/return";
    /**
     * Alipay gateway address
     */
    private String URL = "https://openapi.alipay.com/gateway.do";
    /**
     * Charset for transferred data
     */
    private String charset = "UTF-8";
    /**
     * Response data format
     */
    private String format = "json";
    /**
     * Sign type for transferred data
     */
    private String signType = "RSA2";
    /**
     * Alipay payment way, `FAST_INSTANT_TRADE_PAY`: QR Code Payment
     */
    public static final String PRODUCT_CODE_PAGE = "FAST_INSTANT_TRADE_PAY";
    /**
     * Alipay payment way, `PRODUCT_CODE_H5`: In-App Web-based Payment(H5 Payment)
     */
    public static final String PRODUCT_CODE_H5 = "QUICK_WAP_WAY";

    public String getAppID() {
        return appID;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getURL() {
        return URL;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
