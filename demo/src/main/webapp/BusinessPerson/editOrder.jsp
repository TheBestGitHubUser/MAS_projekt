<%@ page import="com.example.demo.model.SaleRecord" %><%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 08.06.2025
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  SaleRecord saleRecord = (SaleRecord) request.getAttribute("sale");
%>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Seller site</title>
  <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="container">

      <form action="" method="post">
        <input type="hidden" name="orderId" value="<%= saleRecord.getOrderID() %>">
        <tr>
          <td><%= saleRecord.getClientName() %></td>
          <td><%= saleRecord.getClientEmail() %></td>
          <td><%= saleRecord.getProductName() %></td>
          <td><%= saleRecord.getProductPrice() %></td>

          <td>
            <input type="date" name="orderDate" value="<%= saleRecord.getOrderDate() %>">
          </td>

          <td>
            <select name="status">
              <option value="Pending" <%= "Pending".equals(saleRecord.getStatus()) ? "selected" : "" %>>Pending</option>
              <option value="In progress" <%= "In progress".equals(saleRecord.getStatus()) ? "selected" : "" %>>In Progress</option>
              <option value="Completed" <%= "Completed".equals(saleRecord.getStatus()) ? "selected" : "" %>>Completed</option>
            </select>
          </td>

          <td><button type="submit">Save</button></td>
        </tr>
      </form>

    </div>


</body>
</html>
