package com.ryougichan.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/payment")
public class PaymentController {

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
     * @param params Request parameters
     * @param response Response
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void pay(@RequestBody String params, HttpServletResponse response) {
        PrintWriter writer = null;
        String payResult = "This is a payment result";
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
