<%@ page import="com.example.demo.model.SaleRecord" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 08.06.2025
  Time: 09:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<SaleRecord> saleRecords = (List<SaleRecord>) request.getAttribute("sales");
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>climb BRUH</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="container">
        <h2>Lista zamówień</h2>
        <br>

        <form method="get" action="">
            <label for="sort">Sort by:</label>
            <select name="sort" id="sort">
                <option value="date">Purchase Date</option>
                <option value="name">Product Name</option>
            </select>
            <button type="submit">Sort</button>
        </form>

        <br>

        <table border='1'>
            <tr><th>Client name</th><th>Client Email</th><th>Product name</th><th>Product price</th><th>OrderDate</th><th>Status</th></tr>
            <% for (SaleRecord saleRecord: saleRecords) { %>
            <tr><th> <%=saleRecord.getClientName()%> </th><th><%=saleRecord.getClientEmail()%></th><th><%= saleRecord.getProductName()%></th>
                <th><%=saleRecord.getProductPrice()%></th><th> <%=saleRecord.getOrderDate()%></th>
                <th>
                    <% if ("Completed".equals(saleRecord.getStatus())) { %>
                    Completed
                    <% } else { %>
                    <a href="<%= request.getContextPath() %>/editOrder?ID=<%= saleRecord.getOrderID() %>">
                        <%= saleRecord.getStatus() %>
                    </a>
                    <% } %>
                    </th></tr>

            <% } %>


        </table>
    </div>

</body>
</html>
