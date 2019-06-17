<%--
  Created by IntelliJ IDEA.
  User: RyougiChan
  Date: 2019/6/6
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment Test Page</title>
    <style>
        header, footer {
            margin-top: 60px;
            text-align: center;
        }
        #container {
            width: 80%;
            margin: 100px auto;
            overflow: auto;
        }
        #pay-result {
            margin-top: 60px;
            color: #ff0000;
        }
        #request-list {
            list-style: none;
        }
        #request-list #request-list-item {
            float: left;
            width: 40%;
        }
    </style>
</head>
<body>
    <header>
        <h1>Payment Test Page</h1>
    </header>
    <main id="container">
        <ul id="request-list">
            <li id="request-list-item">
                <h2>Payment Request</h2>
                <div>
                    <label for="orderId">Order ID</label>
                    <input type="text" id="orderId" name="orderId" placeholder="Order ID" value="20190806125346" />
                </div>
                <div>
                    <label for="wechat-pay">WeChat Pay</label>
                    <input type="radio" id="wechat-pay" name="payType" value="wechatPay" checked />
                    <label for="alipay">Alipay</label>
                    <input type="radio" id="alipay" name="payType" value="alipay" />
                </div>
                <div>
                    <label for="pc">QR Code Payment</label>
                    <input type="radio" id="pc" name="payWay" value="pc" checked />
                    <label for="h5">In-App Web-based Payment(H5)</label>
                    <input type="radio" id="h5" name="payWay" value="h5" /></div>
                <div>
                    <button id="pay">Pay</button></div>
                <div id="pay-result">

                </div>
            </li>
        </ul>
    </main>
    <footer>
        <p>Released under the <a href="https://www.apache.org/licenses/LICENSE-2.0.html">Apache License 2.0</a></p>
        <p>Copyright © <a href="https://github.com/RyougiChan">RyougiChan</a></p>
    </footer>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        $('#pay').on('click', function(){
            $.ajax({
                url: "/payment/payment/pay",
                type: "post",
                data: JSON.stringify({
                    payType: $('input[name="payType"]:checked').val(),
                    payWay: $('input[name="payWay"]:checked').val(),
                    orderId: $('input[name="orderId"]').val()
                }),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    console.log(data);
                    $('#pay-result').html(data);
                },
                error: function (err) {
                    console.log(err);
                    document.documentElement.innerHTML = err.responseText;
                }
            });
        });
    </script>
</body>
</html>
