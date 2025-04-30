<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>訂單歷史紀錄</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        table {
            margin-top: 15px;
        }
        fieldset {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/view/cart/menu.jsp" %>

    <div style="padding: 15px" class="pure-form">
        <fieldset>
            <legend><h2>訂單歷史紀錄</h2></legend>

            <c:forEach var="order" items="${orders}">
                <fieldset>
                    <legend>訂單編號：${order.orderId}</legend>
                    <p>訂單日期：${order.orderDate}</p>
                    <p>總金額：${order.totalAmount}</p>

                    <table class="pure-table pure-table-bordered">
                        <thead>
                            <tr>
                                <th>商品名稱</th>
                                <th>數量</th>
                                <th>價格</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${order.items}">
                                <tr onmouseover="this.style.backgroundColor='#E0E0ff'" 
                                    onmouseout="this.style.backgroundColor=''">
                                    <td>${item.productName}</td>
                                    <td align="center">${item.quantity}</td>
                                    <td align="right">${item.price}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
            </c:forEach>
        </fieldset>
    </div>
</body>
</html>