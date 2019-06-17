package com.ryougichan.payment.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ryougichan.payment.entity.AlipayConfig;
import com.ryougichan.payment.entity.WeChatPayConfig;
import com.ryougichan.payment.service.IOnlinePay;
import com.ryougichan.payment.service.impl.Alipay;
import com.ryougichan.payment.service.impl.WeChatPay;
import com.ryougichan.payment.util.PayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    Gson gson;

    /**
     * Payment index page
     * @return Index .jsp page
     */
    @RequestMapping("/index")
    public String index() {
        return "payment/index";
    }

    /**
     * Payment request
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void pay(@RequestBody String params, HttpServletResponse response) {
        gson = PayUtil.getGson();
        JsonObject paramJson = gson.fromJson(params, JsonObject.class);
        String payType = paramJson.get("payType").getAsString();
        String payWay = paramJson.get("payWay").getAsString();
        String orderId = paramJson.get("orderId").getAsString();

        IOnlinePay onlinePay;
        if(payType.equals("alipay")) {
            onlinePay = new Alipay();
            payWay = payWay.equals("pc") ? AlipayConfig.PRODUCT_CODE_PAGE : AlipayConfig.PRODUCT_CODE_H5;
        } else {
            onlinePay = new WeChatPay();
            payWay = payWay.equals("pc") ? WeChatPayConfig.TRADE_TYPE_NATIVE : WeChatPayConfig.TRADE_TYPE_MWEB;
        }

        String payResult = onlinePay.pay(payWay, orderId, 0.01);

        if(null != payResult) {
            PrintWriter writer = null;
            try{
                response.setContentType("text/html; charset=utf-8");
                writer = response.getWriter();
                writer.print(payResult);
            }catch(IOException e) {
                // TODO: Logging
            }finally {
                if (null != writer) {
                    writer.flush();
                    writer.close();
                }
            }
        }
    }


    /**
     * Refund request
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public void refund(@RequestBody String params, HttpServletResponse response) {
        JsonObject jo = gson.fromJson(params, JsonObject.class);
        String payType = jo.get("payType").getAsString();
        String orderId = jo.get("orderId").getAsString();

        IOnlinePay onlinePay;
        if(payType.equals("alipay")) {
            onlinePay = new Alipay();
        } else {
            onlinePay = new WeChatPay();
        }

        String tradeId = null;
        Double totalAmount = 0d;
        Double refundAmount = 0d;

        String payResult = onlinePay.refund(orderId, tradeId, totalAmount, refundAmount);

        PrintWriter writer = null;
        try{
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.print(payResult);
        }catch(IOException e) {
            // TODO: Logging
        }finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * Transactions inquiry request
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public void query(@RequestBody String params, HttpServletResponse response) {
        JsonObject jo = gson.fromJson(params, JsonObject.class);
        String payType = jo.get("payType").getAsString();
        String tradeId = jo.get("tradeId").getAsString();
        String orderId = jo.get("orderId").getAsString();

        IOnlinePay onlinePay;
        if(payType.equals("alipay")) {
            onlinePay = new Alipay();
        } else {
            onlinePay = new WeChatPay();
        }
        String payResult = onlinePay.query(orderId, tradeId);

        PrintWriter writer = null;
        try{
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.print(payResult);
        }catch(IOException e) {
            // TODO: Logging
        }finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * Transactions close request
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public void close(@RequestBody String params, HttpServletResponse response) {
        JsonObject jo = gson.fromJson(params, JsonObject.class);
        String payType = jo.get("payType").getAsString();
        String tradeId = jo.get("tradeId").getAsString();
        String orderId = jo.get("orderId").getAsString();

        IOnlinePay onlinePay;
        if(payType.equals("alipay")) {
            onlinePay = new Alipay();
        } else {
            onlinePay = new WeChatPay();
        }
        String payResult = onlinePay.close(orderId, tradeId);

        PrintWriter writer = null;
        try{
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.print(payResult);
        }catch(IOException e) {
            // TODO: Logging
        }finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * Refund inquiry request
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/refundquery", method = RequestMethod.POST)
    public void refundQuery(@RequestBody String params, HttpServletResponse response) {
        JsonObject jo = gson.fromJson(params, JsonObject.class);
        String payType = jo.get("payType").getAsString();
        String tradeId = jo.get("tradeId").getAsString();
        String orderId = jo.get("orderId").getAsString();
        String refundId = jo.get("refundId").getAsString();

        IOnlinePay onlinePay;
        if(payType.equals("alipay")) {
            onlinePay = new Alipay();
        } else {
            onlinePay = new WeChatPay();
        }
        String payResult = onlinePay.refundQuery(orderId, tradeId, refundId);

        PrintWriter writer = null;
        try{
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.print(payResult);
        }catch(IOException e) {
            // TODO: Logging
        }finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * Download bill request
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/downloadbill", method = RequestMethod.POST)
    public void downloadBill(@RequestBody String params, HttpServletResponse response) {
        JsonObject jo = gson.fromJson(params, JsonObject.class);
        String payType = jo.get("payType").getAsString();
        String billType = jo.get("billType").getAsString();
        String billDate = jo.get("billDate").getAsString();

        IOnlinePay onlinePay;
        if(payType.equals("alipay")) {
            onlinePay = new Alipay();
        } else {
            onlinePay = new WeChatPay();
        }
        String result = onlinePay.downloadBill(billDate, billType);

        PrintWriter writer = null;
        try{
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.print(result);
        }catch(IOException e) {
            // TODO: Logging
        }finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * Receive payment callback from WeChat Pay server
     * @param params Request parameters, content type: application/json
     * @param request Request
     * @param response Response
     */
    @RequestMapping(value = "/wepaynotify", method = RequestMethod.POST)
    public void receiveWepayNotify(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * Receive payment callback from Alipay server
     * @param params Request parameters, content type: application/json
     * @param request Request
     * @param response Response
     */
    @RequestMapping(value = "/alipaynotify", method = RequestMethod.POST)
    public void receiveAlipayNotify(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {

    }
}
