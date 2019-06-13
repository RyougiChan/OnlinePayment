package com.ryougichan.payment.service.impl;

import com.ryougichan.payment.service.IOnlinePay;

import javax.servlet.http.HttpServletRequest;

public class Alipay implements IOnlinePay {

    /**
     * Dispatch payment request to third-party payment sever
     *
     * @param payWay      Way of payment, including `QR Code Payment`, `APP Payment`, `In-App Web-based Payment(H5 Payment)` and etc.
     * @param orderNumber Order number generated by sever
     * @param payAmount   Amount Paid
     * @return Return whatever the third-party payment sever return
     */
    @Override
    public String pay(String payWay, String orderNumber, Double payAmount) {
        return null;
    }

    /**
     * Receive payment callback from third-party payment sever if supported
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell third-party payment sever that we have received this notify callback
     * @deprecated The method defined here is just as example because there are specific business logic included in usual
     */
    @Override
    public String receivePaymentNotify(HttpServletRequest request) {
        return null;
    }

    /**
     * Dispatch refund request to third-party payment sever
     *
     * @param orderNumber  Order number generated by sever
     * @param tradeId      The trade number generated by third-party payment sever
     * @param totalAmount  The total trade amount of the order to refund
     * @param refundAmount The amount of the refund(Partial refund supported if possible)
     * @return Return whatever the third-party payment sever return
     */
    @Override
    public String refund(String orderNumber, String tradeId, Double totalAmount, Double refundAmount) {
        return null;
    }

    /**
     * Receive refund callback from third-party payment sever if supported
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell third-party payment sever that we have received this notify callback
     * @deprecated The method defined here is just as example because there are specific business logic included in usual
     */
    @Override
    public String receiveRefundNotify(HttpServletRequest request) {
        return null;
    }

    /**
     * Dispatch transactions inquiry request to third-party payment sever
     *
     * @param orderNumber Order number generated by sever
     * @param tradeId     The trade number generated by third-party payment sever
     * @return Return whatever the third-party payment sever return
     */
    @Override
    public String query(String orderNumber, String tradeId) {
        return null;
    }

    /**
     * Dispatch transactions close request to third-party payment sever
     *
     * @param orderNumber Order number generated by sever
     * @param tradeId     The trade number generated by third-party payment sever
     * @return Return whatever the third-party payment sever return
     */
    @Override
    public String close(String orderNumber, String tradeId) {
        return null;
    }

    /**
     * Dispatch refund inquiry request to third-party payment sever
     *
     * @param orderNumber Order number generated by sever
     * @param tradeId     The trade number generated by third-party payment sever
     * @param refundId    Order refund number generated by sever when refund request dispatched
     * @return Return whatever the third-party payment sever return
     */
    @Override
    public String refundQuery(String orderNumber, String tradeId, String refundId) {
        return null;
    }
}
