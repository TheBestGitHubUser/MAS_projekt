<%@ page import="com.example.demo.model.MyPurchase" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 07.06.2025
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<MyPurchase> myPurchase = (List<MyPurchase>) request.getAttribute("myPurchase");
%>
<html>
<%@include file="Components/head.jsp"%>
<body>
<%@ include file="Components/header.jsp"%>
<div class="container">
    <h2>Lista produktów</h2>
    <br>

    <table border='1'>
        <tr><th>Seller</th><th>Product name</th><th>Order date</th><th>Status</th><th>Picture</th><th>Review</th><th>Details</th></tr>
        <% for (MyPurchase mp: myPurchase) { %>
        <tr><th> <%=mp.getSellerName()%> </th><th><%=mp.getProductName()%></th><th><%= mp.getOrderDate()%></th>
            <th><%=mp.getStatus()%></th><th> <img src="<%=mp.getImageURL()%>" class="img-table"></th>
            <th><%if(mp.getStatus().equals("Completed")) {%><a href="<%= request.getContextPath()%>/reviewOrder?ID=<%=mp.getOrderID()%>"> Link </a><%}else {%>
            review not available<%}%></th>
            <th><a href="<%= request.getContextPath()%>/details?ID=<%=mp.getProductID()%>">Link</a> </th></tr>

        <% } %>


    </table>

</div>
</body>
</html>
