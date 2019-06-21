package com.ryougichan.payment.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
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
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    Gson gson;

    public PaymentController() {
        gson = PayUtil.getGson();
    }

    /**
     * Payment index page
     *
     * @return Index .jsp page
     */
    @RequestMapping("/index")
    public String index() {
        return "payment/index";
    }

    /**
     * Payment request
     *
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void pay(@RequestBody String params, HttpServletResponse response) {
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
                response.setContentType("text/plain; charset=utf-8");
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
     *
     * @param params Request parameters, content type: application/json
     * @param response Response
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public void refund(@RequestBody String params, HttpServletResponse response) {
        JsonObject jo = gson.fromJson(params, JsonObject.class);
        String payType = jo.get("payType").getAsString();
        String orderId = jo.get("orderId").getAsString();
        String tradeId = jo.get("tradeId").getAsString();
        Double totalAmount = jo.get("totalAmount").getAsDouble();
        Double refundAmount = jo.get("refundAmount").getAsDouble();

        IOnlinePay onlinePay;
        if(payType.equals("alipay")) {
            onlinePay = new Alipay();
        } else {
            onlinePay = new WeChatPay();
        }

        String refundResult = onlinePay.refund(orderId, tradeId, totalAmount, refundAmount);

        PrintWriter writer = null;
        try{
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.print(refundResult);
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
     *
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
     *
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
     *
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
     *
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
     *
     * @param request Request
     * @param response Response
     */
    @RequestMapping(value = "/wxpay-notify", method = RequestMethod.POST)
    public void receiveWepayNotify(HttpServletRequest request, HttpServletResponse response) {
        String result = "failure";
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            // TODO: Logging
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            try {
                int readLen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readLen == -1) {
                    break;
                }
                i += readLen;
            } catch (IOException e) {
                // TODO: Logging
            }
        }
        String charEncoding = request.getCharacterEncoding();
        String notifyData;
        try {
            notifyData = new String(buffer, null == charEncoding ? "UTF-8" : charEncoding);
            WXPay wxpay = PayUtil.getWXPay();

            Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);

            if (!wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                // TODO: Logging
                // sign error
            } else {
                // TODO: Verify order, Update order
                // !Be careful: If the order is refunded, we should never update its status to paid when notified
                result = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";

            }
        } catch (UnsupportedEncodingException e) {
            // TODO: Logging
        } catch (Exception e) {
            // TODO: Logging
        }
        PrintWriter writer = null;
        try{
            response.setContentType("text/xml; charset=utf-8");
            writer = response.getWriter();
            // We should tell WeChat Pay server that we has received its request
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
     * Receive payment callback from Alipay server
     *
     * @param request Request
     * @param response Response
     */
    @RequestMapping(value = "/alipay-notify", method = RequestMethod.POST)
    public void receiveAlipayNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }

            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }

        boolean signVerified = false;
        String result = "fail";
        try {
            AlipayConfig alipayConfig = PayUtil.getAlipayConfig();
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());

            if(signVerified) {
                String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                // Alipay trade number
                String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                String trade_status = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                    // Paid success
                    //TODO:1.verify app_id 2.verify seller_id 3.verify out_trade_no 4.update order status
                }
                result = "success";

            }else {
                // TODO: Logging
            }
        } catch (AlipayApiException e) {
            // TODO: Logging
        }
        PrintWriter writer = null;
        try{
            response.setContentType("text/plain; charset=utf-8");
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
}
