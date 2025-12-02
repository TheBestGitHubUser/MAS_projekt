<%@ page import="com.example.demo.model.Product" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: BRUH
  Date: 08.06.2025
  Time: 09:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
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
    <h2>Lista produktów</h2>
    <br>

    <button onclick="window.location.href='<%= request.getContextPath()%>/productAdd'">Add product</button>

    <table border='1'>
        <tr><th>Name</th><th>Description</th><th>Price</th><th>Stock</th><th>Category</th><th>Picture</th><th>Edit</th></tr>
        <% for (Product product: products) { %>
        <tr><th> <%=product.getName()%> </th><th><%=product.getDescription()%></th><th><%= product.getPrice()%></th>
            <th><%=product.getStock()%></th><th> <%=product.getCategory()%></th>
            <th><img src="<%=product.getImageURL()%>" class="img-table"> </th><th>
                <a href="<%=request.getContextPath()%>/productAdd?ID=<%=product.getId()%>">Edit</a> </th></tr>

        <% } %>
    </table>
</div>
</body>
</html>
