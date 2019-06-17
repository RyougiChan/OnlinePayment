package com.ryougichan.payment.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ryougichan.payment.entity.AlipayConfig;
import com.ryougichan.payment.service.IOnlinePay;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class Alipay implements IOnlinePay {

    /**
     * Alipay Configuration
     */
    AlipayConfig alipayConfig;

    /**
     * Dispatch payment request to Alipay server
     *
     * @param payWay    Way of payment, including `QR Code Payment`, `In-App Web-based Payment(H5 Payment)` and etc.
     * @param orderId   Order number generated by server
     * @param payAmount Amount Paid
     * @return Return whatever the Alipay server return(A HTML <form>)
     */
    @Override
    public String pay(String payWay, String orderId, Double payAmount) {
        // Initial Alipay configuration
        this.initAlipayConfig();
        //******************************** Parameters Setting Start *********************************
        // Payment amount
        String totalAmount = payAmount.toString();
        // Payment order name, required
        String subject = "Order Name";
        // Description of product, optional
        String body = "";
        // Expire time
        String timeoutExpress = "2h";
        // Product code, an Alipay identification code, default: `FAST_INSTANT_TRADE_PAY`, means QR Code Payment
        String productCode = payWay == null || payWay.trim().equals("")? AlipayConfig.PRODUCT_CODE_PAGE : payWay;
        //******************************** Parameters Setting End *********************************

        AlipayClient client = new DefaultAlipayClient(alipayConfig.getURL(), alipayConfig.getAppID(), alipayConfig.getPrivateKey(), alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(),alipayConfig.getSignType());

        String form = null;

        if(productCode.equals("QUICK_WAP_WAY")) {
            /////////// In-App Web-based Payment(H5 Payment) ///////////
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
            //******************************** Pay request parameter setting start *********************************
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(orderId);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);
            model.setBody(body);
            model.setTimeoutExpress(timeoutExpress);
            model.setProductCode(productCode);
            alipayRequest.setBizModel(model);
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
            alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
            //******************************** Pay request parameter setting end *********************************

            try {
                form = client.pageExecute(alipayRequest).getBody();
                return form;
            } catch (AlipayApiException e) {
                // TODO: Logging
                return null;
            }
        } else {
            /////////// Default: QR Code Payment ///////////
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            //******************************** Pay request parameter setting start *********************************
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            model.setOutTradeNo(orderId);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);
            model.setBody(body);
            model.setTimeoutExpress(timeoutExpress);
            model.setProductCode(productCode);
            alipayRequest.setBizModel(model);
            alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
            alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
            //******************************** Pay request parameter setting end *********************************

            AlipayTradePagePayResponse response;
            try {
                response = client.pageExecute(alipayRequest);
                if(response.isSuccess()) {
                    form = response.getBody();
                } else {
                    // TODO: Logging
                }
                return form;
            } catch (AlipayApiException e) {
                // TODO: Logging
                return null;
            }
        }
    }

    /**
     * Receive payment callback from Alipay server if supported
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell Alipay server that we have received this notify callback
     * @deprecated The method defined here is for example only because there are specific business logic included in usual
     */
    @Override
    public String receivePaymentNotify(HttpServletRequest request) {
        return null;
    }

    /**
     * Dispatch refund request to Alipay server
     *
     * @param orderId      Order number generated by server
     * @param tradeId      The trade number generated by Alipay server, it cannot be null or empty with orderId at the same time
     * @param totalAmount  The total trade amount of the order to refund
     * @param refundAmount The amount of the refund(Partial refund supported if possible)
     * @return Return whatever the Alipay server return
     */
    @Override
    public String refund(String orderId, String tradeId, Double totalAmount, Double refundAmount) {
        this.initAlipayConfig();
        // To identify a refund request, multiple refunds for the same transaction are guaranteed to be unique.
        // If a partial refund is required, this parameter will be passed.
        String outRequestNO = orderId + System.currentTimeMillis() + Math.random() * 100;

        AlipayClient client = new DefaultAlipayClient(alipayConfig.getURL(), alipayConfig.getAppID(), alipayConfig.getPrivateKey(), alipayConfig.getFormat(), alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(),alipayConfig.getSignType());
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        AlipayTradeRefundModel model=new AlipayTradeRefundModel();
        if(null != orderId && !orderId.trim().equals("")) model.setOutTradeNo(orderId);
        if(null != tradeId && !tradeId.trim().equals("")) model.setTradeNo(tradeId);
        model.setRefundAmount(refundAmount.toString());
        //model.setRefundReason(refundReason);
        model.setOutRequestNo(outRequestNO);
        alipayRequest.setBizModel(model);

        AlipayTradeRefundResponse alipayResponse;
        try {
            alipayResponse = client.execute(alipayRequest);
            if(alipayResponse.isSuccess()) {

                Map<String, String> resData = alipayResponse.getParams();
                boolean signVerified = AlipaySignature.rsaCheckV1(resData, alipayConfig.getAlipayPublicKey(),alipayConfig.getCharset(), alipayConfig.getSignType());

                if(!signVerified) {
                    // Verification failed
                    // TODO: Logging
                } else {
                    // Refund succeed
                    // TODO: Modifying order status or other relative values
                }
                return("succeed");
            }else {
                // TODO: Logging
            }
        } catch (AlipayApiException e) {
            // TODO: Logging
        }
        return("failed");
    }

    /**
     * Receive refund callback from third-party payment server if supported
     * Alipay has no support! It using a synchronous response instead
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell third-party payment server that we have received this notify callback
     * @deprecated The method defined here is for example only because there are specific business logic included in usual
     */
    @Override
    public String receiveRefundNotify(HttpServletRequest request) {
        return null;
    }

    /**
     * Dispatch transactions inquiry request to Alipay server
     *
     * @param orderId Order number generated by server
     * @param tradeId The trade number generated by Alipay server
     * @return Return whatever the Alipay server return
     */
    @Override
    public String query(String orderId, String tradeId) {
        return null;
    }

    /**
     * Dispatch transactions close request to Alipay server
     *
     * @param orderId Order number generated by server
     * @param tradeId The trade number generated by Alipay server
     * @return Return whatever the Alipay server return
     */
    @Override
    public String close(String orderId, String tradeId) {
        return null;
    }

    /**
     * Dispatch refund inquiry request to Alipay server
     *
     * @param orderId  Order number generated by server
     * @param tradeId  The trade number generated by Alipay server
     * @param refundId Order refund number generated by server when refund request dispatched
     * @return Return whatever the Alipay server return
     */
    @Override
    public String refundQuery(String orderId, String tradeId, String refundId) {
        return null;
    }

    /**
     * Dispatch download bill request to access historical transaction list from Alipay server
     *
     * @param billDate Bill time
     * @param billType Bill type
     * @return Return merchant offline bill download address
     */
    @Override
    public String downloadBill(Date billDate, String billType) {
        return null;
    }

    private void initAlipayConfig()
    {
        if(null == this.alipayConfig) {
            alipayConfig = new AlipayConfig();
        }
    }
}
