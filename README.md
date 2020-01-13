# OnlinePayment

## Overview

This project is further Java encapsulation for various online payment channels, including **Alipay**, **WeChat Pay** so far.

**ALERT!!** The code here is just a simple getting start guide with little security consideration. So **DO NOT** use this code in your production environment directly.

## Tech Support

- Java
- JDK 1.8.0
- Tomcat 9.0.16
- Spring 4.3.18

## Project Main Structure

```console
├─controller
│      PaymentController.java       # Controller example
│
├─entity
│      AlipayConfig.java            # Alipay configuration file
│      WeChatPayConfig.java         # WeChat Pay configuration file
│
├─service
│  │  IOnlinePay.java               # Payment interface
│  │
│  └─impl
│          Alipay.java              # Alipay service implement
│          WeChatPay.java           # WeChat Pay service implement
│
└─util
        PayUtil.java                # Tool methods for payment service
```

## Getting Start

### 1. Include Dependencies

- [alipay-sdk](https://docs.open.alipay.com/54/103419)
- [wxpay-sdk](https://pay.weixin.qq.com/wiki/doc/api/external/native.php?chapter=11_1)
  - [httpclient](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient)
  - [httpcore](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore)
  - [slf4j-api](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)

### 2. Initial Configuration

Config `AlipayConfig.java` and `WeChatPayConfig.java`

### 3. Initial Payment object and using relative methods 

More examples see `PaymentController.java`

```java
import com.ryougichan.payment.service.IOnlinePay;
import com.ryougichan.payment.service.impl.Alipay;
import com.ryougichan.payment.service.impl.WeChatPay;

class Program {
    public static void main(String[] args){
        // !! This code is used as a illustration, there are better ways,
        //    using IoC feature, for example.
        IOnlinePay onlinePay;
        // Initial Alipay by using:
        onlinePay = new Alipay();
        // Initial WeChat Pay by using:
        // onlinePay = new WeChatPay();

        String payWay = "PC";
        String orderNumber = String.format("100%s", System.currentTimeMillis());
        double payAmount = 0.01;

        // Call payment
        String payResult = onlinePay.pay(payWay, orderNumber, payAmount);
    }
}
```

## Developer Tips

> Something about code style and stratification

  We add Spring MVC here but not actually use it, that's because we wanna provide a more generic implementation.
  So we recommend that you should rewrite the service layer according to your needing.

> `java.lang.ClassNotFoundException: org.springframework.web.context.ContextLoaderListener`

  This is a Exception of Tomcat server.
  
  **Solution:**
  
  Add dependencies to **deployment** assembly. [See Also](https://stackoverflow.com/questions/6210757/java-lang-classnotfoundexception-org-springframework-web-context-contextloaderl)

## Declaration

All resources in this project are based on [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/), that means you can copy and reissue the contents of this project, but you will also have to provide the **original author information** as well as the **agreement statement**.

Released under the [Apache License 2.0](LICENSE)

Copyright © [RyougiChan](https://github.com/RyougiChan)
