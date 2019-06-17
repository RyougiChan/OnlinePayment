package com.ryougichan.payment.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.Gson;
import com.ryougichan.payment.entity.WeChatPayConfig;
import com.ryougichan.payment.service.IOnlinePay;
import com.ryougichan.payment.util.PayUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeChatPay implements IOnlinePay {

    WeChatPayConfig config;

    /**
     * Dispatch payment request to WeChat Pay server
     *
     * @param payWay    Way of payment, including `QR Code Payment`, `In-App Web-based Payment(H5 Payment)` and etc.
     * @param orderId   Order number generated by server
     * @param payAmount Amount Paid, unit in cent
     * @return Return whatever the WeChat Pay server return
     */
    @Override
    public String pay(String payWay, String orderId, Double payAmount) {
        try {
            // Initial WeChat Pay configuration
            this.initWeChatPayConfig();
            WXPay wxpay = new WXPay(config);
            // Payment order name
            String body = "Order Name";
            // Payment amount
            String totalFee = String.format("%d", (int) (payAmount * 100));
            // Random string
            String nonceStr = WXPayUtil.generateNonceStr();
            // Merchant server notify address for asynchronous callback
            String notifyUrl = config.getNotifyURL();
            // Payment way, `NATIVE` or `MWEB`
            String tradeType = null == payWay || payWay.trim().equals("") ? WeChatPayConfig.TRADE_TYPE_NATIVE : payWay;
            // Merchant server gateway
            String websiteAddr = "";
            // The URL that page return once pay successfully
            String redirectURL = URLEncoder.encode(String.format("https://%s", websiteAddr), "UTF-8");

            // Get client real IP address
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String clientIP = PayUtil.getRealIpAddress(request);

            Map<String, String> data = new HashMap<>();
            data.put("body", body);
            data.put("out_trade_no", orderId);
            data.put("nonce_str", nonceStr);
            data.put("total_fee", totalFee);
            data.put("spbill_create_ip", clientIP);
            data.put("notify_url", notifyUrl);
            data.put("trade_type", tradeType);

            Map<String, String> resp = wxpay.unifiedOrder(data);

            // WeChat Pay QR code URL, `weixin://wxpay/bizpayurl/up?pr=NwY5Mz9&groupid=00` for example
            String codeUrl;
            // WeChat Pay redirect URL, `https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx20161110163838f231619da20804912345&package=1037687096&redirect_url=https%3A%2F%2Fwww.wechatpay.com.cn` for example
            String mwebUrl;
            // WeChat Pay status code
            String returnCode = resp.get("return_code");
            // WeChat Pay return message
            String returnMsg = resp.get("return_msg");
            // WeCHat Pay prepay ID
            String prepayId = "";

            if(tradeType.equals(WeChatPayConfig.TRADE_TYPE_MWEB)) {
                /////////////// In-App Web-based Payment(H5 Payment) ///////////////
                if (returnCode.equals("SUCCESS"))
                {
                    String resultCode = resp.get("result_code");
                    if (resultCode.equals("SUCCESS"))
                    {
                        prepayId = resp.get("prepay_id");
                        mwebUrl = resp.get("mweb_url");
                        mwebUrl = mwebUrl + String.format("&redirect_url=%s", redirectURL);

                        return(mwebUrl);
                    }
                } else {
                    // TODO: Logging
                }
            }
            else {
                /////////////// QR Code Payment ///////////////
                if (returnCode.equals("SUCCESS"))
                {
                    String resultCode = resp.get("result_code");
                    if (resultCode.equals("SUCCESS"))
                    {
                        prepayId = resp.get("prepay_id");
                        codeUrl = resp.get("code_url");

                        return(codeUrl);
                    }
                } else {
                    // TODO: Logging
                }
            }
            return("failed");
        } catch (Exception e1) {
            // TODO: Logging
            return("failed");
        }
    }

    /**
     * Receive payment callback from WeChat Pay server if supported
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell WeChat Pay server that we have received this notify callback
     * @deprecated The method defined here is for example only because there are specific business logic included in usual
     */
    @Override
    public String receivePaymentNotify(HttpServletRequest request) {
        return null;
    }

    /**
     * Dispatch refund request to WeChat Pay server
     *
     * @param orderId      Order number generated by server
     * @param tradeId      The trade number generated by WeChat Pay server
     * @param totalAmount  The total trade amount of the order to refund, unit in cent
     * @param refundAmount The amount of the refund(Partial refund supported if possible), unit in cent
     * @return Return whatever the WeChat Pay server return
     */
    @Override
    public String refund(String orderId, String tradeId, Double totalAmount, Double refundAmount) {
        try {
            this.initWeChatPayConfig();
            WXPay wxpay = new WXPay(config);
            // Refund order number generated by merchant server
            String outRefundNo = orderId + System.currentTimeMillis() + Math.random() * 100;
            String totalFee = String.format("%d", (int) (totalAmount * 100));
            String nonceStr = WXPayUtil.generateNonceStr();
            String notifyUrl = config.getRefundNotifyURL();
            String refundFee = String.format("%d", (int) (refundAmount * 100));

            Map<String, String> data = new HashMap<>();
            data.put("out_trade_no", orderId);
            data.put("nonce_str", nonceStr);
            data.put("total_fee", totalFee);
            data.put("out_refund_no", outRefundNo);
            data.put("notify_url", notifyUrl);
            data.put("refund_fee", refundFee);

            Map<String, String> resp = wxpay.refund(data);
            Gson gson = PayUtil.getGson();
            return gson.toJson(resp);
            // return JSONObject.toJSONString(resp);
        } catch (Exception e1) {
            // TODO: 日志记录点
        }
        return null;
    }

    /**
     * Receive refund callback from WeChat Pay server if supported
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell WeChat Pay server that we have received this notify callback
     * @deprecated The method defined here is for example only because there are specific business logic included in usual
     */
    @Override
    public String receiveRefundNotify(HttpServletRequest request) {
        return null;
    }

    /**
     * Dispatch transactions inquiry request to WeChat Pay server
     *
     * @param orderId Order number generated by server
     * @param tradeId The trade number generated by WeChat Pay server
     * @return Return whatever the WeChat Pay server return
     */
    @Override
    public String query(String orderId, String tradeId) {
        return null;
    }

    /**
     * Dispatch transactions close request to WeChat Pay server
     *
     * @param orderId Order number generated by server
     * @param tradeId The trade number generated by WeChat Pay server
     * @return Return whatever the WeChat Pay server return
     */
    @Override
    public String close(String orderId, String tradeId) {
        return null;
    }

    /**
     * Dispatch refund inquiry request to WeChat Pay server
     *
     * @param orderId  Order number generated by server
     * @param tradeId  The trade number generated by WeChat Pay server
     * @param refundId Order refund number generated by server when refund request dispatched
     * @return Return whatever the WeChat Pay server return
     */
    @Override
    public String refundQuery(String orderId, String tradeId, String refundId) {
        return null;
    }

    /**
     * Dispatch download bill request to access historical transaction list from WeChat Pay server
     *
     * @param billDate Bill time
     * @param billType Bill type
     * @return Return merchant bill data with text table format
     */
    @Override
    public String downloadBill(Date billDate, String billType) {
        return null;
    }

    private void initWeChatPayConfig() throws Exception {
        if(null == config) {
            config = new WeChatPayConfig();
        }
    }
}
