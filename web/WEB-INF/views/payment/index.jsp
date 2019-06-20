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
        .request-result {
            margin-top: 60px;
            color: #ff0000;
            position: fixed;
            right: 10%;
            top: 100px;
            width: 15%;
            word-break: break-all;
        }
        #request-list {
            list-style: none;
        }
        #request-list .request-list-item {
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
            <li class="request-list-item">
                <h2>Payment Request</h2>
                <div>
                    <label for="order-id">Order ID</label>
                    <input type="text" id="order-id" name="orderId" placeholder="Order ID" value="20190806125346" />
                </div>
                <div>
                    <label for="wechat-pay">WeChat Pay</label>
                    <input type="radio" id="wechat-pay" name="payType" value="wechatPay" checked />
                    <label for="alipay">Alipay</label>
                    <input type="radio" id="alipay" name="payType" value="alipay" />
                </div>
                <div>
                    <label for="pc">QR Code Payment</label>
                    <input type="radio" id="pc" name="payWay" value="pc" checked /><br>
                    <label for="h5">In-App Web-based Payment(H5)</label>
                    <input type="radio" id="h5" name="payWay" value="h5" /></div>
                <div>
                    <button id="pay">Pay</button></div>
            </li>
            <li class="request-list-item">
                <h2>Refund Request</h2>
                <div>
                    <label for="refund-order-id">refund-order-id</label>
                    <input type="text" id="refund-order-id" name="refund-order-id" value="20190806125346" /><br>
                    <label for="refund-trade-id">refund-trade-id</label>
                    <input type="text" id="refund-trade-id" name="refund-trade-id" value="" /><br>
                    <label for="refund-total-amount">refund-total-amount</label>
                    <input type="text" id="refund-total-amount" name="refund-total-amount" value="0.01" /><br>
                    <label for="refund-refund-amount">refund-refund-amount</label>
                    <input type="text" id="refund-refund-amount" name="refund-refund-amount" value="0.01" />
                </div>
                <div>
                    <label for="refund-wechat-pay">WeChat Pay</label>
                    <input type="radio" id="refund-wechat-pay" name="refund-pay-type" value="wechatPay" checked />
                    <label for="refund-alipay">Alipay</label>
                    <input type="radio" id="refund-alipay" name="refund-pay-type" value="alipay" />
                </div>
                    <button id="refund">Refund</button>
                </div>
            </li>
            <li class="request-list-item">
                <h2>Query Request</h2>
                <div>
                    <label for="query-order-id">refund-order-id</label>
                    <input type="text" id="query-order-id" name="query-order-id" value="20190806125346" /><br>
                    <label for="query-trade-id">query-trade-id</label>
                    <input type="text" id="query-trade-id" name="query-trade-id" value="" />
                </div>
                <div>
                    <label for="query-wechat-pay">WeChat Pay</label>
                    <input type="radio" id="query-wechat-pay" name="query-pay-type" value="wechatPay" checked />
                    <label for="query-alipay">Alipay</label>
                    <input type="radio" id="query-alipay" name="query-pay-type" value="alipay" />
                </div>
                <button id="query">Query</button>
                </div>
            </li>
            <li class="request-list-item">
                <h2>Close Request</h2>
                <div>
                    <label for="close-order-id">refund-order-id</label>
                    <input type="text" id="close-order-id" name="close-order-id" value="20190806125346" /><br>
                    <label for="close-trade-id">close-trade-id</label>
                    <input type="text" id="close-trade-id" name="close-trade-id" value="" /><br>
                </div>
                <div>
                    <label for="close-wechat-pay">WeChat Pay</label>
                    <input type="radio" id="close-wechat-pay" name="close-pay-type" value="wechatPay" checked />
                    <label for="close-alipay">Alipay</label>
                    <input type="radio" id="close-alipay" name="close-pay-type" value="alipay" />
                </div>
                <button id="close">Close</button>
                </div>
            </li>
            <li class="request-list-item">
                <h2>Refund Query Request</h2>
                <div>
                    <label for="refund-query-order-id">refund-query-order-id</label>
                    <input type="text" id="refund-query-order-id" name="refund-query-order-id" value="20190806125346" /><br>
                    <label for="refund-query-refund-id">refund-query-refund-id</label>
                    <input type="text" id="refund-query-refund-id" name="refund-query-refund-id" value="" /><br>
                    <label for="refund-query-trade-id">refund-query-trade-id</label>
                    <input type="text" id="refund-query-trade-id" name="refund-query-trade-id" value="" />
                </div>
                <div>
                    <label for="refund-query-wechat-pay">WeChat Pay</label>
                    <input type="radio" id="refund-query-wechat-pay" name="refund-query-pay-type" value="wechatPay" checked />
                    <label for="refund-query-alipay">Alipay</label>
                    <input type="radio" id="refund-query-alipay" name="refund-query-pay-type" value="alipay" />
                </div>
                <button id="refund-query">Refund</button>
                </div>
            </li>
            <li class="request-list-item">
                <h2>Download Bill Request</h2>
                <div>
                    <label for="download-bill-bill-date">download-bill-bill-date: WeChat Pay(yyyyMMdd), Alipay(yyyy-MM/yyyy-MM-dd)</label><br>
                    <input type="text" id="download-bill-bill-date" name="download-bill-bill-date" value="20190806" /><br>
                    <label for="download-bill-bill-type">download-bill-bill-type</label>
                    <select id="download-bill-bill-type" name="download-bill-bill-type">
                        <optgroup label="wepay-bill-type">
                            <option>ALL</option>
                            <option>SUCCESS</option>
                            <option>REFUND</option>
                            <option>RECHARGE_REFUND</option>
                        </optgroup>
                        <optgroup label="alipay-bill-type">
                            <option>trade</option>
                            <option>signcustomer</option>
                        </optgroup>
                    </select>
                </div>
                <div>
                    <label for="download-bill-wechat-pay">WeChat Pay</label>
                    <input type="radio" id="download-bill-wechat-pay" name="download-bill-pay-type" value="wechatPay" checked />
                    <label for="download-bill-alipay">Alipay</label>
                    <input type="radio" id="download-bill-alipay" name="download-bill-pay-type" value="alipay" />
                </div>
                <button id="download-bill">download bill</button>
                </div>
            </li>
        </ul>
        <div class="request-result">

        </div>
    </main>
    <footer>
        <p>Released under the <a href="https://www.apache.org/licenses/LICENSE-2.0.html">Apache License 2.0</a></p>
        <p>Copyright © <a href="https://github.com/RyougiChan">RyougiChan</a></p>
    </footer>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
    <script type="text/javascript">
        /*
         * Pay Request
         */
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
                    $('.request-result').text(JSON.stringify(data));
                    $(document.body).append(data);
                },
                error: function (err) {
                    console.log(err);
                    document.documentElement.innerHTML = err.responseText;
                }
            });
        });

        /*
         * Refund Request
         */
        $('#refund').on('click', function () {
            $.ajax({
                url: "/payment/payment/refund",
                type: "post",
                data: JSON.stringify({
                    payType: $('input[name="refund-pay-type"]:checked').val(),
                    tradeId: $('input[name="refund-trade-id"]').val(),
                    orderId: $('input[name="refund-order-id"]').val(),
                    totalAmount: $('input[name="refund-total-amount"]').val(),
                    refundAmount: $('input[name="refund-refund-amount"]').val()
                }),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    console.log(data);
                    $('.request-result').html(JSON.stringify(data));
                },
                error: function (err) {
                    console.log(err);
                    document.documentElement.innerHTML = err.responseText;
                }
            });
        });

        /*
         * Query Request
         */
        $('#query').on('click', function () {
            $.ajax({
                url: "/payment/payment/query",
                type: "post",
                data: JSON.stringify({
                    payType: $('input[name="query-pay-type"]:checked').val(),
                    tradeId: $('input[name="query-trade-id"]').val(),
                    orderId: $('input[name="query-order-id"]').val()
                }),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    console.log(data);
                    $('.request-result').html(JSON.stringify(data));
                },
                error: function (err) {
                    console.log(err);
                    document.documentElement.innerHTML = err.responseText;
                }
            });
        });

        /*
         * Close Request
         */
        $('#close').on('click', function () {
             $.ajax({
                url: "/payment/payment/close",
                type: "post",
                data: JSON.stringify({
                    payType: $('input[name="close-pay-type"]:checked').val(),
                    tradeId: $('input[name="close-trade-id"]').val(),
                    orderId: $('input[name="close-order-id"]').val()
                }),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    console.log(data);
                    $('.request-result').html(JSON.stringify(data));
                },
                error: function (err) {
                    console.log(err);
                    document.documentElement.innerHTML = err.responseText;
                }
            });
        });

        /*
         * Query Request
         */
        $('#refund-query').on('click', function () {
            $.ajax({
                url: "/payment/payment/refundquery",
                type: "post",
                data: JSON.stringify({
                    payType: $('input[name="refund-query-pay-type"]:checked').val(),
                    refundId: $('input[name="refund-query-refund-id"]').val(),
                    tradeId: $('input[name="refund-query-trade-id"]').val(),
                    orderId: $('input[name="refund-query-order-id"]').val()
                }),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    console.log(data);
                    $('.request-result').html(JSON.stringify(data));
                },
                error: function (err) {
                    console.log(err);
                    document.documentElement.innerHTML = err.responseText;
                }
            });
        });

        /*
         * Download Bill Request
         */
        $('#download-bill').on('click', function () {
            $.ajax({
                url: "/payment/payment/downloadbill",
                type: "post",
                data: JSON.stringify({
                    payType: $('input[name="download-bill-pay-type"]:checked').val(),
                    billDate: $('input[name="download-bill-bill-date"]').val(),
                    billType: $('select[name="download-bill-bill-type"]').val()
                }),
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    console.log(data);
                    $('.request-result').html(JSON.stringify(data));
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
