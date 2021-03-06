package com.ryougichan.payment.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Online Payment Abstract Interface
 */
public interface IOnlinePay {

    /**
     * Dispatch payment request to third-party payment server
     *
     * @param payWay      Way of payment, including `QR Code Payment`, `In-App Web-based Payment(H5 Payment)` and etc.
     * @param orderNumber Order number generated by server
     * @param payAmount   Amount Paid
     * @return Return whatever the third-party payment server return
     */
    String pay(String payWay, String orderNumber, double payAmount);

    /**
     * Receive payment callback from third-party payment server if supported
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell third-party payment server that we have received this notify callback
     * @deprecated The method defined here is for example only because there are specific business logic included in usual
     */
    String receivePaymentNotify(HttpServletRequest request);

    /**
     * Dispatch refund request to third-party payment server
     *
     * @param orderNumber  Order number generated by server
     * @param tradeId      The trade number generated by third-party payment server
     * @param totalAmount  The total trade amount of the order to refund
     * @param refundAmount The amount of the refund(Partial refund supported if possible)
     * @return Return whatever the third-party payment server return
     */
    String refund(String orderNumber,String tradeId, double totalAmount,double refundAmount);

    /**
     * Receive refund callback from third-party payment server if supported
     *
     * @param request The javax.servlet.http.HttpServletRequest object itself
     * @return A notify message to tell third-party payment server that we have received this notify callback
     * @deprecated The method defined here is for example only because there are specific business logic included in usual
     */
    String receiveRefundNotify(HttpServletRequest request);


    /**
     * Dispatch transactions inquiry request to third-party payment server
     *
     * @param orderNumber Order number generated by server
     * @param tradeId     The trade number generated by third-party payment server
     * @return Return whatever the third-party payment server return
     */
    String query(String orderNumber, String tradeId);

    /**
     * Dispatch transactions close request to third-party payment server
     *
     * @param orderNumber Order number generated by server
     * @param tradeId     The trade number generated by third-party payment server
     * @return Return whatever the third-party payment server return
     */
    String close(String orderNumber, String tradeId);

    /**
     * Dispatch refund inquiry request to third-party payment server
     *
     * @param orderNumber Order number generated by server
     * @param tradeId     The trade number generated by third-party payment server
     * @param refundId    Order refund number generated by server when refund request dispatched
     * @return Return whatever the third-party payment server return
     */
    String refundQuery(String orderNumber, String tradeId, String refundId);

    /**
     * Dispatch download bill request to access historical transaction list from third-party payment server
     * @param billDate Bill time
     * @param billType Bill type
     * @return Return whatever the third-party payment server return
     */
    String downloadBill(String billDate, String billType);
}
