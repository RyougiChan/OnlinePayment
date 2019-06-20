package com.ryougichan.payment.entity;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WeChatPayConfig extends WXPayConfig {

    /**
     * Mutual authentication certificate saved path
     */
    private String certPath = "";
    /**
     * Merchant APP ID
     */
    private String appID = "";

    /**
     * Merchant ID
     */
    private String mchID = "";

    /**
     * Merchant private key
     */
    private String key = "";

    /**
     * Http connection time out limit in milliseconds
     */
    private int httpConnectTimeoutMs = 8000;

    /**
     * Http read time out limit in milliseconds
     */
    private int httpReadTimeoutMs = 10000;

    /**
     * Merchant sever notify address for asynchronous callback
     */
    private String notifyURL = "https://xxx.com/pay/wxpay/notify";

    /**
     * Merchant sever address for success refund dispatch asynchronous callback
     */
    private String refundNotifyURL = "https://xxx.com/pay/wxpay/refundnotify";

    /**
     * WeChat Pay payment way, <code>TRADE_TYPE_NATIVE</code>: QR Code Payment
     */
    public static final String TRADE_TYPE_NATIVE = "NATIVE";

    /**
     * WeChat Pay payment way, <code>PRODUCT_CODE_H5</code>: In-App Web-based Payment(H5 Payment)
     */
    public static final String TRADE_TYPE_MWEB = "MWEB";

    private byte[] certData;
    public WeChatPayConfig() throws Exception {
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return appID;
    }

    public String getMchID() {
        return mchID;
    }

    public String getKey() {
        return key;
    }

    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public String getRefundNotifyURL() {
        return refundNotifyURL;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {

            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
}
